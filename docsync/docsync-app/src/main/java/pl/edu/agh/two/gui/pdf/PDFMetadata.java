package pl.edu.agh.two.gui.pdf;

import pl.edu.agh.two.file.Metadata;
import pl.edu.agh.two.interfaces.IMetadata;

public class PDFMetadata extends Metadata implements IMetadata {
	private static final long serialVersionUID = -6005595238003881335L;
	public static final String PAGE = "page";

	public PDFMetadata() {
		super();
		this.addValue(PAGE, "1");
	}

	public int getPageNo() {
		return Integer.parseInt(this.getValue(PAGE));
	}

	public void setPageNo(int pageNo) {
		this.addValue(PAGE, pageNo + "");
	}

}
