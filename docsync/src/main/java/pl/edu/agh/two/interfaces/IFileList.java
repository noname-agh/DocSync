package pl.edu.agh.two.interfaces;

import pl.edu.agh.two.file.*;

public interface IFileList {
	public void open(File file);
	public void delete(File file);
	public void add(File file);
	public void updateFile(File file, IMetadata metadata);
}
