package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import componentes_swing.*;

public class CargarOtraHabitacion extends JPanel{

	private ImageIcon imagen;
	private EtiquetaJ icono;
	private EtiquetaJ mensaje;
	private JPanel panelBotones;
	private BotonJ seguirCargando;
	private BotonJ cargarOtraHabitacion;
	private BotonJ salir;
	
	public CargarOtraHabitacion() {
		super();
		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		
		GridBagConstraints cons = new GridBagConstraints();
		
		imagen = new ImageIcon("resources\\presioneUnaTecla.png"); 
		icono = new EtiquetaJ();
		icono.setIcon(imagen);
		mensaje = new EtiquetaJ("<html><body>Se ocupará la habitación. ¿Desea seguir cargando<br>pasajeros, cargar otra habitación o guardar los<br>cambios y salir?</html></body>");
		mensaje.setFont(new Font("Microsoft Tai Le",Font.PLAIN,13));
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.WEST;
		cons.insets = new Insets(10,10,10,10);
		add(icono,cons);
		
		cons.gridx = 1;
		cons.gridwidth = 1;
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.1;
		add(mensaje,cons);
		
		panelBotones = new JPanel(new GridBagLayout());
		panelBotones.setBackground(Color.WHITE);
		seguirCargando = new BotonJ("Seguir cargando");
		cargarOtraHabitacion = new BotonJ("Cargar otra habitación");
		salir = new BotonJ("Salir");
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 2;
		add(panelBotones,cons);
		
		cons.weighty = 0.01;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.SOUTHEAST;
		cons.insets = new Insets(0,10,0,0);
		panelBotones.add(seguirCargando,cons);
		
		cons.gridx = 1;
		panelBotones.add(cargarOtraHabitacion,cons);
		
		cons.gridx = 2;
		panelBotones.add(salir,cons);		
	}

	public BotonJ getSeguirCargando() {
		return seguirCargando;
	}

	public BotonJ getCargarOtraHabitacion() {
		return cargarOtraHabitacion;
	}

	public BotonJ getSalir() {
		return salir;
	}
	
	
	
}
