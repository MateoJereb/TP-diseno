package clases.dto;

public class HabitacionSuiteDTO extends HabitacionDTO{

	public HabitacionSuiteDTO() {
		super();
	}
	
	@Override
	public String toString() {
		return "Habitación Suite Doble (Nro. "+nro.get()+")";
	}
}
