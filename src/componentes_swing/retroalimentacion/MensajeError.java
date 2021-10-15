package componentes_swing.retroalimentacion;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import componentes_swing.BotonJ;
import componentes_swing.EtiquetaJ;

public class MensajeError extends JDialog{

	private JPanel panel;
	private ImageIcon imagen;
	private EtiquetaJ icono;
	private EtiquetaJ mensaje;
	private BotonJ boton1;
	private BotonJ boton2;
	
	public MensajeError(JFrame ventanaPadre, String textoMensaje, String textoBoton1, String textoBoton2) {
		
		super(ventanaPadre,"Error");
		panel = new JPanel(new GridBagLayout());
		setBackground(Color.WHITE);
		setContentPane(panel);
		
		GridBagConstraints cons = new GridBagConstraints();
		
		imagen = new ImageIcon("resources\\error.png"); 
		icono = new EtiquetaJ();
		icono.setIcon(imagen);
		mensaje = new EtiquetaJ(textoMensaje);
		boton1 = new BotonJ(textoBoton1);
		boton2 = new BotonJ(textoBoton2);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.WEST;
		cons.insets = new Insets(10,10,10,10);
		panel.add(icono,cons);
		
		cons.gridx = 1;
		cons.gridwidth = 2;
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.1;
		panel.add(mensaje,cons);
		
		cons.gridwidth = 1;
		cons.gridx = 1;
		cons.gridy = 1;
		cons.weightx = 0.01;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.EAST;
		
		panel.add(boton1,cons);
		
		cons.gridx = 2;
		
		panel.add(boton2,cons);
		
	}
	
	public void setListeners(ActionListener listener1, ActionListener listener2) {
		boton1.addActionListener(listener1);
		boton2.addActionListener(listener2);
	}
	
}
