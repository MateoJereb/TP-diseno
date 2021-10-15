package componentes_swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import clases.TipoDocumento;

public class ListaTipoDoc extends JComboBox<TipoDocumento> {

	public ListaTipoDoc() {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setBackground(Color.WHITE);
		
		addItem(new TipoDocumento(-1,""));
		
		//Agregar resto de elementos
		
		setRenderer(new MyCellRenderer(this));
	}
	
	class MyCellRenderer extends JLabel implements ListCellRenderer{
		
		private JComboBox lista;
		
		public MyCellRenderer(JComboBox lista) {
			this.lista = lista;
		}
		
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			
			TipoDocumento seleccionado = (TipoDocumento) value;
			
			if(seleccionado.getId() == -1) {
				setText("Seleccionar...");
				lista.setForeground(Color.GRAY);
				setForeground(Color.GRAY);
			}
			else {
				lista.setForeground(Color.BLACK);
				setForeground(Color.BLACK);
			}
			
			return this;
		}
		
	}
	
}
