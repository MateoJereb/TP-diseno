package componentes_swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import clases.TipoDocumento;
import clases.dto.TipoDocumentoDTO;
import clases.gestores.GestorPasajeros;


public class ListaTipoDoc extends JComboBox<TipoDocumentoDTO> {

	public ListaTipoDoc() {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setBackground(Color.WHITE);
		
		addItem(new TipoDocumentoDTO(-1,""));
		
		GestorPasajeros gestor = GestorPasajeros.getInstance();
		List<TipoDocumentoDTO> tipos = gestor.buscarTiposDocumento();
		for(TipoDocumentoDTO t : tipos) addItem(t);
		
		
		setRenderer(new MyCellRenderer(this));
	}
	
	public ListaTipoDoc(TipoDocumentoDTO seleccionado) {
		super();
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		setBackground(Color.WHITE);
		
		addItem(new TipoDocumentoDTO(-1,""));
		
		GestorPasajeros gestor = GestorPasajeros.getInstance();
		List<TipoDocumentoDTO> tipos = gestor.buscarTiposDocumento();
		for(TipoDocumentoDTO t : tipos) addItem(t);
		
		setSelectedItem(seleccionado);
		
		setRenderer(new MyCellRenderer(this));
	}
	
	class MyCellRenderer extends JLabel implements ListCellRenderer{
		
		private JComboBox lista;
		
		public MyCellRenderer(JComboBox lista) {
			this.lista = lista;
		}
		
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			
			TipoDocumentoDTO seleccionado = (TipoDocumentoDTO) value;
			
			if(seleccionado.getId() == -1) {
				setText("Seleccionar...");
				lista.setForeground(Color.GRAY);
				setForeground(Color.GRAY);
			}
			else {
				lista.setForeground(Color.BLACK);
				setForeground(Color.BLACK);
				setText(seleccionado.getTipo());
			}
			
			return this;
		}
		
	}
	
}
