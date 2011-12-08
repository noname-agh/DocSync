package pl.edu.agh.two.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.utils.RSSItemFilterUtil;

public class FilterRSSItemsAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {		
		RSSItemFilterUtil.doFilter();
		DocSyncGUI.refreshRSSList();
	}
}
