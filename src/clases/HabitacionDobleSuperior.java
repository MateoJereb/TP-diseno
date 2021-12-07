package clases;

import enums.EstadoHabitacion;

public class HabitacionDobleSuperior extends Habitacion {

	public HabitacionDobleSuperior() {
		
	}
	
	public HabitacionDobleSuperior(Integer nro, Integer planta, Integer capacidad, Double costo_noche,
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
