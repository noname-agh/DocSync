package pl.edu.agh.two.rss;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.log.ILogger;
import pl.edu.agh.two.log.LoggerFactory;
import pl.edu.agh.two.utils.ConfigReader;
import pl.edu.agh.two.utils.WebServiceProxy;
import pl.edu.agh.two.ws.CloudStorage;
import pl.edu.agh.two.ws.RSSItem;


public class RSSService {
	private static final ILogger LOGGER = LoggerFactory.getLogger(RSSService.class, DocSyncGUI.getFrame());

	public static final String wsUrl = ConfigReader.getInstance().getProperty("ws.url");
	public static final String wsNamespace = ConfigReader.getInstance().getProperty("ws.ns");
	public static final String wsName = ConfigReader.getInstance().getProperty("ws.name");

	private static CloudStorage cloud;
	private static RSSService rssService;

	private RSSService() {
		try {
			cloud = WebServiceProxy.create(new URL(wsUrl), new QName(wsNamespace, wsName), CloudStorage.class);
		} catch (Exception e) {
			LOGGER.error("Cannot create rss service.", e, true);
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
			LOGGER.error("Cannot add RSSChanel.", ex, true);
		}
	}

	public void removeChannel(String address) {
		try {
			cloud.removeChannel(address);
		} catch (Exception ex) {
			LOGGER.error("Cannot remove RSSChanel.", ex, true);
		}
	}

	public List<String> getRssChannelList() {
		List<String> list = new LinkedList<String>();
		try {
			list = cloud.getRssChannelList();
		} catch (Exception ex) {
			LOGGER.error("Cannot fetch RSS chanels.", ex, true);
		}
		return list;
	}

	public List<RSSItem> refreshAndGetRssItems() {
		List<RSSItem> list = new LinkedList<RSSItem>();
		try {
			cloud.updateAll();
			list = cloud.getRSSItems();
		} catch (Exception ex) {
			LOGGER.error("Cannot fetch updated RSS messages.", ex, true);
		}
		return list;
	}

	public void updateRSSItem(RSSItem rssItem) {
		try {
			cloud.updateRSSItem(rssItem);
		} catch (Exception ex) {
			LOGGER.error("Cannot update RSS messages.", ex, true);
		}
	}

	public List<RSSItem> getAllRSSItems() {
		List<RSSItem> list = new LinkedList<RSSItem>();
		try {
			list = cloud.getRSSItems();
		} catch (Exception ex) {
			LOGGER.error("Cannot fetch RSS messages !!.", ex, true);
		}
		return list;
	}
}
