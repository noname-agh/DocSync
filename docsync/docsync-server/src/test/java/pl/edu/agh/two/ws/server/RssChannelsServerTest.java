package pl.edu.agh.two.ws.server;

import org.mockito.ArgumentCaptor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.agh.two.ws.dao.RSSChannelDAO;
import pl.edu.agh.two.ws.dao.RSSItemDAO;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

public class RssChannelsServerTest {

	private RSSReader reader;
	private RSSChannelDAO rssChannelDAO;

	@BeforeMethod
	public void setUp() {
		rssChannelDAO = mock(RSSChannelDAO.class);
		RSSItemDAO rssItemDAO = mock(RSSItemDAO.class);
		reader = new RSSReader(rssChannelDAO, rssItemDAO);
	}

	@Test
	public void testAddChannel() {
		String address = "bleble";
		reader.addChannel(address);

		ArgumentCaptor<RSSChannel> channel = ArgumentCaptor.forClass(RSSChannel.class);
		verify(rssChannelDAO).addRSSChannel(channel.capture());
		assertEquals(address, channel.getValue().getAddress());
	}

	@Test
	public void testRemoveChannel() {
		String address = "bleble";
		reader.removeChannel(address);
		ArgumentCaptor<RSSChannel> channel = ArgumentCaptor.forClass(RSSChannel.class);
		verify(rssChannelDAO).removeRSSChannel(channel.capture());
		assertEquals(address, channel.getValue().getAddress());
	}
}
