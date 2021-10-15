package clases;

public class Localidad {

	private Integer idLocalidad;
	private String nombre;
	private String codigo_postal;
	
	private Provincia provincia;
	
	public Localidad(Integer idLocalidad, String nombre, String codigo_postal, Provincia provincia) {
		this.idLocalidad = idLocalidad;
		this.nombre = nombre;
		this.codigo_postal = codigo_postal;
		this.provincia = provincia;
	}

	public Integer getIdLocalidad() {
		return idLocalidad;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public Provincia getProvincia() {
		return provincia;
	}
	
	
}
