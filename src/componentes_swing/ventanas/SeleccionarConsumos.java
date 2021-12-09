package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import clases.dto.ConsumosDTO;
import clases.dto.PasajeroDTO;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaConsumos;
import componentes_swing.modelos_tablas.ModeloTablaPasajeros;
import componentes_swing.retroalimentacion.MensajeConfirmacion;

public class SeleccionarConsumos extends JPanel{

	private TablaJ tabla; 
	private ModeloTablaConsumos modelo;
	private JPanel panel;
	private BotonJ facturarTodos;
	private BotonJ aceptar;
	private BotonJ cancelar;
	
	public SeleccionarConsumos(List<ConsumosDTO>consumos, RealizarFactura realizarFact) {
		super();			
		
		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		GridBagConstraints cons = new GridBagConstraints();
		
		
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.WHITE);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 3;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(20,20,10,20);
		cons.anchor = GridBagConstraints.NORTH;
		this.add(panel, cons);
		
		modelo = new ModeloTablaConsumos();
		tabla = new TablaJ(modelo);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(300);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(30);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(75);
		
		tabla.getColumnModel().getColumn(3).setCellRenderer(new CantidadAFacturarRenderer());
		
		tabla.getDefaultEditor(String.class).addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				Integer fila = tabla.getSelectedRow();
				Integer columna = tabla.getSelectedColumn();
				Integer cantTotalConsumo = (Integer) modelo.getValueAt(tabla.getSelectedRow(),2 );
				
				if(modelo.getValueAt(fila, columna) == null) {
					modelo.setValueAt("", fila, columna);
				}
				else {
					try {
						Integer num = Integer.parseInt((String) modelo.getValueAt(fila, columna));
						
						if(num == 0) {
							modelo.setValueAt("", fila, columna);
						}
						
						if(num < 0) {
							modelo.setValueAt("", fila, columna);
							modelo.fireTableDataChanged();
						}
						
						if(num > cantTotalConsumo) { 
							modelo.setValueAt(cantTotalConsumo, fila, columna); 
							modelo.fireTableDataChanged();
						}
						
					}catch(NumberFormatException e1) {
						modelo.setValueAt("", fila, columna);
						modelo.fireTableDataChanged();
					}
				}
				
				modelo.setValueAt( (Double.parseDouble(modelo.getValueAt(fila, 1).toString())/ Double.parseDouble(modelo.getValueAt(fila, 2).toString()))*(Double.parseDouble(modelo.getValueAt(fila, columna).toString())), fila, 4);
			}

			@Override
			public void editingCanceled(ChangeEvent e) {}

		});
				
			

		JScrollPane scroll = new JScrollPane(tabla);
		
		cons.gridy = 0;
		panel.add(scroll, cons);
		
		aceptar = new BotonJ("Aceptar");
		cancelar = new BotonJ("Cancelar");
		facturarTodos = new BotonJ("Facturar todos");
		
		
		cons.gridx = 1;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(20,5,10,5);
		cons.anchor = GridBagConstraints.EAST;
		panel.add(aceptar, cons);
		cons.weightx = 0;
		cons.insets = new Insets(20,5,10,20);
		cons.anchor= GridBagConstraints.WEST;
		cons.gridx=2;
		panel.add(cancelar, cons);
		cons.gridx=0;
		cons.insets = new Insets(20,20,10,5);
		panel.add(facturarTodos, cons);
		
		actualizarTabla(consumos);
		
		aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LinkedHashMap<ConsumosDTO,Integer> consumosAFacturar = new LinkedHashMap<ConsumosDTO,Integer>();
				
				for(int i=0; i< modelo.getRowCount(); i++) {
					if(modelo.getValueAt(i, 3).toString() !="") {
					Integer cantidad =  Integer.parseInt(modelo.getValueAt(i, 3).toString());
					if (cantidad >0) consumosAFacturar.put(consumos.get(i), cantidad);
					}
				}
				realizarFact.setConsumos(consumosAFacturar);
			}
		});
		
		
		facturarTodos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i=0; i< modelo.getRowCount(); i++) {
					Integer cantidadMaxima = Integer.parseInt(modelo.getValueAt(i,2).toString());
					modelo.setValueAt(cantidadMaxima, i, 3);
					modelo.setValueAt( (Double.parseDouble(modelo.getValueAt(i, 1).toString())/  cantidadMaxima) *(Double.parseDouble(modelo.getValueAt(i, 3).toString())), i, 4);
				}
			}
		});

		
	}
	private void actualizarTabla(List<ConsumosDTO> consumos) {
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		for(ConsumosDTO c : consumos) {
			Vector<Object> fila = new Vector<Object>();
			
			fila.add(c.getDescripcion().get());
			fila.add(c.getMonto().get() * c.getCantidad().get());
			fila.add(c.getCantidad().get());
			fila.add("");
			fila.add(0.00);
			
			data.add(fila);
		}
		
		modelo.setData(data);
		modelo.fireTableDataChanged();
	}
	public BotonJ getCancelar() {
		return cancelar;
	}
	public BotonJ getAceptar() {
		return aceptar;
	}
}
