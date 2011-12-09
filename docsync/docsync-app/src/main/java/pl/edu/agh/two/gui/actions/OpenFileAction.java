package pl.edu.agh.two.gui.actions;

import java.awt.event.KeyEvent;
import pl.edu.agh.two.file.FileOpenException;
import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.gui.FileTableModel;
import pl.edu.agh.two.interfaces.IFileOpener;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class OpenFileAction extends MouseKeyAdapter {

	IFileOpener opener;

	public OpenFileAction(IFileOpener opener) {
		if (opener == null) {
			throw new NullPointerException();
		}
		this.opener = opener;
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		if (mouseEvent.getClickCount() == 2) {
			JTable jTable = (JTable) mouseEvent.getSource();
			openSelectedFile(jTable);
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
			JTable jTable = (JTable) keyEvent.getSource();
			openSelectedFile(jTable);
		}
	}

	private void openSelectedFile(JTable jTable) {
		FileTableModel fileList = (FileTableModel) jTable.getModel();

		int row = jTable.getSelectedRow();
		row = jTable.convertRowIndexToModel(row);
		if (row != -1) {
			try {
				opener.open(fileList.getRow(row));
			} catch (FileOpenException ex) {
				DocSyncGUI.error("Error opening file: " + ex.getMessage());
			}
		}
	}
	
	
}
