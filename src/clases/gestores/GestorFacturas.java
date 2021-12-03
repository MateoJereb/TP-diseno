package clases.gestores;

import java.util.ArrayList;
import java.util.List;

import clases.Factura;

public class GestorFacturas {

	public static GestorFacturas instancia;
	
	private List<Factura> facturas;
	
	public static GestorFacturas getInstance() {
		if(instancia == null) {
			instancia = new GestorFacturas();
		}
		
		return instancia;
	}
	
	private GestorFacturas() {
		facturas = new ArrayList<Factura>();
	}
}
