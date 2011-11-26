package pl.edu.agh.two.gui.pdf;

import pl.edu.agh.two.ws.IMetadata;

public class PDFMetadata {

	public static final String PAGE_KEY = "page";

	private PDFMetadata() {
	}

	public static int getPageNumber(IMetadata metadata) {
		try {
			return Integer.parseInt(metadata.get(PAGE_KEY));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static void setPageNumber(IMetadata metadata, int pageNumber) {
		metadata.put(PAGE_KEY, Integer.toString(pageNumber));
	}
}
