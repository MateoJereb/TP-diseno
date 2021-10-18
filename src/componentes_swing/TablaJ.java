package componentes_swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import clases.dto.TipoDocumentoDTO;

public class TablaJ extends JTable{

	public TablaJ(TableModel modelo) {
		super(modelo);
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		setRowHeight(22);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();	
		centerRenderer.setHorizontalAlignment(EtiquetaJ.CENTER);
		setDefaultRenderer(String.class, centerRenderer);
		setDefaultRenderer(Double.class, centerRenderer);
		setDefaultRenderer(Integer.class, centerRenderer);
		
		setAutoResizeMode(AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}
	
	
}
