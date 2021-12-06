package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import clase_app.App;
import clases.HabitacionDobleEstandar;
import clases.HabitacionDobleSuperior;
import clases.HabitacionFamily;
import clases.HabitacionIndividual;
import clases.dto.HabitacionDTO;
import clases.dto.HabitacionDobleEstandarDTO;
import clases.dto.HabitacionDobleSuperiorDTO;
import clases.dto.HabitacionFamilyDTO;
import clases.dto.HabitacionIndividualDTO;
import clases.dto.HabitacionSuiteDTO;
import clases.dto.PasajeroDTO;
import clases.dto.ReservaDTO;
import clases.gestores.GestorHabitaciones;
import clases.gestores.GestorReservas;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaHabitaciones;
import componentes_swing.retroalimentacion.*;
import enums.EstadoHabitacion;

public class OcuparHabitacion extends JPanel{

	//PanelBusqueda
	private JPanel panelBusqueda;
	private EtiquetaJ eDesde;
	private EtiquetaJ eHasta;
	private EtiquetaJ fechasInvalidas;
	private BotonJ buscar;
	//Date picker desde
	private UtilDateModel modelDesde;
	private JDatePanelImpl datePanelDesde;
	private JDatePickerImpl datePickerDesde;
	//Date picker hasta
	private UtilDateModel modelHasta;
	private JDatePanelImpl datePanelHasta;
	private JDatePickerImpl datePickerHasta;
	
	//PanelHabitaciones
	private JTabbedPane panelHabitaciones;
	private JPanel panelIndividualEstandar;
	private JPanel panelDobleEstandar;
	private JPanel panelDobleSuperior;
	private JPanel panelSuperiorFamilyPlan;
	private JPanel panelSuiteDoble;
	private ModeloTablaHabitaciones modeloIndividualEstandar;
	private TablaJ tablaIndividualEstandar;
	private ModeloTablaHabitaciones modeloDobleEstandar;
	private TablaJ tablaDobleEstandar;
	private ModeloTablaHabitaciones modeloDobleSuperior;
	private TablaJ tablaDobleSuperior;
	private ModeloTablaHabitaciones modeloSuperiorFamilyPlan;
	private TablaJ tablaSuperiorFamilyPlan;
	private ModeloTablaHabitaciones modeloSuiteDoble;
	private TablaJ tablaSuiteDoble;
	private JPanel panelBotonesIndividualEstandar;
	private JPanel panelBotonesDobleEstandar;
	private JPanel panelBotonesDobleSuperior;
	private JPanel panelBotonesSuperiorFamilyPlan;
	private JPanel panelBotonesSuiteDoble;
	private BotonJ ocuparIndividualEstandar;
	private BotonJ ocuparDobleEstandar;
	private BotonJ ocuparDobleSuperior;
	private BotonJ ocuparSuperiorFamilyPlan;
	private BotonJ ocuparSuiteDoble;
	private BotonJ cancelarIndividualEstandar;
	private BotonJ cancelarDobleEstandar;
	private BotonJ cancelarDobleSuperior;
	private BotonJ cancelarSuperiorFamilyPlan;
	private BotonJ cancelarSuiteDoble;
	
	//Seleccion
	private HabitacionDTO habSeleccionada;
	private ModeloTablaHabitaciones modeloSeleccionado;
	private LocalDate fechaInicial;
	private LocalDate fechaFinal;
	private Integer inicioRango;
	private Integer finalRango;
	private Integer columnaRango;
	
	private Boolean seleccionHecha = false;
	 
	
	public OcuparHabitacion(Optional<PasajeroDTO> responsable, Optional<LocalDate> desdeAnterior, Optional<LocalDate> hastaAnterior) {
		super();			
		
		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		panelBusqueda(desdeAnterior, hastaAnterior);
		panelHabitaciones();
		panelBotones();
		listeners(responsable);
		
		if(desdeAnterior.isPresent() && hastaAnterior.isPresent()) {
			Integer anioD = desdeAnterior.get().getYear();
			Integer mesD = desdeAnterior.get().getMonthValue();
			Integer diaD = desdeAnterior.get().getDayOfMonth();
			modelDesde.setDate(anioD,mesD,diaD);
			modelDesde.setSelected(true);
			
			Integer anioH = hastaAnterior.get().getYear();
			Integer mesH = hastaAnterior.get().getMonthValue();
			Integer diaH = hastaAnterior.get().getDayOfMonth();
			modelHasta.setDate(anioH,mesH,diaH);
			modelHasta.setSelected(true);
			
			GestorReservas gestor = GestorReservas.getInstance();
			Map<HabitacionDTO,Map<LocalDate,EstadoHabitacion>> disponibilidades = gestor.verificarDisponibilidad(desdeAnterior.get(), hastaAnterior.get());
			actualizarTablas(disponibilidades);
		}
	}
	
