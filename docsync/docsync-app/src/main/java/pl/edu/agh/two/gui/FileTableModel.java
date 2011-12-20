package pl.edu.agh.two.gui;

import java.io.IOException;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import pl.edu.agh.two.file.DocSyncFile;
import pl.edu.agh.two.file.FileService;
import pl.edu.agh.two.interfaces.IFileList;
import pl.edu.agh.two.log.ILogger;
import pl.edu.agh.two.log.LoggerFactory;
import pl.edu.agh.two.ws.IMetadata;

public class FileTableModel extends AbstractTableModel implements IFileList {
	private static final ILogger LOGGER = LoggerFactory.getLogger(
			FileTableModel.class, DocSyncGUI.getFrame());

	private static final long serialVersionUID = 1L;
	protected LinkedList<DocSyncFile> files;
	protected String[] columnNames = new String[] { "path", "extension" };
	protected Class[] columnClasses = new Class[] { String.class, String.class };

	public FileTableModel() {
		this.files = new LinkedList<DocSyncFile>();
	}

	public LinkedList<DocSyncFile> getDocSyncFileList() {
		return files;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return files.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Class getColumnClass(int col) {
		return columnClasses[col];
	}

	public DocSyncFile getRow(int row) {
		return files.get(row);
	}

	public Object getValueAt(int row, int col) {

		switch (col) {
		case 0:
			return files.get(row).getPath();
		case 1:
			String path = files.get(row).getPath();
			return getExtension(path);
		default:
			return null;
		}
	}

	private static String getExtension(String path) {
		final int index = path.lastIndexOf('.');
		if (index > 0) {
			return path.substring(index + 1);
		} else {
			return "";
		}
	}

	@Override
	public boolean contains(DocSyncFile newfile) {
		for (DocSyncFile file : files) {
			if (file.getHash().equals(newfile.getHash())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void saveList() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getList() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(DocSyncFile file) {
		// TODO

	}

	@Override
	public void add(DocSyncFile file) {
		if (contains(file)) {
			files.add(file);
		}
	}

	@Override
	public void updateFile(DocSyncFile file, IMetadata metadata) {
		metadata.setVersion(metadata.getVersion() + 1);
		file.setMeta(metadata);
		FileService.getInstance().pushMetadata(file);
	}

	@Override
	public void clear() {
		files.clear();

	}

	@Override
	public void addAndSend(DocSyncFile file) {
		if (contains(file)) {
			return;
		}
		try {
			FileService.getInstance().sendFile(file);
			files.add(file);
		} catch (IOException e) {
			LOGGER.error("Cannot send file.", true);
			e.printStackTrace();
		}
	}

}