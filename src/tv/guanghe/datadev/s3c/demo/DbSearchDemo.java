package tv.guanghe.datadev.s3c.demo;

import java.nio.file.FileSystems;
import java.sql.ResultSet;
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


/**
 * 基于Lucene5.5.4的数据库搜索demo
 * @author liuxianan
 */
public class DbSearchDemo
{
	public static final DocService docService = new DocServiceImpl();
	public static final String INDEX_PATH = "E:\\lucene_index"; // 存放Lucene索引文件的位置
	
	/**
	 * 创建索引
	 */
	public void creatIndex()
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
	public void search(String keyWord)
	{
		DirectoryReader directoryReader = null;
		try
		{
			// 1、创建Directory
			Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));
			// 2、创建IndexReader
			directoryReader = DirectoryReader.open(directory);
			// 3、根据IndexReader创建IndexSearch
			IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
			// 4、创建搜索的Query
			// Analyzer analyzer = new StandardAnalyzer();
			Analyzer analyzer = new IKAnalyzer(true); // 使用IK分词
			
			// 简单的查询，创建Query表示搜索域为content包含keyWord的文档
			//Query query = new QueryParser("content", analyzer).parse(keyWord);
			
			String[] fields = {"title", "content", "tag"};
			// MUST 表示and，MUST_NOT 表示not ，SHOULD表示or
			BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
			// MultiFieldQueryParser表示多个域解析， 同时可以解析含空格的字符串，如果我们搜索"上海 中国" 
			Query multiFieldQuery = MultiFieldQueryParser.parse(keyWord, fields, clauses, analyzer);
			
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs topDocs = indexSearcher.search(multiFieldQuery, 100); // 搜索前100条结果
			System.out.println("共找到匹配处：" + topDocs.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			System.out.println("共找到匹配文档数：" + scoreDocs.length);
			
			QueryScorer scorer = new QueryScorer(multiFieldQuery, "content");
			SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span style=\"backgroud:red\">", "</span>");
			Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
			highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
			for (ScoreDoc scoreDoc : scoreDocs)
			{
				if(scoreDoc.score > 0.005){
					// 7、根据searcher和ScoreDoc对象获取具体的Document对象
					Document document = indexSearcher.doc(scoreDoc.doc);
					String content = document.get("content");
					System.out.println("-----------------------------------------");
					System.out.println("文章标题："+document.get("title"));
					System.out.println("文章地址：" + document.get("url"));
					System.out.println("搜索得分：" + scoreDoc.score);
					System.out.println("文章内容：");
					System.out.println(highlighter.getBestFragment(analyzer, "content", content));
					System.out.println("");
					// 8、根据Document对象获取需要的值
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
	}

	public static void main(String args[])
	{
		DbSearchDemo demo = new DbSearchDemo();
		demo.creatIndex();
		demo.search("小杰");
	}
}