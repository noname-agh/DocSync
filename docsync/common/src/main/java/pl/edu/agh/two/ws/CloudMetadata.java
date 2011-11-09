package pl.edu.agh.two.ws;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CloudMetadata implements Serializable {
	private static final long serialVersionUID = 2736125917839501726L;
	
	private long version;
	
	@ElementCollection(fetch=FetchType.LAZY)
	private Map<String, String> metadata;

	@Id
	@GeneratedValue
	private Long id;
	public Long getId() { return this.id; }
	public void setId(Long id) { this.id = id; }
	
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
}
