package pl.edu.agh.two.gui.actions;

import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.gui.RSSTableModel;
import pl.edu.agh.two.rss.RSSService;
import pl.edu.agh.two.ws.RSSItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RSSRefreshAction implements ActionListener {

	private RSSTableModel rssModel;
    private static final Logger log = LoggerFactory.getLogger(RSSRefreshAction.class);

	public RSSRefreshAction(TableModel tableModel) {
		rssModel = (RSSTableModel) tableModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        SwingWorker<List<RSSItem>, Void> worker = new SwingWorker<List<RSSItem>, Void>() {
            @Override
            public List<RSSItem> doInBackground() {
                RSSService rssService = RSSService.getInstance();
                List<RSSItem> items = rssService.refreshAndGetRssItems();

                rssModel.addItems(items);

                return items;
            }

            @Override
            public void done() {
                // refresh
                log.info("Refreshing");
                DocSyncGUI.refreshRSSList();
            }
        };
         worker.execute();
	}

}
