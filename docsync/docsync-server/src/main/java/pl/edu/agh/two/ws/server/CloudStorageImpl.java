package pl.edu.agh.two.ws.server;

import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.ws.Endpoint;

import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudStorage;

@WebService(endpointInterface = "pl.edu.agh.two.ws.CloudStorage", serviceName = "CloudStorage")
public class CloudStorageImpl implements CloudStorage {

	private static final String SERVICE_URL = "http://localhost:8080/";
	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("serverUnit");

	@Override
	public void addFile(CloudFile file) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(file);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void removeFile(CloudFileInfo file) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(file);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public List<CloudFileInfo> getFiles() {
		return new LinkedList<CloudFileInfo>(getAllFilesWithContent());
	}

	@Override
	public CloudFile getFileWithContent(CloudFileInfo fileInfo) {
		throw new RuntimeException("Not implemented yet.");
	}

	@Override
	public List<CloudFile> getAllFilesWithContent() {
		List fileList = null;
		try {
			EntityManager em = emf.createEntityManager();
			fileList = em.createQuery("from CloudFile", CloudFile.class).getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fileList;
	}
	

	@Override
	public void pushMetadata(CloudFileInfo fileInfo) {
		throw new RuntimeException("Not implemented yet.");
	}

	public static void main(String[] args) {
		Endpoint.publish(SERVICE_URL, new CloudStorageImpl());
	}

}
