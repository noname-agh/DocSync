package pl.edu.agh.two.gui.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO: add comments.
 * <p/>
 * Creation date: 2011.10.27
 *
 * @author Tomasz Zdybał
 */
public class ExitAction implements ActionListener {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(ExitAction.class);

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		System.exit(0);
	}
}
