package pl.edu.agh.two.ws.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.testng.Assert.assertEquals;

@org.testng.annotations.Test
public class RssChannelsServerTest {

	public void testAddChannel() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testServerUnit");
		RSSReader c = new RSSReader(emf);

		String address = "bleble";
		c.addChannel(address);

		List<String> channels = c.getRssChannelList();

		assertEquals(channels.size(), 1);
		assertEquals(channels.get(0), address);
	}

	public void testRemoveChannel() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testServerUnit");
		RSSReader c = new RSSReader(emf);

		String address = "bleble";
		c.addChannel(address);

		List<String> channels = c.getRssChannelList();

		assertEquals(channels.size(), 1);
		assertEquals(channels.get(0), address);

		c.removeChannel(address);

		channels = c.getRssChannelList();

		assertEquals(channels.size(), 0);
	}
}
