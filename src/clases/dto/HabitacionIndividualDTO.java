package clases.dto;

public class HabitacionIndividualDTO extends HabitacionDTO {

	public HabitacionIndividualDTO() {
		super();
	}
	
	@Override
	public String toString() {
		return "Habitaci�n Individual Est�ndar (Nro. "+nro.get()+")";
	}
	
}
