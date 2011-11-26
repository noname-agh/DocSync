package pl.edu.agh.two.gui.actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import pl.edu.agh.two.file.FileOpenException;
import pl.edu.agh.two.gui.DocSyncGUI;
import pl.edu.agh.two.gui.FileTableModel;
import pl.edu.agh.two.interfaces.IFileOpener;

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
			if (row != -1) {
				try {
					opener.open(fileList.getRow(row));
				} catch (FileOpenException ex) {
					JOptionPane.showMessageDialog(DocSyncGUI.getFrame(), ex.getMessage(), "Error opening file", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
