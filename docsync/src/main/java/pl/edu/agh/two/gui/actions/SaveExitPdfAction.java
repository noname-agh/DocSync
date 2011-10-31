package pl.edu.agh.two.gui.actions;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.icepdf.ri.common.SwingController;


public class SaveExitPdfAction implements WindowListener {

	private SwingController controller;
	
	public SaveExitPdfAction(SwingController controller) {
		this.controller = controller;
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		int pageNr = controller.getCurrentPageNumber() + 1;
		System.out.println("Saving on page " + pageNr + " and Exiting...");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
