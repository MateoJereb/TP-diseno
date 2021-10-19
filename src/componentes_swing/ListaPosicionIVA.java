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

import clases.dto.PosicionIVADTO;
import clases.dto.TipoDocumentoDTO;
import clases.gestores.GestorPasajeros;
import componentes_swing.ListaTipoDoc.MyCellRenderer;

public class ListaPosicionIVA extends JComboBox<PosicionIVADTO> {

	public ListaPosicionIVA() {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setBackground(Color.WHITE);
		
		addItem(new PosicionIVADTO(-1,"",null,null));
		
		GestorPasajeros gestor = GestorPasajeros.getInstance();
		List<PosicionIVADTO> posiciones = gestor.buscarPosicionesIVA();
		for(PosicionIVADTO p : posiciones) addItem(p);
		
		setRenderer(new MyCellRenderer(this));
		
		addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				setBackground(Color.WHITE);
			}
		});
	}
	
class MyCellRenderer extends JLabel implements ListCellRenderer{
		
		private JComboBox lista;
		
		public MyCellRenderer(JComboBox lista) {
			this.lista = lista;
		}
		
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			
			PosicionIVADTO seleccionado = (PosicionIVADTO) value;
			
			if(seleccionado.getId() == -1) {
				setText("Seleccionar...");
				lista.setForeground(Color.GRAY);
				setForeground(Color.GRAY);
			}
			else {
				lista.setForeground(Color.BLACK);
				setForeground(Color.BLACK);
				setText(seleccionado.getPosicion());
			}
			
			return this;
		}
		
	}
	
}
