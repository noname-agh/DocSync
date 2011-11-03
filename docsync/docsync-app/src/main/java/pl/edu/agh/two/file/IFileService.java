package pl.edu.agh.two.file;

import java.io.IOException;

public interface IFileService {
	public void sendFile(DocSyncFile file) throws IOException;
}
