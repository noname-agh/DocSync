package pl.edu.agh.two.gui.actions;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

import pl.edu.agh.two.gui.DocSyncGUI;

public class GetLogAction implements ActionListener {
	private static final String TITLE = "DocSync - Message Log";

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final JDialog dialog = new JDialog(DocSyncGUI.getFrame(), TITLE, true);
		dialog.setLayout(new BorderLayout());
		
		List<String> logList = DocSyncGUI.getLogList();
		
		JList list = new JList();
		list.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
		list.setListData(logList.toArray());
		
	    JScrollPane scrollPane = new JScrollPane(list);

	    dialog.add(scrollPane, BorderLayout.CENTER);

	    dialog.setSize(640, 480);
	    dialog.setVisible(true);
	}

}
