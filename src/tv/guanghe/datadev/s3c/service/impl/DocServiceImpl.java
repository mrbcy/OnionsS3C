package tv.guanghe.datadev.s3c.service.impl;

import java.util.List;

import tv.guanghe.datadev.s3c.bean.DealResult;
import tv.guanghe.datadev.s3c.bean.Doc;
import tv.guanghe.datadev.s3c.commons.Page;
import tv.guanghe.datadev.s3c.dao.DocDao;
import tv.guanghe.datadev.s3c.dao.impl.DocDaoImpl;
import tv.guanghe.datadev.s3c.service.DocService;
import tv.guanghe.datadev.s3c.util.DocSearchUtil;

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

	@Override
	public Page getDocsByPage(String num) {
		// 得到当前页码
		int pageNum = 1;
		try{
			if(num != null){
				pageNum = Integer.parseInt(num);
			}
		}catch(Exception e){
			
		}
		if(pageNum < 1){
			pageNum = 1;
		}
		// 得到总记录条数
		int recordsNum = docDao.getDocNums();
		
		Page page = new Page(pageNum,recordsNum);
		
		// 得到文档数据
		List<Doc> docs = docDao.getDocsByPage(page.getStartIndex(),page.getPageSize());
		
		page.setRecords(docs);
		return page;
	}

	@Override
	public Doc getDocById(String id) {
		// 得到id
		int docId = 1;
		try{
			if(id != null){
				docId = Integer.parseInt(id);
			}else{
				return null;
			}
		}catch(Exception e){
			
		}
		return docDao.getDocById(docId);
	}

	@Override
	public DealResult addDoc(Doc doc) {
		if(doc == null){
			return new DealResult(false, "未传入文档对象");
		}
		try {
			docDao.addDoc(doc);
		} catch (Exception e) {
			e.printStackTrace();
			return new DealResult(false, "添加过程中出现错误，请稍后重试...");
		}
		DocSearchUtil.rebuildIndex();
		return new DealResult();
	}

	@Override
	public DealResult editDoc(String docId, Doc doc) {
		if(docId == null || docId.trim().length() == 0){
			return new DealResult(false, "未传入文档id");
		}
		if(doc == null){
			return new DealResult(false, "未传入文档对象");
		}
		try {
			int id = Integer.parseInt(docId);
			docDao.editDoc(id,doc);
		} catch (Exception e) {
			e.printStackTrace();
			return new DealResult(false, "编辑文档过程中出现错误，请稍后重试...");
		}
		DocSearchUtil.rebuildIndex();
		return new DealResult();
	}

	@Override
	public DealResult deleteDoc(String docId) {
		if(docId == null || docId.trim().length() == 0){
			return new DealResult(false, "未传入文档id");
		}
		try {
			int id = Integer.parseInt(docId);
			docDao.deleteDoc(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new DealResult(false, "删除文档过程中出现错误，请稍后重试...");
		}
		DocSearchUtil.rebuildIndex();
		return new DealResult();
	}

}
