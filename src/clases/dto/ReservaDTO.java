package clases.dto;

import java.time.LocalDate;

public class ReservaDTO {

	private Integer id;
	private LocalDate entrada;
	private LocalDate salida;
	private String nombre;
	private String apellido;
	private String telefono;
	
	public ReservaDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getEntrada() {
		return entrada;
	}

	public void setEntrada(LocalDate entrada) {
		this.entrada = entrada;
	}

	public LocalDate getSalida() {
		return salida;
	}

	public void setSalida(LocalDate salida) {
		this.salida = salida;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	
	
}
