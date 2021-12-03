package clases;

import java.time.LocalDate;

public class Reserva {

	private Integer id;
	private LocalDate entrada;
	private LocalDate salida;
	private String nombre;
	private String apellido;
	private String telefono;
	
	private Habitacion habitacion;
	
	public Reserva() {
		
	}

	public Reserva(Integer id, LocalDate entrada, LocalDate salida, String nombre, String apellido, String telefono) {
		super();
		this.id = id;
		this.entrada = entrada;
		this.salida = salida;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
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

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	
	
	
}
