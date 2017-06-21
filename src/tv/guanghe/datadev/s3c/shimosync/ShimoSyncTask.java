package tv.guanghe.datadev.s3c.shimosync;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.TimerTask;


import tv.guanghe.datadev.s3c.dao.SysDao;
import tv.guanghe.datadev.s3c.dao.impl.SysDaoImpl;
import tv.guanghe.datadev.s3c.global.SCProp;

public class ShimoSyncTask extends TimerTask{
	private static SysDao sysDao = new SysDaoImpl();
	
    @Override
    public void run() {
        try {
             // 获得Python文件所在路径
        	File rootFile = new File(ShimoSyncTask.class.getClassLoader().getResource("/").getPath());
        	File pyFile = new File(rootFile, "shimo_crawer" + File.separator + "get_file.py");
            System.out.println("Python文件路径：" + pyFile.getAbsolutePath());
            
            // 凑参数
            String[] args = {sysDao.getProperty(SCProp.SHIMO_USER),sysDao.getProperty(SCProp.SHIMO_PWD),
            		"localhost","root","sorry",
            		"http://localhost:8080/OnionsS3C/PublicServlet?op=rebuildIndex&token=5649497f8ded4b6e626a2c15"};
            runTimeMethod(pyFile.getAbsolutePath(), args);
        } catch (Exception e) {
            System.out.println("-------------解析信息发生异常--------------");
            e.printStackTrace();
        }
    }
    
    public void runTimeMethod(String filePath,String[] args) {
    	try {
    		String cmd="python3 "+filePath+" "+join(args, " ");
    		System.out.println(cmd);
    		Process pr = Runtime.getRuntime().exec(cmd);
    		BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
    		String line="";
    		//得到打印在控制台上的结果
    		while ((line = in.readLine()) != null) {
    			System.out.println(line);
    		}
    		in.close();
    		
    		BufferedReader errIn = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
    		//得到错误输出打印在控制台上的结果
    		while ((line = errIn.readLine()) != null) {
    			System.out.println(line);
    		}
    		
    		errIn.close();
    		pr.waitFor();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private  String join(String[] strs,String splitter) {
        StringBuffer sb = new StringBuffer();
        for(String s:strs){
            sb.append(s+splitter);
        }
        return sb.toString().substring(0, sb.toString().length()-1);
    }
}
