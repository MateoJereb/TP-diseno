package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.*;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.github.lgooddatepicker.components.TimePicker;

import base_de_datos.AdministradorBDHabitaciones;
import clase_app.App;
import clases.dto.*;
import clases.gestores.GestorEstadias;
import clases.gestores.GestorPasajeros;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaPasajeros;
import componentes_swing.retroalimentacion.*;
import excepciones.*;

public class GenerarFactura extends JPanel{
	//Componentes
	private JPanel buscarHabitacion;
	private EtiquetaJ eNroHab;
	private EtiquetaJ eHoraSalida;
	private CampoJ cNroHab;
	private BotonJ buscar;
	private JPanel Resultados;
	private TablaJ tabla;
	private ModeloTablaPasajeros modelo;
	private JPanel panelBotones;
	private BotonJ siguiente;
	private BotonJ tercero;
	private BotonJ cancelar;
	private TimePicker horario;
	private List<PasajeroDTO> pasajeros;

	private EstadiaDTO estadiaDTO;
	private PasajeroDTO pasajeroDTO;
	
	
	public GenerarFactura() {
		super();			
				estadiaDTO = null;
				pasajeroDTO =null;
				setBorder(new EmptyBorder(10, 20, 10, 20));
				setLayout(new GridBagLayout());
				setBackground(UIManager.getColor("CheckBox.focus"));
				panelHabitacion();
				panelResultados();
				panelBotones();
	}
	
	public void panelHabitacion() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Buscar Habitación");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		buscarHabitacion = new JPanel();
		buscarHabitacion.setLayout(new GridBagLayout());
		buscarHabitacion.setBackground(Color.WHITE);
		buscarHabitacion.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(20,20,10,20);
		cons.anchor = GridBagConstraints.NORTH;
		this.add(buscarHabitacion, cons);
		
		eNroHab = new EtiquetaJ("Nro. Habitación");
		cNroHab = new CampoJ();
		eHoraSalida = new EtiquetaJ("Hora de Salida");
		horario = new TimePicker();
		buscar = new BotonJ("Buscar");
		
		cons.gridx=0;
		cons.gridy=0;
		cons.weightx=0;
		cons.fill= GridBagConstraints.NONE;
		cons.insets = new Insets(10,5,10,5);
		cons.anchor = GridBagConstraints.CENTER;
		buscarHabitacion.add(eNroHab, cons);
		
		cons.gridx=1;
		cons.weightx=0.1;
		cons.fill= GridBagConstraints.HORIZONTAL;
		cons.anchor= GridBagConstraints.WEST;
		buscarHabitacion.add(cNroHab, cons);
		
		cons.gridx=2;
		cons.weightx=0;
		cons.fill= GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;
		buscarHabitacion.add(eHoraSalida, cons);
		
		cons.gridx=3;
		cons.weightx=0.1;
		cons.fill= GridBagConstraints.HORIZONTAL;
		cons.anchor= GridBagConstraints.WEST;
		horario.setText("hh:mm");
		buscarHabitacion.add(horario, cons);
		
