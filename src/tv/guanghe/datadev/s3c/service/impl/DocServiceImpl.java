package tv.guanghe.datadev.s3c.service.impl;

import java.util.List;

import tv.guanghe.datadev.s3c.bean.Doc;
import tv.guanghe.datadev.s3c.commons.Page;
import tv.guanghe.datadev.s3c.dao.DocDao;
import tv.guanghe.datadev.s3c.dao.impl.DocDaoImpl;
import tv.guanghe.datadev.s3c.service.DocService;

public class DocServiceImpl implements DocService{
	
	private DocDao docDao = new DocDaoImpl();
	
	@Override
	public List<Doc> getAllDocs() {
		return docDao.getAllDocs();
	}

	@Override
	public Page searchDocsByPage(String keyword, String num) {
		int pageNum = 1;
		if(num != null && num.trim().length() > 0){
			pageNum = Integer.parseInt(num);
		}
		
		List<Doc> docs = docDao.getSearchResult(keyword);
		Page page = new Page(pageNum, docs.size());
		List<Doc> records = null;
		int startIndex = page.getStartIndex();
		int endIndex = page.getPageSize() + page.getStartIndex() > docs.size()?docs.size():page.getPageSize() + page.getStartIndex();
		try {
			records = docs.subList(startIndex, endIndex);
		} catch (Exception e) {}
		page.setRecords(records);
		return page;
	}

}
