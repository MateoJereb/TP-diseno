package clases;

public class PosicionIVA {

	private Integer id;
	private String posicion;
	private Double porcentaje;
	
	public PosicionIVA(Integer id, String posicion, Double porcentaje) {
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
	
	
	
}
