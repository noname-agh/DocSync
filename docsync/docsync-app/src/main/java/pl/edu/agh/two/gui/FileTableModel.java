package pl.edu.agh.two.gui;
import pl.edu.agh.two.file.DocSyncFile;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;

import pl.edu.agh.two.interfaces.IFileList;
import pl.edu.agh.two.interfaces.IMetadata;

public class FileTableModel extends AbstractTableModel implements IFileList{
	private static final long serialVersionUID = 1L;
	protected LinkedList<DocSyncFile> files;
	protected String[] filenames;
	protected String[] columnNames = new String[] { "path"}; 
	protected Class[] columnClasses = new Class[] { String.class};
	
	public FileTableModel() {
	this.files = new LinkedList<DocSyncFile>();	
	}	
	public int getColumnCount() { return 1; } 	
	
	public int getRowCount() { return files.size(); } 	
	
	public String getColumnName(int col) { return columnNames[col]; }
	
	public Class getColumnClass(int col) { return columnClasses[col]; } 
	
		
	
	public Object getValueAt(int row, int col) { 
		
		switch(col) {
			case 0: return files.get(row).getPath();				
			default: return null;
		}
	}
	
	@Override
	public boolean contains(DocSyncFile file)
	{
		return files.contains(file);
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
	public void open(DocSyncFile file) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(DocSyncFile file) {
		//TODO
		
	}
	@Override
	public void add(DocSyncFile file) {
		
			files.add(file);
				
	}
	@Override
	public void updateFile(DocSyncFile file, IMetadata metadata) {
		// TODO Auto-generated method stub
		
	}
	
		
	
}