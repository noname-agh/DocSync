package pl.edu.agh.two.ws.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.two.ws.RSSItem;

public class RSSItemDAOImpl implements RSSItemDAO {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public RSSItemDAOImpl() {
	}
	
	public RSSItemDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Transactional
	@Override
	public List<RSSItem> getRSSItems() {
		return entityManager.createQuery("select r from RSSItem as r where r.readed = false", RSSItem.class).getResultList();
	}

	@Transactional
	@Override
	public void updateRSSItem(RSSItem rssItem) {
		entityManager.merge(rssItem);
	}
}
