package componentes_swing.modelos_tablas;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModeloTablaHabitaciones extends AbstractTableModel{
	Vector<String> columnNames = new Vector<String>();
	Vector<Vector<Object>> data;
	
	public ModeloTablaHabitaciones() {
		super();
		columnNames.add("Fecha");
		data = new Vector<Vector<Object>>();
	}
	
	public void addColumn(String c) {
		columnNames.add(c);
	}
	
	public void addAllColumn(Vector<String> columns) {
		columnNames.addAll(columns);
	}
	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex){
		Object salida = null;
		
		try{
			salida = data.get(rowIndex).get(columnIndex);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			salida = new Object();
		}
		
		return salida;
	}
	
	public String getColumnName(int c) {
		return columnNames.get(c);
	}
	
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
	public void setData(Vector<Vector<Object>> data) {
		this.data = data;
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
 }
}
