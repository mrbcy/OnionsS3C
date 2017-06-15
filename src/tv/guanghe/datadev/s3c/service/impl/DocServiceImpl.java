package tv.guanghe.datadev.s3c.service.impl;

import java.util.List;

import tv.guanghe.datadev.s3c.bean.Doc;
import tv.guanghe.datadev.s3c.dao.DocDao;
import tv.guanghe.datadev.s3c.dao.impl.DocDaoImpl;
import tv.guanghe.datadev.s3c.service.DocService;

public class DocServiceImpl implements DocService{
	
	private DocDao docDao = new DocDaoImpl();
	
	@Override
	public List<Doc> getAllDocs() {
		return docDao.getAllDocs();
	}

}
