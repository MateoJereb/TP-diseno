package clases.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import clases.ConsumoFacturado;
import clases.Estadia;
import clases.Pasajero;
import clases.ResponsablePago;
import enums.EstadoFactura;
import enums.EstadoHabitacion;
import enums.TipoFactura;

public class FacturaDTO {
	
	private Optional<Integer> id_factura = Optional.ofNullable(null);
	private Optional<TipoFactura> tipo = Optional.ofNullable(null);
	private Optional<LocalDateTime> fecha = Optional.ofNullable(null);
	private Optional<Double> monto_neto = Optional.ofNullable(null);
	private Optional<Double> iva = Optional.ofNullable(null);
	private Optional<Double> monto_total = Optional.ofNullable(null);
	private Optional<EstadoFactura> estado = Optional.ofNullable(null);
	private Optional<PasajeroDTO> responsable_fisico = Optional.ofNullable(null);
	private Optional<ResponsablePagoDTO> responsable_juridico = Optional.ofNullable(null);
	private Optional<EstadiaDTO> estadia = Optional.ofNullable(null);
	private Optional<List<ConsumoFacturadoDTO>> consumos = Optional.ofNullable(null);
	public Optional<Integer> getId_factura() {
		return id_factura;
	}
	public void setId_factura(Integer id_factura) {
		this.id_factura = Optional.of(id_factura);
	}
	public Optional<TipoFactura> getTipo() {
		return tipo;
	}
	public void setTipo(TipoFactura tipo) {
		this.tipo = Optional.of(tipo);
	}
	public Optional<LocalDateTime> getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = Optional.of(fecha);
	}
	public Optional<Double> getMonto_neto() {
		return monto_neto;
	}
	public void setMonto_neto(Double monto_neto) {
		this.monto_neto = Optional.of(monto_neto);
	}
	public Optional<Double> getIva() {
		return iva;
	}
	public void setIva(Double iva) {
		this.iva = Optional.of(iva);
	}
	public Optional<Double> getMonto_total() {
		return monto_total;
	}
	public void setMonto_total(Double monto_total) {
		this.monto_total = Optional.of(monto_total);
	}
	public Optional<EstadoFactura> getEstado() {
		return estado;
	}
	public void setEstado(EstadoFactura estado) {
		this.estado = Optional.of(estado);
	}
	public Optional<PasajeroDTO> getResponsable_fisico() {
		return responsable_fisico;
	}
	public void setResponsable_fisico(PasajeroDTO responsable_fisico) {
		this.responsable_fisico = Optional.of(responsable_fisico);
	}
	public Optional<ResponsablePagoDTO> getResponsable_juridico() {
		return responsable_juridico;
	}
	public void setResponsable_juridico(ResponsablePagoDTO responsable_juridico) {
		this.responsable_juridico = Optional.of(responsable_juridico);
	}
	public Optional<EstadiaDTO> getEstadia() {
		return estadia;
	}
	public void setEstadia(EstadiaDTO estadia) {
		this.estadia = Optional.of(estadia);
	}
	public Optional<List<ConsumoFacturadoDTO>> getConsumos() {
		return consumos;
	}
	public void setConsumos(List<ConsumoFacturadoDTO> consumos) {
		this.consumos = Optional.of(consumos);
	}
	

	
	
}
