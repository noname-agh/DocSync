package pl.edu.agh.two.interfaces;

import java.io.IOException;
import java.util.List;

import pl.edu.agh.two.file.DocSyncFile;

public interface IFileService {
	public void sendFile(DocSyncFile file) throws IOException;

	public List<DocSyncFile> getAllFilesWithContent();

	public void pushMetadata(DocSyncFile file);
	//public IMetadata pullMetadata(DocSyncFile file);
}
