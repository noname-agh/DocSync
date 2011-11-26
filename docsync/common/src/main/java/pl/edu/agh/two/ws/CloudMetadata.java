package pl.edu.agh.two.ws;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CloudMetadata implements IMetadata {

	private static final long serialVersionUID = 2736125917839501726L;
	private long version;
	@ElementCollection(fetch = FetchType.LAZY)
	private Map<String, String> map;
	@Id
	@GeneratedValue
	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public long getVersion() {
		return version;
	}

	@Override
	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> metadata) {
		this.map = metadata;
	}

	@Override
	public void put(String key, String value) {
		map.put(key, value);
	}

	@Override
	public String get(String key) {
		return map.get(key);
	}
}
