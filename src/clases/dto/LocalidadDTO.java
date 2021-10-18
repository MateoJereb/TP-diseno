package clases.dto;

public class LocalidadDTO {

	private Integer idLocalidad;
	private String nombre;
	private String codigo_postal;
	
	private Integer idProv;
	private String nombreProvincia;
	
	private Integer idPais;
	private String nombrePais;
	
	public LocalidadDTO() {
		super();
	}
	
	public LocalidadDTO(Integer idLocalidad, String nombre, String codigo_postal, Integer idProv,
			String nombreProvincia, Integer idPais, String nombrePais) {
		super();
		this.idLocalidad = idLocalidad;
		this.nombre = nombre;
		this.codigo_postal = codigo_postal;
		this.idProv = idProv;
		this.nombreProvincia = nombreProvincia;
		this.idPais = idPais;
		this.nombrePais = nombrePais;
	}

	public Integer getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Integer idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public Integer getIdProv() {
		return idProv;
	}

	public void setIdProv(Integer idProv) {
		this.idProv = idProv;
	}

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
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
