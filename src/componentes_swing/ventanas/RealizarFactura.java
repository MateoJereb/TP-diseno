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
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clase_app.App;
import clases.Pasajero;
import clases.dto.ConsumosDTO;
import clases.dto.EstadiaDTO;
import clases.dto.FacturaDTO;
import clases.dto.PasajeroDTO;
import clases.dto.ResponsablePagoDTO;
import clases.gestores.GestorConsumos;
import clases.gestores.GestorFacturas;
import componentes_swing.BotonJ;
import componentes_swing.CheckBoxJ;
import componentes_swing.EtiquetaJ;
import componentes_swing.retroalimentacion.MensajeConfirmacion;
import componentes_swing.retroalimentacion.MensajeError;
import componentes_swing.retroalimentacion.MensajeInformativo;
import enums.TipoFactura;
import facturas.*;

public class RealizarFactura extends JPanel{

	private EtiquetaJ eANombreDe;
	private EtiquetaJ eSeleccioneItems;
	private EtiquetaJ eTipoFactura;
	private CheckBoxJ checkEstadia;
	private EtiquetaJ eConsumos;
	private BotonJ seleccionarConsumos;
	private EtiquetaJ etotal;
	private BotonJ aceptar;
	private BotonJ cancelar;
	private JPanel facturacion;
	private JPanel panelBotones;
	private JPanel panel;
	private Double totalAPagar;
	private Double subTotalConsumos;
	private JDialog ventana;
	private LinkedHashMap<ConsumosDTO,Integer> consumosAFacturar ;
	private EstadiaDTO estadia;
	private Object responsable;
	private EstadiaDTO estadiaAFacturar; //Es la estadia que se factura dependiendo si se selecciona checkEstadia o no. Si no se selecciona se pasa a realizarFactura() como null
	
	public RealizarFactura(EstadiaDTO est,Object resp, JDialog vent) {
		super();			
		ventana = vent;
		responsable = resp;
		estadia = est;
		estadiaAFacturar = null;
		consumosAFacturar = new LinkedHashMap<ConsumosDTO,Integer>();
		
		GestorConsumos gestor = GestorConsumos.getInstance();
		List<ConsumosDTO> listaCons= gestor.buscarConsumos(est.getId().get());
		
		for(ConsumosDTO cs : listaCons) consumosAFacturar.put(cs, 0);
		
		GridBagConstraints cons = new GridBagConstraints();

		setBorder(new EmptyBorder(10, 20, 10, 20));
		setLayout(new GridBagLayout());
		setBackground(UIManager.getColor("CheckBox.focus"));
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.WHITE);
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.BOTH;
		this.add(panel, cons);
		
