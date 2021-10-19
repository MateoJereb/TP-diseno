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
import clases.dto.PaisDTO;
import clases.gestores.GestorGeografico;
import componentes_swing.ListaPaises.MyCellRenderer;

public class ListaLocalidades extends JComboBox<LocalidadDTO>{

	public ListaLocalidades() {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setBackground(Color.WHITE);
		
		LocalidadDTO primera = new LocalidadDTO();
		primera.setIdLocalidad(-1);
		addItem(primera);
		
		setRenderer(new MyCellRenderer(this));
		
		addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				setBackground(Color.WHITE);
			}
		});
		
	}
	
	public void setLocalidades(Integer idProv) {
		GestorGeografico gestor = GestorGeografico.getInstance();
		List<LocalidadDTO> localidades = gestor.buscarLocalidades(idProv);
		
		removeAllItems();
		
		LocalidadDTO primera = new LocalidadDTO();
		primera.setIdLocalidad(-1);
		addItem(primera);
	
		for(LocalidadDTO l : localidades) addItem(l);
	}
	
	public void vaciar() {
		removeAllItems();
		
		LocalidadDTO primera = new LocalidadDTO();
		primera.setIdLocalidad(-1);
		addItem(primera);
	}

class MyCellRenderer extends JLabel implements ListCellRenderer{
		
		private JComboBox lista;
		
		public MyCellRenderer(JComboBox lista) {
			this.lista = lista;
		}
		
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			
			LocalidadDTO seleccionado = (LocalidadDTO) value;
			
			if(seleccionado.getIdLocalidad() == -1) {
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
