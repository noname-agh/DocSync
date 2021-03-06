package pl.edu.agh.two.gui;

import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.file.FileOpenerWrapper;
import pl.edu.agh.two.file.ListPersistence;
import pl.edu.agh.two.file.PDFFileOpener;
import pl.edu.agh.two.gui.actions.*;
import pl.edu.agh.two.interfaces.IFileList;
import pl.edu.agh.two.interfaces.IRSSList;
import pl.edu.agh.two.log.ILogger;
import pl.edu.agh.two.log.LoggerFactory;
import pl.edu.agh.two.ws.RSSItem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.10.27
 *
 * @author Tomasz Zdybał
 */
public class DocSyncGUI extends JFrame {
	private static final ILogger LOGGER = LoggerFactory.getLogger(DocSyncGUI.class, DocSyncGUI.getFrame());

	private static DocSyncGUI frame;
	private static JTable fileList;
	private static JTable rssList;

	public static JTable getRssList() {
		return rssList;
	}

	public static void setRssList(JTable rssList) {
		DocSyncGUI.rssList = rssList;
	}

	private static final String TITLE = "DocSync";
	private static final Dimension FRAME_DIMENSION = new Dimension(800, 600);
	private static final String storagePath = "storage";
	private static final ListPersistence<DocSyncFile> fileListPersistence = new ListPersistence<DocSyncFile>(
			storagePath + ".file");
	private static final ListPersistence<RSSItem> rssListPersistence = new ListPersistence<RSSItem>(
			storagePath + ".rss");

	private static final JCheckBox readedCheckbox = new JCheckBox("Readed", true);
	private static final JCheckBox unreadedCheckbox = new JCheckBox("Unreaded", true);

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

		OpenFileAction openFileAction = new OpenFileAction(fileOpener);
		fileList.addMouseListener(openFileAction);
		fileList.addKeyListener(openFileAction);

		OpenRSSAction openRSSAction = new OpenRSSAction();
		rssList.addMouseListener(openRSSAction);
		rssList.addKeyListener(openRSSAction);
		rssList.getColumnModel().getColumn(0).setMaxWidth(50);
		rssList.setRowSorter(new TableRowSorter<RSSTableModel>((RSSTableModel) rssList.getModel()));

		fileList.setPreferredScrollableViewportSize(new Dimension(500, 70));
		fileList.setFillsViewportHeight(true);
		fileList.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		fileList.getColumnModel().getColumn(1).setMaxWidth(150);
		fileList.setRowSorter(new TableRowSorter<FileTableModel>((FileTableModel) fileList.getModel()));
		JScrollPane filesScrollPane = new JScrollPane(fileList);
		JScrollPane rssScrollPane = new JScrollPane(rssList);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		buttonPane.add(readedCheckbox);
		buttonPane.add(unreadedCheckbox);
		readedCheckbox.addActionListener(new FilterRSSItemsAction());
		unreadedCheckbox.addActionListener(new FilterRSSItemsAction());

		JPanel rssPane = new JPanel();
		rssPane.setLayout(new BorderLayout());
		rssPane.add(buttonPane, BorderLayout.NORTH);
		rssPane.add(rssScrollPane, BorderLayout.CENTER);

		getFrame().add(tabs);
		tabs.add("Files", filesScrollPane);
		tabs.add("RSS", rssPane);

		try {
			for (DocSyncFile dcf : fileListPersistence.load()) {
				DocSyncGUI.getFrame().getFileList().add(dcf);
			}
		} catch (IOException e) {
			LOGGER.error("Error loading persisted files.", e, true);
		}
		try {
			IRSSList list = DocSyncGUI.getFrame().getRSSList();
			list.addItems(rssListPersistence.load());
		} catch (IOException e) {
			LOGGER.error("Error loading persisted rss items.", e, true);
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

		JMenuItem getFilesListItem = new JMenuItem("Get files...");
		getFilesListItem.addActionListener(new GetFilesListAction());
		file.add(getFilesListItem);

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

		JMenu log = new JMenu("Log");
		menuBar.add(log);
		JMenuItem showLog = new JMenuItem("Open log");
		showLog.addActionListener(new GetLogAction());
		log.add(showLog);

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

	public IRSSList getRSSList() {
		return (IRSSList) rssList.getModel();
	}

	public static void refreshFileList() {
		((AbstractTableModel) fileList.getModel()).fireTableDataChanged();
	}

	public static void refreshRSSList() {
		((AbstractTableModel) rssList.getModel()).fireTableDataChanged();
	}

	public static void saveListAndExit() {
		IFileList fileModel = (IFileList) getFrame().getFileList();
		IRSSList rssModel = (IRSSList) getFrame().getRSSList();

		try {
			fileListPersistence.save(fileModel.getDocSyncFileList());
		} catch (IOException e) {
			LOGGER.error("Error when saving file list.", e, true);
		}
		try {
			rssListPersistence.save(rssModel.getRSSItemList());
		} catch (IOException e) {
			LOGGER.error("Error when saving rss item list.", e, true);
		}
		System.exit(0);
	}

	public static void addFilesToList(java.util.List<DocSyncFile> list, boolean clear) {
		IFileList fileList = DocSyncGUI.getFrame().getFileList();
		if (clear) {
			fileList.clear();
		}
		for (DocSyncFile file : list) {
			fileList.add(file);
		}
		refreshFileList();
	}

	public static boolean isRSSReadedSelected() {
		return readedCheckbox.isSelected();
	}

	public static boolean isRSSUnreadedSelected() {
		return unreadedCheckbox.isSelected();
	}
}
