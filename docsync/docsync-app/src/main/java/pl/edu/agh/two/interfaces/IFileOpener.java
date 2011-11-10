package pl.edu.agh.two.interfaces;

import java.io.File;

import pl.edu.agh.two.file.FileOpenException;

public interface IFileOpener {

	public void open(File file) throws FileOpenException;

}
