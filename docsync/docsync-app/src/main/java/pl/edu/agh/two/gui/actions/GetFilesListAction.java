package pl.edu.agh.two.gui.actions;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import pl.edu.agh.two.file.FileService;
import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.gui.SelectFilesListModel;
import pl.edu.agh.two.interfaces.IFileService;
import pl.edu.agh.two.ws.CloudFileInfo;

public class GetFilesListAction implements ActionListener {
	private static final String TITLE = "DocSync - Select files to sync";

	private JList list = new JList();
	private SelectFilesListModel model = new SelectFilesListModel();
	IFileService fileService = FileService.getInstance();

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		final JDialog dialog = new JDialog(DocSyncGUI.getFrame(), TITLE, true);
		dialog.setLayout(new BorderLayout());

		list.setModel(model);
		DefaultListSelectionModel selModel = new DefaultListSelectionModel();
		selModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectionModel(selModel);
		dialog.add(new JScrollPane(list), BorderLayout.CENTER);

		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new FlowLayout());
		dialog.add(buttonsPane, BorderLayout.SOUTH);

		JButton buttonAdd = new JButton("Download");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int filesIndices[] = list.getSelectedIndices();
				List<CloudFileInfo> files = new LinkedList<CloudFileInfo>();
				for (int index : filesIndices)
					files.add(model.getCloudFileInfoAt(index));
				DocSyncGUI.addFilesToList(fileService.getFiles(files), false);
			}
		});
		buttonsPane.add(buttonAdd);
		
		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		buttonsPane.add(buttonClose);

		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dialog.dispose();
			}
		});

		initData();

		dialog.pack();
		dialog.setVisible(true);
	}

	private void initData() {
		List<CloudFileInfo> list = fileService.getFilesWithoutContent();

		for (CloudFileInfo file : list)
			model.addElement(file);
	}
}