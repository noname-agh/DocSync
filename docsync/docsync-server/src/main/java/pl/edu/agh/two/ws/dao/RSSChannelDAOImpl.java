package pl.edu.agh.two.ws.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.two.ws.server.RSSChannel;

public class RSSChannelDAOImpl implements RSSChannelDAO {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public RSSChannelDAOImpl() {
	}
	
	public RSSChannelDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public void addRSSChannel(final RSSChannel channel) {
		entityManager.persist(channel);
	}

	@Transactional
	@Override
	public void removeRSSChannel(final RSSChannel channel) {
		RSSChannel channelRef = entityManager.getReference(RSSChannel.class, channel.getAddress());
		entityManager.remove(channelRef);
	}

	@Transactional
	@Override
	public RSSChannel findRSSChannel(final String url) {
		return entityManager.find(RSSChannel.class, url);
	}
	
	@Transactional
	@Override
	public List<RSSChannel> getRSSChannels() {
		return entityManager.createQuery("from RSSChannel", RSSChannel.class).getResultList();
	}
}
