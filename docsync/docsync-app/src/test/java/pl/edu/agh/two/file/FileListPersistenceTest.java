package pl.edu.agh.two.file;

import org.testng.annotations.Test;
import pl.edu.agh.two.gui.pdf.PDFMetadata;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class FileListPersistenceTest {
	@Test
	public void testSaveAndLoad() throws IOException {
		File f = new File("storage.test");
		if (!f.exists()) {
			f.createNewFile();
		}

		FileListPersistence flp = new FileListPersistence(f.getAbsolutePath());

		DocSyncFile pdf = new PDFDocSyncFile("src/main/resources/doc.pdf");
		PDFMetadata meta = new PDFMetadata();
		meta.setPageNo(23);
		pdf.setMeta(meta);

		List<DocSyncFile> list = new LinkedList<DocSyncFile>();
		list.add(pdf);

		flp.save(list);

		list = flp.load();

		assertEquals(list.size(), 1);
		meta = (PDFMetadata) list.get(0).getMeta();
		assertEquals(meta.getPageNo(), 23);

		f.delete();
	}
}
