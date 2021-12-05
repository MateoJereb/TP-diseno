package clases.dto;

public class HabitacionDobleEstandarDTO extends HabitacionDTO {

	public HabitacionDobleEstandarDTO() {
		super();
	}

	@Override
	public String toString() {
		return "Habitación Doble Estándar (Nro. "+nro.get()+")";
	}
}
