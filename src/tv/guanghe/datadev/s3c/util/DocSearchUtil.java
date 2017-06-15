package tv.guanghe.datadev.s3c.util;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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
import org.wltea.analyzer.lucene.IKAnalyzer;

import tv.guanghe.datadev.s3c.bean.Doc;
import tv.guanghe.datadev.s3c.service.DocService;
import tv.guanghe.datadev.s3c.service.impl.DocServiceImpl;

public class DocSearchUtil {
	public static final DocService docService = new DocServiceImpl();
	public static final String INDEX_PATH = "E:\\lucene_index"; // 存放Lucene索引文件的位置
	
	/**
	 * 创建索引
	 */
	public static void creatIndex()
	{
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
	}
	
	/**
	 * 搜索
	 */
	public static List<Doc> search(String keyWord)
	{
		List<Doc> docs = new ArrayList<Doc>();
		
		DirectoryReader directoryReader = null;
		try
		{
			Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));

			directoryReader = DirectoryReader.open(directory);

			IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

			Analyzer analyzer = new IKAnalyzer(true); // 使用IK分词
			
			String[] fields = {"title", "content", "tag"};
			BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
			Query multiFieldQuery = MultiFieldQueryParser.parse(keyWord, fields, clauses, analyzer);
			

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
		return docs;
	}


}
