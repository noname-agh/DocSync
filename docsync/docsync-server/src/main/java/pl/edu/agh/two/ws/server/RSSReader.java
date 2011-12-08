package pl.edu.agh.two.ws.server;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.agh.two.ws.RSSItem;
import de.nava.informa.core.ChannelIF;
import de.nava.informa.core.ItemIF;
import de.nava.informa.impl.basic.ChannelBuilder;
import de.nava.informa.parsers.FeedParser;

public class RSSReader {
	private static final Logger log = LoggerFactory.getLogger(RSSReader.class);

	private static RSSReader instance;
	private EntityManagerFactory emf;

	private RSSReader() {
		this.emf = Persistence.createEntityManagerFactory("serverUnit");
		// example rss feeds at startup
		//addChannel("http://xkcd.com/rss.xml");
		//addChannel("http://planet.lisp.org/rss20.xml");
	}

	public RSSReader(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public static RSSReader getInstance() {
		if (instance == null) {
			instance = new RSSReader();
		}
		return instance;
	}

	public void updateAll() {
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
			e.printStackTrace();
		}
		log.info("RSS Channel updated - " + chanellAddress);
	}

	public void addChannel(String address) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		RSSChannel channel = new RSSChannel();
		channel.setAddress(address);
		em.persist(channel);
		em.getTransaction().commit();
		log.info("RSS Channel added - " + address);
	}
	
	public void removeChannel(String address) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		RSSChannel channel = em.find(RSSChannel.class, address);
		if (channel != null) {
			em.remove(channel);
		}
		em.getTransaction().commit();
		log.info("RSS Channel removed - " + address);
	}
	
	public List<String> getRssChannelList() {
		List<String> subscriptionsList = null;
		try {
			EntityManager em = emf.createEntityManager();
			subscriptionsList = em.createQuery("select c.address from RSSChannel c", String.class).getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.info("Query for RSS Channel list performed");
		return subscriptionsList;
	}
	
	public List<RSSItem> getRSSItems() {
		List<RSSItem> rssItemList = null;
		try {
			EntityManager em = emf.createEntityManager();
			rssItemList = em.createQuery("select r from RSSItem as r where r.readed = false", RSSItem.class)
					.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.debug("returning " + rssItemList.size() + " items");
		return rssItemList;
	}
	
	public void updateRSSItem(RSSItem item) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(item);
		em.getTransaction().commit();
		em.close();
		log.info("RSS Item updated - " + item.getTitle());
	}
}