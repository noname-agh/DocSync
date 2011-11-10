package pl.edu.agh.two.ws.server;

import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudStorage;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "pl.edu.agh.two.ws.CloudStorage", serviceName = "CloudStorage")
public class CloudStorageImpl implements CloudStorage {

	@Override
	public void addFile(CloudFile file) {
		throw new RuntimeException("Not implemented yet.");
	}

	@Override
	public void removeFile(CloudFileInfo file) {
		throw new RuntimeException("Not implemented yet.");
	}

	@Override
	public List<CloudFileInfo> getFiles() {
		throw new RuntimeException("Not implemented yet.");
	}

	@Override
	public CloudFile getFileWithContent(CloudFileInfo fileInfo) {
		throw new RuntimeException("Not implemented yet.");
	}

	@Override
	public List<CloudFile> getAllFilesWithContent() {
		throw new RuntimeException("Not implemented yet.");
	}

	@Override
	public void pushMetadata(CloudFileInfo fileInfo) {
		throw new RuntimeException("Not implemented yet.");
	}

}
