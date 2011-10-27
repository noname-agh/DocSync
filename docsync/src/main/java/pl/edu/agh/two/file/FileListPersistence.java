package pl.edu.agh.two.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FileListPersistence {

	String storagePath;

	public FileListPersistence(String storagePath) {
		this.storagePath = storagePath;
	}

	public void save(List<String> list) throws IOException {
		FileOutputStream fos;
		fos = new FileOutputStream(storagePath);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(new LinkedList<String>(list));
		fos.close();
	}

	@SuppressWarnings("unchecked")
	public List<String> load() throws IOException {
		FileInputStream fis;
		fis = new FileInputStream(storagePath);
		ObjectInputStream ois = new ObjectInputStream(fis);
		try {
			List<String> list = (List<String>) ois.readObject();
			return list;
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		} finally {
			fis.close();
		}
	}

}
