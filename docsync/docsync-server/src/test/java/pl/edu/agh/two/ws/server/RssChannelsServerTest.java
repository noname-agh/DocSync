package pl.edu.agh.two.ws.server;

import org.mockito.ArgumentCaptor;
import pl.edu.agh.two.ws.dao.RSSChannelDAO;
import pl.edu.agh.two.ws.dao.RSSItemDAO;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.assertEquals;
import static org.mockito.Mockito.*;

@org.testng.annotations.Test
public class RssChannelsServerTest {
	
	private RSSReader reader;
	private RSSChannelDAO rssChannelDAO;
	private RSSItemDAO rssItemDAO;
	
	@BeforeMethod
	public void setUp() {
		rssChannelDAO = mock(RSSChannelDAO.class);
		rssItemDAO = mock(RSSItemDAO.class);
		reader = new RSSReader(rssChannelDAO, rssItemDAO);
	}

	public void testAddChannel() {
		String address = "bleble";
		reader.addChannel(address);
		
		ArgumentCaptor<RSSChannel> channel = ArgumentCaptor.forClass(RSSChannel.class);
		verify(rssChannelDAO).addRSSChannel(channel.capture());
		assertEquals(address, channel.getValue().getAddress());
	}

	public void testRemoveChannel() {
		String address = "bleble";
		reader.removeChannel(address);
		ArgumentCaptor<RSSChannel> channel = ArgumentCaptor.forClass(RSSChannel.class);
		verify(rssChannelDAO).removeRSSChannel(channel.capture());
		assertEquals(address, channel.getValue().getAddress());
	}
}
