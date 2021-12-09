package componentes_swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import componentes_swing.EtiquetaJ;

public class CantidadAFacturarRenderer extends JLabel implements TableCellRenderer {
	 
	private Integer capacidadMaxima;
	
	public CantidadAFacturarRenderer() {
		setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
			
			setFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
			setBackground(Color.WHITE);
			setHorizontalAlignment(EtiquetaJ.CENTER);
			
			if(isSelected) setBackground(UIManager.getColor("Table.selectionBackground"));
			if(hasFocus) setBorder(UIManager.getBorder("Table.focusCellBackground"));
			
			if(value != null) {
				try {
					Integer num = Integer.parseInt(value.toString());
					setText(num.toString());
					
					if(num == 0) setForeground(Color.GRAY);
					else setForeground(Color.BLACK);
				}
				catch(NumberFormatException e1) {
					setText("0");
					setForeground(Color.GRAY);
				}
			}
			else {
				setText("0");
				setForeground(Color.GRAY);
			}
			
		return this;
	}

}