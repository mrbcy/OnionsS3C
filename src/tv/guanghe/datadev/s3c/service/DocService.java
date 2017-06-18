package tv.guanghe.datadev.s3c.service;

import java.util.List;

import tv.guanghe.datadev.s3c.bean.DealResult;
import tv.guanghe.datadev.s3c.bean.Doc;
import tv.guanghe.datadev.s3c.commons.Page;

public interface DocService {
	/**
	 * 获取所有的文档信息
	 * @return 没有文档信息返回null
	 */
	public List<Doc> getAllDocs();
	
	/**
	 * 按照分页方式搜索文档
	 * @param keyword 关键字
	 * @param pageNum 页码
	 * @return 分页数据对象
	 */
	public Page searchDocsByPage(String keyword,String pageNum);
	
	/**
	 * 按照分页方式获得所有的文档
	 * @param pageNum 页码
	 * @return 分页的文档列表
	 */
	public Page getDocsByPage(String pageNum);

	/**
	 * 通过id查找文档
	 * @param id docId
	 * @return id不存在返回null
	 */
	public Doc getDocById(String id);

	/**
	 * 添加文档，同时更新搜索索引
	 * @param doc 文档对象
	 * @return 处理结果
	 */
	public DealResult addDoc(Doc doc);

	/**
	 * 编辑文档，同时更新搜索索引
	 * @param docId 文档id
	 * @param doc 文档对象
	 * @return 处理结果
	 */
	public DealResult editDoc(String docId, Doc doc);
}
