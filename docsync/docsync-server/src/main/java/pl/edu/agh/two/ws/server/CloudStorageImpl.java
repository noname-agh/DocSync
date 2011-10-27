package pl.edu.agh.two.ws.server;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudMetadata;
import pl.edu.agh.two.ws.CloudStorage;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.ws.WebServiceException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


@WebService(endpointInterface = "pl.edu.agh.two.ws.CloudStorage", serviceName = "CloudStorage")
public class CloudStorageImpl implements CloudStorage {

	private EntityManagerFactory emf;

	public CloudStorageImpl() {
		emf = Persistence.createEntityManagerFactory("serverUnit");
	}

	public CloudStorageImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public CloudFileInfo addFile(CloudFile file) {
		try {
			/* Make sure hash is correct */
			file.setHash(computeHash(file.getContent()));
		} catch (NoSuchAlgorithmException ex) {
			throw new WebServiceException("Error when creating file hash", ex);
		}

		CloudMetadata metadata = file.getMetadata();
		if (metadata == null) {
			metadata = new CloudMetadata();
			metadata.setVersion(0);
			file.setMetadata(metadata);
		}

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		CloudFileInfo fi = em.find(CloudFileInfo.class, file.getHash());
		boolean updateEntity = false;
		if (fi != null) {
			updateEntity = true;
		}

		if (updateEntity) {
			em.refresh(em.merge(file.getMetadata()));
			em.refresh(em.merge(file));
		} else {
			em.persist(file.getMetadata());
			em.persist(file);
		}

		try {
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


		return file;


	}

	@Override
	public void removeFile(CloudFileInfo file) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		file = em.merge(file);
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
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		CloudFile file = em.find(CloudFile.class, fileInfo.getHash());
		em.getTransaction().commit();
		em.close();
		return file;
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
		CloudMetadata md = fileInfo.getMetadata();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(md);
		em.persist(fileInfo);
		em.getTransaction().commit();

	}

	private String computeHash(byte[] content) throws NoSuchAlgorithmException {
		return Arrays.toString(DigestUtils.md5(content));
	}

	@Override
	public List<String> getRssSubscriptionsList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChannel(String address) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		RssChannel channel = new RssChannel();
		channel.setAddress(address);
		em.persist(channel);
		
		em.getTransaction().commit();
	}

	@Override
	public void removeChannel(String address) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		RssChannel channel = em.find(RssChannel.class, address);
		if(channel != null)
			em.remove(channel);
		
		em.getTransaction().commit();
	}
}
