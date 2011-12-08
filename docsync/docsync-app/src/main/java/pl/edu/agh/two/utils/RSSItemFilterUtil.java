package pl.edu.agh.two.utils;

import java.util.List;

import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.gui.RSSTableModel;
import pl.edu.agh.two.ws.RSSItem;

public class RSSItemFilterUtil {
	public static void doFilter() {
		RSSTableModel tableModel = (RSSTableModel)DocSyncGUI.getRssList().getModel();
		List<RSSItem> items = tableModel.getItems();
		for (RSSItem item : items) {	
			//TODO: bug, bo getReaded zwraca null'a.. narazie jak nikt nie widzi - zalatam, napije sie i pomysle
			if ((item.getReaded()!=null && item.getReaded()) && DocSyncGUI.isRSSReadedSelected() ||  
					(item.getReaded()==null || !item.getReaded()) && 
					DocSyncGUI.isRSSUnreadedSelected()) {
				item.setIsShown(true);
			}
			else item.setIsShown(false);
		}		
	}
}
