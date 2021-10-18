package componentes_swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import clases.dto.PaisDTO;
import clases.dto.TipoDocumentoDTO;
import clases.gestores.GestorGeografico;
import componentes_swing.ListaTipoDoc.MyCellRenderer;

public class ListaPaises extends JComboBox<PaisDTO> {

	public ListaPaises() {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setBackground(Color.WHITE);
		
		GestorGeografico gestor = GestorGeografico.getInstance();
		List<PaisDTO> paises = gestor.buscarPaises();
				
		for(PaisDTO p : paises) {
			addItem(p);
			if(p.getIdPais() == 1) setSelectedItem(p); //Por defecto Argentina
		}
		
		setRenderer(new MyCellRenderer(this));
		
	}
	
class MyCellRenderer extends JLabel implements ListCellRenderer{
		
		private JComboBox lista;
		
		public MyCellRenderer(JComboBox lista) {
			this.lista = lista;
		}
		
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			
			PaisDTO seleccionado = (PaisDTO) value;
			
			if(seleccionado.getIdPais() == -1) {
				setText("Seleccionar...");
				lista.setForeground(Color.GRAY);
				setForeground(Color.GRAY);
			}
			else {
				lista.setForeground(Color.BLACK);
				setForeground(Color.BLACK);
				setText(seleccionado.getNombre());
			}
			
			return this;
		}
	}
	
}
