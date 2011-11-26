package pl.edu.agh.two.ws;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CloudFileInfo implements Serializable {
	private static final long serialVersionUID = 2340870229278373969L;
	
	@Id
	private String hash;
	private String name;
	private IMetadata metadata;
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(IMetadata metadata) {
		this.metadata = metadata;
	}
}
