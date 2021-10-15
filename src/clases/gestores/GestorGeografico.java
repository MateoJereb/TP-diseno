package clases.gestores;

import java.util.ArrayList;
import java.util.List;

import clases.*;

public class GestorGeografico {
	
	public static GestorGeografico instancia;
	
	private List<Localidad> listaLocalidades;
	private List<Provincia> listaProvincias;
	private List<Pais> listaPaises;
	
	public static GestorGeografico getInstance() {
		if(instancia == null) {
			instancia = new GestorGeografico();
		}
		
		return instancia;
	}
	
	public GestorGeografico() {
		listaLocalidades = new ArrayList<Localidad>();
		listaProvincias = new ArrayList<Provincia>();
		listaPaises = new ArrayList<Pais>();
	}
	
}
