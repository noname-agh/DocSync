package pl.edu.agh.two.interfaces;


import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.file.FileOpenException;

public interface IFileOpener {

	/**
	 * Opens given file.
	 * 
	 * @param docSyncFile file to open
	 * @throws FileOpenException 
	 */
	public void open(DocSyncFile docSyncFile) throws FileOpenException;

}
