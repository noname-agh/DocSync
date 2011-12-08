package pl.edu.agh.two.gui.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.interfaces.IRSSList;
import pl.edu.agh.two.rss.RSSService;
import pl.edu.agh.two.ws.RSSItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OpenRSSAction extends MouseAdapter {
	private static final Logger log = LoggerFactory.getLogger(OpenRSSAction.class);
	Desktop desktop;

	public OpenRSSAction() {
		try {
			desktop = Desktop.getDesktop();
		} catch (UnsupportedOperationException ex) {
			log.warn("Desktop extension not supported!");
			desktop = null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			log.debug("Opening RSS Item");

			JTable jTable = (JTable) e.getSource();
			IRSSList rssList = (IRSSList) jTable.getModel();

			RSSItem rssItem = null;
			int row = jTable.getSelectedRow();
			if (row != -1) {
				rssItem = rssList.getItem(row);
			}

			try {
				if (rssItem != null && desktop != null) {
					desktop.browse(new URI(rssItem.getLink()));
					rssItem.setReaded(true);
					RSSService.getInstance().updateRSSItem(rssItem);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
		}
	}
}