	private void panelBusqueda(Optional<LocalDate> desdeAnterior, Optional<LocalDate> hastaAnterior) {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Buscar Habitaci�n");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new GridBagLayout());
		panelBusqueda.setBackground(Color.WHITE);
		panelBusqueda.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(20,20,10,20);
		cons.anchor = GridBagConstraints.NORTH;
		add(panelBusqueda,cons);
		
		eDesde = new EtiquetaJ("Desde fecha");
		eHasta = new EtiquetaJ("Hasta fecha");	
		buscar = new BotonJ("Buscar");
		fechasInvalidas = new EtiquetaJ("Bien");
		fechasInvalidas.setForeground(Color.WHITE);
		
		configurarDatePickers(desdeAnterior,hastaAnterior);
		
		cons.anchor = GridBagConstraints.CENTER;
		cons.weighty = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelBusqueda.add(eDesde,cons);
		
		cons.gridx = 2;
		panelBusqueda.add(eHasta,cons);
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.insets = new Insets(10,5,0,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelBusqueda.add(datePickerDesde,cons);
		
		cons.gridx = 3;
		panelBusqueda.add(datePickerHasta,cons);
		
		cons.gridx = 4;
		cons.gridy = 0;
		cons.weightx = 0.01;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.SOUTH;
		cons.insets = new Insets(0,20,0,20);
		panelBusqueda.add(buscar,cons);
		
		cons.gridx = 1;
		cons.gridy = 1;
		cons.gridwidth = GridBagConstraints.REMAINDER;
		cons.insets = new Insets(5,5,5,20);
		cons.anchor = GridBagConstraints.WEST;
		cons.fill = GridBagConstraints.NONE;
		panelBusqueda.add(fechasInvalidas,cons);		
	}
	
	private void configurarDatePickers(Optional<LocalDate> desdeAnterior, Optional<LocalDate> hastaAnterior) {
		Properties p = new Properties();
		p.put("text.day", "D�a");
		p.put("text.month", "Mes");
		p.put("text.year", "A�o");

		ImageIcon icono = new ImageIcon("resources\\calendario.png");
		
		modelDesde = new UtilDateModel();
		datePanelDesde = new JDatePanelImpl(modelDesde,p);
		datePickerDesde = new JDatePickerImpl(datePanelDesde,new DateLabelFormatter());
		
		JFormattedTextField textoDesde = (JFormattedTextField) datePickerDesde.getComponent(0);
		JButton botonDesde = (JButton) datePickerDesde.getComponent(1);
		
		textoDesde.setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		textoDesde.setBackground(Color.WHITE);
		botonDesde.setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		botonDesde.setBackground(Color.WHITE);
		
		botonDesde.setBorder(null);
		botonDesde.setText("");
		botonDesde.setIcon(icono);
	
		botonDesde.setFocusPainted(false);
		
		textoDesde.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				cambiar();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				cambiar();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				cambiar();
			}
			
			public void cambiar() {
				textoDesde.setBackground(Color.WHITE);
			}
		});
		
		botonDesde.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				botonDesde.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void focusLost(FocusEvent e) {
				botonDesde.setBackground(Color.WHITE);
			}
		});
	
		modelHasta = new UtilDateModel();
		datePanelHasta = new JDatePanelImpl(modelHasta,p);
		datePickerHasta = new JDatePickerImpl(datePanelHasta,new DateLabelFormatter());
		
		JFormattedTextField textoHasta = (JFormattedTextField) datePickerHasta.getComponent(0);
		JButton botonHasta = (JButton) datePickerHasta.getComponent(1);
		
		textoHasta.setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		textoHasta.setBackground(Color.WHITE);
		botonHasta.setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		botonHasta.setBackground(Color.WHITE);
		
		botonHasta.setBorder(null);
		botonHasta.setText("");
		botonHasta.setIcon(icono);
		
		botonHasta.setFocusPainted(false);
		
		textoHasta.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				cambiar();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				cambiar();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				cambiar();
			}
			
			public void cambiar() {
				textoHasta.setBackground(Color.WHITE);
			}
		});
		
		botonHasta.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				botonHasta.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void focusLost(FocusEvent e) {
				botonHasta.setBackground(Color.WHITE);
			}
		});
		
		botonDesde.requestFocus();
		botonDesde.setNextFocusableComponent(botonHasta);
	}

	private void panelHabitaciones(){
		GridBagConstraints cons = new GridBagConstraints();
		Border borde = BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus"));
		
		panelHabitaciones = new JTabbedPane(JTabbedPane.TOP);
		panelHabitaciones.setBorder(borde);
		panelHabitaciones.setFont(new Font("Microsoft Tai Le",Font.PLAIN,13));
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,20,15,20);
		cons.anchor = GridBagConstraints.CENTER;
		
		add(panelHabitaciones,cons);
		
		panelIndividualEstandar = new JPanel(new GridBagLayout());
		panelIndividualEstandar.setBackground(Color.WHITE);
		
		panelDobleEstandar = new JPanel(new GridBagLayout());
		panelDobleEstandar.setBackground(Color.WHITE);
		
		panelDobleSuperior = new JPanel(new GridBagLayout());
		panelDobleSuperior.setBackground(Color.WHITE);
		
		panelSuperiorFamilyPlan = new JPanel(new GridBagLayout());
		panelSuperiorFamilyPlan.setBackground(Color.WHITE);
		
		panelSuiteDoble = new JPanel(new GridBagLayout());
		panelSuiteDoble.setBackground(Color.WHITE);
		
		panelHabitaciones.addTab("Individual Est�ndar",panelIndividualEstandar);
		panelHabitaciones.addTab("Doble Est�ndar",panelDobleEstandar);
		panelHabitaciones.addTab("Doble Superior",panelDobleSuperior);
		panelHabitaciones.addTab("Superior Family Plan",panelSuperiorFamilyPlan);
		panelHabitaciones.addTab("Suite Doble", panelSuiteDoble);
		
		modeloIndividualEstandar = new ModeloTablaHabitaciones();
		tablaIndividualEstandar = new TablaJ(modeloIndividualEstandar);
		JScrollPane scrollIndividualEstandar = new JScrollPane(tablaIndividualEstandar);
		cons.gridy = 0;
		cons.gridwidth = 2;
		panelIndividualEstandar.add(scrollIndividualEstandar,cons);
		
		modeloDobleEstandar = new ModeloTablaHabitaciones();
		tablaDobleEstandar = new TablaJ(modeloDobleEstandar);
		JScrollPane scrollDobleEstandar = new JScrollPane(tablaDobleEstandar);
		panelDobleEstandar.add(scrollDobleEstandar,cons);
		
		modeloDobleSuperior = new ModeloTablaHabitaciones();
		tablaDobleSuperior = new TablaJ(modeloDobleSuperior);
		JScrollPane scrollDobleSuperior = new JScrollPane(tablaDobleSuperior);
		panelDobleSuperior.add(scrollDobleSuperior,cons);
		
		modeloSuperiorFamilyPlan = new ModeloTablaHabitaciones();
		tablaSuperiorFamilyPlan = new TablaJ(modeloSuperiorFamilyPlan);
		JScrollPane scrollSuperiorFamilyPlan = new JScrollPane(tablaSuperiorFamilyPlan);
		panelSuperiorFamilyPlan.add(scrollSuperiorFamilyPlan,cons);
		
		modeloSuiteDoble = new ModeloTablaHabitaciones();
		tablaSuiteDoble = new TablaJ(modeloSuiteDoble);
		JScrollPane scrollSuiteDoble = new JScrollPane(tablaSuiteDoble);
		panelSuiteDoble.add(scrollSuiteDoble,cons);
		
		inicializarTablas();
	}
	
	private void panelBotones() {
		GridBagConstraints cons = new GridBagConstraints();
		panelBotonesIndividualEstandar = new JPanel(new GridBagLayout());
		panelBotonesDobleEstandar = new JPanel(new GridBagLayout());
		panelBotonesDobleSuperior = new JPanel(new GridBagLayout());
		panelBotonesSuperiorFamilyPlan = new JPanel(new GridBagLayout());
		panelBotonesSuiteDoble = new JPanel(new GridBagLayout());
		
		panelBotonesIndividualEstandar.setBackground(Color.WHITE);
		panelBotonesDobleEstandar.setBackground(Color.WHITE);
		panelBotonesDobleSuperior.setBackground(Color.WHITE);
		panelBotonesSuperiorFamilyPlan.setBackground(Color.WHITE);
		panelBotonesSuiteDoble.setBackground(Color.WHITE);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(0,20,0,20);
		cons.anchor = GridBagConstraints.EAST;
		panelIndividualEstandar.add(panelBotonesIndividualEstandar,cons);
		panelDobleEstandar.add(panelBotonesDobleEstandar,cons);
		panelDobleSuperior.add(panelBotonesDobleSuperior,cons);
		panelSuperiorFamilyPlan.add(panelBotonesSuperiorFamilyPlan,cons);
		panelSuiteDoble.add(panelBotonesSuiteDoble,cons);
		
		
		ocuparIndividualEstandar = new BotonJ("Ocupar");
		ocuparDobleEstandar = new BotonJ("Ocupar");
		ocuparDobleSuperior = new BotonJ("Ocupar");
		ocuparSuperiorFamilyPlan = new BotonJ("Ocupar");
		ocuparSuiteDoble = new BotonJ("Ocupar");
		cancelarIndividualEstandar = new BotonJ("Cancelar");
		cancelarDobleEstandar = new BotonJ("Cancelar");
		cancelarDobleSuperior = new BotonJ("Cancelar");
		cancelarSuperiorFamilyPlan = new BotonJ("Cancelar");
		cancelarSuiteDoble = new BotonJ("Cancelar");
		
		cons.gridy = 1;
		cons.weighty = 0;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(0,10,10,0);
		panelBotonesIndividualEstandar.add(ocuparIndividualEstandar,cons);
		panelBotonesDobleEstandar.add(ocuparDobleEstandar,cons);
		panelBotonesDobleSuperior.add(ocuparDobleSuperior,cons);
		panelBotonesSuperiorFamilyPlan.add(ocuparSuperiorFamilyPlan,cons);
		panelBotonesSuiteDoble.add(ocuparSuiteDoble,cons);
		
		cons.gridx=1;
		cons.insets = new Insets(0,10,10,0);
		panelBotonesIndividualEstandar.add(cancelarIndividualEstandar,cons);
		panelBotonesDobleEstandar.add(cancelarDobleEstandar,cons);
		panelBotonesDobleSuperior.add(cancelarDobleSuperior,cons);
		panelBotonesSuperiorFamilyPlan.add(cancelarSuperiorFamilyPlan,cons);
		panelBotonesSuiteDoble.add(cancelarSuiteDoble,cons);
	}
	
	private void listeners(Optional<PasajeroDTO> responsable) {
		buscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fechasSeleccionadas()) {
					fechasInvalidas.setForeground(Color.WHITE);
					if(fechasValidas()) {
						fechasInvalidas.setForeground(Color.WHITE);
						
						Date desdeAux = (Date) datePickerDesde.getModel().getValue();
						Date hastaAux = (Date) datePickerHasta.getModel().getValue();
						
						LocalDate fechaDesde = desdeAux.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						LocalDate fechaHasta = hastaAux.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
						
						GestorReservas gestor = GestorReservas.getInstance();
						Map<HabitacionDTO,Map<LocalDate,EstadoHabitacion>> disponibilidades = gestor.verificarDisponibilidad(fechaDesde, fechaHasta);
						actualizarTablas(disponibilidades);						
					}
					else {
						fechasInvalidas.setText("La fecha 'desde' no puede ser posterior a la fecha 'hasta'");
						fechasInvalidas.setForeground(Color.RED);
					}
				}
				else {
					fechasInvalidas.setText("Fechas incompletas");
					fechasInvalidas.setForeground(Color.RED);
				}
			}
		});
		
		ActionListener listenerOcupar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(seleccionHecha) {
					App.cargarPasajeros(habSeleccionada, fechaInicial, fechaFinal, responsable);
				}
				else {
					String mensaje = "<html><body>Debe seleccionar un rango de fechas<br>v�lido para la habitaci�n a ocupar.</body></html>";
					MensajeError noSelecciono = new MensajeError(App.getVentana(),mensaje,"Aceptar","");
					noSelecciono.getContentPane().remove(3);
					
					App.getVentana().setEnabled(false);
					noSelecciono.pack();
					noSelecciono.setLocationRelativeTo(App.getVentana());
					noSelecciono.setVisible(true);
					
					noSelecciono.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}
					});
					
					ActionListener listenerAceptar = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							noSelecciono.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}
					 };
					 
					 noSelecciono.setListeners(listenerAceptar, null);
				}
			}
		};
		
		ocuparIndividualEstandar.addActionListener(listenerOcupar);
		ocuparDobleEstandar.addActionListener(listenerOcupar);
		ocuparDobleSuperior.addActionListener(listenerOcupar);
		ocuparSuperiorFamilyPlan.addActionListener(listenerOcupar);
		ocuparSuiteDoble.addActionListener(listenerOcupar);
		
		ActionListener listenerCancelar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String textoMensaje = "<html><body>�Desea cancelar la ocupaci�n de la habitaci�n<br>y volver al men� principal?</body></html>";
				MensajeConfirmacion ventanaAux = new MensajeConfirmacion(App.getVentana(),textoMensaje,"S�","No");
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
		};

		cancelarIndividualEstandar.addActionListener(listenerCancelar);
		cancelarDobleEstandar.addActionListener(listenerCancelar);
		cancelarDobleSuperior.addActionListener(listenerCancelar);
		cancelarSuperiorFamilyPlan.addActionListener(listenerCancelar);
		cancelarSuiteDoble.addActionListener(listenerCancelar);
		
		tablaIndividualEstandar.addMouseListener(new MouseAdapter() {
			@Override
			 public void mouseReleased(MouseEvent evt) {
				accionSegunEstado(modeloIndividualEstandar,tablaIndividualEstandar);
			}
		});
		
		tablaDobleEstandar.addMouseListener(new MouseAdapter() {
			@Override
			 public void mouseReleased(MouseEvent evt) {
				accionSegunEstado(modeloDobleEstandar,tablaDobleEstandar);
			}
		});
		
		tablaDobleSuperior.addMouseListener(new MouseAdapter() {
			@Override
			 public void mouseReleased(MouseEvent evt) {
				accionSegunEstado(modeloDobleSuperior,tablaDobleSuperior);
			}
		});
		
		tablaSuperiorFamilyPlan.addMouseListener(new MouseAdapter() {
			@Override
			 public void mouseReleased(MouseEvent evt) {
				accionSegunEstado(modeloSuperiorFamilyPlan,tablaSuperiorFamilyPlan);
			}
		});
		
		tablaSuiteDoble.addMouseListener(new MouseAdapter() {
			@Override
			 public void mouseReleased(MouseEvent evt) {
				accionSegunEstado(modeloSuiteDoble,tablaSuiteDoble);
			}
		});
	}
	
	private void inicializarTablas() { 
		GestorHabitaciones gestor = GestorHabitaciones.getInstance();
		List<HabitacionDTO> habitaciones = gestor.buscarHabitacionesDTO();
		
		for(HabitacionDTO h : habitaciones) {
			if(h.getClass() == HabitacionIndividualDTO.class) modeloIndividualEstandar.addColumn("Nro. "+h.getNro().get());
			else {
				if(h.getClass() == HabitacionDobleEstandarDTO.class) modeloDobleEstandar.addColumn("Nro. "+h.getNro().get());
				else {
					if(h.getClass() == HabitacionDobleSuperiorDTO.class) modeloDobleSuperior.addColumn("Nro. "+h.getNro().get());
					else {
						if(h.getClass() == HabitacionFamilyDTO.class) modeloSuperiorFamilyPlan.addColumn("Nro. "+h.getNro().get());
						else modeloSuiteDoble.addColumn("Nro. "+h.getNro().get());
					}
				}
			}
		}
		
		modeloIndividualEstandar.fireTableStructureChanged();
		modeloDobleEstandar.fireTableStructureChanged();
		modeloDobleSuperior.fireTableStructureChanged();
		modeloSuperiorFamilyPlan.fireTableStructureChanged();
		modeloSuiteDoble.fireTableStructureChanged();
		
		tablaIndividualEstandar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for(int i = 1;i<modeloIndividualEstandar.getColumnCount();i++) {
			tablaIndividualEstandar.getColumnModel().getColumn(i).setPreferredWidth(130);
			tablaIndividualEstandar.getColumnModel().getColumn(i).setMaxWidth(130);
			tablaIndividualEstandar.getColumnModel().getColumn(i).setMinWidth(130);
		}
		
		tablaDobleEstandar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for(int i = 1;i<modeloDobleEstandar.getColumnCount();i++) {
			tablaDobleEstandar.getColumnModel().getColumn(i).setPreferredWidth(130);
			tablaDobleEstandar.getColumnModel().getColumn(i).setMaxWidth(130);
			tablaDobleEstandar.getColumnModel().getColumn(i).setMinWidth(130);
		}
		
		tablaDobleSuperior.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for(int i = 1;i<modeloDobleSuperior.getColumnCount();i++) {
			tablaDobleSuperior.getColumnModel().getColumn(i).setPreferredWidth(130);
			tablaDobleSuperior.getColumnModel().getColumn(i).setMaxWidth(130);
			tablaDobleSuperior.getColumnModel().getColumn(i).setMinWidth(130);
		}
		
		tablaSuperiorFamilyPlan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for(int i = 1;i<modeloSuperiorFamilyPlan.getColumnCount();i++) {
			tablaSuperiorFamilyPlan.getColumnModel().getColumn(i).setPreferredWidth(130);
			tablaSuperiorFamilyPlan.getColumnModel().getColumn(i).setMaxWidth(130);
			tablaSuperiorFamilyPlan.getColumnModel().getColumn(i).setMinWidth(130);
		}
		
		tablaSuiteDoble.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for(int i = 1;i<modeloSuiteDoble.getColumnCount();i++) {
			tablaSuiteDoble.getColumnModel().getColumn(i).setPreferredWidth(130);
			tablaSuiteDoble.getColumnModel().getColumn(i).setMaxWidth(130);
			tablaSuiteDoble.getColumnModel().getColumn(i).setMinWidth(130);
		}
		
		tablaIndividualEstandar.getColumnModel().setColumnSelectionAllowed(true);
		tablaIndividualEstandar.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaIndividualEstandar.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		tablaDobleEstandar.getColumnModel().setColumnSelectionAllowed(true);
		tablaDobleEstandar.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaDobleEstandar.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		tablaDobleSuperior.getColumnModel().setColumnSelectionAllowed(true);
		tablaDobleSuperior.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaDobleSuperior.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		tablaSuperiorFamilyPlan.getColumnModel().setColumnSelectionAllowed(true);
		tablaSuperiorFamilyPlan.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaSuperiorFamilyPlan.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		tablaSuiteDoble.getColumnModel().setColumnSelectionAllowed(true);
		tablaSuiteDoble.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaSuiteDoble.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		
	}
	
	private Boolean fechasSeleccionadas() {
		
		Boolean validos = true;
		
		if(datePickerHasta.getModel().getValue() == null) {
			validos = false;
			datePickerHasta.getComponent(0).setBackground(new Color(255,200,200));
			datePickerHasta.getComponent(1).requestFocus();
		}
		
		if(datePickerDesde.getModel().getValue() == null) {
			validos = false;
			datePickerDesde.getComponent(0).setBackground(new Color(255,200,200));
			datePickerDesde.getComponent(1).requestFocus();
		}
		
		
		return validos;
	}
	
	private Boolean fechasValidas() {
		
		Date desdeAux = (Date) datePickerDesde.getModel().getValue();
		Date hastaAux = (Date) datePickerHasta.getModel().getValue();
		
		LocalDate fechaDesde = desdeAux.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate fechaHasta = hastaAux.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		if(fechaDesde.isAfter(fechaHasta)) {
			return false;
		}
		
		return true;
	}
	
	private void actualizarTablas(Map<HabitacionDTO,Map<LocalDate,EstadoHabitacion>> disponibilidades) {
		Vector<Vector<Object>> dataIndividualEstandar = new Vector<Vector<Object>>();
		Vector<Vector<Object>> dataDobleEstandar = new Vector<Vector<Object>>();
		Vector<Vector<Object>> dataDobleSuperior = new Vector<Vector<Object>>();
		Vector<Vector<Object>> dataSuperiorFamilyPlan = new Vector<Vector<Object>>();
		Vector<Vector<Object>> dataSuiteDoble = new Vector<Vector<Object>>();
		
		//Se extrae una habitacion arbitraria para extraer el keySet con las fechas
		Object[] aux = disponibilidades.keySet().toArray();
		HabitacionDTO habAux = (HabitacionDTO) aux[0];
		
		Set<LocalDate> fechas = disponibilidades.get(habAux).keySet();
		List<HabitacionDTO> habsIndividuales = disponibilidades.keySet().stream().filter(h -> h.getClass() == HabitacionIndividualDTO.class).collect(Collectors.toList());
		List<HabitacionDTO> habsDoblesEstandar = disponibilidades.keySet().stream().filter(h -> h.getClass() == HabitacionDobleEstandarDTO.class).collect(Collectors.toList());
		List<HabitacionDTO> habsDoblesSuperiores = disponibilidades.keySet().stream().filter(h -> h.getClass() == HabitacionDobleSuperiorDTO.class).collect(Collectors.toList());
		List<HabitacionDTO> habsFamilys = disponibilidades.keySet().stream().filter(h -> h.getClass() == HabitacionFamilyDTO.class).collect(Collectors.toList());
		List<HabitacionDTO> habsSuites = disponibilidades.keySet().stream().filter(h -> h.getClass() == HabitacionSuiteDTO.class).collect(Collectors.toList());
	
		
		//Individual estandar
		for(LocalDate fecha : fechas) {
			Vector<Object> fila = new Vector<Object>();
			fila.add(fecha);
			
			for(HabitacionDTO hab : habsIndividuales) {
				HabitacionDTO celda = new HabitacionIndividualDTO();
				celda.setNro(hab.getNro().get());
				celda.setCapacidad(hab.getCapacidad().get());
				celda.setEstado_actual(disponibilidades.get(hab).get(fecha));
				fila.add(celda);
			}
			dataIndividualEstandar.add(fila);
		}
		
		//Doble estandar
		for(LocalDate fecha : fechas) {
			Vector<Object> fila = new Vector<Object>();

			fila.add(fecha);
			
			for(HabitacionDTO hab : habsDoblesEstandar) {
				HabitacionDTO celda = new HabitacionDobleEstandarDTO();
				celda.setNro(hab.getNro().get());
				celda.setCapacidad(hab.getCapacidad().get());
				celda.setEstado_actual(disponibilidades.get(hab).get(fecha));
				fila.add(celda);
			}
			
			dataDobleEstandar.add(fila);
		}
		
		//Doble superior
		for(LocalDate fecha : fechas) {
			Vector<Object> fila = new Vector<Object>();

			fila.add(fecha);
			
			for(HabitacionDTO hab : habsDoblesSuperiores) {
				HabitacionDTO celda = new HabitacionDobleSuperiorDTO();
				celda.setNro(hab.getNro().get());
				celda.setCapacidad(hab.getCapacidad().get());
				celda.setEstado_actual(disponibilidades.get(hab).get(fecha));
				fila.add(celda);
			}
			
			dataDobleSuperior.add(fila);
		}
		
		//Superior family plan
		for(LocalDate fecha : fechas) {
			Vector<Object> fila = new Vector<Object>();

			fila.add(fecha);
			
			for(HabitacionDTO hab : habsFamilys) {
				HabitacionDTO celda = new HabitacionFamilyDTO();
				celda.setNro(hab.getNro().get());
				celda.setCapacidad(hab.getCapacidad().get());
				celda.setEstado_actual(disponibilidades.get(hab).get(fecha));
				fila.add(celda);
			}
			
			dataSuperiorFamilyPlan.add(fila);
		}
		
		//Suite doble
		for(LocalDate fecha : fechas) {
			Vector<Object> fila = new Vector<Object>();
			
			fila.add(fecha);
			
			for(HabitacionDTO hab : habsSuites) {
				HabitacionDTO celda = new HabitacionSuiteDTO();
				celda.setNro(hab.getNro().get());
				celda.setCapacidad(hab.getCapacidad().get());
				celda.setEstado_actual(disponibilidades.get(hab).get(fecha));
				fila.add(celda);
			}
			
			dataSuiteDoble.add(fila);
		}
		
		modeloIndividualEstandar.setData(dataIndividualEstandar);
		modeloDobleEstandar.setData(dataDobleEstandar);
		modeloDobleSuperior.setData(dataDobleSuperior);
		modeloSuperiorFamilyPlan.setData(dataSuperiorFamilyPlan);
		modeloSuiteDoble.setData(dataSuiteDoble);
		
		modeloIndividualEstandar.fireTableDataChanged();
		modeloDobleEstandar.fireTableDataChanged();
		modeloDobleSuperior.fireTableDataChanged();
		modeloSuperiorFamilyPlan.fireTableDataChanged();
		modeloSuiteDoble.fireTableDataChanged();
		
		
	}
	
	private void accionSegunEstado(ModeloTablaHabitaciones modelo, TablaJ tabla) {
		int[] filas = tabla.getSelectedRows();
		int columna = tabla.getSelectedColumn();
		
		if(columna != 0) { //Si se seleccionan celdas de una columna que no sea la de las fechas
			if(seleccionHecha) { //Si ya hay una seleccion, deseleccionarla
				marcarDesocupar(inicioRango, finalRango, columnaRango, modeloSeleccionado);
			}
			
			Boolean fueraDeServicioFlag = false, ocupadaFlag = false, reservadaFlag = false;
			List<LocalDate> fechasReservadas = new ArrayList<LocalDate>();
			HabitacionDTO habitacion = null;
			
			for(int i=filas[0];i<=filas[filas.length-1];i++) {
				Object celda = modelo.getValueAt(i, columna);
				
				if(celda.getClass() == HabitacionIndividualDTO.class) habitacion = (HabitacionIndividualDTO) celda;
				if(celda.getClass() == HabitacionDobleEstandarDTO.class) habitacion = (HabitacionDobleEstandarDTO) celda;
				if(celda.getClass() == HabitacionDobleSuperiorDTO.class) habitacion = (HabitacionDobleSuperiorDTO) celda;
				if(celda.getClass() == HabitacionFamilyDTO.class) habitacion = (HabitacionFamilyDTO) celda;
				if(celda.getClass() == HabitacionSuiteDTO.class) habitacion = (HabitacionSuiteDTO) celda;
				
				if(habitacion.getEstado_actual().get() == EstadoHabitacion.FUERA_DE_SERVICIO) fueraDeServicioFlag = true;
				if(habitacion.getEstado_actual().get() == EstadoHabitacion.OCUPADA) ocupadaFlag = true;
				if(habitacion.getEstado_actual().get() == EstadoHabitacion.RESERVADA) {
					reservadaFlag = true;
					fechasReservadas.add((LocalDate) modelo.getValueAt(i, 0));
				}
			}
			
			if(fueraDeServicioFlag) {
				errorFueraDeServicio();
			}
			else {
				if(ocupadaFlag) {
					errorOcupada();
				}
				else {
					if(reservadaFlag) {
						fechasReservadas(fechasReservadas,habitacion,modelo,filas[0],filas[filas.length-1],columna);
					}
					else {
						marcarOcupar(filas[0], filas[filas.length-1], columna, modelo);
					}
				}
			}
		}
	}
	
	private void errorFueraDeServicio() {
		String mensaje = "<html><body>La habitaci�n se encuentra fuera de servicio.<br>Por favor seleccione otra.</body></html>";
		MensajeError habFueraDeServicio = new MensajeError(App.getVentana(),mensaje,"Aceptar","");
		habFueraDeServicio.getContentPane().remove(3);
		
		App.getVentana().setEnabled(false);
		habFueraDeServicio.pack();
		habFueraDeServicio.setLocationRelativeTo(App.getVentana());
		habFueraDeServicio.setVisible(true);
		
		habFueraDeServicio.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}
		});
		
		ActionListener listenerAceptar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				habFueraDeServicio.dispose();
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}
		 };
		 
		 habFueraDeServicio.setListeners(listenerAceptar, null);
	}
	
	private void errorOcupada() {
		String mensaje = "<html><body>La habitaci�n se encuentra ocupada en el rango<br>de fechas indicadas. Seleccione otra habitaci�n<br>u otro rango de fechas.</body></html>";
		MensajeError habOcupada = new MensajeError(App.getVentana(),mensaje,"Aceptar","");
		habOcupada.getContentPane().remove(3);
		
		App.getVentana().setEnabled(false);
		habOcupada.pack();
		habOcupada.setLocationRelativeTo(App.getVentana());
		habOcupada.setVisible(true);
		
		habOcupada.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}
		});
		
		ActionListener listenerAceptar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				habOcupada.dispose();
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}
		 };
		 
		 habOcupada.setListeners(listenerAceptar, null);
	}
	
	private void fechasReservadas(List<LocalDate> fechas, HabitacionDTO habitacion, ModeloTablaHabitaciones modelo, Integer inicioFila, Integer finalFila, Integer columna) {
		JDialog ventanaFechasReservadas;
		FechasReservadas panelFechasReservadas;
		
		ventanaFechasReservadas = new JDialog(App.getVentana(),"Hotel Premier - Fechas Reservadas");
		panelFechasReservadas = new FechasReservadas(fechas,habitacion.getNro().get());
		ventanaFechasReservadas.setContentPane(panelFechasReservadas);
		
		App.getVentana().setEnabled(false);
		ventanaFechasReservadas.pack();
		ventanaFechasReservadas.setLocationRelativeTo(App.getVentana());
		ventanaFechasReservadas.setVisible(true);
		
		ventanaFechasReservadas.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}
		});
		
		panelFechasReservadas.getOcuparIgual().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				marcarOcupar(inicioFila, finalFila, columna, modelo);
				ventanaFechasReservadas.dispose();
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);	
			}
		});
		
		panelFechasReservadas.getVolver().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaFechasReservadas.dispose();
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}
		});
	}
	
	private void marcarOcupar(Integer inicioFila, Integer finalFila, Integer columna, ModeloTablaHabitaciones modelo) {
		
		for(int i=inicioFila;i<=finalFila;i++) {
			HabitacionDTO aux = (HabitacionDTO) modelo.getValueAt(i, columna);
			aux.setAOcupar(true);
			modelo.setValueAt(aux, i, columna);
			modelo.fireTableDataChanged();
		}
		
		Object h = modelo.getValueAt(inicioFila, columna);
		
		if(h.getClass() == HabitacionIndividualDTO.class) habSeleccionada = (HabitacionIndividualDTO) h;
		if(h.getClass() == HabitacionDobleEstandarDTO.class) habSeleccionada = (HabitacionDobleEstandarDTO) h;
		if(h.getClass() == HabitacionDobleSuperiorDTO.class) habSeleccionada = (HabitacionDobleSuperiorDTO) h;
		if(h.getClass() == HabitacionFamilyDTO.class) habSeleccionada = (HabitacionFamilyDTO) h;
		if(h.getClass() == HabitacionSuiteDTO.class) habSeleccionada = (HabitacionSuiteDTO) h;
		
		modeloSeleccionado = modelo;
		fechaInicial = (LocalDate) modelo.getValueAt(inicioFila, 0);
		fechaFinal = (LocalDate) modelo.getValueAt(finalFila, 0);
		inicioRango = inicioFila;
		finalRango = finalFila;
		columnaRango = columna;
		
		seleccionHecha = true;
	}
	
	private void marcarDesocupar(Integer inicioFila, Integer finalFila, Integer columna, ModeloTablaHabitaciones modelo) {
		for(int i=inicioFila;i<=finalFila;i++) {
			HabitacionDTO aux = (HabitacionDTO) modelo.getValueAt(i, columna);
			aux.setAOcupar(false);
			modelo.setValueAt(aux, i, columna);
			modelo.fireTableDataChanged();
		}
		
		modeloSeleccionado = null;
		habSeleccionada = null;
		fechaInicial = null;
		fechaFinal = null;
		inicioRango = null;
		finalRango = null;
		columnaRango = null;
		
		seleccionHecha = false;
	}
	
}
