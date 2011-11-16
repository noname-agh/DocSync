package pl.edu.agh.two.ws;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlMimeType;

@Entity
public class CloudFile extends CloudFileInfo {
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;

	@XmlMimeType("application/octet-stream")
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
