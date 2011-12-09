package pl.edu.agh.two.gui.actions;

import java.awt.event.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.interfaces.IRSSList;
import pl.edu.agh.two.rss.RSSService;
import pl.edu.agh.two.ws.RSSItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URI;
import pl.edu.agh.two.gui.DocSyncGUI;

public class OpenRSSAction extends MouseKeyAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenRSSAction.class);
	private Desktop desktop;

	public OpenRSSAction() {
		try {
			desktop = Desktop.getDesktop();
		} catch (UnsupportedOperationException ex) {
			LOGGER.warn("Desktop extension not supported!");
			desktop = null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			JTable jTable = (JTable) e.getSource();
			openSelected(jTable);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			JTable jTable = (JTable) e.getSource();
			openSelected(jTable);
		}
	}

	private void openSelected(JTable jTable) {
		LOGGER.debug("Opening RSS Item");
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
		} catch (Exception e1) {
			final String errorMsg = "Error opening rss item.";
			LOGGER.error(errorMsg, e1);
			DocSyncGUI.error(errorMsg);
		}
	}
}
