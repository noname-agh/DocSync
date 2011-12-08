package pl.edu.agh.two.gui.actions;

import pl.edu.agh.two.file.FileOpenException;
import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.gui.FileTableModel;
import pl.edu.agh.two.interfaces.IFileOpener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OpenFileAction extends MouseAdapter {

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
			FileTableModel fileList = (FileTableModel) jTable.getModel();

			int row = jTable.getSelectedRow();
			row = jTable.convertRowIndexToModel(row);
			if (row != -1) {
				try {
					opener.open(fileList.getRow(row));
				} catch (FileOpenException ex) {
					JOptionPane.showMessageDialog(DocSyncGUI.getFrame(), ex.getMessage(), "Error opening file",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
