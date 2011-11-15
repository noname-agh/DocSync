package pl.edu.agh.two.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.file.FileService;
import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.interfaces.IFileList;
import pl.edu.agh.two.interfaces.IFileService;

public class GetAllFilesAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		IFileService fileService = FileService.getInstance();
		List<DocSyncFile> list = fileService.getAllFilesWithContent();
		
		IFileList fileList = DocSyncGUI.getFrame().getFileList();
		for (DocSyncFile file : list) {
			if (!fileList.contains(file)) fileList.add(file);
		}
		DocSyncGUI.refreshFileList();
		System.out.println("dupa");
	}

}
