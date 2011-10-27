package pl.edu.agh.two.file;

import pl.edu.agh.two.interfaces.IMetadata;

import java.io.Serializable;

public abstract class DocSyncFile implements Serializable {
	protected String path;
	protected String hash;
	protected IMetadata meta;

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
