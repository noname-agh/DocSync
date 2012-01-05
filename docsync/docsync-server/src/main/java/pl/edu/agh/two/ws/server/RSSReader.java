package pl.edu.agh.two.ws.server;

import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.agh.two.ws.RSSItem;
import pl.edu.agh.two.ws.dao.RSSChannelDAO;
import pl.edu.agh.two.ws.dao.RSSItemDAO;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RSSReader {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RSSReader.class);

	private RSSReader() {
		// example rss feeds at startup
		// addChannel("http://xkcd.com/rss.xml");
		// addChannel("http://planet.lisp.org/rss20.xml");
	}

	private RSSChannelDAO rssChannelDAO;
	private RSSItemDAO rssItemDAO;

	public RSSReader(RSSChannelDAO rssChannelDAO, RSSItemDAO rssItemDAO) {
		this.rssChannelDAO = rssChannelDAO;
		this.rssItemDAO = rssItemDAO;
	}

	public void updateAll() {
		LOGGER.debug("Updating all rss channels.");
		for (String channelAddress : getRssChannelList()) {
			update(channelAddress);
		}
	}

	public void update(String chanellAddress) {
		try {
			URL url = new URL(chanellAddress);

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(url));

			Collection<SyndEntry> items = feed.getEntries();

			for (SyndEntry item : items) {
				RSSItem dbItem = new RSSItem();
				dbItem.setGuid(item.getLink()); // .getLocation() returns <guid> tag value
				dbItem.setTitle(item.getTitle());
				dbItem.setChannelAddress(url.toString());
				dbItem.setDate(item.getUpdatedDate()); // or .getPublishedDate() ?
				dbItem.setDescription(item.getDescription().getValue());
				dbItem.setLink(item.getLink().toString());

				dbItem.setReaded(false);

				updateRSSItem(dbItem);
			}
		} catch (Exception e) {
			LOGGER.error(String.format("Error when updating channel %s",
					chanellAddress), e);
		}
		LOGGER.info("RSS Channel updated - " + chanellAddress);
	}

	public void addChannel(String address) {
		rssChannelDAO.addRSSChannel(new RSSChannel(address));
		LOGGER.info("RSS Channel added - " + address);
	}

	public void removeChannel(String address) {
		rssChannelDAO.removeRSSChannel(new RSSChannel(address));
		LOGGER.info("RSS Channel removed - " + address);
	}

	public List<String> getRssChannelList() {
		List<RSSChannel> channels = rssChannelDAO.getRSSChannels();
		List<String> addresses = new LinkedList<String>();
		for (RSSChannel channel : channels) {
			addresses.add(channel.getAddress());
		}
		LOGGER.info("Query for RSS Channel list performed");
		return addresses;
	}

	public List<RSSItem> getRSSItems() {
		List<RSSItem> rssItemList = rssItemDAO.getRSSItems();
		LOGGER.debug("returning " + rssItemList.size() + " items");
		return rssItemList;
	}

	public void updateRSSItem(RSSItem item) {
		RSSItem existingItem = rssItemDAO.findRSSItem(item.getGuid());
		if (existingItem != null) {
			item.setReaded(existingItem.getReaded() || item.getReaded());
		}
		rssItemDAO.updateRSSItem(item);
		LOGGER.info("RSS Item updated - " + item.getTitle());
	}
}