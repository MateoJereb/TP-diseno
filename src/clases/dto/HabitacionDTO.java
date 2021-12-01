package clases.dto;

import enums.EstadoHabitacion;

public class HabitacionDTO {
	private Integer nro;
	private Integer planta;
	private Integer capacidad;
	private Double costo_noche;
	private EstadoHabitacion estado_actual;
	private Double descuento;
	private Integer diasParaDescuento;
	
	public HabitacionDTO() {
		super();
	}

	public Integer getNro() {
		return nro;
	}

	public void setNro(Integer nro) {
		this.nro = nro;
	}

	public Integer getPlanta() {
		return planta;
	}

	public void setPlanta(Integer planta) {
		this.planta = planta;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Double getCosto_noche() {
		return costo_noche;
	}

	public void setCosto_noche(Double costo_noche) {
		this.costo_noche = costo_noche;
	}

	public EstadoHabitacion getEstado_actual() {
		return estado_actual;
	}

	public void setEstado_actual(EstadoHabitacion estado_actual) {
		this.estado_actual = estado_actual;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Integer getDiasParaDescuento() {
		return diasParaDescuento;
	}

	public void setDiasParaDescuento(Integer diasParaDescuento) {
		this.diasParaDescuento = diasParaDescuento;
	}
	
	
	
	
}
