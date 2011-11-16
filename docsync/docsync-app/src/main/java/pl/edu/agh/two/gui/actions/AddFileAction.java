package pl.edu.agh.two.gui.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.file.DefaultDocSyncFile;
import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.file.FileService;
import pl.edu.agh.two.file.PDFDocSyncFile;
import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.interfaces.IFileList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
		jc.setFileFilter( new FileNameExtensionFilter("PDF Files", "pdf"));
		int ret = jc.showOpenDialog(DocSyncGUI.getFrame());
		if (ret == JFileChooser.APPROVE_OPTION) {
			final File file = jc.getSelectedFile();
			String fileName = file.getName();
			String filePath = file.getAbsolutePath();
			
			//copying file to storagePath			
			String storagePath = FileService.storagePath;
			String inputPath = filePath;
			String outputPath = storagePath+System.getProperty("file.separator")+fileName;
			log.debug("Copying " + inputPath +" to "+outputPath);
			String copyiedFilePath = copyfile(inputPath, outputPath);
			log.debug("New file created at "+copyiedFilePath);
			
			log.debug("Opening " + fileName);
			String ext = fileName.substring(fileName.lastIndexOf('.'));
			DocSyncFile f;
			if (ext.toLowerCase().equals(".pdf")) {
				f = new PDFDocSyncFile(copyiedFilePath);			
			} else {
				f = new DefaultDocSyncFile(copyiedFilePath);
			}
			if (!fileList.contains(f)) {
				fileList.addAndSend(f);
				DocSyncGUI.refreshFileList();
			}
		}
	}

	private static String copyfile(String srFile, String dtFile){
		try{
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
			InputStream in = new FileInputStream(f1);

			//For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			return f2.getAbsolutePath();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}