package clases.gestores;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import base_de_datos.AdministradorBDFacturas;
import clases.Consumo;
import clases.ConsumoFacturado;
import clases.Estadia;
import clases.Factura;
import clases.HabitacionDobleEstandar;
import clases.HabitacionDobleSuperior;
import clases.HabitacionFamily;
import clases.HabitacionIndividual;
import clases.HabitacionSuite;
import clases.Pasajero;
import clases.ResponsablePago;
import clases.dto.ConsumoFacturadoDTO;
import clases.dto.ConsumosDTO;
import clases.dto.EstadiaDTO;
import clases.dto.FacturaDTO;
import clases.dto.HabitacionDTO;
import clases.dto.HabitacionDobleEstandarDTO;
import clases.dto.HabitacionDobleSuperiorDTO;
import clases.dto.HabitacionFamilyDTO;
import clases.dto.HabitacionIndividualDTO;
import clases.dto.HabitacionSuiteDTO;
import clases.dto.PasajeroDTO;
import clases.dto.ResponsablePagoDTO;
import enums.TipoFactura;

public class GestorFacturas {

	public static GestorFacturas instancia;
	
	private List<Factura> facturas;
	
	public static GestorFacturas getInstance() {
		if(instancia == null) {
			instancia = new GestorFacturas();
		}
		
		return instancia;
	}
	
	private GestorFacturas() {
		facturas = new ArrayList<Factura>();
	}
	public Integer facturar(Object respDTO, EstadiaDTO estadiaDTO, LinkedHashMap<ConsumosDTO, Integer> consumosDTO) {
		GestorConsumos gestorConsumos = GestorConsumos.getInstance();
		List<ConsumoFacturado> consumos = gestorConsumos.buscarConsumos(consumosDTO);
		Estadia estadia=null;
		Factura factura = new Factura();
		factura.setConsumos(consumos);
		
		//Si la estadia no se factura, estadiaDTO es null. De lo contrario debe agregarse a la factura.
		if(estadiaDTO !=null) {
		GestorEstadias gestorEstadia = GestorEstadias.getInstance();
		estadia = gestorEstadia.buscarEstadia(estadiaDTO);		
		factura.setEstadia(estadia);
		}
		Double iva=0.0;
		if(respDTO.getClass()== PasajeroDTO.class) {
			
			GestorPasajeros gestorPasajeros= GestorPasajeros.getInstance();
			Pasajero pasajero = gestorPasajeros.buscarPasajero((PasajeroDTO)respDTO);
			factura.setResponsable_fisico(pasajero);
			factura.setResponsable_juridicoNull();
			factura.setIva(pasajero.getPosicion_iva().getPorcentaje());
			factura.setTipo(pasajero.getPosicion_iva().getTipoFactura());
		}
		if(respDTO.getClass()== ResponsablePagoDTO.class) {
			GestorResponsablePago gestorResponsable = GestorResponsablePago.getInstance();
			ResponsablePago responsablePago = gestorResponsable.buscarResponsable((ResponsablePagoDTO) respDTO);
			factura.setResponsable_juridico(responsablePago);
			factura.setResponsable_fisicoNull();
			factura.setIva(0.21);
			factura.setTipo(TipoFactura.A);
		}
		Double monto = sumarMontos(estadia,consumos);
		factura.setMonto_neto(monto);
		factura.setMonto_total(monto*(1.0+ factura.getIva()));
		
		AdministradorBDFacturas admin = new AdministradorBDFacturas();
		factura.setId(admin.registrarFactura(factura));
		return factura.getId();
	}
	
