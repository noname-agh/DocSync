package pl.edu.agh.two.ws.server.schedule;

import java.text.ParseException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobScheduler implements ServletContextListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobScheduler.class);
	private static final String CRON_SCHEDULE = "0 0/5 * * * ?";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			Scheduler scheduler = getQuartzFactory(sce.getServletContext()).getScheduler();
			JobDetail job =  JobBuilder.newJob(RefreshRSSJob.class).build();
			CronTrigger trigger = TriggerBuilder.newTrigger()
					.withSchedule(CronScheduleBuilder.cronSchedule(CRON_SCHEDULE))
					.build();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException ex) {
			LOGGER.error("Error initializing scheduler", ex);
		} catch (ParseException ex) {
			LOGGER.error("Invalid cron schedule expression", ex);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
	private StdSchedulerFactory getQuartzFactory(ServletContext ctx) {
		return (StdSchedulerFactory) ctx.getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);
	}
	
}
