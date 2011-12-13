package pl.edu.agh.two.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.interfaces.IRSSList;
import pl.edu.agh.two.rss.RSSService;
import pl.edu.agh.two.ws.RSSItem;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.12.01
 *
 * @author Tomasz Zdybał
 */
public class RSSTableModel extends AbstractTableModel implements IRSSList {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(RSSTableModel.class);
	private static final String TITLE_CAPTION = "Title";
	private static final String LINK_CAPTION = "Link";
	private static final String DESCRIPTION_CAPTION = "Description";
	private static final String CHANNEL = "Channel";
	private static final String READ = "Read";
	private static final String[] COLUMN_NAMES =
			new String[]{READ, TITLE_CAPTION, LINK_CAPTION, DESCRIPTION_CAPTION, CHANNEL};

	private List<RSSItem> items = new ArrayList<RSSItem>();

	@Override
	public int getRowCount() {
		return items.size();
	}

	@Override
	public Class<?> getColumnClass(int i) {
		if (i == 0) {
			return Boolean.class;
		} else {
			return super.getColumnClass(i);
		}
	}

	@Override
	public int getColumnCount() {
		// tytuł, link, opis (jeśli jest), kanał
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int i, int j) {
		RSSItem item = getItem(i);

		if (j == 0) {
			return item.getReaded();
		} else if (j == 1) {
			return item.getTitle();
		} else if (j == 2) {
			return item.getLink();
		} else if (j == 3) {
			if (item.getDescription() != null) {
				return item.getDescription();
			} else {
				return "No description";
			}
		} else if (j == 4) {
			return item.getChannelAddress();
		} else {
			return null;
		}
	}

	@Override
	public String getColumnName(int i) {
		return COLUMN_NAMES[i];
	}

	@Override
	public RSSItem getItem(int i) {
		return items.get(i);
	}

	@Override
	public void addItems(List<RSSItem> items) {
		this.items.addAll(items);
		sortItems(this.items);
	}

	private void sortItems(List<RSSItem> items) {
		Collections.sort(items, new RSSItemComparator());
	}

	@Override
	public List<RSSItem> getRSSItemList() {
		return items;
	}

	private static class RSSItemComparator implements Comparator<RSSItem> {
		@Override
		public int compare(RSSItem rssItem, RSSItem rssItem1) {
			if (rssItem.getDate() != null) {
				return rssItem.getDate().compareTo(rssItem1.getDate());
			} else {
				return -1;
			}
		}
	}
}
