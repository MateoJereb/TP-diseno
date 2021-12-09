package clases.dto;

import java.time.LocalDateTime;
import java.util.Optional;

import clases.Consumo;
import clases.Factura;
import enums.TipoFactura;

public class ConsumoFacturadoDTO {

	private Optional<Integer> cantidad = Optional.ofNullable(null);
	private Optional<ConsumosDTO> consumo = Optional.ofNullable(null);
	private Optional<FacturaDTO> factura = Optional.ofNullable(null);
	
	public Optional<Integer> getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = Optional.of(cantidad);
	}
	public Optional<ConsumosDTO> getConsumo() {
		return consumo;
	}
	public void setConsumo(ConsumosDTO consumo) {
		this.consumo = Optional.of(consumo);
	}
	public Optional<FacturaDTO> getFactura() {
		return factura;
	}
	public void setFactura(FacturaDTO factura) {
		this.factura = Optional.of(factura);
	}
	
}
