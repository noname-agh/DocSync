package pl.edu.agh.two.ws.dao;

import java.util.List;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;

public interface CloudFileDAO {
	
	public void addCloudFile(CloudFile cloudFile);
	
	public void updateCloudFile(CloudFile cloudFile);
	
	public void removeCloudFile(CloudFileInfo cloudFile);
	
	public List<CloudFile> getCloudFiles();
	
	public CloudFile findCloudFile(String hash);
	
}
