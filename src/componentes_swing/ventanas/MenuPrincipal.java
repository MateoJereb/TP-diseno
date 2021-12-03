package componentes_swing.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clase_app.App;
import componentes_swing.*;

public class MenuPrincipal extends JPanel{

	//Panel Botones
	private JPanel panelBotones;
	private BotonJ gestionarPasajeros;
	private BotonJ reservarHabitacion;		
	private BotonJ cancelarReserva;		
	private BotonJ ocuparHabitacion;	
	private BotonJ generarFactura;		
	private BotonJ ingresarPago;		
	private BotonJ ingresarNotaCredito;		
	private BotonJ gestionarListado;
	private BotonJ gestionarResponsable;
	
	//Panel Usuario
	private JPanel panelUsuario;
	private ImageIcon logoUsuario;
	private EtiquetaJ imagenUsuario;
	private EtiquetaJ nombreUsuario;
	private BotonJ gestionarUsuario;
	private BotonJ cerrarSesion;
	
	ActionListener enDesarrollo;
	
	public MenuPrincipal() {
		
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		enDesarrollo = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog ventanaAux = new JDialog(App.getVentana(),"Aviso");
				JPanel panelDesarrollo = new JPanel(new GridBagLayout());
				panelDesarrollo.setBackground(Color.WHITE);
				ventanaAux.setContentPane(panelDesarrollo);
				
				GridBagConstraints cons2 = new GridBagConstraints();
				EtiquetaJ aviso = new EtiquetaJ("Esta funcionalidad se encuentra en desarrollo");
				BotonJ aceptar = new BotonJ("Aceptar");
				panelDesarrollo.add(aviso);
				
				cons2.gridx = 0;
				cons2.gridy = 0;
				cons2.gridwidth = 1;
				cons2.gridheight = 1;
				cons2.weightx = 0.1;
				cons2.fill = GridBagConstraints.HORIZONTAL;
				cons2.insets = new Insets(10,10,10,10);
				panelDesarrollo.add(aviso,cons2);
				
				cons2.gridy = 1;
				cons2.fill = GridBagConstraints.NONE;
				panelDesarrollo.add(aceptar,cons2);
				
				App.getVentana().setEnabled(false);
				ventanaAux.pack();
				ventanaAux.setLocationRelativeTo(App.getVentana());
				ventanaAux.setVisible(true);
				
				aceptar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ventanaAux.dispose();
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}						
				});
				
				ventanaAux.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}
				});
			}
		};
		
		panelBotones();
		panelUsuario();
		
	}
	
	private void panelBotones() {
		TitledBorder borde  = BorderFactory.createTitledBorder("Hotel Premier");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,19));
		borde.setTitleJustification(TitledBorder.CENTER);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelBotones = new JPanel();
		panelBotones.setLayout(new GridBagLayout());
		panelBotones.setBackground(Color.WHITE);
		panelBotones.setBorder(borde);
		
		GridBagConstraints cons = new GridBagConstraints();
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(20,20,20,20);
		add(panelBotones,cons);
		
		gestionarPasajeros = new BotonJ("    Gestionar Pasajeros    ");
		reservarHabitacion = new BotonJ("Reservar Habitación");		
		cancelarReserva = new BotonJ("Cancelar Reserva");		
		ocuparHabitacion = new BotonJ("Ocupar Habitación");	
		generarFactura = new BotonJ("Generar Factura");		
		ingresarPago = new BotonJ("Ingresar Pago");		
		ingresarNotaCredito = new BotonJ("Ingresar Nota de Crédito");		
		gestionarListado = new BotonJ("Gestionar Listados");
		gestionarResponsable = new BotonJ("Gestionar Responsables de Pago");
		
		//Ubicaciones
		cons.insets = new Insets(20,20,20,20); 
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.CENTER;
		
		cons.weighty = 0.1;
		cons.gridwidth = 1;
		cons.gridx = 1;
		cons.gridy = 1;
		panelBotones.add(gestionarPasajeros,cons);
		
		cons.gridx = 1;
		cons.gridy = 2;
		panelBotones.add(reservarHabitacion,cons);
		
		cons.gridx = 1;
		cons.gridy = 3;
		panelBotones.add(cancelarReserva,cons);
		
		cons.gridx = 1;
		cons.gridy = 4;
		panelBotones.add(ocuparHabitacion,cons);
		
		cons.gridx = 2;
		cons.gridy = 1;
		panelBotones.add(generarFactura,cons);
		
		cons.gridx = 2;
		cons.gridy = 2;
		panelBotones.add(ingresarPago,cons);
		
		cons.gridx = 2;
		cons.gridy = 3;
		panelBotones.add(ingresarNotaCredito,cons);
		
		cons.gridx = 2;
		cons.gridy = 4;
		panelBotones.add(gestionarListado,cons);
		
		cons.gridx = 0;
		cons.gridy = 5;
		cons.fill = GridBagConstraints.NONE;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		panelBotones.add(gestionarResponsable,cons);
		
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.NONE;
		
		//Listeners botones
		gestionarPasajeros.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.gestionarPasajeros();
			}
		});
		
		ocuparHabitacion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.ocuparHabitacion(Optional.ofNullable(null));
			}
		});
	
		reservarHabitacion.addActionListener(enDesarrollo);
		cancelarReserva.addActionListener(enDesarrollo);		
		generarFactura.addActionListener(enDesarrollo);	
		ingresarPago.addActionListener(enDesarrollo);		
		ingresarNotaCredito.addActionListener(enDesarrollo);		
		gestionarListado.addActionListener(enDesarrollo);
		gestionarResponsable.addActionListener(enDesarrollo);
	
	}
	
	private void panelUsuario() {
		panelUsuario = new JPanel();
		panelUsuario.setLayout(new GridBagLayout());
		panelUsuario.setBackground(UIManager.getColor("CheckBox.focus"));
		
		GridBagConstraints cons = new GridBagConstraints();
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.01;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.SOUTHEAST;
		cons.insets = new Insets(20,20,20,20);
		add(panelUsuario,cons);
		
		logoUsuario = new ImageIcon("resources\\usuario.png");
		imagenUsuario = new EtiquetaJ();
		imagenUsuario.setIcon(logoUsuario);
		String usuario = "[Usuario]"; //Reemplazar por el usuario
		nombreUsuario = new EtiquetaJ("Usuario: "+usuario);
		nombreUsuario.setFont(new Font("Microsoft Tai Le",Font.PLAIN,11));
		gestionarUsuario = new BotonJ("Gestionar usuario");
		cerrarSesion = new BotonJ("Cerrar sesión");
		
		
		cons.gridx = 0;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(10,10,10,10);
		panelUsuario.add(imagenUsuario,cons);
		
		cons.gridy = 1;
		cons.insets = new Insets(0,0,0,0);
		panelUsuario.add(nombreUsuario,cons);
		
		cons.gridy = 2;
		cons.insets = new Insets(10,20,10,20);
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelUsuario.add(gestionarUsuario,cons);
		
		cons.gridy = 3;
		panelUsuario.add(cerrarSesion,cons);
		
		gestionarUsuario.addActionListener(enDesarrollo);
		cerrarSesion.addActionListener(enDesarrollo);
		
		
	}
}
