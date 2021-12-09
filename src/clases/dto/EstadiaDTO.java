package clases.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import clases.Consumo;
import clases.Factura;
import clases.Habitacion;
import clases.Pasajero;

public class EstadiaDTO {

	private Optional<Integer> id = Optional.ofNullable(null);
	private Optional<LocalDateTime> hora_entrada = Optional.ofNullable(null);
	private Optional<LocalDateTime> hora_salida = Optional.ofNullable(null);
	private Optional<Double> monto = Optional.ofNullable(null);
	private Optional<Boolean> pagado = Optional.ofNullable(null);
	private Optional<PasajeroDTO> pasajero = Optional.ofNullable(null);
	private Optional<List<PasajeroDTO>> pasajeros = Optional.ofNullable(null);
	private Optional<HabitacionDTO> habitacion = Optional.ofNullable(null);
	private Optional<List<ConsumosDTO>> ocupacion = Optional.ofNullable(null);
	private Optional<List<FacturaDTO>> facturas = Optional.ofNullable(null);
	public Optional<Integer> getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = Optional.of(id);
	}
	public Optional<LocalDateTime> getHora_entrada() {
		return hora_entrada;
	}
	public void setHora_entrada(LocalDateTime hora_entrada) {
		this.hora_entrada = Optional.of(hora_entrada);
	}
	public Optional<LocalDateTime> getHora_salida() {
		return hora_salida;
	}
	public void setHora_salida(LocalDateTime hora_salida) {
		this.hora_salida = Optional.of(hora_salida);
	}
	public Optional<Double> getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = Optional.of(monto);
	}
	public Optional<Boolean> getPagado() {
		return pagado;
	}
	public void setPagado(Boolean pagado) {
		this.pagado = Optional.of(pagado);
	}
	public Optional<PasajeroDTO> getPasajero() {
		return pasajero;
	}
	public void setPasajero(PasajeroDTO pasajero) {
		this.pasajero = Optional.of(pasajero);
	}
	public Optional<List<PasajeroDTO>> getPasajeros() {
		return pasajeros;
	}
	public void setPasajeros(List<PasajeroDTO> pasajeros) {
		this.pasajeros = Optional.of(pasajeros);
	}
	public Optional<HabitacionDTO> getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(HabitacionDTO habitacion) {
		this.habitacion = Optional.of(habitacion);
	}
	public Optional<List<ConsumosDTO>> getOcupacion() {
		return ocupacion;
	}
	public void setOcupacion(List<ConsumosDTO> ocupacion) {
		this.ocupacion = Optional.of(ocupacion);
	}
	public Optional<List<FacturaDTO>> getFacturas() {
		return facturas;
	}
	public void setFacturas(List<FacturaDTO> facturas) {
		this.facturas = Optional.of(facturas);
	}
}
	
	
	
