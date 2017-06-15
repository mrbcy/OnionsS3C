package tv.guanghe.datadev.s3c.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tv.guanghe.datadev.s3c.bean.Doc;
import tv.guanghe.datadev.s3c.dao.DocDao;
import tv.guanghe.datadev.s3c.util.DBCPUtil;

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

}
