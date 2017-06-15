package tv.guanghe.datadev.s3c.service;

import java.util.List;

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
}
