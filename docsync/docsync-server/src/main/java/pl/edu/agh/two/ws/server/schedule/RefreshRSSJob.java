package pl.edu.agh.two.ws.server.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.ws.server.CloudStorageImpl;

public class RefreshRSSJob implements Job {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RefreshRSSJob.class);
	
	@Override
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		LOGGER.debug("Refreshing RSS");
		new CloudStorageImpl().refreshAndGetRssItems();
		LOGGER.debug("Refreshed RSS");
	}
}
