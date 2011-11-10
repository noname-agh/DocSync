package pl.edu.agh.two.ws.server;

import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudMetadata;
import pl.edu.agh.two.ws.CloudStorage;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.xml.ws.Endpoint;
import java.util.Collections;
import java.util.List;

@WebService(endpointInterface = "pl.edu.agh.two.ws.CloudStorage", serviceName = "CloudStorage")
public class CloudStorageImpl implements CloudStorage {
	
	private static final String SERVICE_URL = "http://localhost:8080/CloudStorage";
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("serverUnit");
	private EntityManager em = emf.createEntityManager();

	@Override
	public void addFile(CloudFile file) {
		throw new RuntimeException("Not implemented yet.");
	}

	@Override
	public void removeFile(CloudFileInfo file) {
		throw new RuntimeException("Not implemented yet.");
	}

	@Override
	public List<CloudFileInfo> getFiles() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public CloudFile getFileWithContent(CloudFileInfo fileInfo) {
		throw new RuntimeException("Not implemented yet.");
	}

	@Override
	public List<CloudFile> getAllFilesWithContent() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public void pushMetadata(CloudFileInfo fileInfo) {
			
				
		CloudMetadata md = fileInfo.getMetadata();	
	
		
		em.persist(md);
		em.persist(fileInfo);

	}
	
	public static void main(String[] args) {
		Endpoint.publish(SERVICE_URL, new CloudStorageImpl());
	}

}
