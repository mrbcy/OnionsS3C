package tv.guanghe.datadev.s3c.bean;

public class DealResult {
	private boolean isSuccess = true;
	private String errorDesc = "";
	
	public DealResult(){}
	
	public DealResult(boolean isSuccess, String errorDesc) {
		super();
		this.isSuccess = isSuccess;
		this.errorDesc = errorDesc;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
	
}
