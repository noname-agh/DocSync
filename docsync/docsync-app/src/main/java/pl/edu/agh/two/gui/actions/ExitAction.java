package pl.edu.agh.two.gui.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.agh.two.file.FileListPersistence;
import pl.edu.agh.two.gui.FileTableModel;
import pl.edu.agh.two.gui.DocSyncGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.10.27
 *
 * @author Tomasz Zdybał
 */
public class ExitAction implements ActionListener {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(ExitAction.class);

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
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
