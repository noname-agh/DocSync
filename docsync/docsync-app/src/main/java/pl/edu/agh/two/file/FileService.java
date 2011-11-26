package pl.edu.agh.two.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.interfaces.IFileService;
import pl.edu.agh.two.utils.ConfigReader;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudStorage;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class FileService implements IFileService {
	private static final Logger log = LoggerFactory.getLogger(FileService.class);

	public static final String wsUrl = ConfigReader.getInstance().getProperty("ws.url");
	public static final String wsNamespace = ConfigReader.getInstance().getProperty("ws.ns");
	public static final String wsName = ConfigReader.getInstance().getProperty("ws.name");

	public static final String storagePath = ConfigReader.getInstance().getProperty("storage.path");
	private static CloudStorage cloud;
	private static IFileService fileService;

	private FileService() {
		Service service = null;
		try {
			service = Service.create(new URL(wsUrl), new QName(wsNamespace, wsName));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		cloud = service.getPort(CloudStorage.class);
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
		List<CloudFile> cloudFiles = cloud.getAllFilesWithContent();
		List<DocSyncFile> list = new LinkedList<DocSyncFile>();
		File dir = new File(FileService.storagePath);
		if (!(dir.exists() && dir.isDirectory())) {
			new File(FileService.storagePath).mkdirs();
		}
		for (CloudFile cloudFile : cloudFiles) {
			list.add(createDocSyncFileFromCloudFile(cloudFile));
		}
		return list;

	}

	@Override
	public void pushMetadata(DocSyncFile file) {
		CloudFileInfo fileInfo = new CloudFileInfo();
		fileInfo.setHash(file.getHash());
		fileInfo.setName(this.getName(file.getPath()));
		fileInfo.setMetadata(file.getMeta());
		cloud.pushMetadata(fileInfo);
	}

	private String getName(String path) {
		int i = path.lastIndexOf(System.getProperty("file.separator"));
		return i >= 0 ? path.substring(i + 1) : null;
	}

	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			log.warn("File %s is to large (filepath: %s)", file.getName(), file.getAbsolutePath());
			return null;
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	private DocSyncFile createDocSyncFileFromCloudFile(CloudFile cloudFile) {
		File file = new File(storagePath + System.getProperty("file.separator") + cloudFile.getName());
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(cloudFile.getContent());
			fileOutputStream.close();
			
			DocSyncFile docsyncFile = new DocSyncFile(file.getAbsolutePath());
			docsyncFile.setMeta(cloudFile.getMetadata());
			docsyncFile.setHash(cloudFile.getHash());
			return docsyncFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getExtension(String fileName) {
		int i = fileName.lastIndexOf('.');
		return i >= 0 ? fileName.substring(i + 1) : null;
	}

}
