package pl.edu.agh.two.ws;

import java.util.Map;

public class CloudMetadata {

	private long version;
	private Map<String, String> metadata;

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
