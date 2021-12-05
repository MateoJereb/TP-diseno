package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import clase_app.App;
import clases.dto.*;
import clases.gestores.GestorPasajeros;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaPasajeros;
import componentes_swing.modelos_tablas.ModeloTablaPasajerosAgregados;
import componentes_swing.retroalimentacion.MensajeConfirmacion;
import componentes_swing.retroalimentacion.MensajeInformativo;

public class CargarPasajeros extends JPanel{

	private JPanel panelSuperior;
	private JPanel panelInferior;
	
	//Panel informacion
	private JPanel panelInformacion;
	private EtiquetaJ eInfo;
	
	//Panel busqueda
	private JPanel panelBusqueda;
	private EtiquetaJ eApellido;
	private CampoJ cApellido;
	private EtiquetaJ eNombre;
	private CampoJ cNombre;
	private EtiquetaJ eTipoDoc;
	private ListaTipoDoc lTipoDoc; 
	private EtiquetaJ eNroDoc;
	private CampoJ cNroDoc;
	private BotonJ buscar;
	
	//Panel resultados
	private JPanel panelResultados;
	private ModeloTablaPasajeros modeloResultados;
	private TablaJ tablaResultados;
	
	//Panel agregados
	private JPanel panelAgregados;
	private ModeloTablaPasajerosAgregados modeloAgregados;
	private TablaJ tablaAgregados;
	private List<PasajeroDTO> pasajerosAgregados = new ArrayList<PasajeroDTO>();
	private JPanel panelBotones;
	private EtiquetaJ capacidadAlcanzada;
	private BotonJ siguiente;
	private BotonJ cancelar;
	private ImageIcon iconoBasura;
	
	private BotonJ menuPrincipal;
	
	//Cargar otra habitacion
	private JDialog ventanaCargarOtraHabitacion;
	private CargarOtraHabitacion panelCargarOtraHabitacion;
	
	public CargarPasajeros(HabitacionDTO habitacion, LocalDate fechaDesde, LocalDate fechaHasta, Optional<PasajeroDTO> responsable) {
		super();			
		
		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.NORTH;
		
		panelSuperior = new JPanel(new GridBagLayout());
		panelSuperior.setBackground(UIManager.getColor("CheckBox.focus"));
		add(panelSuperior,cons);
		
		
		cons.gridy = 1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.CENTER;
		
		panelInferior = new JPanel(new GridBagLayout());
		panelInferior.setBackground(UIManager.getColor("CheckBox.focus"));
		add(panelInferior,cons);
		
		panelInformacion(habitacion);
		panelBusqueda();
		panelResultados();
		panelAgregados();
		listeners(habitacion,responsable,fechaDesde,fechaHasta);
		
		if(responsable.isPresent()) inicializarResponsable(responsable);
	}
	
	private void panelInformacion(HabitacionDTO habitacion) {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Información Habitación");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelInformacion = new JPanel(new GridBagLayout());
		panelInformacion.setBackground(Color.WHITE);
		panelInformacion.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.01;
		cons.weighty = 0.01;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(20,10,10,10);
		cons.anchor = GridBagConstraints.NORTH;
		panelSuperior.add(panelInformacion,cons);
		
		eInfo = new EtiquetaJ("<html><body>Se está ocupando la habitación:<br><br>&emsp\u2022Nro: "+habitacion.getNro().get()+"<br><br>&emsp\u2022Tipo: "+textoTipoHab(habitacion)+"<br><br></html></body>");
		
		cons.weighty = 0.1;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(0,20,0,20);
		panelInformacion.add(eInfo,cons);
	}
	
	private void panelBusqueda() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Buscar Pasajero");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelBusqueda = new JPanel(new GridBagLayout());
		panelBusqueda.setBackground(Color.WHITE);
		panelBusqueda.setBorder(borde);
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(20,10,10,10);
		cons.anchor = GridBagConstraints.NORTH;
		panelSuperior.add(panelBusqueda,cons);
		
		eApellido = new EtiquetaJ("Apellido");
		cApellido = new CampoJ();
		eNombre = new EtiquetaJ("Nombre");
		cNombre = new CampoJ();
		eTipoDoc = new EtiquetaJ("Tipo de documento");
		lTipoDoc = new ListaTipoDoc();
		eNroDoc = new EtiquetaJ("Nro. documento");
		cNroDoc = new CampoJ();
		buscar = new BotonJ("Buscar");
		
