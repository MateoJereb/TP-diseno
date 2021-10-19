package componentes_swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import clases.dto.LocalidadDTO;
import clases.dto.ProvinciaDTO;
import clases.gestores.GestorGeografico;
import componentes_swing.ListaLocalidades.MyCellRenderer;

public class ListaProvincias extends JComboBox<ProvinciaDTO> {

	public ListaProvincias() {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setBackground(Color.WHITE);
		
		setProvincias(1); //Por defecto las provincias de Argentina
		
		setRenderer(new MyCellRenderer(this));
		
		addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				setBackground(Color.WHITE);
			}
		});
	}
	
	public void setProvincias(Integer idPais) {
		GestorGeografico gestor = GestorGeografico.getInstance();
		List<ProvinciaDTO> provincias = gestor.buscarProvincias(idPais);
		
		removeAllItems();
		
		ProvinciaDTO primera = new ProvinciaDTO();
		primera.setIdProv(-1);
		addItem(primera);
		
		for(ProvinciaDTO p : provincias) addItem(p);
		
	}
	
	public void vaciar() {
		removeAllItems();
		
		ProvinciaDTO primera = new ProvinciaDTO();
		primera.setIdProv(-1);
		addItem(primera);
	}
	
class MyCellRenderer extends JLabel implements ListCellRenderer{
		
		private JComboBox lista;
		
		public MyCellRenderer(JComboBox lista) {
			this.lista = lista;
		}
		
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			
			ProvinciaDTO seleccionado = (ProvinciaDTO) value;
			
			if(seleccionado.getIdProv() == -1) {
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
