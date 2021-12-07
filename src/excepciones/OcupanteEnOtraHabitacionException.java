package excepciones;

import java.util.ArrayList;
import java.util.List;

import clases.dto.PasajeroDTO;

public class OcupanteEnOtraHabitacionException extends Exception{

	private List<PasajeroDTO> estanEnOtraHabitacion;
	
	public OcupanteEnOtraHabitacionException(List<PasajeroDTO> pasajeros) {
		super("El pasajero ya est� ocupando otra habitaci�n actualmente");
		estanEnOtraHabitacion = pasajeros;
	}
	
	public List<PasajeroDTO> getPasajeros() {
		return estanEnOtraHabitacion;
	}
	
}
