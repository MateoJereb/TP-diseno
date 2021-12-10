package clases.gestores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import base_de_datos.AdministradorBDEstadias;
import clases.*;
import clases.dto.*;
import enums.EstadoHabitacion;
import excepciones.HabitacionInexistenteException;
import excepciones.OcupanteEnOtraHabitacionException;

public class GestorEstadias {

	public static GestorEstadias instancia;
	
	private List<Estadia> estadias;
	
	public static GestorEstadias getInstance() {
		if(instancia == null) {
			instancia = new GestorEstadias();
		}
		
		return instancia;
	}
	
	private GestorEstadias() {
		estadias = new ArrayList<Estadia>();
	}
	
	public Map<HabitacionDTO,Map<LocalDate,EstadoHabitacion>> verificarDisponibilidades(Map<HabitacionDTO,Map<LocalDate,EstadoHabitacion>> mapReservas, LocalDate fechaDesdeAux, LocalDate fechaHastaAux){
		
		LocalDateTime fechaDesde = fechaDesdeAux.atTime(23,59);
		LocalDateTime fechaHasta = fechaHastaAux.atTime(23,59);
		AdministradorBDEstadias adminBD = new AdministradorBDEstadias();
		List<Estadia> estadias = adminBD.recuperarEstadias(fechaDesde, fechaHasta);
		
		Map<HabitacionDTO,Map<LocalDate,EstadoHabitacion>> mapFinal = mapReservas;
		
		for(HabitacionDTO hab  : mapFinal.keySet()) {
			for(LocalDate fecha : mapFinal.get(hab).keySet()) {
				if(hab.getEstado_actual().get() != EstadoHabitacion.FUERA_DE_SERVICIO && fechaOcupada(hab,fecha,estadias)) {
					mapFinal.get(hab).replace(fecha, EstadoHabitacion.OCUPADA);
				}
			}
		}
		
		return mapFinal;
	}
	
	private Boolean fechaOcupada(HabitacionDTO hab, LocalDate fecha, List<Estadia> estadias) {
		List<Estadia> estadiasHabitacionEnFecha = estadias.stream()
				.filter(e -> e.getHabitacion().getNro().equals(hab.getNro().get()))
				.filter(e -> entreFechas(fecha,e.getHora_entrada().toLocalDate(),e.getHora_salida().toLocalDate()))
				.collect(Collectors.toList());
		
		if(estadiasHabitacionEnFecha.isEmpty()) return false;
		
		return true;
	}
	
	private Boolean entreFechas(LocalDate fecha, LocalDate comienzoRango, LocalDate finalRango) {
		
		if(fecha.isBefore(comienzoRango) || fecha.isAfter(finalRango)) return false;
		
		return true;
	}

	public void ocuparHabitacion(HabitacionDTO hab, List<PasajeroDTO> ocup, PasajeroDTO resp, LocalDate fechaDesde, LocalDate fechaHasta) throws OcupanteEnOtraHabitacionException{
		GestorPasajeros gestorP = GestorPasajeros.getInstance();
		Pasajero responsable = gestorP.convertirDTO(resp);
		List<Pasajero> ocupantes = gestorP.convertirDTO(ocup);
		
		GestorHabitaciones gestorH = GestorHabitaciones.getInstance();
		Habitacion habitacion = gestorH.convertirDTO(hab);
		
		LocalDateTime hora_entrada = fechaDesde.atTime(LocalTime.now());
		LocalDateTime hora_salida = fechaHasta.atTime(10,0);
		
		Estadia estadia = new Estadia(null,hora_entrada,hora_salida,null,false);
		estadia.setHabitacion(habitacion);
		estadia.setResponsable(responsable);
		estadia.setPasajeros(ocupantes);
		
		AdministradorBDEstadias adminBD = new AdministradorBDEstadias();
		adminBD.registrarEstadia(estadia);
	}

	public List<PasajeroDTO> buscarOcupantes (Integer nroHab)throws HabitacionInexistenteException{
		List<PasajeroDTO> salida = new ArrayList<PasajeroDTO>();
		AdministradorBDEstadias adminBD = new AdministradorBDEstadias();
		List<Pasajero> pasajeros= new ArrayList<Pasajero>();
			pasajeros =  adminBD.recuperarOcupantes(nroHab);
			salida = instancia.generarDTOPasajeros(pasajeros);
		
			
		return salida;
	}
	
	public List<PasajeroDTO> generarDTOPasajeros(List<Pasajero> pasajeros){
		List<PasajeroDTO> salida = new ArrayList<PasajeroDTO>();
		
		for(Pasajero p : pasajeros) {
			PasajeroDTO dto = new PasajeroDTO();
			
			dto.setId(p.getId());
			dto.setApellido(p.getApellido());
			dto.setNombre(p.getNombre());
			dto.setIdTipoDoc(p.getTipo_doc().getId());
			dto.setTipo(p.getTipo_doc().getTipo());
			dto.setNro_doc(p.getNro_doc());
			dto.setPorcentaje(p.getPosicion_iva().getPorcentaje());
			dto.setTipoFactura(p.getPosicion_iva().getTipoFactura());
			dto.setPosicion(p.getPosicion_iva().getPosicion());
			
			
			salida.add(dto);
			
		}
		
		return salida;
	
	}
	public EstadiaDTO calcularCosto(String nroHabitacion,String horaSalida) {
		
		AdministradorBDEstadias adminBd = new AdministradorBDEstadias();
		Estadia estadia = null;
		estadia = adminBd.recuperarEstadia(nroHabitacion);
				
		Integer cantDias = calcularCantDias(estadia);
		Double costo = cantDias *estadia.getHabitacion().getCosto_noche();
		
		if(estadia.getHora_salida().getHour()>11 && (estadia.getHora_entrada().getHour() <18 || (estadia.getHora_entrada().getHour()==18 && estadia.getHora_salida().getMinute()==0))) costo+= ((estadia.getHabitacion().getCosto_noche())/2);
		else {
			if (estadia.getHora_salida().getHour() >18 || (estadia.getHora_salida().getHour() == 18 && estadia.getHora_salida().getMinute()> 0)) 
				costo += estadia.getHabitacion().getCosto_noche();
		}
		
		if(cantDias >= estadia.getHabitacion().getDiasParaDescuento()) costo*=(1-estadia.getHabitacion().getDescuento());
		estadia.setMonto(costo);
		adminBd.actualizarMonto(estadia, costo);
		return generarDTO(estadia);
	}
	private Integer calcularCantDias(Estadia est){
		
		long diff =ChronoUnit.DAYS.between(est.getHora_entrada().toLocalDate(), est.getHora_salida().toLocalDate());
		Integer cantDias = Math.toIntExact(diff);
		return cantDias;
	}
	private EstadiaDTO generarDTO(Estadia est) {
		EstadiaDTO salida = new EstadiaDTO();
		HabitacionDTO hab = new HabitacionDTO();
		hab.setCosto_noche(est.getHabitacion().getCosto_noche());
		hab.setDescuento(est.getHabitacion().getDescuento());
		hab.setDiasParaDescuento(est.getHabitacion().getDiasParaDescuento());
		
		salida.setId(est.getId());
		salida.setHora_entrada(est.getHora_entrada());
		salida.setHora_salida(est.getHora_salida());
		salida.setMonto(est.getMonto());
		salida.setHabitacion(hab);
		return salida;
		
	}
	public  Estadia buscarEstadia(EstadiaDTO estDTO) {
		AdministradorBDEstadias admin = new AdministradorBDEstadias();
		return admin.buscarEstadia(estDTO.getId().get());
	}
	
	
}
