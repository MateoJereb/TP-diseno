package clases;

import enums.TipoFactura;

public class PosicionIVA {

	private Integer id;
	private String posicion;
	private Double porcentaje;
	private TipoFactura tipo_factura;
	
	public PosicionIVA() {
		
	}
	
	public PosicionIVA(Integer id, String posicion, Double porcentaje, TipoFactura tipo_factura) {
		this.id = id;
		this.posicion = posicion;
		this.porcentaje = porcentaje;
	}
	public Integer getId() {
		return id;
	}
	public String getPosicion() {
		return posicion;
	}
	public Double getPorcentaje() {
		return porcentaje;
	}
	public TipoFactura getTipoFactura() {
		return tipo_factura;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}
	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}
	public void setTipo_factura(TipoFactura tipo_factura) {
		this.tipo_factura = tipo_factura;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((porcentaje == null) ? 0 : porcentaje.hashCode());
		result = prime * result + ((posicion == null) ? 0 : posicion.hashCode());
		result = prime * result + ((tipo_factura == null) ? 0 : tipo_factura.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PosicionIVA other = (PosicionIVA) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (porcentaje == null) {
			if (other.porcentaje != null)
				return false;
		} else if (!porcentaje.equals(other.porcentaje))
			return false;
		if (posicion == null) {
			if (other.posicion != null)
				return false;
		} else if (!posicion.equals(other.posicion))
			return false;
		if (tipo_factura != other.tipo_factura)
			return false;
		return true;
	}
	
	
}
