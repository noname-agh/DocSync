package pl.edu.agh.two.ws.server;

import pl.edu.agh.two.ws.dao.RSSItemDAOImpl;
import javax.persistence.EntityManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudMetadata;
import pl.edu.agh.two.ws.CloudStorage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.two.ws.dao.CloudFileDAOImpl;
import pl.edu.agh.two.ws.dao.RSSChannelDAOImpl;
import static org.testng.AssertJUnit.assertEquals;

public class CloudStorageIT {

	private static final String SERVICE_URL = "http://localhost:8888/";

	private Endpoint endpoint;
	private CloudStorage proxy;

	@BeforeClass
	public void setUp() throws MalformedURLException {
		/* Publish service */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testServerUnit");
		EntityManager em = emf.createEntityManager();
		CloudFileDAOImpl cloudFileDAO = new CloudFileDAOImpl();
		cloudFileDAO.setEntityManager(em);
		RSSReader rssReader = new RSSReader(new RSSChannelDAOImpl(em), new RSSItemDAOImpl(em));
		endpoint = Endpoint.publish(SERVICE_URL, new CloudStorageImpl(cloudFileDAO, rssReader));

		/* Create proxy to service */
		Service service = Service.create(new URL(SERVICE_URL + "CloudStorage?wsdl"), new QName(
				"http://server.ws.two.agh.edu.pl/", "CloudStorage"));
		proxy = service.getPort(CloudStorage.class);
	}

	@AfterClass
	public void tearDown() {
		if (endpoint != null) {
			endpoint.stop();
		}
	}

	@Test
	void test() {
		Map<String, String> meta = new HashMap<String, String>();
		meta.put("key", "value");

		CloudMetadata fileMeta = new CloudMetadata();
		fileMeta.setMap(meta);

		CloudFile file = new CloudFile();
		file.setName("name");
		file.setContent(new byte[]{1, 2, 3});
		file.setMetadata(fileMeta);

		CloudFileInfo fileInfo = proxy.addFile(file);

		CloudFile retFile = proxy.getFileWithContent(fileInfo);

		assertEquals(file.getContent(), retFile.getContent());
		assertEquals(file.getName(), retFile.getName());
		assertEquals(0, retFile.getMetadata().getVersion());
		assertEquals("value", retFile.getMetadata().getMap().get("key"));
	}

}
