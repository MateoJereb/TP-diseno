package facturas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;

import clases.dto.ConsumoFacturadoDTO;
import clases.dto.EstadiaDTO;
import clases.dto.FacturaDTO;
import clases.dto.PasajeroDTO;
import clases.dto.ResponsablePagoDTO;
import componentes_swing.EtiquetaJ;
import componentes_swing.TablaJ;

public class FacturaB extends JPanel {

	private Border blackLineBorder = BorderFactory.createLineBorder(Color.BLACK);
	private ModeloTablaFactura modelo;
	private TablaJ tabla;
	
	public FacturaB(FacturaDTO factura, Boolean estadiaFacturada) {
		setBackground(Color.WHITE);
		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(570,2000));
		
		infoHotelYFactura(factura.getId_factura().get(),factura.getFecha().get());
		tipoFactura();
		infoCliente(factura.getResponsable_fisico(),factura.getResponsable_juridico());
		detalles(factura,estadiaFacturada);
		total(factura);
	}
	
	private void infoHotelYFactura(Integer nroFactura, LocalDateTime fechaFactura) {
		JPanel panelSuperior = new JPanel(new GridLayout(1,2));
		JPanel infoHotel = new JPanel(new GridBagLayout());
		infoHotel.setBackground(Color.WHITE);
		infoHotel.setBorder(blackLineBorder);
		
		EtiquetaJ nombreHotel = new EtiquetaJ("HOTEL PREMIER");
		nombreHotel.setFont(new Font("Calibri",Font.BOLD,20));
		EtiquetaJ datosHotel = new EtiquetaJ("<html><body><center>"
				+ "CUIT: 30-53755001-1<br>"
				+ "Lavaise 610<br>"
				+ "+54 342 4567890<br>"
				+ "I.V.A Responsable Inscripto</center></body></html>");
		datosHotel.setFont(new Font("Calibri",Font.PLAIN,11));
		
		GridBagConstraints cons = new GridBagConstraints();
					
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(10, 15, 0, 15);
		cons.anchor = GridBagConstraints.CENTER;
		infoHotel.add(nombreHotel,cons);
				
		cons.gridy = 1;
		cons.insets = new Insets(10, 15, 10, 15);
		infoHotel.add(datosHotel,cons);
		
		
		JPanel infoFactura = new JPanel(new GridBagLayout());
		infoFactura.setBackground(Color.WHITE);
		infoFactura.setBorder(blackLineBorder);
		
		EtiquetaJ nro = new EtiquetaJ("Factura #"+nroFactura);
		nro.setFont(new Font("Calibri",Font.PLAIN,14));
		
		EtiquetaJ fecha = new EtiquetaJ("Fecha: "+fechaFactura.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		fecha.setFont(new Font("Calibri",Font.PLAIN,11));
		
		GridBagConstraints cons2 = new GridBagConstraints();
		cons2.gridx = 0;
		cons2.gridy = 0;
		cons2.gridwidth = 1;
		cons2.gridheight = 1;
		cons2.weightx = 0.1;
		cons2.weighty = 0;
		cons2.fill = GridBagConstraints.NONE;
		cons2.insets = new Insets(10, 15, 0, 15);
		cons2.anchor = GridBagConstraints.EAST;
		infoFactura.add(nro,cons2);
		
		cons2.gridy = 1;
		cons2.insets = new Insets(10, 15, 10, 15);
		infoFactura.add(fecha,cons2);
		
		panelSuperior.add(infoHotel);
		panelSuperior.add(infoFactura);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth =2;
		cons.insets = new Insets(0, 0, 0, 0);
		cons.fill = GridBagConstraints.HORIZONTAL;
		add(panelSuperior,cons);
	}
	
	private void tipoFactura() {
		JPanel panelAux = new JPanel(new GridBagLayout());
		panelAux.setBackground(Color.WHITE);
		
		JPanel blancoIzq = new JPanel(new GridBagLayout());
		blancoIzq.setBackground(Color.WHITE);
		blancoIzq.setBorder(blackLineBorder);
		
		JPanel blancoDer = new JPanel(new GridBagLayout());
		blancoDer.setBackground(Color.WHITE);
		blancoDer.setBorder(blackLineBorder);
		
		JPanel tipoFactura = new JPanel(new GridBagLayout());
		tipoFactura.setBackground(Color.WHITE);
		tipoFactura.setBorder(blackLineBorder);
		
		EtiquetaJ letra = new EtiquetaJ("B");
		letra.setFont(new Font("Calibri",Font.BOLD,18));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(5, 10, 5, 10);
		tipoFactura.add(letra,cons);
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.insets = new Insets(0,0,0,0);
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;
		panelAux.add(tipoFactura,cons);
		
		cons.gridx = 0;
		cons.weightx = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		panelAux.add(blancoIzq,cons);
		
		cons.gridx = 2;
		panelAux.add(blancoDer,cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 2;
		add(panelAux,cons);
		
	}

	private void infoCliente(Optional<PasajeroDTO> respFisico, Optional<ResponsablePagoDTO> respJuridico) {
		JPanel infoCliente = new JPanel(new GridBagLayout());
		infoCliente.setBackground(Color.WHITE);
		infoCliente.setBorder(blackLineBorder);
		
		String razon = null;
		String direccion = null;
		String localidad = null;
		String codigoPostal = null;
		String telefono = null;
		String posIva = null;
		
		if(respFisico.isPresent()) {
			razon = respFisico.get().getNombre().get()+" "+respFisico.get().getApellido().get();
			direccion = respFisico.get().getCalle().get()+ " "+respFisico.get().getNumero().get();
			if(respFisico.get().getPiso().isPresent()) direccion+="Piso "+respFisico.get().getPiso().get();
			if(respFisico.get().getDepartamento().isPresent()) direccion+="Departamento "+respFisico.get().getDepartamento().get();
			localidad = respFisico.get().getNombreLocalidad().get();
			codigoPostal = respFisico.get().getCodigo_postal().get();
			telefono = respFisico.get().getTelefono().get();
			posIva = respFisico.get().getPosicion().get();
		}
		else {
			razon = respJuridico.get().getRazon_social().get();
			direccion = respJuridico.get().getCalle().get()+ " "+respJuridico.get().getNumero().get();
			if(respJuridico.get().getPiso().isPresent()) direccion+="Piso "+respJuridico.get().getPiso().get();
			if(respJuridico.get().getDepartamento().isPresent()) direccion+="Departamento "+respJuridico.get().getDepartamento().get();
			localidad = respJuridico.get().getNombreLocalidad().get();
			codigoPostal = respJuridico.get().getCodigo_postal().get();
			telefono = respJuridico.get().getTelefono().get();
			posIva = "Responsable Inscripto";
		}
		
		EtiquetaJ info = new EtiquetaJ("<html><body>"
				+"Razón social: "+razon+"<br>"
				+ "Dirección: "+direccion+"<br>"
				+ "Localidad: "+localidad+"<br>"
				+ "Código postal: "+codigoPostal+"<br>"
				+ "Teléfono: "+telefono+"<br>"
				+ "IVA: "+posIva+"<br></body></html>");
		info.setFont(new Font("Calibri",Font.PLAIN,11));
		
		GridBagConstraints cons = new GridBagConstraints();
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(5, 15, 5, 15);
		cons.anchor = GridBagConstraints.WEST;
		infoCliente.add(info,cons);
		
		cons.gridy = 2;
		cons.gridwidth = 2;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(0, 0, 0, 0);
		add(infoCliente,cons);
		
	}
	
	private void detalles(FacturaDTO factura, Boolean estadiaFacturada) {
		JPanel detalles = new JPanel(new GridBagLayout());
		detalles.setBackground(getBackground());
		detalles.setBorder(blackLineBorder);
		
		modelo = new ModeloTablaFactura();
		tabla = new TablaJ(modelo);
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBorder(blackLineBorder);
		scroll.setPreferredSize(new Dimension(510,510));
		
		inicializarTabla(factura,estadiaFacturada);
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(10,10,10,10);
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.anchor = GridBagConstraints.CENTER;
		cons.fill = GridBagConstraints.BOTH;
		
		detalles.add(scroll,cons);
		
		cons.gridy = 3;
		cons.gridwidth = 2;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		cons.insets = new Insets(0, 0, 0, 0);
		add(detalles,cons);
	}
	
	private void inicializarTabla(FacturaDTO factura, Boolean estadiaFacturada) {
		JTableHeader header = tabla.getTableHeader();
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		if(estadiaFacturada) {
			Vector<Object> filaEstadia = new Vector<Object>();
			filaEstadia.add(descripcionEstadia(factura.getEstadia().get()));
			filaEstadia.add(factura.getEstadia().get().getMonto().get());
			filaEstadia.add(1);
			filaEstadia.add(factura.getEstadia().get().getMonto().get());
			data.add(filaEstadia);
		}
		
		for(ConsumoFacturadoDTO c : factura.getConsumos().get()) {
			Vector<Object> fila = new Vector<Object>();
			fila.add(c.getConsumo().get().getDescripcion().get());
			fila.add(c.getConsumo().get().getMonto().get());
			fila.add(c.getCantidad().get());
			fila.add(c.getCantidad().get()*c.getConsumo().get().getMonto().get());
			data.add(fila);
		}
		
		modelo.setData(data);
		
		tabla.setColumnSelectionAllowed(false);
		tabla.setFocusable(false);
		tabla.setRowSelectionAllowed(false);
		tabla.setCellSelectionEnabled(false);
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.setGridColor(Color.BLACK);
		tabla.setFont(new Font("Calibri",Font.PLAIN,11));
		
		tabla.getColumnModel().getColumn(0).setPreferredWidth(329);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(60);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(60);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(60);
		
		header.setPreferredSize(new Dimension(header.getPreferredSize().width,30));
		header.setFont(new Font("Calibri",Font.BOLD,11));
		header.setBackground(Color.LIGHT_GRAY);
		header.setForeground(Color.BLACK);
		
	}

	private String descripcionEstadia(EstadiaDTO estadia) {
		String tipoHab = estadia.getHabitacion().get().toString();
		long cantDias = ChronoUnit.DAYS.between(estadia.getHora_entrada().get().toLocalDate(), estadia.getHora_salida().get().toLocalDate());
		
		return "Estadía "+tipoHab+" "+cantDias+" días";
	}
	
	private void total(FacturaDTO factura) {
		JPanel total = new JPanel(new GridBagLayout());
		total.setBackground(Color.WHITE);
		
		JPanel totIzq = new JPanel(new GridBagLayout());
		totIzq.setBackground(Color.WHITE);
		totIzq.setBorder(blackLineBorder);
		
		EtiquetaJ textoTotIzq = new EtiquetaJ("TOTAL $");
		textoTotIzq.setFont(new Font("Calibri",Font.BOLD,14));
		
		JPanel totDer = new JPanel(new GridBagLayout());
		totDer.setBackground(Color.WHITE);
		totDer.setBorder(blackLineBorder);
		
		EtiquetaJ textoTotDer = new EtiquetaJ(factura.getMonto_total().get().toString());
		textoTotDer.setFont(new Font("Calibri",Font.BOLD,14));
		
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.insets = new Insets(5, 5, 5, 5);
		cons.anchor = GridBagConstraints.EAST;
		
		totIzq.add(textoTotIzq,cons);
		totDer.add(textoTotDer,cons);
		
		JPanel blanco = new JPanel();
		blanco.setBackground(Color.WHITE);
		blanco.setBorder(blackLineBorder);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridheight = 1;
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.insets = new Insets(0, 0, 0, 0);
		cons.fill = GridBagConstraints.BOTH;
		total.add(blanco,cons);
		
		cons.gridx = 1;
		cons.gridheight = 1;
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridy = 0;
		total.add(totIzq,cons);
		
		cons.gridx = 2;
		total.add(totDer,cons);
		
		cons.gridx = 0;
		cons.gridwidth = 2;
		cons.gridy = 4;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 0.1;
		cons.weighty = 0;
		add(total,cons);
		
	}
	
}
