package tv.guanghe.datadev.s3c.dao;

import java.util.List;

import tv.guanghe.datadev.s3c.bean.Doc;

public interface DocDao {

	/**
	 * 获得所有的文档信息
	 * @return 没有文档信息返回null
	 */
	List<Doc> getAllDocs();

	/**
	 * 根据关键字进行文档的搜索
	 * @param keyword 关键字
	 * @return 没有匹配的文档则返回空的list
	 */
	List<Doc> getSearchResult(String keyword);

}
