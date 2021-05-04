package coursework.table;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class CarsTableModel extends AbstractTableModel {
    private static final int COLUMN_COUNT = 5;
    private Vector<Object[]> data;

    public CarsTableModel() {
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
            case 0: return "Гос.номер авто";
            case 1: return "Тип кузова";
            case 2: return "Марка";
            case 3: return "Цвет";
            case 4: return "Владелец";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
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

