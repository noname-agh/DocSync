package pl.edu.agh.two.ws.server;

import org.testng.annotations.Test;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudMetadata;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class DbTest {
	@Test
	public void TestEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testServerUnit");
		EntityManager em = emf.createEntityManager();

		CloudFile cf = new CloudFile();
		cf.setHash("asdasd");
		cf.setName("aaaaaa");
		cf.setContent(new byte[]{0, 0});

		CloudMetadata md = new CloudMetadata();
		md.setVersion(123);
		Map<String, String> metadata = new HashMap<String, String>();
		metadata.put("aaa", "bbb");
		md.setMap(metadata);

		cf.setMetadata(md);

		em.persist(md);
		em.persist(cf);

		em.close();
		emf.close();
	}
}
