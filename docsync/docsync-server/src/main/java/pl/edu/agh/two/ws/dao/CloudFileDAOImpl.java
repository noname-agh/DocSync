package pl.edu.agh.two.ws.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.two.ws.CloudFile;
import pl.edu.agh.two.ws.CloudFileInfo;

@Repository
public class CloudFileDAOImpl implements CloudFileDAO {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public void addCloudFile(CloudFile cloudFile) {
		entityManager.persist(cloudFile);
	}

	@Transactional
	@Override
	public void updateCloudFile(CloudFile cloudFile) {
		entityManager.merge(cloudFile);
	}
	
	@Transactional
	@Override
	public void removeCloudFile(CloudFileInfo cloudFile) {
		CloudFile cloudFileRef = entityManager.getReference(CloudFile.class, cloudFile.getHash());
		entityManager.remove(cloudFileRef);
	}

	@Transactional
	@Override
	public List<CloudFile> getCloudFiles() {
		return entityManager.createQuery("from CloudFile", CloudFile.class).getResultList();
	}

	@Transactional
	@Override
	public CloudFile findCloudFile(String hash) {
		return entityManager.find(CloudFile.class, hash);
	}
}
