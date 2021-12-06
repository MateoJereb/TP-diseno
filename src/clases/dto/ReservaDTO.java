package clases.dto;

import java.time.LocalDate;
import java.util.Optional;

public class ReservaDTO {

	private Optional<Integer> id;
	private Optional<LocalDate> entrada;
	private Optional<LocalDate> salida;
	private Optional<String> nombre;
	private Optional<String> apellido;
	private Optional<String> telefono;
	
	public ReservaDTO() {
		super();
	}

	public void setId(Integer id) {
		this.id = Optional.of(id);
	}

	public void setEntrada(LocalDate entrada) {
		this.entrada = Optional.of(entrada);
	}

	public void setSalida(LocalDate salida) {
		this.salida = Optional.of(salida);
	}

	public void setNombre(String nombre) {
		this.nombre = Optional.of(nombre);
	}

	public void setApellido(String apellido) {
		this.apellido = Optional.of(apellido);
	}

	public void setTelefono(String telefono) {
		this.telefono = Optional.of(telefono);
	}

	public Optional<Integer> getId() {
		return id;
	}

	public Optional<LocalDate> getEntrada() {
		return entrada;
	}

	public Optional<LocalDate> getSalida() {
		return salida;
	}

	public Optional<String> getNombre() {
		return nombre;
	}

	public Optional<String> getApellido() {
		return apellido;
	}

	public Optional<String> getTelefono() {
		return telefono;
	}
	
	
	
}
