package clases;

import enums.EstadoHabitacion;

public class HabitacionIndividual extends Habitacion {

	public HabitacionIndividual() {
		
	}
	
	public HabitacionIndividual(Integer nro, Integer planta, Integer capacidad, Double costo_noche,
			EstadoHabitacion estado_actual, Double descuento, Integer diasParaDescuento) {
		this.nro = nro;
		this.planta = planta;
		this.capacidad = capacidad;
		this.costo_noche = costo_noche;
		this.estado_actual = estado_actual;
		this.descuento = descuento;
		this.diasParaDescuento = diasParaDescuento;
	}
	
}
