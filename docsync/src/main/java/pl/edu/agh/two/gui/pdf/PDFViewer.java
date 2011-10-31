package pl.edu.agh.two.gui.pdf;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.gui.actions.SaveExitPdfAction;
/**
 * 
 * @author Grzegorz Jankowski & Jaroslaw Szczesniak
 * 
 * @Usage
 * PDFViewer viewer = new PDFViewer();
 * viewer.open(filePath);
 * viewer.open(filePath, pageNr);
 *
 */
public class PDFViewer extends JFrame {

	private static final Logger logger = LoggerFactory.getLogger(DocSyncGUI.class);
	private static final long serialVersionUID = 1L;
	private SwingController controller;
	private SwingViewBuilder factory;
	private JPanel viewComponentPanel;

	public PDFViewer() {
		super();
		init();
		this.setVisible(true);
	}

	private void init() {
		try {
			this.setTitle("PDFViewer");
			this.setBounds(0, 0, 800, 600);
			this.setLocationRelativeTo(null);
			controller = new SwingController();
			factory = new SwingViewBuilder(controller);
			viewComponentPanel = factory.buildViewerPanel();
			this.addWindowListener(new SaveExitPdfAction(controller));
			this.getContentPane().add(viewComponentPanel);
			this.pack();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	public void open(String filePath) {
		try {
			this.setTitle("PDFViewer [::] " + filePath);
			controller.openDocument(filePath);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	public void open(String filePath, int pageNr) {
		try {
			this.setTitle("PDFViewer [::] " + filePath);
			controller.openDocument(filePath);
			controller.goToDeltaPage(--pageNr);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

}
