package pl.edu.agh.two.file;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.two.interfaces.IFileOpener;

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
		extension = extension.toLowerCase();
		openers.put(extension, opener);
	}

	@Override
	public void open(DocSyncFile docSyncFile) throws FileOpenException {
		File file = new File(docSyncFile.getPath());
		String extension = getExtension(file);
		IFileOpener opener = openers.get(extension);
		if (opener == null && defaultOpener != null) {
			opener = defaultOpener;
		}

		if (opener != null) {
			opener.open(docSyncFile);
		} else {
			throw new FileOpenException(String.format("No file opener for %s", file));
		}
	}

	private String getExtension(File file) {
		String name = file.getName().toLowerCase();
		int i = name.lastIndexOf('.');
		return i >= 0 ? name.substring(i + 1) : null;
	}

}
