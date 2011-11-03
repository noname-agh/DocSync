package pl.edu.agh.two.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.gui.actions.ExitAction;
import pl.edu.agh.two.gui.actions.ExternalFileOpenAction;
import pl.edu.agh.two.interfaces.IFileList;

import javax.swing.*;
import java.awt.*;

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
	private IFileList fileList;
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
		
		JTable table = new JTable(new FileTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
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
		return fileList;
	}
}
