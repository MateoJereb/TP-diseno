package clases.dto;

import java.time.LocalDate;
import java.util.Optional;

public class PasajeroDTO {

	//Pasajero
	private Optional<Integer> id = Optional.ofNullable(null);
	private Optional<String> apellido = Optional.ofNullable(null);
	private Optional<String> nombre = Optional.ofNullable(null);
	private Optional<String> nro_doc = Optional.ofNullable(null);
	private Optional<String> cuit = Optional.ofNullable(null);
	private Optional<LocalDate> fecha_nacimiento = Optional.ofNullable(null);
	private Optional<String> telefono = Optional.ofNullable(null);
	private Optional<String> mail = Optional.ofNullable(null);
	private Optional<String> ocupacion = Optional.ofNullable(null);
	private Optional<String> nacionalidad = Optional.ofNullable(null);
	
	//Direccion
	private Optional<Integer> idDireccion = Optional.ofNullable(null);
	private Optional<String> calle = Optional.ofNullable(null);
	private Optional<String> numero = Optional.ofNullable(null);
	private Optional<String> departamento = Optional.ofNullable(null);
	private Optional<Integer> piso = Optional.ofNullable(null);
	
	//Localidad
	private Optional<Integer> idLocalidad = Optional.ofNullable(null);
	private Optional<String> nombreLocalidad = Optional.ofNullable(null);
	private Optional<String> codigo_postal = Optional.ofNullable(null);
	
	//Provincia
	private Optional<Integer> idProv = Optional.ofNullable(null);
	private Optional<String> nombreProv = Optional.ofNullable(null);
	
	//Pais
	private Optional<Integer> idPais = Optional.ofNullable(null);
	private Optional<String> nombrePais = Optional.ofNullable(null);
	
	//TipoDocumento
	private Optional<Integer> idTipoDoc = Optional.ofNullable(null);
	private Optional<String> tipo = Optional.ofNullable(null);
	
	//PosicionIVA
	private Optional<Integer> idPosIva = Optional.ofNullable(null);
	private Optional<String> posicion = Optional.ofNullable(null);
	private Optional<Double> porcentaje = Optional.ofNullable(null);
	
	public PasajeroDTO() {
		super();
	}

	public Optional<Integer> getId(){
		return id;
	}
	
	public Optional<String> getApellido() {
		return apellido;
	}

	public Optional<String> getNombre() {
		return nombre;
	}

	public Optional<String> getNro_doc() {
		return nro_doc;
	}

	public Optional<String> getCuit() {
		return cuit;
	}

	public Optional<LocalDate> getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public Optional<String> getTelefono() {
		return telefono;
	}

	public Optional<String> getMail() {
		return mail;
	}

	public Optional<String> getOcupacion() {
		return ocupacion;
	}

	public Optional<String> getNacionalidad() {
		return nacionalidad;
	}

	public Optional<Integer> getIdDireccion() {
		return idDireccion;
	}

	public Optional<String> getCalle() {
		return calle;
	}

	public Optional<String> getNumero() {
		return numero;
	}

	public Optional<String> getDepartamento() {
		return departamento;
	}

	public Optional<Integer> getPiso() {
		return piso;
	}

	public Optional<Integer> getIdLocalidad() {
		return idLocalidad;
	}

	public Optional<String> getNombreLocalidad() {
		return nombreLocalidad;
	}

	public Optional<String> getCodigo_postal() {
		return codigo_postal;
	}

	public Optional<Integer> getIdProv() {
		return idProv;
	}

	public Optional<String> getNombreProv() {
		return nombreProv;
	}

	public Optional<Integer> getIdPais() {
		return idPais;
	}

	public Optional<String> getNombrePais() {
		return nombrePais;
	}

	public Optional<Integer> getIdTipoDoc() {
		return idTipoDoc;
	}

	public Optional<String> getTipo() {
		return tipo;
	}

	public Optional<Integer> getIdPosIva() {
		return idPosIva;
	}

	public Optional<String> getPosicion() {
		return posicion;
	}

	public Optional<Double> getPorcentaje() {
		return porcentaje;
	}
	
	public void setId(Integer id) {
		this.id = Optional.of(id);
	}

	public void setApellido(String apellido) {
		this.apellido = Optional.of(apellido);
	}

	public void setNombre(String nombre) {
		this.nombre = Optional.of(nombre);
	}

	public void setNro_doc(String nro_doc) {
		this.nro_doc = Optional.of(nro_doc);
	}

	public void setCuit(String cuit) {
		this.cuit = Optional.of(cuit);
	}

	public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
		this.fecha_nacimiento = Optional.of(fecha_nacimiento);
	}

	public void setTelefono(String telefono) {
		this.telefono = Optional.of(telefono);
	}

	public void setMail(String mail) {
		this.mail = Optional.of(mail);
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = Optional.of(ocupacion);
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = Optional.of(nacionalidad);
	}

	public void setIdDireccion(Integer idDireccion) {
		this.idDireccion = Optional.of(idDireccion);
	}

	public void setCalle(String calle) {
		this.calle = Optional.of(calle);
	}

	public void setNumero(String numero) {
		this.numero = Optional.of(numero);
	}

	public void setDepartamento(String departamento) {
		this.departamento = Optional.of(departamento);
	}

	public void setPiso(Integer piso) {
		this.piso = Optional.of(piso);
	}

	public void setIdLocalidad(Integer idLocalidad) {
		this.idLocalidad = Optional.of(idLocalidad);
	}

	public void setNombreLocalidad(String nombreLocalidad) {
		this.nombreLocalidad = Optional.of(nombreLocalidad);
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = Optional.of(codigo_postal);
	}

	public void setIdProv(Integer idProv) {
		this.idProv = Optional.of(idProv);
	}

	public void setNombreProv(String nombreProv) {
		this.nombreProv = Optional.of(nombreProv);
	}

	public void setIdPais(Integer idPais) {
		this.idPais = Optional.of(idPais);
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = Optional.of(nombrePais);
	}

	public void setIdTipoDoc(Integer idTipoDoc) {
		this.idTipoDoc = Optional.of(idTipoDoc);
	}

	public void setTipo(String tipo) {
		this.tipo = Optional.of(tipo);
	}

	public void setIdPosIva(Integer idPosIva) {
		this.idPosIva = Optional.of(idPosIva);
	}

	public void setPosicion(String posicion) {
		this.posicion = Optional.of(posicion);
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = Optional.of(porcentaje);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PasajeroDTO other = (PasajeroDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
