package pl.edu.agh.two.ws.server;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.ws.WebServiceException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;
import pl.edu.agh.two.ws.CloudMetadata;
import pl.edu.agh.two.ws.CloudStorage;
import pl.edu.agh.two.ws.IMetadata;
import pl.edu.agh.two.ws.RSSItem;

@WebService(endpointInterface = "pl.edu.agh.two.ws.CloudStorage", serviceName = "CloudStorage")
public class CloudStorageImpl implements CloudStorage {
	private static final Logger log = LoggerFactory
			.getLogger(CloudStorageImpl.class);

	private EntityManagerFactory emf;

	public CloudStorageImpl() {		
		this(Persistence.createEntityManagerFactory("serverUnit"));
	}

	public CloudStorageImpl(EntityManagerFactory emf) {
		this.emf = emf;
		//JobRunner.runJob();
	}

	@Override
	public CloudFileInfo addFile(CloudFile file) {
		try {
			/* Make sure hash is correct */
			file.setHash(computeHash(file.getContent()));
		} catch (NoSuchAlgorithmException ex) {
			throw new WebServiceException("Error when creating file hash", ex);
		}

		IMetadata metadata = file.getMetadata();
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
			fileList = em.createQuery("from CloudFile", CloudFile.class)
					.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fileList;
	}

	@Override
	public void pushMetadata(CloudFileInfo fileInfo) {
		log.debug("Pushing metadata for file " + fileInfo);
		IMetadata md = fileInfo.getMetadata();
		EntityManager em = emf.createEntityManager();
		CloudFile file = em.find(CloudFile.class, fileInfo.getHash());
		file.setMetadata(md);
		em.getTransaction().begin();
		em.merge(file);
		// em.persist(md);
		// em.persist(fileInfo);
		em.getTransaction().commit();
	}

	private String computeHash(byte[] content) throws NoSuchAlgorithmException {
		return Arrays.toString(DigestUtils.md5(content));
	}

	@Override
	public List<String> getRssChannelList() {
		List<String> subscriptionsList = null;
		try {
			EntityManager em = emf.createEntityManager();
			subscriptionsList = em.createQuery(
					"select c.address from RssChannel c", String.class)
					.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return subscriptionsList;
	}

	@Override
	public void addRSSItem(RSSItem item) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(item);
		em.getTransaction().commit();
		em.close();
	}
	
	@Override
	public void updateRSSItem(RSSItem item) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(item);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void removeRSSItem(RSSItem item) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		RSSItem itemCopy = em.find(RSSItem.class, item.getId()); 
		em.remove(itemCopy);
		em.getTransaction().commit();
		em.close();
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

	@Override
	public List<RSSItem> getRSSItems() {
		List<RSSItem> rssItemList = null;
		try {
			EntityManager em = emf.createEntityManager();
			rssItemList = em.createQuery(
					"select r from RSSItem as r where r.readed = false", RSSItem.class)
					.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rssItemList;
	}
	
	public List<RSSItem> refreshAndGetRssItems() {
		EntityManager em = emf.createEntityManager();
		List<RSSItem> list = em.createNamedQuery("getUnreadedRSSItems").getResultList();
		return list;
	}
}
