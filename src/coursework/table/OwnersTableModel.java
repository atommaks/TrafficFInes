package coursework.table;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.Vector;

public class OwnersTableModel  extends AbstractTableModel {
    private static final int COLUMN_COUNT = 8;
    private Vector<Object[]> data;

    public OwnersTableModel() {
        data = new Vector<>();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex){
        switch (columnIndex) {
            case 0: return "ФИО";
            case 1: return "Дата рождения";
            case 2: return "Город";
            case 3: return "Номер прав";
            case 4: return "Дата выдачи";
            case 5: return "Дата окончания";
            case 6: return "Категории";
            case 7: return "Код подразделения ГИБДД";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        if (columnIndex == 1 || columnIndex == 4 || columnIndex == 5)
            return Date.class;
        return String.class;
    }

    @Override
    public void setValueAt(Object value, int i, int j){
        data.get(i)[j] = value;
        fireTableCellUpdated(i, j);
    }

    public void addRow(Object[] row){
        int n = getRowCount();
        data.add(row);
        fireTableRowsInserted(n - 1, n);
    }

    public void deleteRow(int rowIndex) {
        data.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void repaint(){
        if (data.size() != 0) {
            data.clear();
            fireTableDataChanged();
        }
    }
}


