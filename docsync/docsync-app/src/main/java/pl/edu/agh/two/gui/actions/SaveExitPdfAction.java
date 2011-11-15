package pl.edu.agh.two.gui.actions;

import org.icepdf.ri.common.SwingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.gui.pdf.PDFMetadata;
import pl.edu.agh.two.interfaces.IFileList;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class SaveExitPdfAction implements WindowListener {
	private static final Logger log = LoggerFactory.getLogger(SaveExitPdfAction.class);

	private SwingController controller;
	private DocSyncFile file;
	private IFileList fileList;

	public SaveExitPdfAction(DocSyncFile file, IFileList fileList, SwingController controller) {
		this.controller = controller;
		this.file = file;
		this.fileList = fileList;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		int pageNr = controller.getCurrentPageNumber() + 1;
		PDFMetadata meta = new PDFMetadata();
		meta.setPageNo(pageNr);
		fileList.updateFile(file, meta);
		log.info("Saving " + file + " on page " + pageNr + " and Exiting...");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
