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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import clase_app.App;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaHabitaciones;
import componentes_swing.retroalimentacion.*;

public class OcuparHabitacion extends JPanel{

	//PanelBusqueda
	private JPanel panelBusqueda;
	private EtiquetaJ eDesde;
	private EtiquetaJ eHasta;
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
	
	public OcuparHabitacion() {
		super();			
		
		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		panelBusqueda();
		panelHabitaciones();
		panelBotones();
		listeners();
		
	}
	
	private void panelBusqueda() {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Buscar Habitación");
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
		configurarDatePickers();
		
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
		cons.insets = new Insets(10,5,10,20);
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
		cons.insets = new Insets(0,20,10,20);
		panelBusqueda.add(buscar,cons);
		
	}
	
	private void configurarDatePickers() {
		Properties p = new Properties();
		p.put("text.day", "Día");
		p.put("text.month", "Mes");
		p.put("text.year", "Año");

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
		
		panelHabitaciones.addTab("Individual Estándar",panelIndividualEstandar);
		panelHabitaciones.addTab("Doble Estándar",panelDobleEstandar);
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
		
		actualizarTablas();
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
	
	private void listeners() {
		ActionListener listenerOcupar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO listener ocupar 
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
		};

		cancelarIndividualEstandar.addActionListener(listenerCancelar);
		cancelarDobleEstandar.addActionListener(listenerCancelar);
		cancelarDobleSuperior.addActionListener(listenerCancelar);
		cancelarSuperiorFamilyPlan.addActionListener(listenerCancelar);
		cancelarSuiteDoble.addActionListener(listenerCancelar);
	}
	
	private void actualizarTablas() {
		
	}
}
