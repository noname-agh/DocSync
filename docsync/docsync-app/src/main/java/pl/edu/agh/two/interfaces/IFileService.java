package pl.edu.agh.two.interfaces;

import java.io.IOException;
import java.util.List;

import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.ws.CloudFileInfo;

public interface IFileService {
	public void sendFile(DocSyncFile file) throws IOException;

	public List<DocSyncFile> getAllFilesWithContent();

	public void pushMetadata(DocSyncFile file);
	//public IMetadata pullMetadata(DocSyncFile file);
	
	public List<CloudFileInfo> getFilesWithoutContent();
	
	public List<DocSyncFile> getFiles(List<CloudFileInfo> cloudFileInfos);
}
