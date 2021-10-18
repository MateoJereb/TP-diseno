package clases.dto;

import enums.TipoFactura;

public class PosicionIVADTO {

	private Integer id;
	private String posicion;
	private Double porcentaje;
	private TipoFactura tipo_factura;
	
	public PosicionIVADTO() {
		super();
	}
	
	public PosicionIVADTO(Integer id, String posicion, Double porcentaje, TipoFactura tipo_factura) {
		super();
		this.id = id;
		this.posicion = posicion;
		this.porcentaje = porcentaje;
		this.tipo_factura = tipo_factura;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public TipoFactura getTipo_factura() {
		return tipo_factura;
	}

	public void setTipo_factura(TipoFactura tipo_factura) {
		this.tipo_factura = tipo_factura;
	}
	
	
	
}
