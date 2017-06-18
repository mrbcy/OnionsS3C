package tv.guanghe.datadev.s3c.dao;

public interface SysDao {

	void setProperty(String string, String lastModifiedTime);

	String getProperty(String indexLastModifiedTime);

}
