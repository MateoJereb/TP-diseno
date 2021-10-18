package componentes_swing.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import clases.dto.PaisDTO;
import clases.dto.ProvinciaDTO;
import clases.dto.TipoDocumentoDTO;
import componentes_swing.*;

public class DarAltaPasajero extends JPanel {

	private JPanel panelDatos;
	private JPanel datosIzq;
	private JPanel datosDer;
	private EtiquetaJ eApellido;
	private CampoJ cApellido;
	private EtiquetaJ eNombre;
	private CampoJ cNombre;
	private EtiquetaJ eTipoDoc;
	private ListaTipoDoc lTipoDoc;
	private EtiquetaJ eNroDoc;
	private CampoJ cNroDoc;
	private EtiquetaJ eCUIT;
	private CampoJMascara cCUIT;
	private EtiquetaJ eRespIva;
	private ListaPosicionIVA lRespIva;
	private EtiquetaJ eFechaNac;
	private EtiquetaJ eTelefono;
	private CampoJ cTelefono;
	private EtiquetaJ eEmail;
	private CampoJ cEmail;
	private EtiquetaJ eOcupacion;
	private CampoJ cOcupacion;
	private EtiquetaJ eNacionalidad;
	private CampoJ cNacionalidad;
	private EtiquetaJ eCalle;
	private CampoJ cCalle;
	private EtiquetaJ eNumero;
	private CampoJ cNumero;
	private EtiquetaJ ePiso;
	private CampoJ cPiso;
	private EtiquetaJ eDepartamento;
	private CampoJ cDepartamento;
	private EtiquetaJ eCodPostal;
	private CampoJ cCodPostal;
	private EtiquetaJ ePais;
	private ListaPaises lPais;
	private EtiquetaJ eProvincia;
	private ListaProvincias lProvincia;
	private EtiquetaJ eLocalidad;
	private ListaLocalidades lLocalidad;
	private EtiquetaJ camposObligatorios;
	private BotonJ siguiente;
	private BotonJ cancelar;
	
	//Date picker
	private UtilDateModel model;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	private JPanel panelDirec;
	
	public DarAltaPasajero(String apellidoCargado, String nombreCargado, TipoDocumentoDTO tipoDocCargado, String nroDocCargado) {
		super();			
		
		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		panelDatos(apellidoCargado,nombreCargado,tipoDocCargado,nroDocCargado);
		listeners();
	}
	
