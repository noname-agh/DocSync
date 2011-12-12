package pl.edu.agh.two.file;

import pl.edu.agh.two.ws.IMetadata;
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

		ListPersistence<DocSyncFile> flp = new ListPersistence<DocSyncFile>(f.getAbsolutePath());

		DocSyncFile pdf = new DocSyncFile("src/main/resources/doc.pdf");
		PDFMetadata.setPageNumber(pdf.getMeta(), 23);

		List<DocSyncFile> list = new LinkedList<DocSyncFile>();
		list.add(pdf);

		flp.save(list);

		list = flp.load();

		assertEquals(list.size(), 1);
		IMetadata meta = list.get(0).getMeta();
		assertEquals(PDFMetadata.getPageNumber(meta), 23);

		f.delete();
	}
}
