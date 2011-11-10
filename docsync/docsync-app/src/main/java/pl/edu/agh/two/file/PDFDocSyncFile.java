package pl.edu.agh.two.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.gui.pdf.PDFViewer;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.11.03
 *
 * @author Tomasz Zdybał
 */
public class PDFDocSyncFile extends DocSyncFile {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(PDFDocSyncFile.class);

	public PDFDocSyncFile(String path) {
		super(path);
	}

	@Override
	public void open() {
		PDFViewer viewer = new PDFViewer(this);
		viewer.open();
	}

	@Override
	public void delete() {
		// TODO: implementation

	}
}
