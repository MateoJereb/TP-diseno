package clases.dto;

public class HabitacionIndividualDTO extends HabitacionDTO {

	public HabitacionIndividualDTO() {
		super();
	}
	
	@Override
	public String toString() {
		return "Habitación Individual Estándar (Nro. "+nro.get()+")";
	}
	
}
