package pl.edu.agh.two.ws;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

@Entity
public class CloudFile extends CloudFileInfo {
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
