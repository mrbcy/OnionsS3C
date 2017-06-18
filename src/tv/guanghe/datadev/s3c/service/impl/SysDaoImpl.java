package tv.guanghe.datadev.s3c.service.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import tv.guanghe.datadev.s3c.service.SysDao;
import tv.guanghe.datadev.s3c.util.DBCPUtil;

public class SysDaoImpl implements SysDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	
	@Override
	public void setProperty(String key, String value) {
		try {
			if(getProperty(key) != null){
				qr.update("update sys_configs set s_value=? where s_key=?",value,key);
			}
			qr.update("insert into sys_configs(s_key,s_value) values(?,?)",key,value);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public String getProperty(String key) {
		try {
			String value = (String) qr.query("select s_value from sys_configs where s_key = ?", new ScalarHandler(1),key);
			return value;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
