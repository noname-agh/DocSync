package pl.edu.agh.two.ws.dao;

import java.util.List;
import pl.edu.agh.two.ws.server.RSSChannel;

public interface RSSChannelDAO {

	public void addRSSChannel(final RSSChannel channel);

	public void removeRSSChannel(final RSSChannel channel);

	public RSSChannel findRSSChannel(final String url);

	public List<RSSChannel> getRSSChannels();
}
