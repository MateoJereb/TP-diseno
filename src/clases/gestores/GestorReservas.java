package clases.gestores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import base_de_datos.AdministradorBDHabitaciones;
import base_de_datos.AdministradorBDReservas;
import clases.Habitacion;
import clases.HabitacionDobleEstandar;
import clases.HabitacionDobleSuperior;
import clases.HabitacionFamily;
import clases.HabitacionIndividual;
import clases.Reserva;
import clases.dto.HabitacionDTO;
import clases.dto.HabitacionDobleEstandarDTO;
import clases.dto.HabitacionDobleSuperiorDTO;
import clases.dto.HabitacionFamilyDTO;
import clases.dto.HabitacionIndividualDTO;
import clases.dto.HabitacionSuiteDTO;
import enums.EstadoHabitacion;

public class GestorReservas {

	public static GestorReservas instancia;
	
	private List<Reserva> reservas;
	
	public static GestorReservas getInstance() {
		if(instancia == null) {
			instancia = new GestorReservas();
		}
		
		return instancia;
	}
	
	private GestorReservas() {
		reservas = new ArrayList<Reserva>();
	}
	
	public Map<HabitacionDTO,Map<LocalDate,EstadoHabitacion>> verificarDisponibilidad(LocalDate fechaDesde, LocalDate fechaHasta){
	
		GestorHabitaciones gestorHab = GestorHabitaciones.getInstance();
		List<Habitacion> habitaciones = gestorHab.buscarHabitaciones();
		
		AdministradorBDReservas adminBD = new AdministradorBDReservas();
		List<Reserva> reservas = adminBD.recuperarReservas(fechaDesde, fechaHasta);
		
		List<LocalDate> fechas = generarFechas(fechaDesde,fechaHasta);
		
		Map<HabitacionDTO,Map<LocalDate,EstadoHabitacion>> mapReservas = new LinkedHashMap<HabitacionDTO, Map<LocalDate,EstadoHabitacion>>();
		
		for(Habitacion hab : habitaciones) {
			Map<LocalDate,EstadoHabitacion> disponibilidad = new LinkedHashMap<LocalDate, EstadoHabitacion>();
			
			for(LocalDate fecha : fechas) {
				if(hab.getEstado_actual() == EstadoHabitacion.FUERA_DE_SERVICIO) {
					disponibilidad.put(fecha, EstadoHabitacion.FUERA_DE_SERVICIO);
				}
				else {
					if(fechaReservada(hab, fecha, reservas)) {
						disponibilidad.put(fecha, EstadoHabitacion.RESERVADA);
					}
					else {
						disponibilidad.put(fecha, EstadoHabitacion.DISPONIBLE);
					}
				}
			}
			
			HabitacionDTO habAux = generarDTOHabitacion(hab);
			
			mapReservas.put(habAux, disponibilidad);
		}
		
		GestorEstadias gestorEst = GestorEstadias.getInstance();
		
		Map<HabitacionDTO,Map<LocalDate,EstadoHabitacion>> mapFinal = gestorEst.verificarDisponibilidades(mapReservas, fechaDesde, fechaHasta);
		
		return mapFinal;
	}
	
	private List<LocalDate> generarFechas(LocalDate fechaDesde, LocalDate fechaHasta){
		List<LocalDate> salida = new ArrayList<LocalDate>();
		
		LocalDate aux = fechaDesde;
		
		while(aux.isBefore(fechaHasta)) {
			salida.add(aux);
			aux = aux.plusDays(1);
		}
		
		salida.add(fechaHasta);
		
		return salida;
	}
	
	private Boolean fechaReservada(Habitacion hab, LocalDate fecha, List<Reserva> reservas) {
		return false;
	}
	
	private HabitacionDTO generarDTOHabitacion(Habitacion h) {
		Integer nro = h.getNro();
		Integer planta = h.getPlanta();
		Integer capacidad = h.getCapacidad();
		Double costo_noche = h.getCosto_noche();
		EstadoHabitacion estado_actual = h.getEstado_actual();
		Double descuento = h.getDescuento();
		Integer diasParaDescuento = h.getDiasParaDescuento();
		
		HabitacionDTO dto = null;
		
		if(h.getClass() == HabitacionIndividual.class) dto = new HabitacionIndividualDTO();
		else {
			if(h.getClass() == HabitacionDobleEstandar.class) dto = new HabitacionDobleEstandarDTO();
			else {
				if(h.getClass() == HabitacionDobleSuperior.class) dto = new HabitacionDobleSuperiorDTO();
				else {
					if(h.getClass() == HabitacionFamily.class) dto = new HabitacionFamilyDTO();
					else dto = new HabitacionSuiteDTO();
				}
			}
		}
		
		dto.setNro(nro);
		dto.setPlanta(planta);
		dto.setCapacidad(capacidad);
		dto.setCosto_noche(costo_noche);
		dto.setEstado_actual(estado_actual);
		dto.setDescuento(descuento);
		dto.setDiasParaDescuento(diasParaDescuento);
		
		return dto;
		
	}
}
