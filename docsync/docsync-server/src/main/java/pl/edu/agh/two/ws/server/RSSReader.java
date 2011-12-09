package pl.edu.agh.two.ws.server;

import java.net.URL;
import java.util.Collection;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.agh.two.ws.RSSItem;
import de.nava.informa.core.ChannelIF;
import de.nava.informa.core.ItemIF;
import de.nava.informa.impl.basic.ChannelBuilder;
import de.nava.informa.parsers.FeedParser;
import java.util.LinkedList;
import pl.edu.agh.two.ws.dao.RSSChannelDAO;
import pl.edu.agh.two.ws.dao.RSSItemDAO;

public class RSSReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(RSSReader.class);

	private RSSReader() {
		// example rss feeds at startup
		//addChannel("http://xkcd.com/rss.xml");
		//addChannel("http://planet.lisp.org/rss20.xml");
	}

	private RSSChannelDAO rssChannelDAO;
	private RSSItemDAO rssItemDAO;

	public RSSReader(RSSChannelDAO rssChannelDAO, RSSItemDAO rssItemDAO) {
		this.rssChannelDAO = rssChannelDAO;
		this.rssItemDAO = rssItemDAO;
	}
	
	public void updateAll() {
		LOGGER.debug("Updating all rss channels.");
		for(String channelAddress : getRssChannelList() ) {
			update(channelAddress);
		}
	}
	
	public void update(String chanellAddress) {
		try {		
			URL url = new URL(chanellAddress);
			ChannelIF channel = FeedParser.parse(new ChannelBuilder(), url);
			Collection<ItemIF> items = channel.getItems();
			//TODO: download only new items if possible

			for(ItemIF item : items) {
				RSSItem dbItem = new RSSItem();
				dbItem.setGuid(item.getGuid().getLocation());  // .getLocation() returns <guid> tag value
				dbItem.setTitle(item.getTitle());
				dbItem.setChannelAddress(url.toString());
				dbItem.setDate(item.getDate());
				dbItem.setDescription(item.getDescription());
				dbItem.setLink(item.getLink().toString());

				dbItem.setReaded(false);

				updateRSSItem(dbItem);
			}
		} catch (Exception e) {
			LOGGER.error(String.format("Error when updating channel %s", chanellAddress), e);
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