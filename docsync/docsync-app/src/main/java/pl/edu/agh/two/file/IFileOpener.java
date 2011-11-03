package pl.edu.agh.two.file;

import java.io.File;

public interface IFileOpener {

	public void open(File file) throws FileOpenException;

}
