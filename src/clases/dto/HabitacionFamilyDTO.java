package clases.dto;

public class HabitacionFamilyDTO extends HabitacionDTO {

	public HabitacionFamilyDTO() {
		super();
	}
	
	@Override
	public String toString() {
		return "Habitación Superior Family Plan (Nro. "+nro.get()+")";
	}
}
