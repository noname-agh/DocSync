package pl.edu.agh.two.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.file.FileListPersistence;
import pl.edu.agh.two.file.FileOpenerWrapper;
import pl.edu.agh.two.file.PDFFileOpener;
import pl.edu.agh.two.gui.actions.AddFileAction;
import pl.edu.agh.two.gui.actions.ExitAction;
import pl.edu.agh.two.gui.actions.GetAllFilesAction;
import pl.edu.agh.two.gui.actions.GetLogAction;
import pl.edu.agh.two.gui.actions.OpenFileAction;
import pl.edu.agh.two.gui.actions.OpenRSSAction;
import pl.edu.agh.two.gui.actions.RSSManagerAction;
import pl.edu.agh.two.gui.actions.RSSRefreshAction;
import pl.edu.agh.two.interfaces.IFileList;

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
	private static JTable rssList;
	private static ArrayList<String> logList = new ArrayList<String>();
	private static final String TITLE = "DocSync";
	private static final Dimension FRAME_DIMENSION = new Dimension(800, 600);
	private static final String storagePath = "storage";
	private static final FileListPersistence fileListPersistence = new FileListPersistence(storagePath);

	public DocSyncGUI() throws HeadlessException {
		super();
	}

	public static void createAndShowGUI() {
		frame = new DocSyncGUI();
		initGUI();
		getFrame().setVisible(true);
		getFrame().setLocationRelativeTo(null);
	}

	private static void initGUI() {
		fileList = new JTable(new FileTableModel());
		rssList = new JTable(new RSSTableModel());
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().setTitle(TITLE);
		getFrame().setSize(FRAME_DIMENSION);

		initMenu();
		getFrame().setLayout(new BorderLayout());


		JTabbedPane tabs = new JTabbedPane();

		// Open PDFs using special opener, and use default for other file types.
		FileOpenerWrapper fileOpener = new FileOpenerWrapper();
		fileOpener.registerOpener("pdf", new PDFFileOpener());
		fileList.addMouseListener(new OpenFileAction(fileOpener));
		rssList.addMouseListener(new OpenRSSAction());

		fileList.setPreferredScrollableViewportSize(new Dimension(500, 70));
		fileList.setFillsViewportHeight(true);
		fileList.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		fileList.getColumnModel().getColumn(1).setMaxWidth(150);
		fileList.setRowSorter(new TableRowSorter<FileTableModel>((FileTableModel) fileList.getModel()));
		JScrollPane filesScrollPane = new JScrollPane(fileList);
		JScrollPane rssScrollPane = new JScrollPane(rssList);
		getFrame().add(tabs);
		tabs.add("Files", filesScrollPane);
		tabs.add("RSS", rssScrollPane);

		try {
			for (DocSyncFile dcf : fileListPersistence.load()) {
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

		JMenuItem getAllFilesItem = new JMenuItem("Get all files");
		getAllFilesItem.addActionListener(new GetAllFilesAction());
		file.add(getAllFilesItem);

		JMenu rssManager = new JMenu("RSS");
		menuBar.add(rssManager);
		JMenuItem rssManagerItem = new JMenuItem("RSS Manager");
		rssManagerItem.addActionListener(new RSSManagerAction());
		rssManager.add(rssManagerItem);

		JMenuItem rssRefreshItem = new JMenuItem("Refresh messages");
		rssRefreshItem.addActionListener(new RSSRefreshAction(rssList.getModel()));
		rssManager.add(rssRefreshItem);

		file.add(new JSeparator());

		JMenuItem exitItem = new JMenuItem("Exit");
		file.add(exitItem);
		exitItem.addActionListener(new ExitAction());
		
		JMenuItem logItem = new JMenuItem("Log");
		logItem.addActionListener(new GetLogAction());
		menuBar.add(logItem);

		getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				DocSyncGUI.saveListAndExit();
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

	public static void refreshRSSList() {
		((AbstractTableModel) rssList.getModel()).fireTableDataChanged();
	}

	public static void saveListAndExit() {
		FileTableModel model = (FileTableModel) getFrame().getFileList();

		FileListPersistence flp = new FileListPersistence(DocSyncGUI.getStoragePath());
		try {
			flp.save(model.getDocSyncFileList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public static List<String> getLogList() {
		return DocSyncGUI.logList;
	}
	
	public static void error(String errorMsg) {
		DocSyncGUI.logList.add(new Date() + " [ERROR] " + errorMsg);
		JOptionPane.showMessageDialog(getFrame(), errorMsg);
	}
	
	public static void debug(String debugMsg) {
		DocSyncGUI.logList.add(new Date() + " [DEBUG] " + debugMsg);
		JOptionPane.showMessageDialog(getFrame(), debugMsg);
	}
	
	public static void info(String infoMsg) {
		DocSyncGUI.logList.add(new Date() + " [INFO] " + infoMsg);
		JOptionPane.showMessageDialog(getFrame(), infoMsg);
	}
}
