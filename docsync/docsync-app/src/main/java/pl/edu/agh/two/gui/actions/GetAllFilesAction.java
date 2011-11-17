package pl.edu.agh.two.gui.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.file.FileService;
import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.interfaces.IFileList;
import pl.edu.agh.two.interfaces.IFileService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GetAllFilesAction implements ActionListener {
	private static final Logger log = LoggerFactory.getLogger(GetAllFilesAction.class);

	@Override
	public void actionPerformed(ActionEvent arg0) {
		IFileService fileService = FileService.getInstance();
		List<DocSyncFile> list = fileService.getAllFilesWithContent();

		IFileList fileList = DocSyncGUI.getFrame().getFileList();
		for (DocSyncFile file : list) {
			if (!fileList.contains(file)) {
				fileList.add(file);
			}
		}
		DocSyncGUI.refreshFileList();
		log.debug("All files fetched from server.");
	}

}