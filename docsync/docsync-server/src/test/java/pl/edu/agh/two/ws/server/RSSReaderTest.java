package pl.edu.agh.two.ws.server;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.agh.two.ws.RSSItem;
import pl.edu.agh.two.ws.dao.RSSChannelDAO;
import pl.edu.agh.two.ws.dao.RSSItemDAO;

/**
 * Tests RSS reader.
 * <p/>
 * Creation date: 2012.01.05
 *
 * @author Tomasz Zdybał
 */

public class RSSReaderTest {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(RSSReaderTest.class);
	private static final String CHIP_URL = "http://www.chip.pl/RSS";
	private static final String ARCH_URL = "http://www.archlinux.org/feeds/news/";
	private RSSReader rssReader;
	private RSSItemDAO rssItemDAO;

	@BeforeMethod
	public void setUp() throws Exception {
		final RSSChannelDAO rssChannelDAO = Mockito.mock(RSSChannelDAO.class);
		rssItemDAO = Mockito.mock(RSSItemDAO.class);

		rssReader = new RSSReader(rssChannelDAO, rssItemDAO);
	}

	@Test
	public void testUpdateChip() throws Exception {
		log.debug("Test RSS bug - check chip.pl RSS (previously not working)");

		rssReader.update(CHIP_URL);

		Mockito.verify(rssItemDAO, Mockito.atLeastOnce()).updateRSSItem(Mockito.any(RSSItem.class));
	}

	@Test
	public void testUpdateArch() throws Exception {
		log.debug("Test RSS bug - check archlinux.org RSS (previously working - regression test)");

		rssReader.update(ARCH_URL);

		Mockito.verify(rssItemDAO, Mockito.atLeastOnce()).updateRSSItem(Mockito.any(RSSItem.class));
	}
}
