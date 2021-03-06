package pl.edu.agh.two.file;

import java.awt.*;

import java.io.File;
import pl.edu.agh.two.interfaces.IFileOpener;

public class DefaultFileOpener implements IFileOpener {

	@Override
	public void open(DocSyncFile docSyncFile) throws FileOpenException {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.open(new File(docSyncFile.getPath()));
			} catch (Exception ex) {
				throw new FileOpenException(ex);
			}
		} else {
			throw new FileOpenException("java.awt.Desktop extension is not available.");
		}
	}

}