		cons.gridx = 0;
		cons.anchor = GridBagConstraints.CENTER;
		cons.weighty = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelBusqueda.add(eApellido,cons);
		
		cons.gridx = 2;
		panelBusqueda.add(eNombre,cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		panelBusqueda.add(eTipoDoc,cons);
		
		cons.gridx = 2;
		panelBusqueda.add(eNroDoc,cons);
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelBusqueda.add(cApellido,cons);
		
		cons.gridx = 3;
		cons.weightx = 0.15;
		panelBusqueda.add(cNombre,cons);
		
		cons.gridx = 1;
		cons.gridy = 1;
		cons.weightx = 0.1;
		panelBusqueda.add(lTipoDoc,cons);
		
		cons.gridx = 3;
		cons.weightx = 0.15;
		panelBusqueda.add(cNroDoc,cons);
		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.gridwidth = 4;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(0,20,10,20);
		panelBusqueda.add(buscar,cons);
	}
	
	private void panelResultados() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Resultados");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelResultados = new JPanel(new GridBagLayout());
		panelResultados.setBackground(Color.WHITE);
		panelResultados.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.45;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,10,0,10);
		panelInferior.add(panelResultados,cons);
		
		modeloResultados = new ModeloTablaPasajeros();
		tablaResultados = new TablaJ(modeloResultados);
		JScrollPane scroll = new JScrollPane(tablaResultados);
		tablaResultados.getColumnModel().getColumn(4).setMaxWidth(0);
		tablaResultados.getColumnModel().getColumn(4).setMinWidth(0);
		tablaResultados.getColumnModel().getColumn(4).setPreferredWidth(0);
		
		actualizarTablaResultados(new ArrayList<PasajeroDTO>());
		
		TableRowSorter<ModeloTablaPasajeros> sorter = new TableRowSorter<ModeloTablaPasajeros>(modeloResultados);
		tablaResultados.setRowSorter(sorter);
		
		cons.insets = new Insets(10,10,10,10);
		panelResultados.add(scroll,cons);
		
	}
	
	private void panelAgregados() {
		iconoBasura = new ImageIcon("resources\\basura.png");
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Pasajeros agregados");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelAgregados = new JPanel(new GridBagLayout());
		panelAgregados.setBackground(Color.WHITE);
		panelAgregados.setBorder(borde);
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,10,0,10);
		panelInferior.add(panelAgregados,cons);
		
		menuPrincipal = new BotonJ("Salir al menú principal");
		
		modeloAgregados = new ModeloTablaPasajerosAgregados();
		tablaAgregados = new TablaJ(modeloAgregados);
		JScrollPane scroll = new JScrollPane(tablaAgregados);
		
		tablaAgregados.getColumnModel().getColumn(4).setMaxWidth(0);
		tablaAgregados.getColumnModel().getColumn(4).setMinWidth(0);
		tablaAgregados.getColumnModel().getColumn(4).setPreferredWidth(0);
		tablaAgregados.getColumnModel().getColumn(0).setPreferredWidth(70);
		tablaAgregados.getColumnModel().getColumn(1).setPreferredWidth(70);
		tablaAgregados.getColumnModel().getColumn(2).setPreferredWidth(70);
		tablaAgregados.getColumnModel().getColumn(3).setPreferredWidth(70);
		tablaAgregados.getColumnModel().getColumn(5).setPreferredWidth(10);
		tablaAgregados.getColumnModel().getColumn(6).setPreferredWidth(5);
		
		tablaAgregados.setRowSelectionAllowed(false);
		tablaAgregados.setColumnSelectionAllowed(false);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,10,10,10);
		panelAgregados.add(scroll,cons);
		
		cons.gridx = 1;
		cons.gridy = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,10,5,25);
		panelInferior.add(menuPrincipal,cons);
		
		capacidadAlcanzada = new EtiquetaJ("Capacidad de la habitación alcanzada");
		siguiente = new BotonJ("Siguiente");
		cancelar = new BotonJ("Cancelar");
		
		panelBotones = new JPanel(new GridBagLayout());
		panelBotones.setBackground(Color.WHITE);
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,20,0,10);
		cons.anchor = GridBagConstraints.SOUTHEAST;
		panelAgregados.add(panelBotones,cons);
		
		cons.gridy = 1;
		cons.weighty = 0.01;
		cons.weightx = 0;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(0,10,10,0);
		capacidadAlcanzada.setForeground(Color.WHITE);
		panelBotones.add(capacidadAlcanzada,cons);
		
		cons.gridx = 1;
		cons.insets = new Insets(0,10,10,0);
		panelBotones.add(siguiente,cons);
		
		cons.gridx=2;
		cons.insets = new Insets(0,10,10,0);
		panelBotones.add(cancelar,cons);
		
	}
	
	private void listeners(HabitacionDTO habitacion, Optional<PasajeroDTO> responsable,LocalDate fechaDesde, LocalDate fechaHasta) {
		buscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PasajeroDTO datos = new PasajeroDTO();
				datos.setApellido(cApellido.getText());
				datos.setNombre(cNombre.getText());
				
				if(((TipoDocumentoDTO)lTipoDoc.getSelectedItem()).getId() != -1) {
					datos.setIdTipoDoc(((TipoDocumentoDTO)lTipoDoc.getSelectedItem()).getId());
				}
				
				if(cNroDoc.getText().length() > 0) {
					datos.setNro_doc(cNroDoc.getText());
				}
				
				GestorPasajeros gestor = GestorPasajeros.getInstance();
				List<PasajeroDTO> resultado = gestor.buscarPasajeros(datos);
				actualizarTablaResultados(resultado);
				
				if(resultado.size() == 0) {
					String mensaje = "<html><body>No se encontraron resultados que coincidan<br>con los criterios de búsqueda.</html></body>";
					MensajeInformativo noHayResultados = new MensajeInformativo(App.getVentana(),mensaje,"Aceptar","");
					noHayResultados.getContentPane().remove(3); //Quitar el segundo boton
					
					App.getVentana().setEnabled(false);
					noHayResultados.pack();
					noHayResultados.setLocationRelativeTo(App.getVentana());
					noHayResultados.setVisible(true);
					
					noHayResultados.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}
					});
					
					ActionListener listenerAceptar = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							noHayResultados.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}
					};
					
					noHayResultados.setListeners(listenerAceptar, null);
				}
			}
		});
	
		siguiente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO listener siguiente
			}
		});
		
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String textoMensaje = "¿Desea cancelar la ocupación de la habitación?";
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
						App.ocuparHabitacion(responsable,Optional.of(fechaDesde),Optional.of(fechaHasta));
						
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
		
		menuPrincipal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String textoMensaje = "<html><body>¿Desea cancelar la ocupación de la habitación<br>y volver al menú principal?</body></html>";
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
	
		tablaResultados.addMouseListener(new MouseAdapter() {
			@Override
			 public void mouseClicked(MouseEvent evt) {
				Integer fila = tablaResultados.getSelectedRow();
				Integer id = (Integer) modeloResultados.getValueAt(fila, 4);
				String apellido = (String) modeloResultados.getValueAt(fila, 0);
				String nombre = (String) modeloResultados.getValueAt(fila, 1);
				String tipoDoc = (String) modeloResultados.getValueAt(fila, 2);
				String nroDoc = (String) modeloResultados.getValueAt(fila, 3);
				
				PasajeroDTO pasajeroDTO = new PasajeroDTO();
				pasajeroDTO.setApellido(apellido);
				pasajeroDTO.setNombre(nombre);
				pasajeroDTO.setTipo(tipoDoc);
				pasajeroDTO.setNro_doc(nroDoc);
				pasajeroDTO.setId(id);
				
				if(!pasajerosAgregados.contains(pasajeroDTO)) {
					if(modeloAgregados.getRowCount() < habitacion.getCapacidad().get()) {
						pasajerosAgregados.add(pasajeroDTO);
						actualizarTablaAgregados(pasajeroDTO);
					}
					else {
						capacidadAlcanzada.setForeground(Color.RED);
					}
				}
			}
		});
		
		tablaAgregados.addMouseListener(new MouseAdapter() {
			@Override
			 public void mouseClicked(MouseEvent evt) {
				int fila = tablaAgregados.getSelectedRow();
				int columna = tablaAgregados.getSelectedColumn();
				
				if(columna == 6) {
					PasajeroDTO dtoAux = new PasajeroDTO();
					dtoAux.setId((Integer)modeloAgregados.getValueAt(fila, 4));
					pasajerosAgregados.remove(dtoAux);
					
					modeloAgregados.removeRow(fila);
					modeloAgregados.fireTableDataChanged();
					capacidadAlcanzada.setForeground(Color.WHITE);
				}
			}
		});
		
		tablaAgregados.getDefaultEditor(Boolean.class).addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				Integer fila = tablaAgregados.getSelectedRow();
				Boolean valor = (Boolean) modeloAgregados.getValueAt(fila, 5);
				
				if(valor) {
					for(int i=0;i<tablaAgregados.getRowCount();i++) {
						if(i != fila) modeloAgregados.setValueAt(false, i, 5);
					}
				}
			}

			@Override
			public void editingCanceled(ChangeEvent e) {}
		});
	}

	private String textoTipoHab(HabitacionDTO hab) {
		if(hab.getClass() == HabitacionIndividualDTO.class) {
			return "Individual Estándar";
		}
		
		if(hab.getClass() == HabitacionDobleEstandarDTO.class) {
			return "Doble Estándar";
		}
		
		if(hab.getClass() == HabitacionDobleSuperiorDTO.class) {
			return "Doble Superior";
		}
		
		if(hab.getClass() == HabitacionFamilyDTO.class) {
			return "Superior Family Plan";
		}
		
		if(hab.getClass() == HabitacionSuiteDTO.class) {
			return "Suite Doble";
		}
		
		return "";
	}

	private void actualizarTablaResultados(List<PasajeroDTO> pasajeros) {
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
		
		modeloResultados.setData(data);
		modeloResultados.fireTableDataChanged();
	}

	private void actualizarTablaAgregados(PasajeroDTO p) {
		Vector<Object> fila = new Vector<Object>();
		
		fila.add(p.getApellido().get());
		fila.add(p.getNombre().get());
		fila.add(p.getTipo().get());
		fila.add(p.getNro_doc().get());
		fila.add(p.getId().get());
		fila.add(false);
		fila.add(iconoBasura);
		
		modeloAgregados.addRow(fila);
		modeloAgregados.fireTableDataChanged();	
	}
	
	private void cargarOtraHabitacion(LocalDate fechadesde, LocalDate fechaHasta) {
		ventanaCargarOtraHabitacion = new JDialog(App.getVentana(),"Hotel Premier - Cargar otra habitación");
		panelCargarOtraHabitacion = new CargarOtraHabitacion();
		ventanaCargarOtraHabitacion.setContentPane(panelCargarOtraHabitacion);
		
		App.getVentana().setEnabled(false);
		ventanaCargarOtraHabitacion.pack();
		ventanaCargarOtraHabitacion.setLocationRelativeTo(App.getVentana());
		ventanaCargarOtraHabitacion.setVisible(true);
		
		ventanaCargarOtraHabitacion.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}
		});
		
		panelCargarOtraHabitacion.getSeguirCargando().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaCargarOtraHabitacion.dispose();
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}
		});
		
		panelCargarOtraHabitacion.getCargarOtraHabitacion().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO actualizar informacion
				ventanaCargarOtraHabitacion.dispose();
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
				
				//TODO -> instancias PasajeroDTO responsable y pasarlo como parametro
				
			}
		});
		
		panelCargarOtraHabitacion.getSalir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO actualizar informacion
				ventanaCargarOtraHabitacion.dispose();
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
				App.menuPrincipal();
			}
		});
	}

	private void inicializarResponsable(Optional<PasajeroDTO> p) {
		Vector<Object> fila = new Vector<Object>();
		
		fila.add(p.get().getApellido().get());
		fila.add(p.get().getNombre().get());
		fila.add(p.get().getTipo().get());
		fila.add(p.get().getNro_doc().get());
		fila.add(p.get().getId().get());
		fila.add(true);
		fila.add(iconoBasura);
		
		pasajerosAgregados.add(p.get());
		modeloAgregados.addRow(fila);
		modeloAgregados.fireTableDataChanged();	
	}

}
