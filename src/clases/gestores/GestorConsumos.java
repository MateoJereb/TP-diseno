package clases.gestores;

import java.util.ArrayList;
import java.util.List;

import clases.Consumo;

public class GestorConsumos {

	public static GestorConsumos instancia;
	
	private List<Consumo> consumos;
	
	public static GestorConsumos getInstance() {
		if(instancia == null) {
			instancia = new GestorConsumos();
		}
		
		return instancia;
	}
	
	private GestorConsumos() {
		consumos = new ArrayList<Consumo>();
	}
	
}
