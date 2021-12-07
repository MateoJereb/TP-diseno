package clases.gestores;

import java.util.ArrayList;
import java.util.List;

import base_de_datos.AdministradorBDHabitaciones;
import clases.Habitacion;
import clases.HabitacionDobleEstandar;
import clases.HabitacionDobleSuperior;
import clases.HabitacionFamily;
import clases.HabitacionIndividual;
import clases.HabitacionSuite;
import clases.dto.HabitacionDTO;
import clases.dto.HabitacionDobleEstandarDTO;
import clases.dto.HabitacionDobleSuperiorDTO;
import clases.dto.HabitacionFamilyDTO;
import clases.dto.HabitacionIndividualDTO;
import clases.dto.HabitacionSuiteDTO;
import enums.EstadoHabitacion;

public class GestorHabitaciones {

	public static GestorHabitaciones instancia;
	
	private List<Habitacion> habitaciones;
	
	public static GestorHabitaciones getInstance() {
		if(instancia == null) {
			instancia = new GestorHabitaciones();
		}
		
		return instancia;
	}
	
	private GestorHabitaciones() {
		habitaciones = new ArrayList<Habitacion>();
	}
	
	public List<Habitacion> buscarHabitaciones(){
		AdministradorBDHabitaciones adminBD = new AdministradorBDHabitaciones();
		return adminBD.recuperarHabitaciones();
	}
	
	public List<HabitacionDTO> buscarHabitacionesDTO(){
		List<HabitacionDTO> salida;
		
		AdministradorBDHabitaciones adminBD = new AdministradorBDHabitaciones();
		List<Habitacion> habitaciones = adminBD.recuperarHabitaciones();
		
		salida = generarDTO(habitaciones); 
		
		return salida;
	}
	
	public List<HabitacionDTO> generarDTO(List<Habitacion> habitaciones){
		List<HabitacionDTO> salida = new ArrayList<HabitacionDTO>();
		
		for(Habitacion h : habitaciones) {
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
			
			salida.add(dto);
		}
		
		return salida;
	}
	
	public Habitacion convertirDTO(HabitacionDTO h) {
		AdministradorBDHabitaciones adminBD = new AdministradorBDHabitaciones();
		Habitacion salida = adminBD.habitacionPorNro(h.getNro().get()); 
		return salida;
	}
}
