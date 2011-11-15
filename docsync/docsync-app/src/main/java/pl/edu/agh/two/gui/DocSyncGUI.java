package pl.edu.agh.two.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.file.FileListPersistence;
import pl.edu.agh.two.gui.actions.ExitAction;
import pl.edu.agh.two.gui.actions.AddFileAction;
import pl.edu.agh.two.interfaces.IFileList;
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
	private static final String storagePath = "storage";
	private static FileListPersistence fileListPersistence = new FileListPersistence(storagePath);
	
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

		try {
			for(DocSyncFile dcf : fileListPersistence.load()) {
				DocSyncGUI.getFrame().getFileList().add(dcf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DocSyncGUI.refreshFileList();
	}

	private static void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		menuBar.add(file);

		JMenuItem addItem = new JMenuItem("Add");
		addItem.addActionListener(new AddFileAction());
		file.add(addItem);

		file.add(new JSeparator());		

		JMenuItem exitItem = new JMenuItem("Exit");
		file.add(exitItem);
		exitItem.addActionListener(new ExitAction());	
		
		getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(WindowEvent winEvt) {
		        DocSyncGUI.saveListandExit();
		    }
		});
		
		getFrame().setJMenuBar(menuBar);
	}

	public static DocSyncGUI getFrame() {
		return frame;
	}

	public static String getStoragePath() {
		return storagePath;
	}
	
	public IFileList getFileList() {
		return (IFileList) fileList.getModel();
	}

	public static void refreshFileList() {
		((AbstractTableModel) fileList.getModel()).fireTableDataChanged();
	}
	
	public static void saveListandExit() {
	FileTableModel model = (FileTableModel)DocSyncGUI.getFrame().getFileList();
		
		FileListPersistence flp = new FileListPersistence(DocSyncGUI.getStoragePath());
		try {
			flp.save(model.getDocSyncFileList());
		} catch (IOException e) {
			e.printStackTrace();
		}		
		System.exit(0);		
	}
}
