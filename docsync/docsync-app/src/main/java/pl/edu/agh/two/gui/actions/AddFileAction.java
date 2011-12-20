package pl.edu.agh.two.gui.actions;

//import org.apache.commons.io.FileUtils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.file.FileService;
import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.interfaces.IFileList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
public class AddFileAction implements ActionListener {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(AddFileAction.class);

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		IFileList fileList = DocSyncGUI.getFrame().getFileList();
		JFileChooser jc = new JFileChooser("Add file");
		jc.setApproveButtonText("Add");
		jc.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
		int ret = jc.showOpenDialog(DocSyncGUI.getFrame());
		if (ret == JFileChooser.APPROVE_OPTION) {
			final File file = jc.getSelectedFile();
			String fileName = file.getName();
			String filePath = file.getAbsolutePath();

			//copying file to storagePath
			String storagePath = FileService.storagePath;
			String inputPath = filePath;
			String outputPath = storagePath + System.getProperty("file.separator") + fileName;
			log.debug("Copying " + inputPath + " to " + outputPath);
			String copyiedFilePath = copyFile(inputPath, outputPath);
			log.debug("New file created at " + copyiedFilePath);

			log.debug("Opening " + fileName);
			DocSyncFile f = new DocSyncFile(copyiedFilePath);
			if (fileList.contains(f) == null) {
				fileList.addAndSend(f);
				DocSyncGUI.refreshFileList();
			}
		}
	}

	private static String copyFile(String srFile, String dtFile) {
		try {
			File dir = new File(FileService.storagePath);
			if (!(dir.exists() && dir.isDirectory())) {
				new File(FileService.storagePath).mkdirs();
			}
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
			FileUtils.copyFile(f1, f2);
			return f2.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}