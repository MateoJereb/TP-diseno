package clases.dto;

import java.util.Optional;

import enums.EstadoHabitacion;

public abstract class HabitacionDTO {
	protected Optional<Integer> nro;
	protected Optional<Integer> planta;
	protected Optional<Integer> capacidad;
	protected Optional<Double> costo_noche;
	protected Optional<EstadoHabitacion> estado_actual;
	protected Optional<Double> descuento;
	protected Optional<Integer> diasParaDescuento;
	
	protected Boolean aOcupar = false;

	public Optional<Integer> getNro() {
		return nro;
	}

	public void setNro(Integer nro) {
		this.nro = Optional.of(nro);
	}

	public Optional<Integer> getPlanta() {
		return planta;
	}

	public void setPlanta(Integer planta) {
		this.planta = Optional.of(planta);
	}

	public Optional<Integer> getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = Optional.of(capacidad);
	}

	public Optional<Double> getCosto_noche() {
		return costo_noche;
	}

	public void setCosto_noche(Double costo_noche) {
		this.costo_noche = Optional.of(costo_noche);
	}

	public Optional<EstadoHabitacion> getEstado_actual() {
		return estado_actual;
	}

	public void setEstado_actual(EstadoHabitacion estado_actual) {
		this.estado_actual = Optional.of(estado_actual);
	}

	public Optional<Double> getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = Optional.of(descuento);
	}

	public Optional<Integer> getDiasParaDescuento() {
		return diasParaDescuento;
	}

	public void setDiasParaDescuento(Integer diasParaDescuento) {
		this.diasParaDescuento = Optional.of(diasParaDescuento);
	}

	public Boolean getAOcupar() {
		return aOcupar;
	}

	public void setAOcupar(Boolean aOcupar) {
		this.aOcupar = aOcupar;
	}
	
	public abstract String toString();
	
}
