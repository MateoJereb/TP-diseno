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

	public Integer getId() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo_postal == null) ? 0 : codigo_postal.hashCode());
		result = prime * result + ((idLocalidad == null) ? 0 : idLocalidad.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((provincia == null) ? 0 : provincia.hashCode());
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
		Localidad other = (Localidad) obj;
		if (codigo_postal == null) {
			if (other.codigo_postal != null)
				return false;
		} else if (!codigo_postal.equals(other.codigo_postal))
			return false;
		if (idLocalidad == null) {
			if (other.idLocalidad != null)
				return false;
		} else if (!idLocalidad.equals(other.idLocalidad))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (provincia == null) {
			if (other.provincia != null)
				return false;
		} else if (!provincia.equals(other.provincia))
			return false;
		return true;
	}
	
	
}
