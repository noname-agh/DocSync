package pl.edu.agh.two.ws.server;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudMetadata;

public class DbTest {
	public static void main(String[] argv) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("serverUnit");
		EntityManager em = emf.createEntityManager();
		
		CloudFile cf = new CloudFile();
		cf.setHash("asdasd");
		cf.setName("aaaaaa");
		cf.setContent(new byte[] {0,0});
		
		CloudMetadata md = new CloudMetadata();
		md.setVersion(123);
		Map<String, String> metadata = new HashMap<String, String>();
		metadata.put("aaa", "bbb");
		md.setMetadata(metadata);
		
		cf.setMetadata(md);
		
		em.persist(md);
		em.persist(cf);
		
		em.close();
		emf.close();
	}
}