		panelFacturacion(est.getId().get(),est.getMonto().get());
		panelBotones();
	}
	
	private void panelFacturacion(Integer idEstadia,Double montoEstadia) {
		GridBagConstraints cons = new GridBagConstraints();
		TitledBorder borde  = BorderFactory.createTitledBorder("Facturaci�n");
		borde.setTitleFont(new Font("Microsoft Tai Le",Font.PLAIN,14));
		borde.setTitleJustification(TitledBorder.LEFT);
		borde.setBorder(BorderFactory.createLineBorder(UIManager.getColor("CheckBox.focus")));
		totalAPagar =0.0;
		subTotalConsumos=0.0;
		facturacion = new JPanel();
		facturacion.setLayout(new GridBagLayout());
		facturacion.setBackground(Color.WHITE);
		facturacion.setBorder(borde);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(20,20,20,20);
		cons.anchor = GridBagConstraints.NORTH;
		panel.add(facturacion, cons);
		
		
		eSeleccioneItems= new EtiquetaJ("Seleccione los items a facturar. ");
		if(responsable.getClass() ==PasajeroDTO.class) {
		eTipoFactura = new EtiquetaJ("Tipo Factura: "+((PasajeroDTO)responsable).getTipoFactura().get());
		eANombreDe = new EtiquetaJ("Se realizar� la factura a nombre de: "+((PasajeroDTO)responsable).getApellido().get()+" "+((PasajeroDTO)responsable).getNombre().get());
		}
		else {
			eTipoFactura = new EtiquetaJ("Tipo Factura: A");
			eANombreDe = new EtiquetaJ("Se realizar� la factura a nombre de: "+((ResponsablePagoDTO)responsable).getRazon_social().get());

		}
		checkEstadia = new CheckBoxJ("Valor de la estad�a: ");
		checkEstadia.setForeground(Color.BLACK);
		eConsumos= new EtiquetaJ("Consumos de la habitaci�n: $ "+subTotalConsumos);
		seleccionarConsumos= new BotonJ("Seleccionar consumos");
		etotal = new EtiquetaJ ("TOTAL: $ "+totalAPagar);
		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		etotal.setFont(new Font("Microsoft Tai Le", Font.BOLD,13).deriveFont(fontAttributes));
		aceptar = new BotonJ("Aceptar");
		cancelar= new BotonJ("Cancelar");
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(5,30,5,5);
		cons.anchor = GridBagConstraints.WEST;
		facturacion.add(eANombreDe, cons);
		
		cons.gridy=1;
		facturacion.add(eSeleccioneItems, cons);
		
		cons.gridy=2;
		cons.insets= new Insets(15,50,5,5);
		facturacion.add(eTipoFactura, cons);
		cons.gridy=3;
		facturacion.add(checkEstadia, cons);
		cons.gridy=4;
		facturacion.add(eConsumos, cons);;
		cons.anchor=GridBagConstraints.CENTER;
		cons.gridy=5;
		facturacion.add(seleccionarConsumos, cons);
		cons.anchor= GridBagConstraints.EAST;
		cons.gridy=7;
		facturacion.add(etotal, cons);
		checkEstadia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(checkEstadia.isSelected()) {
					totalAPagar+= montoEstadia;
					estadiaAFacturar = estadia;
				}
				else {
					totalAPagar-=montoEstadia;
					estadiaAFacturar=null;
				}
				agregarIvaTotal(totalAPagar);
				etotal.setText("TOTAL: $ "+totalAPagar);

			}
		});
		
		seleccionarConsumos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarConsumos(consumosAFacturar);
			}
		});
	}
	private void panelBotones() {
		
		GridBagConstraints cons = new GridBagConstraints();
		panelBotones = new JPanel();
		panelBotones.setLayout(new GridBagLayout());
		panelBotones.setBackground(Color.WHITE);
		aceptar = new BotonJ("Aceptar");
		cancelar = new BotonJ("Cancelar");
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(0,20,20,20);
		cons.anchor = GridBagConstraints.SOUTH;
		panel.add(panelBotones, cons);
		
		cons.gridx=0;
		cons.gridy=0;
		cons.weightx=0.1;
		cons.fill= GridBagConstraints.NONE;
		cons.insets= new Insets(10,5,10,0);
		cons.anchor= GridBagConstraints.EAST;
		panelBotones.add(aceptar, cons);
		cons.gridx=1;
		cons.weightx=0;
		cons.anchor= GridBagConstraints.WEST;
		panelBotones.add(cancelar, cons);
		
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(montoNoNulo()) {
					GestorFacturas gestor = GestorFacturas.getInstance();
					
					LinkedHashMap<ConsumosDTO,Integer> mapFactura = new LinkedHashMap<ConsumosDTO, Integer>();
					
					for(ConsumosDTO c : consumosAFacturar.keySet()) {
						if(consumosAFacturar.get(c) > 0) mapFactura.put(c, consumosAFacturar.get(c));
					}
					
					Integer idFactura = gestor.facturar(responsable, estadiaAFacturar, mapFactura);
					
					
					if(idFactura==-1) {
						//Si la factura no se puede crear, se retorna un id negativo y se muestra el mensaje de Error correspondiente
						String mensaje ="<html><body>No se pudo generar la factura correctamente<br>Intente nuevamente</body></html>";
						MensajeError error = new MensajeError(App.getVentana(),mensaje, "Aceptar", "");
						error.getContentPane().remove(3);
					
						App.getVentana().setEnabled(false);
						error.pack();
						error.setLocationRelativeTo(ventana);
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
								error.dispose();
								App.getVentana().setEnabled(true);
								App.getVentana().setVisible(true);
								
						}
							
						};
						error.setListeners(listenerAceptar, null);
					}
					else {
						ventana.dispose();
						
						FacturaDTO facturaDTO = null;
						JPanel panelFactura = null;
						
						if(responsable.getClass() == PasajeroDTO.class) facturaDTO = gestor.recuperarFacturaRespFisico(idFactura);
						else facturaDTO = gestor.recuperarFacturaRespJuridico(idFactura);
						
						if(facturaDTO.getTipo().get() == TipoFactura.A) panelFactura = new FacturaA(facturaDTO,checkEstadia.isSelected());
						else panelFactura = new FacturaB(facturaDTO,checkEstadia.isSelected());
							
						FacturaImpresion ventanaImpresion = new FacturaImpresion(App.getVentana(), panelFactura,facturaDTO);
						
						ventanaImpresion.setSize(570,800);
						ventanaImpresion.setLocationRelativeTo(App.getVentana());
						ventanaImpresion.setVisible(true);
					}
				}
				else {
					// Mostrar mensaje error monto  nulo
					String mensaje ="<html><body>El monto es 0.0. Seleccione un monto a facturar.</body></html>";
					MensajeError error = new MensajeError(App.getVentana(),mensaje, "Aceptar", "");
					error.getContentPane().remove(3);
					
					App.getVentana().setEnabled(false);
					error.pack();
					error.setLocationRelativeTo(ventana);
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
							error.dispose();
							App.generarFactura();
							App.getVentana().setEnabled(true);
							App.getVentana().setVisible(true);
							
						}
						
					};
					error.setListeners(listenerAceptar, null);
			}
			}
			
		});
	}
	private void seleccionarConsumos(LinkedHashMap<ConsumosDTO,Integer> consumosAFacturar){
		JDialog ventanaSeleccionarConsumos = new JDialog(ventana,"Seleccionar consumos");
		SeleccionarConsumos panel = new SeleccionarConsumos(consumosAFacturar,this);
		ventanaSeleccionarConsumos.setContentPane(panel);
		ventanaSeleccionarConsumos.setSize(700,600);
		ventanaSeleccionarConsumos.setLocationRelativeTo(ventana);
		App.getVentana().setEnabled(false);
		ventana.setEnabled(false);

		
		panel.getAceptar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ventanaSeleccionarConsumos.dispose();
				App.getVentana().setVisible(true);
				ventana.setEnabled(true);
				ventana.setVisible(true);		
			}
			
		});
		
		panel.getCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaSeleccionarConsumos.dispose();
				App.getVentana().setVisible(true);
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}
		});
		
		
		
		ventanaSeleccionarConsumos.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				App.getVentana().setVisible(true);
				ventana.setEnabled(true);
				ventana.setVisible(true);
			}	
		});
		
		ventanaSeleccionarConsumos.setVisible(true);
		
	
	}
	public BotonJ getCancelar() {
		return cancelar;
	}
	public void setConsumos(LinkedHashMap<ConsumosDTO, Integer> cons) {
		this.consumosAFacturar=cons;
		actualizarSubTotalConsumos(cons);
		return;
	}
	private void actualizarSubTotalConsumos(LinkedHashMap<ConsumosDTO, Integer> cons) {
		subTotalConsumos =0.0;
		
		for(ConsumosDTO consumos: cons.keySet()) {
		
			subTotalConsumos+=consumos.getMonto().get()*cons.get(consumos);
		}
		
		totalAPagar = subTotalConsumos;
		if(checkEstadia.isSelected()) totalAPagar += estadiaAFacturar.getMonto().get();
		agregarIvaTotal(totalAPagar);
		eConsumos.setText("Consumos de la habitaci�n: $ "+subTotalConsumos);
		etotal.setText("TOTAL: $ "+totalAPagar);
		return;
	}
	public BotonJ getAceptar() {
		return aceptar;
	}
	private void agregarIvaTotal(Double totalAPagar) {
		if(responsable.getClass()== PasajeroDTO.class)totalAPagar*= (1+ ((PasajeroDTO)responsable).getPorcentaje().get());
		else totalAPagar*= (1.21);
	}
	private Boolean montoNoNulo() {
		return(totalAPagar !=0.0);
	}

}
