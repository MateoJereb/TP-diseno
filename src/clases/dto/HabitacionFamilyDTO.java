package clases.dto;

public class HabitacionFamilyDTO extends HabitacionDTO {

	public HabitacionFamilyDTO() {
		super();
	}
	
	@Override
	public String toString() {
		return "Habitaci�n Superior Family Plan (Nro. "+nro.get()+")";
	}
}
