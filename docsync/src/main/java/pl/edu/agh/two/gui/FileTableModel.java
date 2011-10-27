package pl.edu.agh.two.gui;

import java.util.Date;
import javax.swing.table.AbstractTableModel;

class FileTableModel extends AbstractTableModel {
    private String[] columnNames = {"Filename",
                                    "Path",
                                    "Date",
                                    "Selected"
                                    };
    private Object[][] data = {
    {"SomeBook", "/mylib/books/SomeBook.pdf",
    	new Date(), new Boolean(false)},
    {"SomeArticle", "/mylib/articles/SomeArticle.pdf",
    	new Date(), new Boolean(false)},
    };

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        if (col == 0 || col == 3) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

}
