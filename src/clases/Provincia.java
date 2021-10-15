package clases;

public class Provincia {

	private Integer idProv;
	private String nombre;
	
	private Pais pais;
	
	public Provincia(Integer idProv, String nombre, Pais pais) {
		this.idProv = idProv;
		this.nombre = nombre;
		this.pais = pais;
	}

	public Integer getIdProv() {
		return idProv;
	}

	public String getNombre() {
		return nombre;
	}

	public Pais getPais() {
		return pais;
	}
	
	
	
}
