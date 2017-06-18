package tv.guanghe.datadev.s3c.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import tv.guanghe.datadev.s3c.bean.Doc;
import tv.guanghe.datadev.s3c.dao.DocDao;
import tv.guanghe.datadev.s3c.util.DBCPUtil;
import tv.guanghe.datadev.s3c.util.DocSearchUtil;

public class DocDaoImpl implements DocDao {
	
	QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	
	@Override
	public List<Doc> getAllDocs() {
		try {
			return qr.query("select * from docs",
					new BeanListHandler<Doc>(Doc.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Doc> getSearchResult(String keyword) {
		return DocSearchUtil.search(keyword);
	}

	@Override
	public int getDocNums() {
		try {
			Long num = (Long) qr.query("select count(*) from docs", new ScalarHandler(1));
			return num.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Doc> getDocsByPage(int startIndex, int pageSize) {
		try {
			List<Doc> docs = qr.query("select * from docs order by id desc limit ?,?", 
										new BeanListHandler<Doc>(Doc.class),startIndex,pageSize);

			if(docs == null || docs.size() == 0){
				return null;
			}
			return docs;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Doc getDocById(int docId) {
		try {
			return qr.query("select * from docs where id = ?",
					new BeanHandler<Doc>(Doc.class),docId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addDoc(Doc doc) {
		try {
			qr.update("insert into docs(title,content,url,tags) values(?,?,?,?)",doc.getTitle(),
					doc.getContent(),doc.getUrl(),doc.getTags());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void editDoc(int docId, Doc doc) {
		try {
			qr.update("update docs set title = ?,content = ?,url = ?,tags = ? where id = ?",doc.getTitle(),
					doc.getContent(),doc.getUrl(),doc.getTags(),docId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
