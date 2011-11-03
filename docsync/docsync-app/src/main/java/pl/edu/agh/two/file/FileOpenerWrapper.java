package pl.edu.agh.two.file;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileOpenerWrapper implements IFileOpener {

	private Map<String, IFileOpener> openers;
	private IFileOpener defaultOpener;

	public FileOpenerWrapper() {
		openers = new HashMap<String, IFileOpener>();
		defaultOpener = new DefaultFileOpener();
	}

	public IFileOpener getDefaultOpener() {
		return defaultOpener;
	}

	public void setDefaultOpener(IFileOpener defaultOpener) {
		this.defaultOpener = defaultOpener;
	}

	public void registerOpener(String extension, IFileOpener opener) {
		openers.put(extension, opener);
	}

	public void open(File file) throws FileOpenException {
		String extension = getExtension(file);
		IFileOpener opener = openers.get(extension);
		if (opener == null && defaultOpener != null) {
			opener = defaultOpener;
		}

		if (opener != null) {
			opener.open(file);
		} else {
			throw new FileOpenException(String.format("No file opener for %s", file));
		}
	}

	private String getExtension(File file) {
		String name = file.getName();
		int i = name.lastIndexOf('.');
		return i >= 0 ? name.substring(i + 1) : null;
	}

}
