package pl.edu.agh.two.gui.actions;

import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.rss.RSSService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class RSSManagerAction implements ActionListener {
	private static final String TITLE = "DocSync - RSS Manager";

	private JList list = new JList();
	private DefaultListModel model = new DefaultListModel();

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		final JDialog dialog = new JDialog(DocSyncGUI.getFrame(), TITLE, true);
		dialog.setLayout(new BorderLayout());

		list.setModel(model);
		DefaultListSelectionModel selModel = new DefaultListSelectionModel();
		selModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectionModel(selModel);
		dialog.add(new JScrollPane(list), BorderLayout.CENTER);

		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new FlowLayout());
		dialog.add(buttonsPane, BorderLayout.SOUTH);

		JButton buttonAdd = new JButton("Add");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String url = (String) JOptionPane.showInputDialog(dialog, "Add new RSS", "Add new RSS",
						JOptionPane.PLAIN_MESSAGE);
				if (url.length() > 0) {
					model.addElement(url);
					RSSService rssService = RSSService.getInstance();
					rssService.addChannel(url);
				}
			}
		});
		buttonsPane.add(buttonAdd);

		JButton buttonRemove = new JButton("Remove");
		buttonRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selected = list.getSelectedIndex();
				if (selected != -1) {
					RSSService rssService = RSSService.getInstance();
					rssService.removeChannel((String) (list.getSelectedValue()));
					model.remove(selected);
				}
			}
		});
		buttonsPane.add(buttonRemove);

		JButton buttonOK = new JButton("OK");
		buttonOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		buttonsPane.add(buttonOK);

		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dialog.dispose();
			}
		});

		initData();

		dialog.pack();
		dialog.setLocationRelativeTo(DocSyncGUI.getFrame());
		dialog.setVisible(true);

	}

	private void initData() {
		RSSService rssService = RSSService.getInstance();

		List<String> list = rssService.getRssChannelList();
		model.clear();
		for (String channel : list) {
			model.addElement(channel);
		}
	}

}
