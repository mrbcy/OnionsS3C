package tv.guanghe.datadev.s3c.service;

import java.util.List;

import tv.guanghe.datadev.s3c.bean.Doc;

public interface DocService {
	/**
	 * 获取所有的文档信息
	 * @return 没有文档信息返回null
	 */
	public List<Doc> getAllDocs();
}
