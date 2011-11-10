package pl.edu.agh.two.ws.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudMetadata;
import pl.edu.agh.two.ws.CloudStorage;

import static org.testng.AssertJUnit.*;

public class CloudStorageImplTest {
	
	private CloudStorage storage;
	
	@BeforeMethod
	public void setUp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testServerUnit");
		storage = new CloudStorageImpl(emf);
	}

	@Test
	public void testAddFile() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("key", "value");
		
		CloudMetadata metadata = new  CloudMetadata();
		metadata.setMetadata(data);
		
		CloudFile file = new CloudFile();
		file.setName("abc");
		file.setMetadata(metadata);
		file.setContent(new byte[] {1,2,3});
		
		CloudFileInfo fileInfo = storage.addFile(file);
		CloudFile retFile = storage.getFileWithContent(fileInfo);
		
		assertNotNull(retFile.getHash());
		assertEquals(file.getName(), retFile.getName());
		assertEquals(file.getContent(), retFile.getContent());
	}

	@Test
	public void testRemoveFile() {
		CloudFile file = new CloudFile();
		file.setContent(new byte[] {0});
		CloudFileInfo fileInfo = storage.addFile(file);
		assertEquals(1, storage.getFiles().size());
		storage.removeFile(fileInfo);
		assertEquals(0, storage.getFiles().size());
	}

}
