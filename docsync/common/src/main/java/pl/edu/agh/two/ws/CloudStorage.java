package pl.edu.agh.two.ws;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface CloudStorage {

	void addFile(CloudFile file);

	void removeFile(CloudFileInfo file);

	List<CloudFileInfo> getFiles();

	CloudFile getFileWithContent(CloudFileInfo file);

	void synchronizeMetadata(CloudFileInfo file);

}
