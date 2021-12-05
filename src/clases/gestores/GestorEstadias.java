package clases.gestores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import base_de_datos.AdministradorBDEstadias;
import clases.Estadia;
import clases.dto.HabitacionDTO;
import enums.EstadoHabitacion;

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
}
