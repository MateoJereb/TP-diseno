package clases;

import enums.EstadoHabitacion;

public class HabitacionDobleEstandar extends Habitacion {

	public HabitacionDobleEstandar() {
		
	}
	
	public HabitacionDobleEstandar(Integer nro, Integer planta, Integer capacidad, Double costo_noche,
			EstadoHabitacion estado_actual, Double descuento, Integer diasParaDescuento) {
		super();
		this.nro = nro;
		this.planta = planta;
		this.capacidad = capacidad;
		this.costo_noche = costo_noche;
		this.estado_actual = estado_actual;
		this.descuento = descuento;
		this.diasParaDescuento = diasParaDescuento;
	}
	
}
