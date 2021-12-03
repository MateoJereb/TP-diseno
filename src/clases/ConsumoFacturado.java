package clases;

public class ConsumoFacturado {

	private Integer cantidad;
	
	private Consumo consumo;
	private Factura factura;
	
	public ConsumoFacturado() {
		super();
	}

	public ConsumoFacturado(Integer cantidad, Consumo consumo, Factura factura) {
		super();
		this.cantidad = cantidad;
		this.consumo = consumo;
		this.factura = factura;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Consumo getConsumo() {
		return consumo;
	}

	public void setConsumo(Consumo consumo) {
		this.consumo = consumo;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	
}
