package pl.edu.agh.two.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import pl.edu.agh.two.gui.pdf.PDFMetadata;
import pl.edu.agh.two.interfaces.IFileService;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudMetadata;
import pl.edu.agh.two.ws.CloudStorage;

public class FileService implements IFileService {
	public static final String wsUrl = "http://149.156.205.250:13733/CloudStorage?wsdl";
	public static final String wsNamespace = "http://server.ws.two.agh.edu.pl/";
	public static final String wsName = "CloudStorage";

	private static final String storagePath = ".";
	private static CloudStorage cloud;

	public FileService() {
		Service service = null;
		try {
			service = Service.create(new URL(wsUrl), new QName(wsNamespace,
					wsName));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		cloud = service.getPort(CloudStorage.class);
	}

	@Override
	public void sendFile(DocSyncFile file) throws IOException {
		CloudFile cfile = new CloudFile();
		cfile.setHash(file.getHash());
		cfile.setName(getName(file.getPath()));
		cfile.setContent(getBytesFromFile(new File(file.getPath())));
		cloud.addFile(cfile);
	}

	@Override
	public List<DocSyncFile> getAllFilesWithContent() {
		List<CloudFile> cloudFiles = cloud.getAllFilesWithContent();
		List<DocSyncFile> list = new LinkedList<DocSyncFile>();
		for (CloudFile cloudFile : cloudFiles) {
			list.add(createDocSyncFileFromCloudFile(cloudFile));
		}
		return list;

	}

	@Override
	public void pushMetadata(DocSyncFile file) {
		CloudMetadata cmeta = new CloudMetadata();
		cmeta.setMetadata(file.getMeta().getMap());
		CloudFileInfo fileInfo = new CloudFileInfo();
		fileInfo.setHash(file.getHash());
		fileInfo.setName(this.getName(file.getPath()));
		fileInfo.setMetadata(cmeta);
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
			// File is too large
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

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	private DocSyncFile createDocSyncFileFromCloudFile(CloudFile cloudFile) {

		File file = new File(storagePath + System.getProperty("file.separator")
				+ cloudFile.getName());
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(cloudFile.getContent());
			fileOutputStream.close();

			if (getExtension(cloudFile.getName()).equals("pdf")) {
				PDFMetadata metadata = new PDFMetadata();
				try {
					metadata.setPageNo(Integer.parseInt(cloudFile.getMetadata()
							.getMetadata().get("page")));
				} catch (Exception e) {
					metadata.setPageNo(1);
				}

				PDFDocSyncFile docSyncFile = new PDFDocSyncFile(
						file.getAbsolutePath());
				docSyncFile.setMeta(metadata);
				return docSyncFile;
			}
			else {
				throw new Exception("Unsported file extension");
			}
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
