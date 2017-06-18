package tv.guanghe.datadev.s3c.bean;

public class Doc {
	public static final String DOC_TYPE_INTERNAL = "internal";
	public static final String DOC_TYPE_EXTERNAL = "external";
	
	private int id;
	private String title;
	private String content;
	private String url;
	private String tags;
	private String type;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
