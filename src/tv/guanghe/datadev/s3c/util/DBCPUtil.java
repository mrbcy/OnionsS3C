package tv.guanghe.datadev.s3c.util;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBCPUtil {
	private static DataSource dataSource;
	public static String dbUser;
	public static String dbPwd;
	public static String dbConnStr;
	static{
		try {

			InputStream in = DBCPUtil.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			Properties props = new Properties();
			props.load(in);
			dbUser = props.getProperty("username");
			dbPwd = props.getProperty("password");
			dbConnStr = props.getProperty("url");
			dataSource = BasicDataSourceFactory.createDataSource(props);
		}  catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
		
	}
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	public static Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("获取数据库连接失败");
		}
	}
	
}
