package tv.guanghe.datadev.s3c.util;

import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.google.gson.FieldNamingPolicy;
import com.sun.org.apache.bcel.internal.generic.NEW;

import tv.guanghe.datadev.s3c.bean.Doc;
import tv.guanghe.datadev.s3c.dao.SysDao;
import tv.guanghe.datadev.s3c.dao.impl.SysDaoImpl;
import tv.guanghe.datadev.s3c.global.SystemConfigProperties;
import tv.guanghe.datadev.s3c.service.DocService;
import tv.guanghe.datadev.s3c.service.impl.DocServiceImpl;

public class DocSearchUtil {
	public static final DocService docService = new DocServiceImpl();
	public static final String INDEX_PATH = "lucene_index"; // 存放Lucene索引文件的位置
	private static ReadWriteLock rwLock = new ReentrantReadWriteLock();
	private static String lastModifiedTime;
	private static SysDao sysDao = new SysDaoImpl();
	
	public static class BuildIndexRunner implements Runnable{
		
		private ReadWriteLock rwLock;

		public BuildIndexRunner(ReadWriteLock rwLock){
			this.rwLock = rwLock;
		}

		@Override
		public void run() {
			rwLock.writeLock().lock();
			IndexWriter indexWriter = null;
			try
			{
				Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));
				//Analyzer analyzer = new StandardAnalyzer();
				Analyzer analyzer = new IKAnalyzer(true);
				IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
				indexWriter = new IndexWriter(directory, indexWriterConfig);
				indexWriter.deleteAll();// 清除以前的index
				
				// 得到所有的文档
				List<Doc> docs = docService.getAllDocs();
				
				for(Doc doc : docs){
					if(doc.getTags() == null){
						doc.setTags("");
					}
					Document document = new Document();
					document.add(new Field("id", doc.getId()+"", TextField.TYPE_STORED));
					document.add(new Field("title", doc.getTitle(), TextField.TYPE_STORED));
					document.add(new Field("content", doc.getContent(), TextField.TYPE_STORED));
					document.add(new Field("tag", doc.getTags(), TextField.TYPE_STORED));
					document.add(new Field("url", doc.getUrl(), TextField.TYPE_STORED));
					indexWriter.addDocument(document);
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(indexWriter != null) indexWriter.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			rwLock.writeLock().unlock();
			updateIndexLastModifiedTime();
		}
	}
	
	public static String getIndexLastModifiedTime(){
		if(lastModifiedTime != null){
			lastModifiedTime = sysDao.getProperty(SystemConfigProperties.INDEX_LAST_MODIFIED_TIME);
			if(lastModifiedTime != null){
				return lastModifiedTime;
			}
			
		}
		updateIndexLastModifiedTime();
		
		return lastModifiedTime;
	}
	
	public static void updateIndexLastModifiedTime(){
		lastModifiedTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		sysDao.setProperty(SystemConfigProperties.INDEX_LAST_MODIFIED_TIME,lastModifiedTime);
	}
	
	/**
	 * 创建索引
	 */
	public static void rebuildIndex()
	{
		new Thread(new BuildIndexRunner(rwLock)).start();
	}
	
	/**
	 * 搜索
	 */
	public static List<Doc> search(String keyWord)
	{
		List<Doc> docs = new ArrayList<Doc>();
		
		DirectoryReader directoryReader = null;
		rwLock.readLock().lock();
		try
		{
			Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));

			directoryReader = DirectoryReader.open(directory);

			IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

			Analyzer analyzer = new IKAnalyzer(true); // 使用IK分词
			
			String[] fields = {"title", "content", "tag"};
			BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
			MultiFieldQueryParser multiParser1=new MultiFieldQueryParser(fields,analyzer);
			multiParser1.setAllowLeadingWildcard(true);
			Query multiFieldQuery = multiParser1.parse("*"+keyWord+"*");
			
			

			TopDocs topDocs = indexSearcher.search(multiFieldQuery, 10000); // 搜索前10000条结果
			
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			
			
			QueryScorer scorer = new QueryScorer(multiFieldQuery, "content");
			SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<b>", "</b>");
			Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
			highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
			for (ScoreDoc scoreDoc : scoreDocs)
			{
				if(scoreDoc.score > 0.005){
					Doc doc = new Doc();
					Document document = indexSearcher.doc(scoreDoc.doc);
					String content = document.get("content");
					doc.setContent(highlighter.getBestFragment(analyzer, "content", content));
					doc.setTitle(document.get("title"));
					doc.setUrl(document.get("url"));
					int id = 0;
					try {
						id = Integer.parseInt(document.get("id"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					doc.setId(id);
					docs.add(doc);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(directoryReader != null) directoryReader.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		rwLock.readLock().unlock();
		return docs;
	}


}
