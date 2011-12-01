package pl.edu.agh.two.rss;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.file.FileService;
import pl.edu.agh.two.utils.ConfigReader;
import pl.edu.agh.two.ws.CloudStorage;
import pl.edu.agh.two.ws.RSSItem;


public class RSSService {
	private static final Logger log = LoggerFactory.getLogger(RSSService.class);
	
	public static final String wsUrl = ConfigReader.getInstance().getProperty("ws.url");
	public static final String wsNamespace = ConfigReader.getInstance().getProperty("ws.ns");
	public static final String wsName = ConfigReader.getInstance().getProperty("ws.name");
	
	private static CloudStorage cloud;
	private static RSSService rssService;
	
	private RSSService() {
		Service service = null;
		try {
			service = Service.create(new URL(wsUrl), new QName(wsNamespace, wsName));
		} catch (MalformedURLException e) {
			log.error("Can not create rss service.", e);
		}
		cloud = service.getPort(CloudStorage.class);
	}
	
	public static RSSService getInstance() {
		if (rssService==null) rssService = new RSSService();
		return rssService;
	}
	
	public void addChannel(String address) {
		cloud.addChannel(address);
	}
	
	public void removeChannel(String address) {
		cloud.removeChannel(address);
	}
	
	public List<String> getRssChannelList() {
		return cloud.getRssChannelList();
	}
	
	public List<RSSItem> refreshAndGetRssItems() {
		return cloud.refreshAndGetRssItems();
	}
}
