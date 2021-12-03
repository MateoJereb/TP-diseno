package componentes_swing.ventanas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import clases.dto.ReservaDTO;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaReservas;

public class FechasReservadas extends JPanel {

	private ModeloTablaReservas modelo;
	private TablaJ tabla;
	private JPanel panelBotones;
	private BotonJ ocuparIgual;
	private BotonJ volver;
	
	public FechasReservadas(List<ReservaDTO> reservas) {
		super();
		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		GridBagConstraints cons = new GridBagConstraints();
		
		modelo = new ModeloTablaReservas();
		tabla = new TablaJ(modelo);
		JScrollPane scroll = new JScrollPane(tabla);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
		
		modelo.setData(new Vector<Vector<Object>>());
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(20,20,10,20);
		add(scroll,cons);
		
		panelBotones = new JPanel(new GridBagLayout());
		panelBotones.setBackground(UIManager.getColor("CheckBox.focus"));
		ocuparIgual = new BotonJ("Ocupar igual");
		volver = new BotonJ("Volver");
		
		cons.gridy = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.weighty = 0;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(0,20,0,20);
		add(panelBotones,cons);
		
		cons.gridy = 1;
		cons.weighty = 0.01;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,10,10,0);
		panelBotones.add(ocuparIgual,cons);
		
		cons.gridx=1;
		cons.insets = new Insets(0,10,10,0);
		panelBotones.add(volver,cons);
		
	}
	
	public BotonJ getOcuparIgual() {
		return ocuparIgual;
	}
	
	public BotonJ getVolver() {
		return volver;
	}
	
}
