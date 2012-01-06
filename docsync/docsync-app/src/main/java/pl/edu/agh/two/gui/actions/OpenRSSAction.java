package pl.edu.agh.two.gui.actions;

import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.interfaces.IRSSList;
import pl.edu.agh.two.log.ILogger;
import pl.edu.agh.two.log.LoggerFactory;
import pl.edu.agh.two.rss.RSSService;
import pl.edu.agh.two.ws.RSSItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.URI;

public class OpenRSSAction extends MouseKeyAdapter {

	private static final ILogger LOGGER = LoggerFactory.getLogger(OpenRSSAction.class, DocSyncGUI.getFrame());
	private Desktop desktop;

	public OpenRSSAction() {
		try {
			desktop = Desktop.getDesktop();
		} catch (UnsupportedOperationException ex) {
			LOGGER.warn("Desktop extension not supported!", true);
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
		LOGGER.debug("Opening RSS Item", false);
		IRSSList rssList = (IRSSList) jTable.getModel();

		RSSItem rssItem = null;
		int row = jTable.getSelectedRow();
		row = jTable.convertRowIndexToModel(row);
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
			LOGGER.error("Error opening rss item.", e1, true);
		}
	}
}
