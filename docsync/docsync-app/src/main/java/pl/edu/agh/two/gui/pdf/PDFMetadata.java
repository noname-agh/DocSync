package pl.edu.agh.two.gui.pdf;

import pl.edu.agh.two.interfaces.IMetadata;

public class PDFMetadata implements IMetadata {
	private static final long serialVersionUID = -6005595238003881335L;
	private int pageNo;

	public PDFMetadata() {
		pageNo = 1;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}


}
