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

	/**
	 * 得到文档的总数
	 * @return 文档总数
	 */
	int getDocNums();

	/**
	 * 按照分页的方式得到文档数据
	 * @param startIndex 开始index
	 * @param pageSize 
	 * @return 
	 */
	List<Doc> getDocsByPage(int startIndex, int pageSize);

	/**
	 * 根据id获得文档
	 * @param docId 文档id
	 * @return 没有对应的id返回null
	 */
	Doc getDocById(int docId);

	/**
	 * 添加文档
	 * @param doc 文档对象
	 */
	void addDoc(Doc doc);

	/**
	 * 编辑文档
	 * @param id 文档id
	 * @param doc 文档对象
	 */
	void editDoc(int id, Doc doc);

}
