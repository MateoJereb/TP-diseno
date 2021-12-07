package clases.gestores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import base_de_datos.AdministradorBDEstadias;
import clases.Estadia;
import clases.Habitacion;
import clases.Pasajero;
import clases.dto.HabitacionDTO;
import clases.dto.PasajeroDTO;
import enums.EstadoHabitacion;
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
}
