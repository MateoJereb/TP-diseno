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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import clase_app.App;
import clases.dto.PasajeroDTO;
import clases.dto.TipoDocumentoDTO;
import clases.gestores.GestorPasajeros;
import componentes_swing.*;
import componentes_swing.modelos_tablas.ModeloTablaPasajeros;
import componentes_swing.retroalimentacion.*;

public class GestionarPasajeros extends JPanel {

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
	private TablaJ tabla;
	private ModeloTablaPasajeros modelo;
	
	//Panel botones
	private JPanel panelBotones;
	private BotonJ siguiente;
	private BotonJ cancelar;
	
	public GestionarPasajeros() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		panelBusqueda();
		panelResultados();
		panelBotones();
		
		buscar.setNextFocusableComponent(siguiente);
		cancelar.setNextFocusableComponent(cApellido);
	}
	
	public GestionarPasajeros(String apellidoCargado, String nombreCargado, TipoDocumentoDTO tipoDocCargado, String nroDocCargado) {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		
		panelBusqueda();
		panelResultados();
		panelBotones();
		
		buscar.setNextFocusableComponent(siguiente);
		cancelar.setNextFocusableComponent(cApellido);
		
		//Cargar los valores en los campos
		cApellido.setText(apellidoCargado);
		cNombre.setText(nombreCargado);
		lTipoDoc.setSelectedItem(tipoDocCargado);
		cNroDoc.setText(nroDocCargado);
		
		//Actualiza la tabla segun los valores cargados
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
		actualizarTabla(resultado);
	}
	
	private void panelBusqueda() {
		TitledBorder borde  = BorderFactory.createTitledBorder("Buscar pasajeros");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		
		panelBusqueda = new JPanel(new GridBagLayout());
		panelBusqueda.setBackground(Color.WHITE);
		panelBusqueda.setBorder(borde);
		
		GridBagConstraints cons = new GridBagConstraints();
		
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
		
		eApellido = new EtiquetaJ("Apellido");
		cApellido = new CampoJ();
		eNombre = new EtiquetaJ("Nombre");
		cNombre = new CampoJ();
		eTipoDoc = new EtiquetaJ("Tipo de documento");
		lTipoDoc = new ListaTipoDoc();
		eNroDoc = new EtiquetaJ("Nro. documento");
		cNroDoc = new CampoJ();
		buscar = new BotonJ("Buscar");
		
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
				actualizarTabla(resultado);
				
				if(resultado.size() == 0) {
					MensajeInformativo noHayResultados = new MensajeInformativo(App.getVentana(),"<html><body>No se encontraron resultados que coincidan con los<br>criterios de búsqueda.</body></html>","Dar alta de pasajero","Nueva búsqueda");
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
					
					ActionListener listenerAlta = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							noHayResultados.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
							App.darAltaPasajero(cApellido.getText(), cNombre.getText(), (TipoDocumentoDTO)lTipoDoc.getSelectedItem(), cNroDoc.getText());
						}					
					};
					
					ActionListener listenerNuevaBusqueda = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							noHayResultados.dispose();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
						}					
					};
					
					noHayResultados.setListeners(listenerAlta, listenerNuevaBusqueda);
				}
			}
		});
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
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(10,20,15,20);
		cons.anchor = GridBagConstraints.CENTER;
		
		add(panelResultados,cons);
		
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
		
		TableRowSorter<ModeloTablaPasajeros> sorter = new TableRowSorter<ModeloTablaPasajeros>(modelo);
		tabla.setRowSorter(sorter);
		
		cons.gridy = 0;
		panelResultados.add(scroll,cons);
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
		panelResultados.add(panelBotones,cons);
		
		siguiente = new BotonJ("Siguiente");
		cancelar = new BotonJ("Cancelar");
		
		cons.gridy=0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(0,10,10,0);
		panelBotones.add(siguiente,cons);
		
		cons.gridx=1;
		panelBotones.add(cancelar,cons);
		
		siguiente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer fila = tabla.getSelectedRow();
				
				if(fila == -1) {						
					App.darAltaPasajero(cApellido.getText(), cNombre.getText(), (TipoDocumentoDTO)lTipoDoc.getSelectedItem(), cNroDoc.getText());
				}
				else {
					//Modificar
				}
			}
		});
		
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
	
}
