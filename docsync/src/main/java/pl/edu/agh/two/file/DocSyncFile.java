package pl.edu.agh.two.file;

import pl.edu.agh.two.interfaces.IMetadata;

import java.io.Serializable;

public abstract class DocSyncFile implements Serializable {
	protected String path;
	protected String hash;
	protected IMetadata meta;

	public abstract void open();
	public abstract void delete();
	public abstract void update();
}
