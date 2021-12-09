package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import clase_app.App;
import clases.dto.*;
import clases.gestores.GestorResponsablePago;
import componentes_swing.*;
import componentes_swing.retroalimentacion.*;
import excepciones.RPInexistenteException;

public class FacturarTercero extends JPanel{
	private EtiquetaJ eCuit;
	private CampoJMascara cCuit;
	private EtiquetaJ razonSocial;
	private BotonJ lupa;
	private BotonJ aceptar;
	private BotonJ cancelar;
	private ResponsablePagoDTO respDTO;
	private EstadiaDTO estDTO;
	
	public FacturarTercero(EstadiaDTO estadiaDTO) {
		estDTO = estadiaDTO;
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setBackground(Color.WHITE);
		setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		eCuit = new EtiquetaJ("Ingrese CUIT de tercero");
		
		cCuit = new CampoJMascara();
		razonSocial = new EtiquetaJ("");
		aceptar = new BotonJ("Aceptar");
		cancelar = new BotonJ("Cancelar");
		ImageIcon iconolupa = new ImageIcon("resources\\lupa.png");
		
		lupa = new BotonJ("");
		lupa.setIcon(iconolupa);
		lupa.setBorder(null);
		lupa.requestFocus();
		lupa.setFocusPainted(false);
		
		lupa.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	lupa.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void focusLost(FocusEvent e) {
            	lupa.setBackground(Color.WHITE);
            }
        });
		
		
		
		cons.gridx=0;
		cons.gridy=0;
		cons.weightx=0;
		cons.weighty=0;
		cons.anchor= GridBagConstraints.WEST;
		cons.insets= new Insets(5,40,5,5);
		this.add(eCuit, cons);
		
		cons.gridy=1;
		cons.weightx=0.1;
		cons.fill= GridBagConstraints.HORIZONTAL;
		cons.insets= new Insets(5,40,10,5);
		cons.gridwidth=2;
		cCuit.setPlaceholder("xx-xxxxxxxx-x");
		cCuit.setPhColor(Color.DARK_GRAY);
		this.add(cCuit, cons);
		
		cons.gridwidth=1;
		cons.gridx=2;
		cons.anchor = GridBagConstraints.WEST;
		cons.fill= GridBagConstraints.NONE;
		cons.insets= new Insets(5,5,10,5);
		this.add(lupa, cons);
		
		cons.gridx=0;
		cons.gridy=2;
		cons.weightx=0;
		cons.insets= new Insets(5,40,5,5);
		this.add(razonSocial,cons);
		
		cons.gridx=1;
		cons.gridy=3;
		cons.weightx=0;
		cons.weighty=0;
		cons.fill= GridBagConstraints.NONE;
		cons.anchor= GridBagConstraints.EAST;
		cons.insets= new Insets(10,5,10,0);
		this.add(aceptar, cons);
		
		cons.anchor= GridBagConstraints.WEST;
		cons.gridx=2;
		this.add(cancelar, cons);
		
		cCuit.addKeyListener(new KeyAdapter() {
			public void KeyTyped(KeyEvent e) {
				char car = e.getKeyChar();
				if((car<'0' || car>'9') && car !='-') e.consume();
			}
		});
		
		lupa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GestorResponsablePago gestor = GestorResponsablePago.getInstance();
				respDTO = null;
				if(cCuit.getText().length()>0) {
					try{
						respDTO = gestor.buscarResponsable(cCuit.getText());
						razonSocial.setText("Razón Social: "+respDTO.getRazon_social().get());
						razonSocial.setForeground(Color.BLACK);
						

					}catch (RPInexistenteException e1) {
						razonSocial.setText("Responsable de Pago no encontrado");
						razonSocial.setForeground(Color.RED);
					}
				}
				else {
					razonSocial.setText("Responsable de Pago no encontrado");
					razonSocial.setForeground(Color.RED);
				}
			}
		});

		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if( respDTO == null ) {
					String mensaje =  "<html><body>No existe un Responsable de Pago con el cuit ingesado.<br>Verifique los datos ingresados e intente de nuevo</body></html>";
					mostrarError(mensaje);
				}
				else {
					String mensaje =  "<html><body>La factura se realizará a nombre del tercero con <br>CUIT "+respDTO.getCuit().get()+" y razón social "+respDTO.getRazon_social().get()+"</body></html>";
					MensajeConfirmacion conf = new MensajeConfirmacion(App.getVentana(),mensaje, "Aceptar", "Cancelar");
					
					App.getVentana().setEnabled(false);
					conf.pack();
					conf.setLocationRelativeTo(App.getVentana());
					conf.setVisible(true); 
					
					conf.addWindowListener(new WindowAdapter() {
						
						public void windowClosing(WindowEvent e) {
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}
						});
					
					ActionListener listenerAceptar = new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							
							conf.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
							realizarFactura();
							
						}
						
					};
					ActionListener listenerCancelar = new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							
							conf.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
							
						}
						
					};
					
					conf.setListeners(listenerAceptar, null);
		
	}
			}
			
		});
}
	
	public BotonJ getCancelar() {
		return cancelar;
	}
	private void realizarFactura() {
		JFrame ventana = App.getVentana();
		JDialog ventanaRealizarFactura = new JDialog(ventana,"Realizar Factura");
		RealizarFactura panel = new RealizarFactura(estDTO,respDTO,ventanaRealizarFactura);
		ventanaRealizarFactura.setContentPane(panel);
		ventanaRealizarFactura.setSize(600,700);
		ventanaRealizarFactura.setLocationRelativeTo(null);
		App.getVentana().setEnabled(false);

		
		
		panel.getCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaRealizarFactura.dispose();
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}
		});
		
		ventanaRealizarFactura.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}	
		});
		
		ventanaRealizarFactura.setVisible(true);
		
	}
	private void mostrarError(String mensaje){
		MensajeError error = new MensajeError(App.getVentana(),mensaje, "Aceptar", "");
		error.getContentPane().remove(3);
		
		App.getVentana().setEnabled(false);
		error.pack();
		error.setLocationRelativeTo(App.getVentana());
		error.setVisible(true); 
		
		error.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}	
		});
		ActionListener listenerAceptar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				error.dispose();
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
				
			}
			
		};
		error.setListeners(listenerAceptar, null);
	}
	public BotonJ getAceptar() {
		return aceptar;
	}
}
