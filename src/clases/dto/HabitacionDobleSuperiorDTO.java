package clases.dto;

public class HabitacionDobleSuperiorDTO extends HabitacionDTO {

	public HabitacionDobleSuperiorDTO() {
		super();
	}
	
	@Override
	public String toString() {
		return "Habitación Doble Superior (Nro. "+nro.get()+")";
	}
}
