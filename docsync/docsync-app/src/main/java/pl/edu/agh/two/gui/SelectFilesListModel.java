package pl.edu.agh.two.gui;

import java.util.AbstractList;
import java.util.LinkedList;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import pl.edu.agh.two.ws.CloudFileInfo;

public class SelectFilesListModel implements ListModel {

	AbstractList<CloudFileInfo> fileList = new LinkedList<CloudFileInfo>();

	public void addElement(CloudFileInfo file) {
		fileList.add(file);
	}

	@Override
	public void addListDataListener(ListDataListener arg0) {
	}

	@Override
	public Object getElementAt(int index) {
		return fileList.get(index).getName();
	}

	@Override
	public int getSize() {
		return fileList.size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
	}

	public CloudFileInfo getCloudFileInfoAt(int index) {
		return fileList.get(index);
	}
	
	public void clear() {
		fileList.clear();
	}
}
