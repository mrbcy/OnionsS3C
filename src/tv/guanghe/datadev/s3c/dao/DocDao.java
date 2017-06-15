package tv.guanghe.datadev.s3c.dao;

import java.util.List;

import tv.guanghe.datadev.s3c.bean.Doc;

public interface DocDao {

	/**
	 * 获得所有的文档信息
	 * @return 没有文档信息返回null
	 */
	List<Doc> getAllDocs();

}
