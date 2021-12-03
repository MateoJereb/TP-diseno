package clases;

import java.time.LocalDateTime;
import java.util.List;

public class Estadia {

	private Integer id;
	private LocalDateTime hora_entrada;
	private LocalDateTime hora_salida;
	private Double monto;
	private Boolean pagado;
	
	private Pasajero responsable;
	private List<Pasajero> pasajeros;
	private Habitacion habitacion;
	private List<Consumo> consumos;
	private List<Factura> facturas;
	
	public Estadia() {
		super();
	}

	public Estadia(Integer id, LocalDateTime hora_entrada, LocalDateTime hora_salida, Double monto, Boolean pagado) {
		super();
		this.id = id;
		this.hora_entrada = hora_entrada;
		this.hora_salida = hora_salida;
		this.monto = monto;
		this.pagado = pagado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getHora_entrada() {
		return hora_entrada;
	}

	public void setHora_entrada(LocalDateTime hora_entrada) {
		this.hora_entrada = hora_entrada;
	}

	public LocalDateTime getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(LocalDateTime hora_salida) {
		this.hora_salida = hora_salida;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Boolean getPagado() {
		return pagado;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}

	public Pasajero getResponsable() {
		return responsable;
	}

	public void setResponsable(Pasajero responsable) {
		this.responsable = responsable;
	}

	public List<Pasajero> getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros(List<Pasajero> pasajeros) {
		this.pasajeros = pasajeros;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public List<Consumo> getConsumos() {
		return consumos;
	}

	public void setConsumos(List<Consumo> consumos) {
		this.consumos = consumos;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	
}
