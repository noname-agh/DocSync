package pl.edu.agh.two.file;

import java.io.IOException;
import java.util.List;

public interface IFileService {
	public void sendFile(DocSyncFile file) throws IOException;

	public List<DocSyncFile> getAllFilesWithContent();

	public void pushMetadata(DocSyncFile file);
	//public IMetadata pullMetadata(DocSyncFile file);
}
