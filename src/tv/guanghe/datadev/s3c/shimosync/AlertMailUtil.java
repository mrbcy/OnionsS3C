package tv.guanghe.datadev.s3c.shimosync;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringEscapeUtils;


public class AlertMailUtil {
	private static int failCount = 0;
	
	public static void reset(){
		failCount = 0;
	}
	
	public static boolean shouldContinue(){
		return failCount < 2;
	}
	
	public static void increaseFail(){
		failCount++;
		if(failCount == 2){ // 只发送一次报警邮件
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						// 获得Python文件所在路径
			        	File rootFile;
						rootFile = new File(AlertMailUtil.class.getResource("/").toURI().getPath());
						File pyFile = new File(rootFile, "shimo_crawer" + File.separator + "send_fail_email.py");
			        	String[] args = {"【S3C告警】同步石墨文档失败","我们已经累计失败3次了，请尽快处理"};
			        	ShimoSyncTask.runTimeMethod(pyFile.getAbsolutePath(), args);
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}).start();
			
		}
	}
}
