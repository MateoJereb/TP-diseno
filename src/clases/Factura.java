package clases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import enums.EstadoFactura;
import enums.TipoFactura;

public class Factura {

	private Integer id;
	private TipoFactura tipo;
	private LocalDateTime fecha;
	private Double monto_neto;
	private Double iva;
	private Double monto_total;
	private EstadoFactura estado;
	
	private Optional<Pasajero> responsable_fisico;
	private Optional<ResponsablePago> responsable_juridico;
	private Estadia estadia;
	private List<ConsumoFacturado> consumos;
	
	public Factura() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoFactura getTipo() {
		return tipo;
	}

	public void setTipo(TipoFactura tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Double getMonto_neto() {
		return monto_neto;
	}

	public void setMonto_neto(Double monto_neto) {
		this.monto_neto = monto_neto;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getMonto_total() {
		return monto_total;
	}

	public void setMonto_total(Double monto_total) {
		this.monto_total = monto_total;
	}

	public EstadoFactura getEstado() {
		return estado;
	}

	public void setEstado(EstadoFactura estado) {
		this.estado = estado;
	}

	public Optional<Pasajero> getResponsable_fisico() {
		return responsable_fisico;
	}

	public void setResponsable_fisico(Optional<Pasajero> responsable_fisico) {
		this.responsable_fisico = responsable_fisico;
	}

	public Optional<ResponsablePago> getResponsable_juridico() {
		return responsable_juridico;
	}

	public void setResponsable_juridico(Optional<ResponsablePago> responsable_juridico) {
		this.responsable_juridico = responsable_juridico;
	}

	public Estadia getEstadia() {
		return estadia;
	}

	public void setEstadia(Estadia estadia) {
		this.estadia = estadia;
	}
	
	
	
}
