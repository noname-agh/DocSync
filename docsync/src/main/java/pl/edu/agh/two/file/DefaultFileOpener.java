package pl.edu.agh.two.file;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class DefaultFileOpener implements IFileOpener {

	@Override
	public void open(File file) throws FileOpenException {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.open(file);
			} catch (Exception ex) {
				throw new FileOpenException(ex);
			}
		} else {
			throw new FileOpenException("java.awt.Desktop extension is not available.");
		}
	}
	
}