	private Double sumarMontos(Estadia estadia, List<ConsumoFacturado> consumo) {
		Double monto =0.0;
		
		if(estadia!=null) monto +=estadia.getMonto();
		for(ConsumoFacturado cons: consumo) {
			monto+= cons.getConsumo().getMonto() * cons.getCantidad();
		}
		return monto;
	}
	public FacturaDTO recuperarFacturaRespFisico(Integer idFactura) {
		AdministradorBDFacturas admin = new AdministradorBDFacturas();
		return convertiraDTORespFisico(admin.recuperarFacturaRespFisico(idFactura));
		
	}
	public FacturaDTO recuperarFacturaRespJuridico(Integer idFactura) {
		AdministradorBDFacturas admin = new AdministradorBDFacturas();
		return convertiraDTORespJuridico(admin.recuperarFacturaRespJuridico(idFactura));
	}
	private FacturaDTO convertiraDTORespFisico(Factura factura) {
		FacturaDTO salida = new FacturaDTO();
		EstadiaDTO estadia = new EstadiaDTO();
		HabitacionDTO habitacion = null;
		PasajeroDTO pasajero = new PasajeroDTO();
		List<ConsumoFacturadoDTO> consumosFacturadosDTO = new ArrayList<ConsumoFacturadoDTO>();
		
		salida.setId_factura(factura.getId());
		salida.setTipo(factura.getTipo());
		salida.setFecha(factura.getFecha());
		salida.setMonto_neto(factura.getMonto_neto());
		salida.setIva(factura.getIva());
		salida.setMonto_total(factura.getMonto_total());
		pasajero.setNombre(factura.getResponsable_fisico().get().getNombre());
		pasajero.setApellido(factura.getResponsable_fisico().get().getApellido());
		pasajero.setTelefono(factura.getResponsable_fisico().get().getTelefono());
		pasajero.setNombreLocalidad(factura.getResponsable_fisico().get().getDireccion().getLocalidad().getNombre());
		pasajero.setCodigo_postal(factura.getResponsable_fisico().get().getDireccion().getLocalidad().getCodigo_postal());
		pasajero.setCalle(factura.getResponsable_fisico().get().getDireccion().getCalle());
		pasajero.setNumero(factura.getResponsable_fisico().get().getDireccion().getNumero());
		estadia.setHora_entrada(factura.getEstadia().getHora_entrada());
		estadia.setHora_salida(factura.getEstadia().getHora_salida());
		
		if(factura.getEstadia().getHabitacion().getClass() == HabitacionIndividual.class) habitacion = new HabitacionIndividualDTO();
		if(factura.getEstadia().getHabitacion().getClass() == HabitacionDobleEstandar.class) habitacion = new HabitacionDobleEstandarDTO();
		if(factura.getEstadia().getHabitacion().getClass() == HabitacionSuite.class) habitacion = new HabitacionSuiteDTO();
		if(factura.getEstadia().getHabitacion().getClass() == HabitacionDobleSuperior.class) habitacion = new HabitacionDobleSuperiorDTO();
		if(factura.getEstadia().getHabitacion().getClass() == HabitacionFamily.class) habitacion = new HabitacionFamilyDTO();
		
		habitacion.setNro(factura.getEstadia().getHabitacion().getNro());
		pasajero.setPosicion(factura.getResponsable_fisico().get().getPosicion_iva().getPosicion());
		
		for(ConsumoFacturado cons: factura.getConsumos()) {
			ConsumoFacturadoDTO consFactDTO= new ConsumoFacturadoDTO();
			ConsumosDTO consDTO = new ConsumosDTO();
			consDTO.setDescripcion(cons.getConsumo().getDescripcion());
			consDTO.setMonto(cons.getConsumo().getMonto());
			
			consFactDTO.setFactura(salida);
			consFactDTO.setConsumo(consDTO);
			consFactDTO.setCantidad(cons.getCantidad());
			consumosFacturadosDTO.add(consFactDTO);
			
		}
		return salida;

	}
	private FacturaDTO convertiraDTORespJuridico(Factura factura) {
		FacturaDTO salida = new FacturaDTO();
		EstadiaDTO estadia = new EstadiaDTO();
		HabitacionDTO habitacion = null;
		ResponsablePagoDTO responsable = new ResponsablePagoDTO();
		List<ConsumoFacturadoDTO> consumosFacturadosDTO = new ArrayList<ConsumoFacturadoDTO>();
		
		salida.setId_factura(factura.getId());
		salida.setTipo(factura.getTipo());
		salida.setFecha(factura.getFecha());
		salida.setMonto_neto(factura.getMonto_neto());
		salida.setIva(factura.getIva());
		salida.setMonto_total(factura.getMonto_total());
		responsable.setRazon_social(factura.getResponsable_juridico().get().getRazon_social());
		responsable.setTelefono(factura.getResponsable_fisico().get().getTelefono());
		responsable.setNombreLocalidad(factura.getResponsable_juridico().get().getDireccion().getLocalidad().getNombre());
		responsable.setCodigo_postal(factura.getResponsable_juridico().get().getDireccion().getLocalidad().getCodigo_postal());
		responsable.setCalle(factura.getResponsable_fisico().get().getDireccion().getCalle());
		responsable.setNumero(factura.getResponsable_fisico().get().getDireccion().getNumero());
		estadia.setHora_entrada(factura.getEstadia().getHora_entrada());
		estadia.setHora_salida(factura.getEstadia().getHora_salida());
		
		if(factura.getEstadia().getHabitacion().getClass() == HabitacionIndividual.class) habitacion = new HabitacionIndividualDTO();
		if(factura.getEstadia().getHabitacion().getClass() == HabitacionDobleEstandar.class) habitacion = new HabitacionDobleEstandarDTO();
		if(factura.getEstadia().getHabitacion().getClass() == HabitacionSuite.class) habitacion = new HabitacionSuiteDTO();
		if(factura.getEstadia().getHabitacion().getClass() == HabitacionDobleSuperior.class) habitacion = new HabitacionDobleSuperiorDTO();
		if(factura.getEstadia().getHabitacion().getClass() == HabitacionFamily.class) habitacion = new HabitacionFamilyDTO();
		
		habitacion.setNro(factura.getEstadia().getHabitacion().getNro());
		for(ConsumoFacturado cons: factura.getConsumos()) {
			ConsumoFacturadoDTO consFactDTO= new ConsumoFacturadoDTO();
			ConsumosDTO consDTO = new ConsumosDTO();
			consDTO.setDescripcion(cons.getConsumo().getDescripcion());
			consDTO.setMonto(cons.getConsumo().getMonto());
			
			consFactDTO.setFactura(salida);
			consFactDTO.setConsumo(consDTO);
			consFactDTO.setCantidad(cons.getCantidad());
			consumosFacturadosDTO.add(consFactDTO);
			
		}
		return salida;

	}
	
}
