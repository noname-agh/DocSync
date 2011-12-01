package pl.edu.agh.two.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.interfaces.IRSSList;
import pl.edu.agh.two.ws.RSSItem;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
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

	private List<RSSItem> items = new ArrayList<RSSItem>();

	@Override
	public int getRowCount() {
		// TODO: implementation
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO: implementation
		return 0;
	}

	@Override
	public Object getValueAt(int i, int i1) {
		// TODO: implementation
		return null;
	}

	@Override
	public void getList() {
		// TODO: implementation

	}

	@Override
	public RSSItem getItem(int i) {
		// TODO: implementation
		return null;
	}
}
