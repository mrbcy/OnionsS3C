package tv.guanghe.datadev.s3c.shimosync;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ShimoSyncListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
//		new TimerManager();
	}


}
