package pl.edu.agh.two.file;

import java.lang.*;
import pl.edu.agh.two.interfaces.*;

public abstract class File {
	protected String path;
	protected String hash;
	protected IMetadata meta;

	public abstract void open();
	public abstract void delete();
	public abstract void update();
}
