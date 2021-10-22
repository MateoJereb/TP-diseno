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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import clase_app.App;
import clases.dto.LocalidadDTO;
import clases.dto.PaisDTO;
import clases.dto.PasajeroDTO;
import clases.dto.PosicionIVADTO;
import clases.dto.ProvinciaDTO;
import clases.dto.TipoDocumentoDTO;
import clases.gestores.GestorPasajeros;
import componentes_swing.*;
import componentes_swing.retroalimentacion.*;

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
	private EtiquetaJ camposInvalidos;
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
		listeners(apellidoCargado,nombreCargado,tipoDocCargado,nroDocCargado);
		
		lRespIva.setNextFocusableComponent(datePicker.getComponent(1));
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
		
		TipoDocumentoDTO tipo = tipoDocCargado;
		if(tipo.getId() == -1) tipo = new TipoDocumentoDTO(1,"DNI"); //Si no selecciono ninguno, precarga DNI por ser el valor predeterminado
		lTipoDoc = new ListaTipoDoc(tipo);
		
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
		camposInvalidos = new EtiquetaJ();
		camposInvalidos.setForeground(Color.RED);
		camposInvalidos.setVisible(false);

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
		configurarDatePicker();
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
	    
	    cons.gridx = 2;
	    cons.anchor = GridBagConstraints.EAST;
	    cons.insets = new Insets(10,10,10,10);
	    cons.weightx=0;
	    panelBotones.add(camposInvalidos,cons);
	    cons.gridx=3;
	    cons.anchor= GridBagConstraints.EAST;
	    panelBotones.add(siguiente, cons);
	    cons.gridx=4;
	    panelBotones.add(cancelar, cons);
			
	}
	
	private void configurarDatePicker() {
		Properties p = new Properties();
		p.put("text.day", "Día");
		p.put("text.month", "Mes");
		p.put("text.year", "Año");
		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model,p);
		datePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
		
		JFormattedTextField texto = (JFormattedTextField) datePicker.getComponent(0);
		JButton boton = (JButton) datePicker.getComponent(1);
		
		texto.setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		texto.setBackground(Color.WHITE);
		boton.setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		boton.setBackground(Color.WHITE);
		
		ImageIcon icono = new ImageIcon("resources\\calendario.png");
		boton.setBorder(null);
		boton.setText("");
		boton.setIcon(icono);
		
		boton.setFocusPainted(false);
		
		texto.getDocument().addDocumentListener(new DocumentListener() {
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
				texto.setBackground(Color.WHITE);
			}
		});
		
		boton.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				boton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void focusLost(FocusEvent e) {
				boton.setBackground(Color.WHITE);
			}
		});
	}
	
	private void listeners(String apellidoCargado, String nombreCargado, TipoDocumentoDTO tipoDocCargado, String nroDocCargado) {
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
		
		siguiente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(obligatoriosCompletos()) {
					if(emailValido()) {
						if(pisoNumerico()) {
							GestorPasajeros gestor = GestorPasajeros.getInstance();
							
							PasajeroDTO datos = new PasajeroDTO();
							datos.setNro_doc(cNroDoc.getText());
							datos.setIdTipoDoc(((TipoDocumentoDTO)lTipoDoc.getSelectedItem()).getId());
							datos.setTipo(((TipoDocumentoDTO)lTipoDoc.getSelectedItem()).getTipo());
							
							if(gestor.dniExistente(datos)) {
								MensajeInformativo existeDoc = new MensajeInformativo(App.getVentana(),"<html><body>¡CUIDADO! El tipo y número de documento ya<br>existen en el sistema.</body><html>","Aceptar igualmente","Corregir");
								App.getVentana().setEnabled(false);
								existeDoc.pack();
								existeDoc.setLocationRelativeTo(App.getVentana());
								existeDoc.setVisible(true);

								existeDoc.addWindowListener(new WindowAdapter() {
									public void windowClosing(WindowEvent e) {
										App.getVentana().setEnabled(true);
										App.getVentana().setVisible(true);
									}
								});
								
								ActionListener listenerAceptar = new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										existeDoc.dispose();
										App.getVentana().setEnabled(true);
										App.getVentana().setVisible(true);
										crearPasajero();
									}									
								};
								
								ActionListener listenerCorregir = new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										
										existeDoc.dispose();
										App.getVentana().setEnabled(true);
										App.getVentana().setVisible(true);
										lTipoDoc.requestFocus();
									}
								};
								
								existeDoc.setListeners(listenerAceptar, listenerCorregir);
							}
							else {
								crearPasajero();
							}
						}
						else {
							cPiso.setBackground(new Color(255,200,200));
							cPiso.requestFocus();
							camposInvalidos.setText("El piso debe ser numérico");
							camposInvalidos.setVisible(true);
						}
					}
					else {
						cEmail.setBackground(new Color(255,200,200));
						cEmail.requestFocus();
						camposInvalidos.setText("Ingrese un tipo de mail valido");
						camposInvalidos.setVisible(true);
					}
				}
				else {
					camposInvalidos.setText("Campos obligatorios incompletos");
					camposInvalidos.setVisible(true);
				}
			}
		});
		
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MensajeConfirmacion confirmacion = new MensajeConfirmacion(App.getVentana(),"¿Desea cancelar el alta del pasajero?","Sí","No");
				App.getVentana().setEnabled(false);
				confirmacion.pack();
				confirmacion.setLocationRelativeTo(App.getVentana());
				confirmacion.setVisible(true); 
				
				confirmacion.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}
				});
				
				ActionListener listenerSi = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						confirmacion.dispose();
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
						App.gestionarPasajeros(apellidoCargado,nombreCargado,tipoDocCargado,nroDocCargado);
					}
				};
				
				ActionListener listenerNo = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						confirmacion.dispose();
						App.getVentana().setEnabled(true);
						App.getVentana().setVisible(true);
					}
				};
				
				confirmacion.setListeners(listenerSi,listenerNo);
			}
		});

	}

	private Boolean obligatoriosCompletos() {
		Boolean validos = true;
		
		if(((LocalidadDTO)lLocalidad.getSelectedItem()).getIdLocalidad() == -1) {
			validos = false;
			lLocalidad.setBackground(new Color(255,200,200));
			lLocalidad.requestFocus();
		}
		
		if(((ProvinciaDTO)lProvincia.getSelectedItem()).getIdProv() == -1) {
			validos = false;
			lProvincia.setBackground(new Color(255,200,200));
			lProvincia.requestFocus();
		}
		
		if(cCodPostal.getText().length() == 0) {
			validos = false;
			cCodPostal.setBackground(new Color(255,200,200));
			cCodPostal.requestFocus();
		}
		
		if(cNumero.getText().length() == 0) {
			validos = false;
			cNumero.setBackground(new Color(255,200,200));
			cNumero.requestFocus();
		}
		
		if(cCalle.getText().length() == 0) {
			validos = false;
			cCalle.setBackground(new Color(255,200,200));
			cCalle.requestFocus();
		}
		
		if(cNacionalidad.getText().length() == 0) {
			validos = false;
			cNacionalidad.setBackground(new Color(255,200,200));
			cNacionalidad.requestFocus();
		}
		
		if(cOcupacion.getText().length() == 0) {
			validos = false;
			cOcupacion.setBackground(new Color(255,200,200));
			cOcupacion.requestFocus();
		}
		
		if(cTelefono.getText().length() == 0) {
			validos = false;
			cTelefono.setBackground(new Color(255,200,200));
			cTelefono.requestFocus();
		}
		
		if(datePicker.getModel().getValue() == null) {
			validos = false;
			datePicker.getComponent(0).setBackground(new Color(255,200,200));
			datePicker.getComponent(1).requestFocus();
		}
		
		if(((PosicionIVADTO)lRespIva.getSelectedItem()).getId() == -1) {
			validos = false;
			lRespIva.setBackground(new Color(255,200,200));
			lRespIva.requestFocus();
		}
		
		if(cNroDoc.getText().length() == 0) {
			validos = false;
			cNroDoc.setBackground(new Color(255,200,200));
			cNroDoc.requestFocus();
		}
		
		if(((TipoDocumentoDTO)lTipoDoc.getSelectedItem()).getId() == -1) {
			validos = false;
			lTipoDoc.setBackground(new Color(255,200,200));
			lTipoDoc.requestFocus();
		}
		
		if(cNombre.getText().length() == 0) {
			validos = false;
			cNombre.setBackground(new Color(255,200,200));
			cNombre.requestFocus();
		}
		
		if(cApellido.getText().length() == 0) {
			validos = false;
			cApellido.setBackground(new Color(255,200,200));
			cApellido.requestFocus();
		}
		
		return validos;
	}

	
	private Boolean emailValido() {
		
        if(cEmail.getText().length() == 0) return true; //Si no escribio mail, esta bien porque es opcional
		
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        
        Matcher mather = pattern.matcher(cEmail.getText());
        
        return mather.find();
    
	}
	
	private Boolean pisoNumerico() {
		try {
			if(cPiso.getText().length() > 0) {
				Integer.parseInt(cPiso.getText());
			}
		}
		catch(NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
	private void crearPasajero() {
		Date fechaSeleccionada = (Date) datePicker.getModel().getValue();
		LocalDate fechaNacimiento = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		PasajeroDTO pasajero = new PasajeroDTO();
		
		pasajero.setApellido(cApellido.getText());
		pasajero.setNombre(cNombre.getText());
		pasajero.setIdTipoDoc(((TipoDocumentoDTO)lTipoDoc.getSelectedItem()).getId());
		pasajero.setNro_doc(cNroDoc.getText());
		if(cCUIT.getText().length() > 0) pasajero.setCuit(cCUIT.getText());
		pasajero.setIdPosIva(((PosicionIVADTO)lRespIva.getSelectedItem()).getId());
		pasajero.setFecha_nacimiento(fechaNacimiento);
		pasajero.setTelefono(cTelefono.getText());
		if(cEmail.getText().length() > 0) pasajero.setMail(cEmail.getText());
		pasajero.setOcupacion(cOcupacion.getText());
		pasajero.setNacionalidad(cNacionalidad.getText());
		pasajero.setCalle(cCalle.getText());
		pasajero.setNumero(cNumero.getText());
		if(cPiso.getText().length() > 0) pasajero.setPiso(Integer.parseInt(cPiso.getText()));
		if(cDepartamento.getText().length() > 0) pasajero.setDepartamento(cDepartamento.getText());
		pasajero.setCodigo_postal(cCodPostal.getText());
		pasajero.setIdLocalidad(((LocalidadDTO)lLocalidad.getSelectedItem()).getIdLocalidad());
		
		GestorPasajeros gestor = GestorPasajeros.getInstance();
		Boolean creado = gestor.crearPasajero(pasajero);
		
		if(creado) {
			MensajeInformativo pasajeroCreado = new MensajeInformativo(App.getVentana(),"<html><body>El pasajero "+cNombre.getText()+" "+cApellido.getText()+"<br>ha sido satisfactoriamente cargado en el sistema.<br>¿Desea cargar otro?</body><html>","Sí","No");
			App.getVentana().setEnabled(false);
			pasajeroCreado.pack();
			pasajeroCreado.setLocationRelativeTo(App.getVentana());
			pasajeroCreado.setVisible(true);
			
			pasajeroCreado.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					App.getVentana().setEnabled(true);
					App.getVentana().setVisible(true);
					App.darAltaPasajero("","",new TipoDocumentoDTO(-1,""),"");
				}
			});
			
			ActionListener listenerSi = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pasajeroCreado.dispose();
					App.getVentana().setEnabled(true);
					App.getVentana().setVisible(true);
					App.darAltaPasajero("","",new TipoDocumentoDTO(-1,""),"");
				}				
			};
			
			ActionListener listenerNo = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pasajeroCreado.dispose();
					App.getVentana().setEnabled(true);
					App.getVentana().setVisible(true);
					App.gestionarPasajeros();
				}				
			};
			
			pasajeroCreado.setListeners(listenerSi, listenerNo);
		}
		else {
			MensajeError pasajeroNoCreado = new MensajeError(App.getVentana(),"<html><body>El pasajero no se pudo crear con éxito.<br>Intente nuevamente.</body></html>","Aceptar","");
			pasajeroNoCreado.getContentPane().remove(3);
			App.getVentana().setEnabled(false);
			pasajeroNoCreado.pack();
			pasajeroNoCreado.setLocationRelativeTo(App.getVentana());
			pasajeroNoCreado.setVisible(true);
			
			pasajeroNoCreado.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					App.getVentana().setEnabled(true);
					App.getVentana().setVisible(true);
				}
			});
			
			ActionListener listener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pasajeroNoCreado.dispose();
					App.getVentana().setEnabled(true);
					App.getVentana().setVisible(true);
				}
			};
			
			pasajeroNoCreado.setListeners(listener,null);
		}
	}
}
