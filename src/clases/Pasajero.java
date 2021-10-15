package clases;

import java.time.LocalDate;

public class Pasajero {

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
	
	public Pasajero(String apellido, String nombre, String nro_doc, String cuit, LocalDate fecha_nacimiento,
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
	
	
	
}
