package coursework.table;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.Vector;

public class FinesTableModel extends AbstractTableModel {
    private static final int COLUMN_COUNT = 9;
    private Vector<Object[]> data;

    public FinesTableModel() {
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
            case 0: return "ID";
            case 1: return "ФИО";
            case 2: return "Номер прав";
            case 3: return "Гос.номер авто";
            case 4: return "Дата нарушения";
            case 5: return "Тип нарушения";
            case 6: return "Штраф";
            case 7: return "Лишение";
            case 8: return "Оплачен";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        if (columnIndex == 0 || columnIndex == 6)
            return Integer.class;
        else if (columnIndex == 4)
            return Date.class;
        else if (columnIndex > 6)
            return Boolean.class;

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