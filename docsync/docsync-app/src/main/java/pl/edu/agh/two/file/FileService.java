package pl.edu.agh.two.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.interfaces.IFileService;
import pl.edu.agh.two.log.*;
import pl.edu.agh.two.utils.ConfigReader;
import pl.edu.agh.two.utils.WebServiceProxy;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudStorage;

public class FileService implements IFileService {
	private static final ILogger LOGGER = LoggerFactory.getLogger(FileService.class, DocSyncGUI.getFrame());

	public static final String wsUrl = ConfigReader.getInstance().getProperty(
			"ws.url");
	public static final String wsNamespace = ConfigReader.getInstance()
			.getProperty("ws.ns");
	public static final String wsName = ConfigReader.getInstance().getProperty(
			"ws.name");

	public static final String storagePath = ConfigReader.getInstance()
			.getProperty("storage.path");
	private static CloudStorage cloud;
	private static IFileService fileService;

	private FileService() {
		try {
			cloud = WebServiceProxy.create(new URL(wsUrl), new QName(wsNamespace, wsName), CloudStorage.class);
		} catch (Exception e) {
			LOGGER.error("Cannot create service.", e, true);
		}
	}

	/*
	 * TODO: change visibility - refactor this class
	 */
	public FileService(CloudStorage cloudStorage) {
		cloud = cloudStorage;
	}

	public static IFileService getInstance() {
		if (fileService == null) {
			fileService = new FileService();
		}
		return fileService;
	}

	@Override
	public void sendFile(DocSyncFile file) throws IOException {
		CloudFile cfile = new CloudFile();
		cfile.setName(getName(file.getPath()));
		cfile.setContent(getBytesFromFile(new File(file.getPath())));
		CloudFileInfo cInfo = cloud.addFile(cfile);
		file.setHash(cInfo.getHash());
	}

	@Override
	public List<DocSyncFile> getAllFilesWithContent() {
		List<DocSyncFile> list = new LinkedList<DocSyncFile>();
		try {
			List<CloudFile> cloudFiles = cloud.getAllFilesWithContent();
			File dir = new File(FileService.storagePath);
			if (!(dir.exists() && dir.isDirectory())) {
				new File(FileService.storagePath).mkdirs();
			}
			for (CloudFile cloudFile : cloudFiles) {
				list.add(createDocSyncFileFromCloudFile(cloudFile));
			}
		} catch (Exception ex) {
			LOGGER.error("Cannot fetch files from server.", ex, true);
		}
		return list;

	}

	@Override
	public void pushMetadata(DocSyncFile file) {
		try {
			CloudFileInfo fileInfo = new CloudFileInfo();
			fileInfo.setHash(file.getHash());
			fileInfo.setName(this.getName(file.getPath()));
			fileInfo.setMetadata(file.getMeta());
			cloud.pushMetadata(fileInfo);
		} catch (Exception ex) {
			LOGGER.error("Cannot push metadata.", ex, true);
		}
	}

	private String getName(String path) {
		int i = path.lastIndexOf(System.getProperty("file.separator"));
		return i >= 0 ? path.substring(i + 1) : null;
	}

	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(file);

			// Get the size of the file
			long length = file.length();

			// You cannot create an array using a long type.
			// It needs to be an int type.
			// Before converting to an int type, check
			// to ensure that file is not larger than Integer.MAX_VALUE.
			if (length > Integer.MAX_VALUE) {
				LOGGER.warn("File " + file.getName() + " is to large (filepath: " + file.getAbsolutePath() + ")", true);
				return null;
			}

			// Create the byte array to hold the data
			byte[] bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}

			return bytes;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	private DocSyncFile createDocSyncFileFromCloudFile(CloudFile cloudFile) {
		File file = new File(storagePath + System.getProperty("file.separator")
				+ cloudFile.getName());
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(cloudFile.getContent());
			fileOutputStream.close();

			DocSyncFile docsyncFile = new DocSyncFile(file.getAbsolutePath());
			docsyncFile.setMeta(cloudFile.getMetadata());
			docsyncFile.setHash(cloudFile.getHash());
			return docsyncFile;
		} catch (Exception ex) {
			LOGGER.error("Cannot get files content.", ex, true);
		}
		return null;
	}

	private String getExtension(String fileName) {
		int i = fileName.lastIndexOf('.');
		return i >= 0 ? fileName.substring(i + 1) : null;
	}

	@Override
	public List<CloudFileInfo> getFilesWithoutContent() {
		List<CloudFileInfo> returnList = new ArrayList<CloudFileInfo>();
		try {
			List<DocSyncFile> docSyncFileList = DocSyncGUI.getFrame()
					.getFileList().getDocSyncFileList();
			for (CloudFileInfo cloudFileInfo : cloud.getFiles()) {
				boolean found = false;
				for (DocSyncFile docSyncFile : docSyncFileList) {
					if (docSyncFile.getHash().equals(cloudFileInfo.getHash())) {
						found = true;
						if (docSyncFile.getMeta().getVersion() < cloudFileInfo
								.getMetadata().getVersion()) {
							returnList.add(cloudFileInfo);
						}
					}
				}
				if (!found) {
					returnList.add(cloudFileInfo);
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Cannot get files without content.", ex, true);
		}
		return returnList;
	}

	@Override
	public List<DocSyncFile> getFiles(List<CloudFileInfo> cloudFileInfos) {
		List<DocSyncFile> list = new LinkedList<DocSyncFile>();
		try {
			for (CloudFileInfo cloudFileInfo : cloudFileInfos) {
				DocSyncFile file = createDocSyncFileFromCloudFile(cloud
						.getFileWithContent(cloudFileInfo));
				list.add(file);
			}
		} catch (Exception ex) {
			LOGGER.error("Cannot get files.", ex, true);
		}
		return list;
	}

}
