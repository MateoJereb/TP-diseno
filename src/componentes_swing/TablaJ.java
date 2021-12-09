package componentes_swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import clases.HabitacionFamily;
import clases.dto.HabitacionDTO;
import clases.dto.HabitacionDobleEstandarDTO;
import clases.dto.HabitacionDobleSuperiorDTO;
import clases.dto.HabitacionFamilyDTO;
import clases.dto.HabitacionIndividualDTO;
import clases.dto.HabitacionSuiteDTO;
import enums.EstadoHabitacion;

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
		setDefaultRenderer(HabitacionDTO.class, new EditorCeldaHabitaciones());
		setDefaultRenderer(LocalDate.class, new EditorCeldaFechas());
		
		setAutoResizeMode(AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setReorderingAllowed(false);
		
	}
	
	class EditorCeldaHabitaciones extends JLabel implements TableCellRenderer{
		
		public EditorCeldaHabitaciones() {
			setOpaque(true);
			
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			
			if(esHabitacionDTO(value)) {
				HabitacionDTO hab = (HabitacionDTO) value;
				
				if(hab.getAOcupar()) {
					setText("A OCUPAR");
					setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
					setBackground(new Color(255,200,200));
					setForeground(Color.BLACK);
					setHorizontalAlignment(EtiquetaJ.CENTER);
					if(hasFocus || isSelected) setBackground(UIManager.getColor("Table.selectionBackground"));
				}
				else {
					if(hab.getEstado_actual().get() == EstadoHabitacion.FUERA_DE_SERVICIO) {
						setText("FUERA DE SERVICIO");
						setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
						setForeground(Color.BLACK);
						setBackground(Color.LIGHT_GRAY);
						setHorizontalAlignment(EtiquetaJ.CENTER);
						
						if(hasFocus || isSelected) setBackground(UIManager.getColor("Table.selectionBackground"));
					}
					if(hab.getEstado_actual().get() == EstadoHabitacion.DISPONIBLE) {
						setText("DISPONIBLE");
						setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
						setForeground(new Color(0,150,0));
						setBackground(Color.WHITE);
						setHorizontalAlignment(EtiquetaJ.CENTER);
						
						if(hasFocus || isSelected) setBackground(UIManager.getColor("Table.selectionBackground"));
				
					}
					if(hab.getEstado_actual().get() == EstadoHabitacion.RESERVADA) {
						setText("RESERVADA");
						setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
						setForeground(new Color(255,165,0));
						setBackground(Color.WHITE);
						setHorizontalAlignment(EtiquetaJ.CENTER);
						
						if(hasFocus || isSelected) setBackground(UIManager.getColor("Table.selectionBackground"));
					}
					if(hab.getEstado_actual().get() == EstadoHabitacion.OCUPADA) {
						setText("OCUPADA");
						setFont(new Font("Microsoft Tai Le",Font.BOLD,12));
						setForeground(new Color(200,0,0));
						setBackground(Color.WHITE);
						setHorizontalAlignment(EtiquetaJ.CENTER);
						
						if(hasFocus || isSelected) setBackground(UIManager.getColor("Table.selectionBackground"));
					}
				}
			}
			
			return this;
		}
		
		private Boolean esHabitacionDTO(Object o) {
			if(o.getClass() == HabitacionIndividualDTO.class) return true;
			if(o.getClass() == HabitacionDobleEstandarDTO.class) return true;
			if(o.getClass() == HabitacionDobleSuperiorDTO.class) return true;
			if(o.getClass() == HabitacionFamilyDTO.class) return true;
			if(o.getClass() == HabitacionSuiteDTO.class) return true;
			
			return false;
		}
		
	}

	class EditorCeldaFechas extends JLabel implements TableCellRenderer{
		
		public EditorCeldaFechas() {
			setOpaque(true);
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			
			if(value.getClass() == LocalDate.class) {
				LocalDate fecha = (LocalDate) value;
				setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
				setText(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				setHorizontalAlignment(EtiquetaJ.CENTER);
			}
			
			return this;
		}
		
	}
}
