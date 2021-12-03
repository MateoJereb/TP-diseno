package clases;

import java.time.LocalDate;
import java.util.List;

import clases.dto.PasajeroDTO;

public class Pasajero {

	private Integer id;
	private String apellido;
	private String nombre;
	private String nro_doc;
	private String cuit;
	private LocalDate fecha_nacimiento;
	private String telefono;
	private String mail;
	private String ocupacion;
	private String nacionalidad;
	
	private Direccion direccion;
	private TipoDocumento tipo_doc;
	private PosicionIVA posicion_iva;
	private List<Factura> facturas;
	private List<Estadia> estadias;
	private List<Estadia> responsable_de;
	
	public Pasajero() {
		super();
	}
	
	public Pasajero(PasajeroDTO dto) {
		apellido = dto.getApellido().get();
		nombre = dto.getNombre().get();
		nro_doc = dto.getNro_doc().get();
		if(dto.getCuit().isPresent()) cuit = dto.getCuit().get();
		fecha_nacimiento = dto.getFecha_nacimiento().get();
		telefono = dto.getTelefono().get();
		if(dto.getMail().isPresent()) dto.getMail().get();
		ocupacion = dto.getOcupacion().get();
		nacionalidad = dto.getNacionalidad().get();
	}
	
	public Pasajero(Integer id, String apellido, String nombre, String nro_doc, String cuit, LocalDate fecha_nacimiento,
			String telefono, String mail, String ocupacion, String nacionalidad, Direccion direccion,
			TipoDocumento tipo_doc, PosicionIVA posicion_iva) {
		this.apellido = apellido;
		this.nombre = nombre;
		this.nro_doc = nro_doc;
		this.cuit = cuit;
		this.fecha_nacimiento = fecha_nacimiento;
		this.telefono = telefono;
		this.mail = mail;
		this.ocupacion = ocupacion;
		this.nacionalidad = nacionalidad;
		this.direccion = direccion;
		this.tipo_doc = tipo_doc;
		this.posicion_iva = posicion_iva;
	}
	public Integer getId() {
		return id;
	}
	public String getApellido() {
		return apellido;
	}
	public String getNombre() {
		return nombre;
	}
	public String getNro_doc() {
		return nro_doc;
	}
	public String getCuit() {
		return cuit;
	}
	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getMail() {
		return mail;
	}
	public String getOcupacion() {
		return ocupacion;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public TipoDocumento getTipo_doc() {
		return tipo_doc;
	}
	public PosicionIVA getPosicion_iva() {
		return posicion_iva;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setNro_doc(String nro_doc) {
		this.nro_doc = nro_doc;
	}

	public void setTipo_doc(TipoDocumento tipo_doc) {
		this.tipo_doc = tipo_doc;
	}
	
	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}
	

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	

	public void setPosicion_iva(PosicionIVA posicion_iva) {
		this.posicion_iva = posicion_iva;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((cuit == null) ? 0 : cuit.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((fecha_nacimiento == null) ? 0 : fecha_nacimiento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((nacionalidad == null) ? 0 : nacionalidad.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nro_doc == null) ? 0 : nro_doc.hashCode());
		result = prime * result + ((ocupacion == null) ? 0 : ocupacion.hashCode());
		result = prime * result + ((posicion_iva == null) ? 0 : posicion_iva.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((tipo_doc == null) ? 0 : tipo_doc.hashCode());
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
		Pasajero other = (Pasajero) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (cuit == null) {
			if (other.cuit != null)
				return false;
		} else if (!cuit.equals(other.cuit))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (fecha_nacimiento == null) {
			if (other.fecha_nacimiento != null)
				return false;
		} else if (!fecha_nacimiento.equals(other.fecha_nacimiento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (nacionalidad == null) {
			if (other.nacionalidad != null)
				return false;
		} else if (!nacionalidad.equals(other.nacionalidad))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nro_doc == null) {
			if (other.nro_doc != null)
				return false;
		} else if (!nro_doc.equals(other.nro_doc))
			return false;
		if (ocupacion == null) {
			if (other.ocupacion != null)
				return false;
		} else if (!ocupacion.equals(other.ocupacion))
			return false;
		if (posicion_iva == null) {
			if (other.posicion_iva != null)
				return false;
		} else if (!posicion_iva.equals(other.posicion_iva))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		if (tipo_doc == null) {
			if (other.tipo_doc != null)
				return false;
		} else if (!tipo_doc.equals(other.tipo_doc))
			return false;
		return true;
	}
	
}