	private void panelDatos(String apellidoCargado, String nombreCargado, TipoDocumentoDTO tipoDocCargado, String nroDocCargado) {
		
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Datos");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelDatos = new JPanel();
		panelDatos.setLayout(new GridBagLayout());
		panelDatos.setBackground(Color.WHITE);
		panelDatos.setBorder(borde);


		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(20,20,10,20);
		cons.anchor = GridBagConstraints.NORTH;
		this.add(panelDatos, cons);
		
		datosIzq = new JPanel();
		datosDer = new JPanel();
		
		datosIzq.setLayout(new GridBagLayout());
		datosIzq.setBackground(Color.WHITE);
		datosDer.setLayout(new GridBagLayout());
		datosDer.setBackground(Color.WHITE);
	
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(0,0,0,0);
		cons.anchor = GridBagConstraints.CENTER;
		panelDatos.add(datosIzq, cons);
		cons.gridx=1;
		panelDatos.add(datosDer, cons);
		
		eApellido = new EtiquetaJ("Apellido");
		cApellido = new CampoJ(apellidoCargado);
		eNombre = new EtiquetaJ("Nombre");
		cNombre = new CampoJ(nombreCargado);
		eTipoDoc = new EtiquetaJ("Tipo de documento");
		lTipoDoc = new ListaTipoDoc(tipoDocCargado);
		eNroDoc = new EtiquetaJ("Nro. documento");
		cNroDoc = new CampoJ(nroDocCargado);
		eCUIT = new EtiquetaJ("CUIT");
		cCUIT = new CampoJMascara();
		eRespIva = new EtiquetaJ("Responsable IVA");
		lRespIva = new ListaPosicionIVA();
		eFechaNac = new EtiquetaJ("Fecha de Nacimiento");
		eTelefono = new EtiquetaJ("Teléfono");
		cTelefono = new CampoJ();
		eEmail = new EtiquetaJ("Email");
		cEmail = new CampoJ();
		eOcupacion = new EtiquetaJ("Ocupación");
		cOcupacion = new CampoJ();
		eNacionalidad = new EtiquetaJ("Nacionalidad");
		cNacionalidad = new CampoJ();
		eCalle = new EtiquetaJ("Calle");
		cCalle = new CampoJ();
		eNumero = new EtiquetaJ("Número");
		cNumero = new CampoJ();
		ePiso = new EtiquetaJ("Piso");
		cPiso = new CampoJ();
		eDepartamento = new EtiquetaJ("Departamento");
		cDepartamento = new CampoJ();
		eCodPostal = new EtiquetaJ("Código Postal");
		cCodPostal = new CampoJ();
		ePais = new EtiquetaJ("País");
		lPais = new ListaPaises();
		eProvincia = new EtiquetaJ("Provincia");
		lProvincia = new ListaProvincias();
		eLocalidad = new EtiquetaJ("Localidad");
		lLocalidad = new ListaLocalidades();
		camposObligatorios = new EtiquetaJ("Campos obligatorios");
		siguiente = new BotonJ("Siguiente");
		cancelar = new BotonJ("Cancelar");

		
		cons.gridx = 0;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		datosIzq.add(eApellido, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco1 = new EtiquetaJ("(*)");
		asterisco1.setForeground(Color.RED);
		datosIzq.add(asterisco1, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		datosIzq.add(cApellido,cons);
		
		cons.gridy=1;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		datosIzq.add(eNombre, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco2 = new EtiquetaJ("(*)");
		asterisco2.setForeground(Color.RED);
		datosIzq.add(asterisco2, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		datosIzq.add(cNombre,cons);
		
		cons.gridy=2;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		datosIzq.add(eTipoDoc, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco3 = new EtiquetaJ("(*)");
		asterisco3.setForeground(Color.RED);
		datosIzq.add(asterisco3, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		datosIzq.add(lTipoDoc,cons);
		
		cons.gridy=3;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		datosIzq.add(eNroDoc, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco4 = new EtiquetaJ("(*)");
		asterisco4.setForeground(Color.RED);
		datosIzq.add(asterisco4, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		datosIzq.add(cNroDoc,cons);
		
		cons.gridy=4;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		datosIzq.add(eCUIT, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cCUIT.setPlaceholder("xx-xxxxxxxx-x");
		cCUIT.setPhColor(Color.DARK_GRAY);
		datosIzq.add(cCUIT,cons);
		
		cons.gridy=5;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		datosIzq.add(eRespIva, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco5 = new EtiquetaJ("(*)");
		asterisco5.setForeground(Color.RED);
		datosIzq.add(asterisco5, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		datosIzq.add(lRespIva,cons);
		
		cons.gridy=6;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		datosIzq.add(eFechaNac, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco6 = new EtiquetaJ("(*)");
		asterisco6.setForeground(Color.RED);
		datosIzq.add(asterisco6, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		Properties p = new Properties();
		p.put("text.day", "Día");
		p.put("text.month", "Mes");
		p.put("text.year", "Año");
		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model,p);
		datePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
		datosIzq.add(datePicker,cons);
		
		
		cons.gridy=7;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		datosIzq.add(eTelefono, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco7 = new EtiquetaJ("(*)");
		asterisco7.setForeground(Color.RED);
		datosIzq.add(asterisco7, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		datosIzq.add(cTelefono,cons);
		
		cons.gridy=8;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		datosIzq.add(eEmail, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		datosIzq.add(cEmail,cons);
		
		cons.gridy=9;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		datosIzq.add(eOcupacion, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco8 = new EtiquetaJ("(*)");
		asterisco8.setForeground(Color.RED);
		datosIzq.add(asterisco8, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		datosIzq.add(cOcupacion,cons);

		
		cons.gridy=0;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.weighty = 0;

		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		datosDer.add(eNacionalidad, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco9 = new EtiquetaJ("(*)");
		asterisco9.setForeground(Color.RED);
		datosDer.add(asterisco9, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		datosDer.add(cNacionalidad,cons);
		
		panelDirec = new JPanel();
		TitledBorder bordedirec  = BorderFactory.createTitledBorder("Direccion");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		panelDirec.setLayout(new GridBagLayout());
		panelDirec.setBackground(Color.WHITE);
		panelDirec.setBorder(bordedirec);
		
		cons.gridy=1;
		cons.gridx = 0;
		cons.gridwidth=3;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.CENTER;
		datosDer.add(panelDirec, cons);
		
		cons.gridwidth=1;
		cons.gridx = 0;
		cons.gridy=0;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDirec.add(eCalle, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco10 = new EtiquetaJ("(*)");
		asterisco10.setForeground(Color.RED);
		panelDirec.add(asterisco10, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelDirec.add(cCalle,cons);
		
		cons.gridy=1;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDirec.add(eNumero, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco11 = new EtiquetaJ("(*)");
		asterisco11.setForeground(Color.RED);
		panelDirec.add(asterisco11, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelDirec.add(cNumero,cons);
		
		cons.gridy=2;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDirec.add(ePiso, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelDirec.add(cPiso,cons);
		
		cons.gridy=3;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDirec.add(eDepartamento, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelDirec.add(cDepartamento,cons);
		
		cons.gridy=4;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDirec.add(eCodPostal, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco12 = new EtiquetaJ("(*)");
		asterisco12.setForeground(Color.RED);
		panelDirec.add(asterisco12, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelDirec.add(cCodPostal,cons);
		
		cons.gridy=5;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDirec.add(ePais, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco13 = new EtiquetaJ("(*)");
		asterisco13.setForeground(Color.RED);
		panelDirec.add(asterisco13, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelDirec.add(lPais,cons);
		
		cons.gridy=6;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDirec.add(eProvincia, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco14 = new EtiquetaJ("(*)");
		asterisco14.setForeground(Color.RED);
		panelDirec.add(asterisco14, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelDirec.add(lProvincia,cons);
		
		cons.gridy=7;
		cons.gridx = 0;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.EAST;
		cons.insets = new Insets(10,20,10,5);
		panelDirec.add(eLocalidad, cons);
		cons.gridx=1;
		cons.insets = new Insets(0,0,0,0);
		EtiquetaJ asterisco15 = new EtiquetaJ("(*)");
		asterisco15.setForeground(Color.RED);
		panelDirec.add(asterisco15, cons);
		cons.gridx = 2;
		cons.insets = new Insets(10,5,10,20);
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.15;
		cons.fill = GridBagConstraints.HORIZONTAL;
		panelDirec.add(lLocalidad,cons);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridBagLayout());
		panelBotones.setBackground(Color.WHITE);
		
		cons.gridx=0;
		cons.gridy=1;
		cons.gridwidth=2;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx=0;
		cons.weighty=0;
		cons.insets = new Insets(0,0,0,0);
		cons.anchor= GridBagConstraints.SOUTH;
		
		panelDatos.add(panelBotones, cons);
		
		cons.gridx=0;
		cons.gridy=0;
		cons.gridwidth=1;
		cons.weightx=0;
		cons.fill=GridBagConstraints.HORIZONTAL;
		cons.anchor= GridBagConstraints.CENTER;
		cons.insets= new Insets(0,5,5,0);
		EtiquetaJ asterisco16 = new EtiquetaJ("(*)");
		asterisco16.setForeground(Color.RED);
		panelBotones.add(asterisco16, cons);
		cons.anchor= GridBagConstraints.WEST;
		cons.weightx=0.1;
		cons.gridx=1;
	    panelBotones.add(camposObligatorios, cons);
	    
		cons.insets = new Insets(10,10,10,10);
	    cons.weightx=0;
	    cons.gridx=2;
	    cons.anchor= GridBagConstraints.EAST;
	    panelBotones.add(siguiente, cons);
	    cons.gridx=3;
	    panelBotones.add(cancelar, cons);
			
	}
	
	private void listeners() {
		lPais.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					lProvincia.setProvincias(((PaisDTO)lPais.getSelectedItem()).getIdPais());
				}
			}
		});
		
		lProvincia.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if(((ProvinciaDTO)lProvincia.getSelectedItem()).getIdProv() == -1) {
						lLocalidad.vaciar();
					}
					else {
						lLocalidad.setLocalidades(((ProvinciaDTO)lProvincia.getSelectedItem()).getIdProv());
					}
				}
			}
		});
	}

}
