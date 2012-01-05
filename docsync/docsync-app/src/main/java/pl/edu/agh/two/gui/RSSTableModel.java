package pl.edu.agh.two.gui;

import pl.edu.agh.two.interfaces.IRSSList;
import pl.edu.agh.two.log.ILogger;
import pl.edu.agh.two.log.LoggerFactory;
import pl.edu.agh.two.rss.RSSService;
import pl.edu.agh.two.utils.RSSItemFilterUtil;
import pl.edu.agh.two.ws.RSSItem;

import javax.swing.table.AbstractTableModel;
import java.util.*;

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
	private static final ILogger LOGGER = LoggerFactory.getLogger(RSSTableModel.class, DocSyncGUI.getFrame());
	private static final String TITLE_CAPTION = "Title";
	private static final String LINK_CAPTION = "Link";
	private static final String DESCRIPTION_CAPTION = "Description";
	private static final String CHANNEL = "Channel";
	private static final String READ = "Read";
	private static final String[] COLUMN_NAMES =
			new String[]{READ, TITLE_CAPTION, LINK_CAPTION, DESCRIPTION_CAPTION, CHANNEL};

	private List<RSSItem> items = new ArrayList<RSSItem>();

	public List<RSSItem> getItems() {
		return items;
	}

	public void setItems(List<RSSItem> items) {
		this.items = items;
	}

	public RSSTableModel() {
		RSSItem item = new RSSItem();
		item.setTitle("Title");
		item.setLink("http://www.archlinux.org/feeds/packages/");
		item.setDescription("description");
		items.add(item);

		RSSItem item2 = new RSSItem();
		item2.setTitle("Title");
		item2.setLink("link");
		items.add(item2);
	}

	@Override
	public int getRowCount() {
		return getFilteredItems().size();
	}

	public List<RSSItem> getFilteredItems() {
		List<RSSItem> filteredItems = new LinkedList<RSSItem>();
		for (RSSItem item : items) {
			if (item.getIsShown()) {
				filteredItems.add(item);
			}
		}
		return filteredItems;
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

	public void getList() {
		LOGGER.info("Getting RSS items from server", false);

		// clear list
		items.clear();

		// get messages
		RSSService service = RSSService.getInstance();
		items.addAll(service.getAllRSSItems());

		// sort
		sortItems(items);

		RSSItemFilterUtil.doFilter();
		// refresh
		DocSyncGUI.refreshRSSList();
	}

	@Override
	public RSSItem getItem(int i) {
		return getFilteredItems().get(i);
	}

	@Override
	public void addItems(List<RSSItem> items) {
		this.items.clear();
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
			if (rssItem.getDate() != null && rssItem1.getDate() != null) {
				return rssItem.getDate().compareTo(rssItem1.getDate());
			} else {
				return -1;
			}
		}
	}
}
