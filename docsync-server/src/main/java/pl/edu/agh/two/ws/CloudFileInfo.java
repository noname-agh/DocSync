package pl.edu.agh.two.ws;

public class CloudFileInfo {
	private String hash;
	private String name;
	private CloudMetadata metadata;

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

	public CloudMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(CloudMetadata metadata) {
		this.metadata = metadata;
	}
}
