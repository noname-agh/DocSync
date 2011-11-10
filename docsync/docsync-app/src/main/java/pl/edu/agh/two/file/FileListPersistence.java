package pl.edu.agh.two.file;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileListPersistence {

	String storagePath;

	public FileListPersistence(String storagePath) {
		this.storagePath = storagePath;
	}

	public void save(List<DocSyncFile> list) throws IOException {
		File f = new File(storagePath);
		
		FileOutputStream fos;
		fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(new LinkedList<DocSyncFile>(list));
		fos.close();
	}

	@SuppressWarnings("unchecked")
	public List<DocSyncFile> load() throws IOException {
		File f = new File(storagePath);
		
		if(!f.exists())
			return new LinkedList<DocSyncFile>();
		
		FileInputStream fis;
		fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		try {
			List<DocSyncFile> list = (List<DocSyncFile>) ois.readObject();
			return list;
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		} finally {
			fis.close();
		}
	}

}
