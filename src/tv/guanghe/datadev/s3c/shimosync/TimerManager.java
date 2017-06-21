package tv.guanghe.datadev.s3c.shimosync;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimerManager {
	//时间间隔
    private static final long PERIOD_HOUR = 60 * 60 * 1000;
    
    public TimerManager() {
         Calendar calendar = Calendar.getInstance(); 
                
         /*** 定制每日09:00-19:00每隔一小时执行方法 ***/

         calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 0);
         calendar.set(Calendar.MINUTE, 0);
         calendar.set(Calendar.SECOND, 0);
          
         Date date=calendar.getTime(); //第一次执行定时任务的时间
         System.out.println(date);
         System.out.println("before 方法比较："+date.before(new Date()));
         //如果第一次执行定时任务的时间 小于 当前的时间
         //此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。循环执行的周期则以当前时间为准
         
         Timer timer = new Timer();
          
         ShimoSyncTask task = new ShimoSyncTask();

         timer.schedule(task,date,PERIOD_HOUR);
        }
}
