package pl.edu.agh.two.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.table.TableModel;

import pl.edu.agh.two.gui.RSSTableModel;
import pl.edu.agh.two.rss.RSSService;
import pl.edu.agh.two.ws.RSSItem;

public class RSSRefreshAction implements ActionListener {

	private RSSTableModel rssModel;
	
	public RSSRefreshAction(TableModel tableModel){
		rssModel = (RSSTableModel)tableModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		RSSService rssService = RSSService.getInstance();
		List<RSSItem> items = rssService.refreshAndGetRssItems();
		rssModel.addItems(items);
	}

}