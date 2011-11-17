package pl.edu.agh.two.file;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.gui.pdf.PDFMetadata;
import pl.edu.agh.two.interfaces.IMetadata;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

public abstract class DocSyncFile implements Serializable {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(DocSyncFile.class);


	protected String path;
	protected String hash;
	protected IMetadata meta;

	public DocSyncFile(String path) {
		this.hash = computeHash(path);
		this.path = path;
		this.meta = new PDFMetadata();
	}

	private String computeHash(String path) {
		try {
			InputStream inputStream = new FileInputStream(path);
			return Arrays.toString(DigestUtils.md5(inputStream));
		} catch (Exception e) {
			log.debug("Error", e);
			return null;
		}
	}


	public abstract void open();

	public abstract void delete();

	public String getPath() {
		return path;
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
