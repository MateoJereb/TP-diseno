package clases;

import java.time.LocalDateTime;
import java.util.List;

import enums.TipoServicio;

public class Consumo {

	private Integer id;
	private Double monto;
	private Integer cantidad;
	private TipoServicio servicio;
	private String descripcion;
	private LocalDateTime fecha;
	private Integer cantPaga;
	
	private List<ConsumoFacturado> facturas;
	
	public Consumo() {
		
	}

	public Consumo(Integer id, Double monto, Integer cantidad, TipoServicio servicio, String descripcion,
			LocalDateTime fecha) {
		super();
		this.id = id;
		this.monto = monto;
		this.cantidad = cantidad;
		this.servicio = servicio;
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public TipoServicio getServicio() {
		return servicio;
	}

	public void setServicio(TipoServicio servicio) {
		this.servicio = servicio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Integer getCantPaga() {
		return cantPaga;
	}

	public void setCantPaga(Integer cantPaga) {
		this.cantPaga = cantPaga;
	}

	public List<ConsumoFacturado> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<ConsumoFacturado> facturas) {
		this.facturas = facturas;
	}
	
	
	
}
