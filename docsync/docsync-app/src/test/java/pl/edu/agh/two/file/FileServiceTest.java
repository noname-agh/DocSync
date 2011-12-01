package pl.edu.agh.two.file;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudStorage;
import pl.edu.agh.two.ws.IMetadata;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.11.24
 *
 * @author Tomasz Zdybał
 */
public class FileServiceTest {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(FileServiceTest.class);

	@Test(enabled = false)
	public void testSendFile() throws Exception {
		CloudStorage storageMock = Mockito.mock(CloudStorage.class);

		final CloudFileInfo mock = Mockito.mock(CloudFileInfo.class);
		Mockito.stub(storageMock.addFile(Mockito.<CloudFile>any())).toReturn(mock);

		FileService service = new FileService(storageMock);

		IMetadata meta = Mockito.mock(IMetadata.class);

		Map<String, String> map = new HashMap<String, String>();
		map.put("ala", "makota");
		Mockito.stub(meta.getMap()).toReturn(map);

		DocSyncFile file = Mockito.mock(DocSyncFile.class);
		Mockito.stub(file.getPath()).toReturn("/dev/null");
		Mockito.stub(file.getMeta()).toReturn(meta);

		service.sendFile(file);

		ArgumentCaptor<CloudFile> captor = ArgumentCaptor.forClass(CloudFile.class);
		Mockito.verify(storageMock).addFile(captor.capture());
		assertNotNull(captor.getValue());
		assertNotNull(captor.getValue().getMetadata());
		assertEquals(captor.getValue().getMetadata().get("ala"), "makota");
	}

	@Test(enabled = false)
	public void testGetAllFilesWithContent() throws Exception {

	}

	@Test(enabled = false)
	public void testPushMetadata() throws Exception {

	}

	@Test(enabled = false)
	public void testGetBytesFromFile() throws Exception {

	}
}
