package clases.dto;

public class PaisDTO {

	private Integer idPais;
	private String nombre;
	
	public PaisDTO() {
		super();
	}
	
	public PaisDTO(Integer idPais, String nombre) {
		super();
		this.idPais = idPais;
		this.nombre = nombre;
	}

	public Integer getIdPais() {
		return idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
}
