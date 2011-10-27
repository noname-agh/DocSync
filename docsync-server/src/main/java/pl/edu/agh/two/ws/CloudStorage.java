package pl.edu.agh.two.ws;

import java.util.List;

public interface CloudStorage {

	void addFile(CloudFile file);

	void removeFile(CloudFileInfo file);

	List<CloudFileInfo> getFiles();

	CloudFile getFileWithContent(CloudFileInfo file);

	void synchronizeMetadata(CloudFileInfo file);

}
