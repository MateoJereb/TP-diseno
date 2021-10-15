package clases.gestores;

import java.util.ArrayList;
import java.util.List;

import clases.*;

public class GestorPasajeros {

	private static GestorPasajeros instancia;
	
	private List<Pasajero> listaPasajeros;
	private List<Direccion> listaDirecciones;
	private List<PosicionIVA> listaPosicionesIVA;
	private List<TipoDocumento> listaTiposDocumento;
	
	public static GestorPasajeros getInstance() {
		if(instancia == null) {
			instancia = new GestorPasajeros();
		}
		
		return instancia;
	}
	
	public GestorPasajeros() {
		listaPasajeros = new ArrayList<Pasajero>();
		listaDirecciones = new ArrayList<Direccion>();
		listaPosicionesIVA = new ArrayList<PosicionIVA>();
		listaTiposDocumento = new ArrayList<TipoDocumento>();
	}
	
}
