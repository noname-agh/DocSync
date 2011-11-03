package pl.edu.agh.two.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.gui.pdf.PDFMetadata;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.11.03
 *
 * @author Tomasz Zdybał
 */
public class PDFDocSyncFile extends DocSyncFile {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(PDFDocSyncFile.class);

	public PDFDocSyncFile(String path) {
		this.hash = computeHash(path);
		this.path = path;
		this.meta = new PDFMetadata();
	}

	private String computeHash(String path) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			InputStream is = new FileInputStream(path);
			try {
				is = new DigestInputStream(is, md);
				// read stream to EOF as normal...
			} finally {
				is.close();
			}
			return Arrays.toString(md.digest());
		} catch (Exception e) {
			log.debug("Error", e);
			return null;
		}
	}

	@Override
	public void open() {
		// TODO: implementation

	}

	@Override
	public void delete() {
		// TODO: implementation

	}
}
