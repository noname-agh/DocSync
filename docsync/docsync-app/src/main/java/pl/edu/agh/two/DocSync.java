package pl.edu.agh.two;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.two.gui.DocSyncGUI;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.10.27
 *
 * @author Tomasz Zdybał
 */
public class DocSync {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(DocSync.class);

	public static void main(String args[]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DocSyncGUI.createAndShowGUI();
			}
		});
	}
}
