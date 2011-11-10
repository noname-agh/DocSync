package pl.edu.agh.two.file;

import pl.edu.agh.two.DocSync;
import pl.edu.agh.two.interfaces.IFileService;
import pl.edu.agh.two.interfaces.IMetadata;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudMetadata;
import pl.edu.agh.two.ws.CloudStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class FileService implements IFileService {
	public static final String wsUrl = "http://localhost:8080/CloudStorage?wsdl";
	public static final String wsNamespace = "http://server.ws.two.agh.edu.pl/";
	public static final String wsName = "CloudStorage";

	public CloudStorage cloud = null;
	
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
		// TODO: implementation
		throw new RuntimeException("Not implemented yet!");
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

}
