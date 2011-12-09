package pl.edu.agh.two.ws.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
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
		return entityManager.createQuery("from RSSItem", RSSItem.class).getResultList();
	}

	@Transactional
	@Override
	public RSSItem findRSSItem(String guid) {
		return entityManager.find(RSSItem.class, guid);
	}
	
	@Transactional
	@Override
	public void updateRSSItem(RSSItem rssItem) {
		entityManager.merge(rssItem);
	}
}
