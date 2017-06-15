package tv.guanghe.datadev.s3c.commons;



import java.util.List;

public class Page {
	private List records;   // 数据集
	private int curPageNum; // 当前页
	private int totalRecordsNum; // 数据总数
	
	private int totalPage;  // 总页数
	private int startIndex; // 当前页数据开始索引
	private int pageSize;   // 一页显示的记录条数
	
	private int nextPageNum; // 下一页
	private int prevPageNum; // 上一页
	
	private String urlPattern; // 数据请求地址ַ
	
	public Page(int curPageNum, int totalRecordsNum) {
		super();
		init(curPageNum, totalRecordsNum, 10);
	}

	public Page(int curPageNum, int totalRecordsNum, int pageSize) {
		super();
		init(curPageNum, totalRecordsNum, pageSize);
	}
	
	private void init(int curPageNum, int totalRecordsNum, int pageSize){
		this.curPageNum = curPageNum;
		this.totalRecordsNum = totalRecordsNum;
		this.pageSize = pageSize;
		
		this.startIndex = (curPageNum -1) * pageSize;
		this.totalPage = totalRecordsNum % pageSize == 0?(totalRecordsNum / pageSize):(totalRecordsNum / pageSize + 1);
	}

	public List getRecords() {
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
	}

	public int getCurPageNum() {
		return curPageNum;
	}

	public void setCurPageNum(int curPageNum) {
		this.curPageNum = curPageNum;
	}

	public int getTotalRecordsNum() {
		return totalRecordsNum;
	}

	public void setTotalRecordsNum(int totalRecordsNum) {
		this.totalRecordsNum = totalRecordsNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNextPageNum() {
		nextPageNum = curPageNum + 1;
		if(nextPageNum > totalPage){
			nextPageNum = totalPage;
		}
		return nextPageNum;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public int getPrevPageNum() {
		prevPageNum = curPageNum - 1;
		if(prevPageNum < 1){
			prevPageNum = 1;
		}
		return prevPageNum;
	}

	public void setPrevPageNum(int prevPageNum) {
		this.prevPageNum = prevPageNum;
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	
	
}
