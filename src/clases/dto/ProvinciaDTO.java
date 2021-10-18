package clases.dto;

public class ProvinciaDTO {

	private Integer idProv;
	private String nombre;
	
	private Integer idPais;
	private String nombrePais;
	
	public ProvinciaDTO() {
		super();
	}
	
	public ProvinciaDTO(Integer idProv, String nombre, Integer idPais, String nombrePais) {
		super();
		this.idProv = idProv;
		this.nombre = nombre;
		this.idPais = idPais;
		this.nombrePais = nombrePais;
	}

	public Integer getIdProv() {
		return idProv;
	}

	public void setIdProv(Integer idProv) {
		this.idProv = idProv;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdPais() {
		return idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
	
	
	
	
	
}
