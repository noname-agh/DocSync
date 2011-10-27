package pl.edu.agh.two.ws.test;

import junit.framework.TestCase;
import pl.edu.agh.two.ws.CloudStorage;
import pl.edu.agh.two.ws.server.CloudStorageImpl;

public class RssChannelsServerTest extends TestCase {

	public void testAddChannel() {
		CloudStorage c = new CloudStorageImpl();
		
		c.addChannel("bleble");
		
		
	}

	public void testRemoveChannel() {
		fail("Not yet implemented");
	}

}
