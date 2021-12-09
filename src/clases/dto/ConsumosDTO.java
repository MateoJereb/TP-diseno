package clases.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import enums.EstadoHabitacion;

public class ConsumosDTO {
	
	private Optional<Integer> idConsumo = Optional.ofNullable(null);
	private Optional<Double> monto = Optional.ofNullable(null);
	private Optional<Integer> cantidad = Optional.ofNullable(null);
	private Optional<EstadoHabitacion> nro_doc = Optional.ofNullable(null);
	private Optional<String> descripcion = Optional.ofNullable(null);
	private Optional<LocalDateTime> fecha = Optional.ofNullable(null);
	private Optional<Integer> cantPagados = Optional.ofNullable(null);


	public Optional<Integer> getIdConsumo() {
		return idConsumo;
	}
	public void setIdConsumo(Integer idConsumo) {
		this.idConsumo = Optional.of(idConsumo);
	}
	public Optional<Double> getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = Optional.of(monto);
	}
	public Optional<Integer> getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = Optional.of(cantidad);
	}
	public Optional<EstadoHabitacion> getNro_doc() {
		return nro_doc;
	}
	public void setNro_doc(EstadoHabitacion nro_doc) {
		this.nro_doc = Optional.of(nro_doc);
	}
	public Optional<String> getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = Optional.of(descripcion);
	}
	public Optional<LocalDateTime> getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = Optional.of(fecha);
	}
	public Optional<Integer> getCantPagados() {
		return cantPagados;
	}
	public void setCantPagado(Integer pagado) {
		this.cantPagados = Optional.of(pagado);
	}
}
	
