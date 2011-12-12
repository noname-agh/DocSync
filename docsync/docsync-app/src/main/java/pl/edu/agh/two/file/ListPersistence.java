package pl.edu.agh.two.file;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class ListPersistence<T extends Serializable> {

	String storagePath;

	public ListPersistence(String storagePath) {
		this.storagePath = storagePath;
	}

	public void save(List<T> list) throws IOException {
		File f = new File(storagePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(new LinkedList<T>(list));
			fos.close();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> load() throws IOException {
		File f = new File(storagePath);
		
		if(!f.exists())
			return new LinkedList<T>();
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			List<T> list = (List<T>) ois.readObject();
			return list;
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

}
