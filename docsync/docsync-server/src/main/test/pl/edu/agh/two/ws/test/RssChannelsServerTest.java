package pl.edu.agh.two.ws.test;

import java.util.List;

import junit.framework.TestCase;
import pl.edu.agh.two.ws.CloudStorage;
import pl.edu.agh.two.ws.server.CloudStorageImpl;

public class RssChannelsServerTest extends TestCase {

	public void testAddChannel() {
		CloudStorage c = new CloudStorageImpl();
		
		String address = "bleble";
		c.addChannel(address);
		
		List<String> channels = c.getRssChannelList();
		
		assertEquals(channels.size(), 1);
		assertEquals(channels.get(0), address);
	}

	public void testRemoveChannel() {
		CloudStorage c = new CloudStorageImpl();
		
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
