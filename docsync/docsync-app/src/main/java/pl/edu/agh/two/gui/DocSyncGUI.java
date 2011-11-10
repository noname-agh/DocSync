package pl.edu.agh.two.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.gui.actions.ExitAction;
import pl.edu.agh.two.gui.actions.ExternalFileOpenAction;
import pl.edu.agh.two.interfaces.IFileList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.10.27
 *
 * @author Tomasz Zdybał
 */
public class DocSyncGUI extends JFrame {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(DocSyncGUI.class);

	private static DocSyncGUI frame;
	private static JTable fileList;
	private static final String TITLE = "DocSync";
	private static final Dimension FRAME_DIMENSION = new Dimension(800, 600);

	public DocSyncGUI() throws HeadlessException {
		super();
	}

	public static void createAndShowGUI() {
		frame = new DocSyncGUI();
		initGUI();
		getFrame().setVisible(true);
	}

	private static void initGUI() {
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().setTitle(TITLE);
		getFrame().setSize(FRAME_DIMENSION);

		initMenu();

		getFrame().setLayout(new BorderLayout());

		final FileTableModel tableModel = new FileTableModel();
		fileList = new JTable(tableModel);
		fileList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					tableModel.open(fileList.getSelectedRow());
				}
			}
		});
		fileList.setPreferredScrollableViewportSize(new Dimension(500, 70));
		fileList.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(fileList);
		getFrame().add(scrollPane);


	}

	private static void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		menuBar.add(file);

		JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(new ExternalFileOpenAction());
		file.add(openItem);

		file.add(new JSeparator());

		JMenuItem exitItem = new JMenuItem("Exit");
		file.add(exitItem);
		exitItem.addActionListener(new ExitAction());

		getFrame().setJMenuBar(menuBar);
	}

	public static DocSyncGUI getFrame() {
		return frame;
	}

	public IFileList getFileList() {
		return (IFileList) fileList.getModel();
	}

	public static void refreshFileList() {
		((AbstractTableModel) fileList.getModel()).fireTableDataChanged();
	}
}
