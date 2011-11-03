package pl.edu.agh.two.interfaces;

import pl.edu.agh.two.file.*;

public interface IFileList {
	public void open(DocSyncFile file);
	public void delete(DocSyncFile file);
	public void add(DocSyncFile file);
	public void updateFile(DocSyncFile file, IMetadata metadata);
}
