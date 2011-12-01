package pl.edu.agh.two.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.file.FileListPersistence;

import pl.edu.agh.two.gui.actions.AddFileAction;
import pl.edu.agh.two.gui.actions.ExitAction;
import pl.edu.agh.two.gui.actions.GetAllFilesAction;
import pl.edu.agh.two.gui.actions.RSSManagerAction;
import pl.edu.agh.two.gui.actions.RSSRefreshAction;

import pl.edu.agh.two.file.FileOpenerWrapper;
import pl.edu.agh.two.file.PDFFileOpener;
import pl.edu.agh.two.gui.actions.*;

import pl.edu.agh.two.interfaces.IFileList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
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
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(DocSyncGUI.class);

	private static DocSyncGUI frame;
	private static JTable fileList;
	private static JTable rssList;
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
	}

	private static void initGUI() {
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().setTitle(TITLE);
		getFrame().setSize(FRAME_DIMENSION);

		initMenu();

		getFrame().setLayout(new BorderLayout());

		fileList = new JTable(new FileTableModel());
		rssList = new JTable(new RSSTableModel());

		JTabbedPane tabs = new JTabbedPane();

		// Open PDFs using special opener, and use default for other file types.
		FileOpenerWrapper fileOpener = new FileOpenerWrapper();
		fileOpener.registerOpener("pdf", new PDFFileOpener());
		fileList.addMouseListener(new OpenFileAction(fileOpener));

		fileList.setPreferredScrollableViewportSize(new Dimension(500, 70));
		fileList.setFillsViewportHeight(true);
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
		rssRefreshItem.addActionListener(new RSSRefreshAction());
		rssManager.add(rssRefreshItem);

		file.add(new JSeparator());

		JMenuItem exitItem = new JMenuItem("Exit");
		file.add(exitItem);
		exitItem.addActionListener(new ExitAction());

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
}
