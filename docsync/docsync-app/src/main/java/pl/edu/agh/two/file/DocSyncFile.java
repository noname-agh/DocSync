package pl.edu.agh.two.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.ws.IMetadata;

import java.io.Serializable;

public class DocSyncFile implements Serializable {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(DocSyncFile.class);


	protected String path;
	protected String hash;
	protected IMetadata meta;

	public DocSyncFile(String path) {
		this.path = path;
		this.meta = new Metadata();
		this.hash = null;
	}

	public void delete() {
		// TODO
	}
	public String getPath() {
		return path;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getHash() {
		return hash;
	}

	public IMetadata getMeta() {
		return meta;
	}

	public void setMeta(IMetadata meta) {
		this.meta = meta;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DocSyncFile other = (DocSyncFile) obj;
		if ((this.hash == null) ? (other.hash != null) : !this.hash.equals(other.hash)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 29 * hash + (this.hash != null ? this.hash.hashCode() : 0);
		return hash;
	}
}
