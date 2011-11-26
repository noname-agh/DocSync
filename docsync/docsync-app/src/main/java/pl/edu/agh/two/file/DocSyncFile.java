package pl.edu.agh.two.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.gui.pdf.PDFMetadata;
import pl.edu.agh.two.ws.IMetadata;

import java.io.Serializable;

public abstract class DocSyncFile implements Serializable {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(DocSyncFile.class);


	protected String path;
	protected String hash;
	protected IMetadata meta;

	public DocSyncFile(String path) {
		this.path = path;
		this.meta = new PDFMetadata();
		this.hash = null;
	}

	public abstract void open();

	public abstract void delete();

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
}
