package clases.dto;

public class HabitacionSuiteDTO extends HabitacionDTO{

	public HabitacionSuiteDTO() {
		super();
	}
	
	@Override
	public String toString() {
		return "Habitaci�n Suite Doble (Nro. "+nro.get()+")";
	}
}
