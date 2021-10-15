package clases;

import java.util.Optional;

public class Direccion {

	private String calle;
	private String numero;
	private String departamento;
	private Integer piso;
	
	private Optional<Pasajero> pasajero;
	private Localidad localidad;
	
		
	public Direccion(String calle, String numero, String departamento, Integer piso, Pasajero pasajero,
			Localidad localidad) {
		super();
		this.calle = calle;
		this.numero = numero;
		this.departamento = departamento;
		this.piso = piso;
		this.pasajero = Optional.of(pasajero);
		this.localidad = localidad;
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
	
	
	
}
