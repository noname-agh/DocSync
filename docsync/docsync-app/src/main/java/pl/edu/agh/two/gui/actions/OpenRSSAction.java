package pl.edu.agh.two.gui.actions;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JTable;

import pl.edu.agh.two.interfaces.IRSSList;
import pl.edu.agh.two.rss.RSSService;
import pl.edu.agh.two.ws.RSSItem;

public class OpenRSSAction implements ActionListener {

	Desktop desktop;

	public OpenRSSAction() {
		desktop = Desktop.getDesktop();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTable jTable = (JTable) e.getSource();
		IRSSList rssList = (IRSSList) jTable.getModel();
		
		RSSItem rssItem = null;
		int row = jTable.getSelectedRow();
		if (row != -1) {
			rssItem = rssList.getItem(row);
		}
		
		try {
			desktop.browse(new URI(rssItem.getLink()));
			rssItem.setReaded(true);
			RSSService.getInstance().updateRSSItem(rssItem);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
	}

}
