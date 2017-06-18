package tv.guanghe.datadev.s3c.service;

public interface SysDao {

	void setProperty(String string, String lastModifiedTime);

	String getProperty(String indexLastModifiedTime);

}
