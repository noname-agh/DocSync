package pl.edu.agh.two.ws.server;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import pl.edu.agh.two.ws.RSSItem;

import de.nava.informa.core.ChannelIF;
import de.nava.informa.core.ItemIF;
import de.nava.informa.impl.basic.ChannelBuilder;
import de.nava.informa.parsers.FeedParser;

public class RSSReader {

	static void test() {
		try {
			URL url = new URL("http://planet.lisp.org/rss20.xml");
			ChannelIF channel = FeedParser.parse(new ChannelBuilder(), url);

			System.out.println("Channel: " + channel.getTitle());
			System.out.println("Description: " + channel.getDescription());
			System.out.println("PubDate: " + channel.getPubDate());
			Collection items = channel.getItems();
			for (Iterator i = items.iterator(); i.hasNext();) {
				ItemIF item = (ItemIF) i.next();
				
				RSSItem dbItem = new RSSItem();
				dbItem.setChannelAddress(url.toString());
				dbItem.setDate(item.getDate());
				dbItem.setDescription(item.getDescription());
				dbItem.setGuid(item.getGuid().toString());			
				dbItem.setLink(item.getLink().toString());
				dbItem.setReaded(false);
				
				
				/*
				System.out.println(item.getTitle());
				System.out.println("\t" + item.getDescription());
				System.out.println("Categories: " + item.getCategories());
				System.out.println("Date: " + item.getDate());
				System.out.println("Link: " + item.getLink() + "\n");
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
