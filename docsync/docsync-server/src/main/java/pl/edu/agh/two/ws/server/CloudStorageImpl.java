package pl.edu.agh.two.ws.server;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.ws.*;

import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import pl.edu.agh.two.ws.dao.CloudFileDAO;

@WebService(endpointInterface = "pl.edu.agh.two.ws.CloudStorage", serviceName = "CloudStorage")
public class CloudStorageImpl implements CloudStorage {
	private static final Logger LOGGER = LoggerFactory.getLogger(CloudStorageImpl.class);

	private CloudFileDAO cloudFileDAO;
	
	public CloudStorageImpl(CloudFileDAO cloudFileDAO) {
		this.cloudFileDAO = cloudFileDAO;
	}
	
	@Override
	public CloudFileInfo addFile(CloudFile newFile) {
		try {
			/* Make sure hash is correct */
			newFile.setHash(computeHash(newFile.getContent()));
		} catch (NoSuchAlgorithmException ex) {
			throw new WebServiceException("Error when creating file hash", ex);
		}

		IMetadata metadata = newFile.getMetadata();
		if (metadata == null) {
			metadata = new CloudMetadata();
			metadata.setVersion(0);
			newFile.setMetadata(metadata);
		}
		
		CloudFile cloudFile = cloudFileDAO.findCloudFile(newFile.getHash());
		if (cloudFile != null) {
			LOGGER.warn(String.format("Duplicated file hash, overwritting: %s", cloudFile.getName()));
			newFile.getMetadata().setVersion(cloudFile.getMetadata().getVersion()+1);
			cloudFile.setContent(newFile.getContent());
			cloudFile.setMetadata(newFile.getMetadata());
			cloudFileDAO.updateCloudFile(cloudFile);
		} else {
			cloudFileDAO.addCloudFile(newFile);
			LOGGER.info(String.format("Added new file: %s", newFile.getName()));
		}
		
		return newFile;
	}

	@Override
	public void removeFile(CloudFileInfo fileInfo) {
		cloudFileDAO.removeCloudFile(fileInfo);
	}

	@Override
	public List<CloudFileInfo> getFiles() {
		return new LinkedList<CloudFileInfo>(getAllFilesWithContent());
	}

	@Override
	public CloudFile getFileWithContent(CloudFileInfo fileInfo) {
		return cloudFileDAO.findCloudFile(fileInfo.getHash());
	}

	@Override
	public List<CloudFile> getAllFilesWithContent() {
		return cloudFileDAO.getCloudFiles();
	}

	@Override
	public void pushMetadata(CloudFileInfo fileInfo) {
		LOGGER.debug("Pushing metadata for file " + fileInfo);
		CloudFile file = cloudFileDAO.findCloudFile(fileInfo.getHash());
		IMetadata md = fileInfo.getMetadata();
		md.setVersion(md.getVersion() + 1);
		file.setMetadata(md);
		cloudFileDAO.updateCloudFile(file);
	}

	@Override
	public void updateAll() {
		RSSReader.getInstance().updateAll();
	}

	private String computeHash(byte[] content) throws NoSuchAlgorithmException {
		return Arrays.toString(DigestUtils.md5(content));
	}

	@Override
	public List<RSSItem> getRSSItems() {
		return RSSReader.getInstance().getRSSItems();
	}

	@Override
	public List<String> getRssChannelList() {
		return RSSReader.getInstance().getRssChannelList();
	}

	@Override
	public void addChannel(String address) {
		RSSReader.getInstance().addChannel(address);
	}

	@Override
	public void removeChannel(String address) {
		RSSReader.getInstance().removeChannel(address);
	}

	@Override
	public void updateRSSItem(RSSItem item) {
		RSSReader.getInstance().updateRSSItem(item);
	}

}
