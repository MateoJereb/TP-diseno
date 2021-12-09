package clases;

import java.util.Optional;

import clases.dto.PasajeroDTO;

public class Direccion {

	private Integer idDireccion;
	private String calle;
	private String numero;
	private Integer piso;
	private String departamento;
	
	private Optional<Pasajero> pasajero;
	private Optional<ResponsablePago> responsable_pago;
	private Localidad localidad;
	
		
	public Direccion(Integer idDireccion, String calle, String numero, Integer piso, String departamento, Pasajero pasajero,
			Localidad localidad) {
		this.idDireccion = idDireccion;
		this.calle = calle;
		this.numero = numero;
		this.departamento = departamento;
		this.piso = piso;
		this.pasajero = Optional.of(pasajero);
		this.localidad = localidad;
	}
	
	public Direccion(PasajeroDTO dto) {
		calle = dto.getCalle().get();
		numero = dto.getNumero().get();
		if(dto.getNumero().isPresent()) numero = dto.getNumero().get();
		if(dto.getDepartamento().isPresent()) departamento = dto.getDepartamento().get();
	}

	public Direccion() {
		
	}
	
	public Integer getId() {
		return idDireccion;
	}
	
	public String getCalle() {
		return calle;
	}

	public String getNumero() {
		return numero;
	}

	public String getDepartamento() {
		return departamento;
	}

	public Integer getPiso() {
		return piso;
	}

	public Optional<Pasajero> getPasajero() {
		return pasajero;
	}
	
	public Localidad getLocalidad() {
		return localidad;
	}
	
	public void setPasajero(Pasajero pasajero) {
		this.pasajero = Optional.of(pasajero);
	}
	
	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public void setIdDireccion(Integer idDireccion) {
		this.idDireccion = idDireccion;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public void setPasajero(Optional<Pasajero> pasajero) {
		this.pasajero = pasajero;
	}

	public void setResponsable_pago(Optional<ResponsablePago> responsable_pago) {
		this.responsable_pago = responsable_pago;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calle == null) ? 0 : calle.hashCode());
		result = prime * result + ((departamento == null) ? 0 : departamento.hashCode());
		result = prime * result + ((idDireccion == null) ? 0 : idDireccion.hashCode());
		result = prime * result + ((localidad == null) ? 0 : localidad.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((pasajero == null) ? 0 : pasajero.hashCode());
		result = prime * result + ((piso == null) ? 0 : piso.hashCode());
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
		Direccion other = (Direccion) obj;
		if (calle == null) {
			if (other.calle != null)
				return false;
		} else if (!calle.equals(other.calle))
			return false;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
			return false;
		if (idDireccion == null) {
			if (other.idDireccion != null)
				return false;
		} else if (!idDireccion.equals(other.idDireccion))
			return false;
		if (localidad == null) {
			if (other.localidad != null)
				return false;
		} else if (!localidad.equals(other.localidad))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (pasajero == null) {
			if (other.pasajero != null)
				return false;
		} else if (!pasajero.equals(other.pasajero))
			return false;
		if (piso == null) {
			if (other.piso != null)
				return false;
		} else if (!piso.equals(other.piso))
			return false;
		return true;
	}
	
	
	
}
