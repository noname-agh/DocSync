package pl.edu.agh.two.file;

import pl.edu.agh.two.gui.pdf.PDFViewer;
import pl.edu.agh.two.interfaces.IFileOpener;

public class PDFFileOpener implements IFileOpener {

	@Override
	public void open(DocSyncFile docSyncFile) throws FileOpenException {
		PDFViewer viewer = new PDFViewer(docSyncFile);
		viewer.open();
	}
}
