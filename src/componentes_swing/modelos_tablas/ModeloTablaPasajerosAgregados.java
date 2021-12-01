package componentes_swing.modelos_tablas;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModeloTablaPasajerosAgregados extends AbstractTableModel {
	String[] columnNames = {"Apellido","Nombre","Tipo doc.","Nro. doc.","","Resp.",""};
	Vector<Vector<Object>> data;
	
	public ModeloTablaPasajerosAgregados() {
		super();
		data = new Vector<Vector<Object>>();
	}
	
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
	
	public void addRow(Vector<Object> row) {
		data.add(row);
	}
	
	public void removeRow(int i) {
		data.removeElementAt(i);
	}
	
	public boolean isCellEditable(int row, int col) {
		if(col == 5) {
			return true;
		}
		return false;
	}

	public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
 }
}
