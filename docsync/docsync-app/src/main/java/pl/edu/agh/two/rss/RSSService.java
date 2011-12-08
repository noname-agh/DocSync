package pl.edu.agh.two.rss;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.agh.two.gui.DocSyncGUI;
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
			cloud = service.getPort(CloudStorage.class);
		} catch (Exception e) {
			log.error("Cannot create rss service.", e);
		}
	}

	public static RSSService getInstance() {
		if (rssService == null) {
			rssService = new RSSService();
		}
		return rssService;
	}

	public void addChannel(String address) {
		try {
			cloud.addChannel(address);
		} catch (Exception ex) {
			log.error("Cannot add RSSChanel.", ex);
			DocSyncGUI.error("Cannot add RSSChanel.");
		}
	}

	public void removeChannel(String address) {
		try {
			cloud.removeChannel(address);
		} catch (Exception ex) {
			log.error("Cannot remove RSSChanel.", ex);
			DocSyncGUI.error("Cannot remove RSSChanel.");
		}
	}

	public List<String> getRssChannelList() {
		List<String> list = new LinkedList<String>();
		try {
			list = cloud.getRssChannelList();
		} catch (Exception ex) {
			log.error("Cannot fetch RSS chanels.", ex);
			DocSyncGUI.error("Cannot fetch RSS chanels.");
		}
		return list;
	}

	public List<RSSItem> refreshAndGetRssItems() {
		List<RSSItem> list = new LinkedList<RSSItem>();
		try {
			cloud.updateAll();
			list = cloud.getRSSItems();
		} catch (Exception ex) {
			log.error("Cannot fetch updated RSS messages.", ex);
			DocSyncGUI.error("Cannot fetch updated RSS messages.");
		}
		return list;
	}

	public void updateRSSItem(RSSItem rssItem) {
		try {
			cloud.updateRSSItem(rssItem);
		} catch (Exception ex) {
			log.error("Cannot update RSS messages.", ex);
			DocSyncGUI.error("Cannot update RSS messages.");
		}
	}

	public List<RSSItem> getAllRSSItems() {
		List<RSSItem> list = new LinkedList<RSSItem>();
		try {
			list = cloud.getRSSItems();
		} catch (Exception ex) {
			log.error("Cannot fetch RSS messages !!.", ex);
			DocSyncGUI.error("Cannot fetch RSS messages !!.");
		}
		return list;
	}
}