		cons.gridx=4;
		cons.weightx=0;
		cons.anchor= GridBagConstraints.EAST;
		buscarHabitacion.add(buscar, cons);
		
		
	buscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(tiempoinvalido() || habitacioninvalida()){
					String mensaje =  "<html><body>Hay un error en el/los campos Nro. Habitación y/o Hora de Salida.<br>Verifique los datos ingresados e intente de nuevo</body></html>";
					mostrarError(mensaje);
					if(tiempoinvalido())horario.getComponentTimeTextField().setBackground(new Color(255,200,200));
					if(habitacioninvalida())cNroHab.setBackground(new Color(255,200,200));
				}
				else {
					GestorEstadias gestor = GestorEstadias.getInstance();
				try {
					pasajeros = new ArrayList<PasajeroDTO> ();
					pasajeros = gestor.buscarOcupantes(Integer.parseInt(cNroHab.getText()));
					actualizarTabla(pasajeros);
					if(pasajeros.isEmpty()) {
						estadiaDTO =null;
						String mensaje =  "<html><body>No hay Pasajeros asociados a una estadia no paga en esta Habitacion.</body></html>";
						mostrarInformativo(mensaje);
					}
					else {
						estadiaDTO = calcularCosto(cNroHab.getText(), horario.getComponentTimeTextField().getText());
					}
					}catch(HabitacionInexistenteException e1) {
						String mensaje =  "<html><body>No existe una habitación con el nro. indicado.</body></html>";
						mostrarError(mensaje);
					}
					
				}
			}
		});
		
		
	}
	public void panelResultados() {
		
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Resultados");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		Resultados = new JPanel();
		Resultados.setLayout(new GridBagLayout());
		Resultados.setBackground(Color.WHITE);
		Resultados.setBorder(borde);
		

		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(20,20,10,20);
		cons.anchor = GridBagConstraints.NORTH;
		this.add(Resultados, cons);
		
		modelo = new ModeloTablaPasajeros();
		tabla = new TablaJ(modelo);
		JScrollPane scroll = new JScrollPane(tabla);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(200);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(20);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(75);
		tabla.getColumnModel().getColumn(4).setMaxWidth(0);
		tabla.getColumnModel().getColumn(4).setMinWidth(0);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(0);
		
		
		actualizarTabla(new ArrayList<PasajeroDTO>());

		cons.gridy = 0;
		Resultados.add(scroll, cons);
	}
	private void panelBotones() {
		GridBagConstraints cons = new GridBagConstraints();
		panelBotones = new JPanel(new GridBagLayout());
		panelBotones.setBackground(Color.WHITE);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,20,0,20);
		cons.anchor = GridBagConstraints.EAST;
		Resultados.add(panelBotones,cons);
		
		siguiente = new BotonJ("Siguiente");
		cancelar = new BotonJ("Cancelar");
		tercero = new BotonJ("Realizar a nombre de tercero");
		
		cons.gridy=0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(0,10,10,0);
		panelBotones.add(siguiente,cons);
		
		cons.gridx=1;
		panelBotones.add(tercero, cons);
		
		cons.gridx=2;
		panelBotones.add(cancelar,cons);
		
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String textoMensaje = "<html><body>¿Desea cancelar la búsqueda y<br>volver al menú principal?</body></html>";
				MensajeConfirmacion ventanaAux = new MensajeConfirmacion(App.getVentana(),textoMensaje,"Sí","No");
				App.getVentana().setEnabled(false);
				ventanaAux.pack();
				ventanaAux.setLocationRelativeTo(App.getVentana());
				ventanaAux.setVisible(true); 
				
				ventanaAux.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}
				});
				
				ActionListener listenerSi = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ventanaAux.dispose();
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
						App.menuPrincipal();
					}
				 };
				 
				 ActionListener listenerNo = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ventanaAux.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}
				};

				ventanaAux.setListeners(listenerSi, listenerNo);	
			}
		});

		tercero.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(estadiaDTO==null) mostrarError("<html><body>No se ha seleccionado una estadia no paga aún.</html></body>");
				else realizarATercero();
			}
		});
		siguiente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(estadiaDTO==null) mostrarError("<html><body>No se ha seleccionado una estadia no paga aún.</html></body>");
				else {
				if(tabla.getSelectedRow() ==-1) {
					String mensaje =  "<html><body>No se selecciono ningún responsable de pago.<br>Seleccione uno y vuelva a intentarlo</body></html>";
					mostrarError(mensaje);

				}
				else {
					GestorPasajeros gestorPasajero = GestorPasajeros.getInstance();
					if(gestorPasajero.esMayor((Integer) modelo.getValueAt(tabla.getSelectedRow(), 4))) {
						pasajeroDTO = pasajeros.get(tabla.getSelectedRow());
						realizarFactura();
					}
					else {
						String mensaje =  "<html><body>El pasajero seleccionado es Menor.<br>Seleccione otro.</body></html>";
						mostrarError(mensaje);

					}
					
				}
				}
			}
		});
	}
	
	
	private void actualizarTabla(List<PasajeroDTO> pasajeros) {
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		for(PasajeroDTO p : pasajeros) {
			Vector<Object> fila = new Vector<Object>();
			
			fila.add(p.getApellido().get());
			fila.add(p.getNombre().get());
			fila.add(p.getTipo().get());
			fila.add(p.getNro_doc().get());
			fila.add(p.getId().get());
			
			data.add(fila);
		}
		
		modelo.setData(data);
		modelo.fireTableDataChanged();
	}
	private  boolean tiempoinvalido()
    {
		String tiempo = horario.getComponentTimeTextField().getText();
       // String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
 
       // Pattern p = Pattern.compile(regex);

        if (tiempo.length()>0) {
        	//Matcher m = p.matcher(tiempo);
            return !tiempo.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
        }
 
       // Matcher m = p.matcher(tiempo);
        return true;
    }

	private boolean habitacioninvalida() {
		String hab = cNroHab.getText();
		if(hab.length()>0 && hab.matches("[+-]?\\d*(\\.\\d+)?")) {
			return false;
		}
		else {
			return true;
		}
	}
	private void realizarATercero() {

		JFrame ventana = App.getVentana();
		JDialog ventanaTercero = new JDialog(ventana,"Realizar a nombre de tercero");
		FacturarTercero panel = new FacturarTercero(estadiaDTO);
		ventanaTercero.setContentPane(panel);
		ventanaTercero.setSize(500, 250);
		ventanaTercero.setLocationRelativeTo(null);
		App.getVentana().setEnabled(false);

		
		panel.getAceptar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ventanaTercero.dispose();
				ventana.setEnabled(true);
				ventana.setVisible(true);
					
				}
			
		});
		
		panel.getCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaTercero.dispose();
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}
		});
		
		ventanaTercero.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}	
		});
		
		ventanaTercero.setVisible(true);
		
	}
	private void realizarFactura() {
		JFrame ventana = App.getVentana();
		JDialog ventanaRealizarFactura = new JDialog(ventana,"Realizar Factura");
		RealizarFactura panel = new RealizarFactura(estadiaDTO,pasajeroDTO, ventanaRealizarFactura);
		ventanaRealizarFactura.setContentPane(panel);
		ventanaRealizarFactura.pack();
		ventanaRealizarFactura.setLocationRelativeTo(null);
		App.getVentana().setEnabled(false);

		
		
		panel.getCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String textoMensaje = "<html><body>¿Desea cancelar la realizacion de la factura?</body></html>";
				MensajeConfirmacion ventanaAux = new MensajeConfirmacion(App.getVentana(),textoMensaje,"Sí","No");
				App.getVentana().setEnabled(false);
				ventanaAux.pack();
				ventanaAux.setLocationRelativeTo(App.getVentana());
				ventanaAux.setVisible(true); 
				
				ventanaAux.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}
				});
				
				ActionListener listenerSi = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ventanaAux.dispose();
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
						ventanaRealizarFactura.dispose();
						ventana.setEnabled(true);
						ventana.setVisible(true);
					}
				 };
				 
				 ActionListener listenerNo = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ventanaAux.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}
				};

				ventanaAux.setListeners(listenerSi, listenerNo);	
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
	private EstadiaDTO calcularCosto(String nroHab, String horaSalida) {
		EstadiaDTO estadiaDto = new EstadiaDTO();
		GestorEstadias gestor = GestorEstadias.getInstance();
		return gestor.calcularCosto(nroHab,horaSalida);
		
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
	private void mostrarInformativo(String mensaje) {
		MensajeInformativo noEstadia = new MensajeInformativo(App.getVentana(),mensaje, "Aceptar", "");
		noEstadia.getContentPane().remove(3);
		
		App.getVentana().setEnabled(false);
		noEstadia.pack();
		noEstadia.setLocationRelativeTo(App.getVentana());
		noEstadia.setVisible(true); 
		
		noEstadia.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}	
		});
		ActionListener listenerAceptar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				noEstadia.dispose();
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
				
			}
			
		};
		noEstadia.setListeners(listenerAceptar, null);
	}
}
