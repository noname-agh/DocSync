package pl.edu.agh.two.gui.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.file.PDFDocSyncFile;
import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.interfaces.IFileList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.11.03
 *
 * @author Tomasz Zdybał
 */
public class ExternalFileOpenAction implements ActionListener {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(ExternalFileOpenAction.class);

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		IFileList fileList = DocSyncGUI.getFrame().getFileList();
		JFileChooser jc = new JFileChooser("Open file");
		int ret = jc.showOpenDialog(DocSyncGUI.getFrame());
		if (ret == JFileChooser.APPROVE_OPTION) {
			final File file = jc.getSelectedFile();
			String fileName = file.getName();
			log.debug("Opening " + fileName);
			String ext = fileName.substring(fileName.lastIndexOf('.'));
			if (ext.toLowerCase().equals(".pdf")) {
				DocSyncFile f = new PDFDocSyncFile(file.getAbsolutePath());
				if(!fileList.contains(f))
				{
					fileList.add(f);
					DocSyncGUI.getFrame();
					DocSyncGUI.refreshFileList();
				}
			}
		}
	}
}
