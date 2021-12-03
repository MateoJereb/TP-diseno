package clases;

import java.util.List;

import enums.EstadoHabitacion;

public abstract class Habitacion {

	protected Integer nro;
	protected Integer planta;
	protected Integer capacidad;
	protected Double costo_noche;
	protected EstadoHabitacion estado_actual;
	protected Double descuento;
	protected Integer diasParaDescuento;
	
	protected List<Reserva> reservas;
	
	public Habitacion() {
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

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	
}
