package pl.edu.agh.two.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.11.10
 *
 * @author Tomasz Zdybał
 */
public class DefaultDocSyncFile extends DocSyncFile {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(DefaultDocSyncFile.class);

	public DefaultDocSyncFile(String path) {
		super(path);
	}

	@Override
	public void open() {
		DefaultFileOpener opener = new DefaultFileOpener();
		try {
			opener.open(this);
		} catch (FileOpenException e) {
			log.debug("Error", e);
		}
	}

	@Override
	public void delete() {
		// TODO: implementation

	}
}
