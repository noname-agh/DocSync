package pl.edu.agh.two.ws.dao;

import java.util.List;
import pl.edu.agh.two.ws.RSSItem;

public interface RSSItemDAO {

	List<RSSItem> getRSSItems();

	RSSItem findRSSItem(String guid);
	
	void updateRSSItem(RSSItem rssItem);
}
