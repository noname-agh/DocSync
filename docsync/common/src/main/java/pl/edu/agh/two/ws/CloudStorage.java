package pl.edu.agh.two.ws;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface CloudStorage {

	/**
	 * Add new file to cloud storage. File hash is not used, and may be null.
	 *
	 * @param file file to add
	 * @return file info with of added file
	 */
	CloudFileInfo addFile(CloudFile file);

	void removeFile(CloudFileInfo file);

	List<CloudFileInfo> getFiles();

	/**
	 * Returns single file with content.
	 * NOT IMPLEMENTED AND NOT NEEDED FOR Iteration 3.
	 *
	 * @param fileInfo fileInfo to fetch content
	 * @return file with content
	 */
	CloudFile getFileWithContent(CloudFileInfo fileInfo);

	/**
	 * Returns all files with content.
	 *
	 * @return all files with content
	 */
	List<CloudFile> getAllFilesWithContent();

	void pushMetadata(CloudFileInfo fileInfo);

	//CloudMetadata pullMetadata(CloudFileInfo file);

	List<RSSItem> getRSSItems();

	List<String> getRssChannelList();

	void addChannel(String address);

	void removeChannel(String address);

	void updateRSSItem(RSSItem item);
}
