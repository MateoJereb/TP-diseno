package clases.dto;

import java.util.List;
import java.util.Optional;

import clases.Direccion;
import clases.Factura;

public class ResponsablePagoDTO {

	private Optional<String> razon_social = Optional.ofNullable(null);
	private Optional<String> cuit = Optional.ofNullable(null);
	private Optional<String> telefono = Optional.ofNullable(null);
	private Optional<List<FacturaDTO>> facturas = Optional.ofNullable(null);
	
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
	
	
	public Optional<Integer> getIdDireccion() {
			return idDireccion;
		}
		public void setIdDireccion(Integer idDireccion) {
			this.idDireccion = Optional.of(idDireccion);
		}
		public Optional<String> getCalle() {
			return calle;
		}
		public void setCalle(String calle) {
			this.calle = Optional.of(calle);
		}
		public Optional<String> getNumero() {
			return numero;
		}
		public void setNumero(String numero) {
			this.numero = Optional.of(numero);
		}
		public Optional<String> getDepartamento() {
			return departamento;
		}
		public void setDepartamento(String departamento) {
			if(departamento != null) this.departamento = Optional.of(departamento);
		}
		public Optional<Integer> getPiso() {
			return piso;
		}
		public void setPiso(Integer piso) {
			if(piso != null) this.piso = Optional.of(piso);
		}
		public Optional<Integer> getIdLocalidad() {
			return idLocalidad;
		}
		public void setIdLocalidad(Integer idLocalidad) {
			this.idLocalidad = Optional.of(idLocalidad);
		}
		public Optional<String> getNombreLocalidad() {
			return nombreLocalidad;
		}
		public void setNombreLocalidad(String nombreLocalidad) {
			this.nombreLocalidad = Optional.of(nombreLocalidad);
		}
		public Optional<String> getCodigo_postal() {
			return codigo_postal;
		}
		public void setCodigo_postal(String codigo_postal) {
			this.codigo_postal = Optional.of(codigo_postal);
		}
		public Optional<Integer> getIdProv() {
			return idProv;
		}
		public void setIdProv(Integer idProv) {
			this.idProv = Optional.of(idProv);
		}
		public Optional<String> getNombreProv() {
			return nombreProv;
		}
		public void setNombreProv(String nombreProv) {
			this.nombreProv = Optional.of(nombreProv);
		}
		public Optional<Integer> getIdPais() {
			return idPais;
		}
		public void setIdPais(Integer idPais) {
			this.idPais = Optional.of(idPais);
		}
		public Optional<String> getNombrePais() {
			return nombrePais;
		}
		public void setNombrePais(String nombrePais) {
			this.nombrePais = Optional.of(nombrePais);
		}
	public Optional<String> getRazon_social() {
		return razon_social;
	}
	public void setRazon_social(String razon_social) {
		this.razon_social = Optional.of(razon_social);
	}
	public Optional<String> getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = Optional.of(cuit);
	}
	public Optional<String> getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = Optional.of(telefono);
	}
	public Optional<List<FacturaDTO>> getFacturas() {
		return facturas;
	}
	public void setFacturas(List<FacturaDTO> facturas) {
		this.facturas = Optional.of(facturas);
	}
	
}
