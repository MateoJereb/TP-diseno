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

	public Integer getId() {
		return idProv;
	}

	public String getNombre() {
		return nombre;
	}

	public Pais getPais() {
		return pais;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProv == null) ? 0 : idProv.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
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
		Provincia other = (Provincia) obj;
		if (idProv == null) {
			if (other.idProv != null)
				return false;
		} else if (!idProv.equals(other.idProv))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		return true;
	}
	
	
	
}
