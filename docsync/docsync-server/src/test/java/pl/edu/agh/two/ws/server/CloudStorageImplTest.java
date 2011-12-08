package pl.edu.agh.two.ws.server;

import pl.edu.agh.two.ws.dao.CloudFileDAO;
import java.util.HashMap;
import java.util.Map;

import org.mockito.ArgumentCaptor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudMetadata;
import pl.edu.agh.two.ws.CloudStorage;

import static org.testng.AssertJUnit.*;
import static org.mockito.Mockito.*;

public class CloudStorageImplTest {
	
	private CloudStorage storage;
	private CloudFileDAO fileDAO;
	
	@BeforeMethod
	public void setUp() {
		fileDAO = mock(CloudFileDAO.class);
		RSSReader rssReader = mock(RSSReader.class);
		storage = new CloudStorageImpl(fileDAO, rssReader);
	}

	@Test
	public void testAddFile() {
		CloudMetadata metadata = new  CloudMetadata();
		Map<String, String> data = new HashMap<String, String>();
		data.put("key", "value");
		metadata.setMap(data);
		
		CloudFile file = new CloudFile();
		file.setName("filename.pdf");
		file.setMetadata(metadata);
		file.setContent(new byte[] {1,2,3});
		
		ArgumentCaptor<CloudFile> fileCaptor = ArgumentCaptor.forClass(CloudFile.class);
		storage.addFile(file);
		
		verify(fileDAO).addCloudFile(fileCaptor.capture());
		assertEquals("filename.pdf", fileCaptor.getValue().getName());
		assertEquals(new byte[] {1,2,3}, fileCaptor.getValue().getContent());
		assertEquals("value", fileCaptor.getValue().getMetadata().get("key"));
	}

	@Test
	public void testRemoveFile() {
		CloudFile file = mock(CloudFile.class);
		stub(file.getHash()).toReturn("0x123");
		storage.removeFile(file);
		verify(fileDAO).removeCloudFile(file);
	}

}
