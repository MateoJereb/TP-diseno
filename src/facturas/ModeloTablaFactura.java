package facturas;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModeloTablaFactura extends AbstractTableModel {

	String[] columnNames = {"Descripci�n","P. unitario","Cantidad","Importe"};
	Vector<Vector<Object>> data;
	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
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
		return columnNames[c];
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
