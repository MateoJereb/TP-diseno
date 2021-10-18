package clases.gestores;

import java.util.ArrayList;
import java.util.List;

import base_de_datos.AdministradorBDPasajero;
import clases.*;
import clases.dto.PasajeroDTO;
import clases.dto.PosicionIVADTO;
import clases.dto.TipoDocumentoDTO;

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
	
	public List<PasajeroDTO> buscarPasajeros(PasajeroDTO datos){
		List<PasajeroDTO> salida;
		
		AdministradorBDPasajero adminBD  = new AdministradorBDPasajero();
		
		List<Pasajero> pasajeros = adminBD.recuperarPasajeros(datos);
		
		salida = generarDTOPasajeros(pasajeros);
		
		return salida;
	}
	
	private List<PasajeroDTO> generarDTOPasajeros(List<Pasajero> pasajeros){
		List<PasajeroDTO> salida = new ArrayList<PasajeroDTO>();
		
		for(Pasajero p : pasajeros) {
			PasajeroDTO dto = new PasajeroDTO();
			
			dto.setApellido(p.getApellido());
			dto.setNombre(p.getNombre());
			dto.setIdTipoDoc(p.getTipo_doc().getId());
			dto.setTipo(p.getTipo_doc().getTipo());
			dto.setNro_doc(p.getNro_doc());
			
			salida.add(dto);
			
		}
		
		return salida;
	}
	
	public List<TipoDocumentoDTO> buscarTiposDocumento(){
		List<TipoDocumentoDTO> salida = new ArrayList<TipoDocumentoDTO>();
		
		AdministradorBDPasajero adminBD = new AdministradorBDPasajero();
		
		List<TipoDocumento> tipos = adminBD.recuperarTiposDocumento();
		
		salida = generarDTOTiposDocumento(tipos);
		
		return salida;
	}
	
	private List<TipoDocumentoDTO> generarDTOTiposDocumento(List<TipoDocumento> tipos){
		List<TipoDocumentoDTO> salida = new ArrayList<TipoDocumentoDTO>();
		
		for(TipoDocumento d : tipos) {
			TipoDocumentoDTO dto = new TipoDocumentoDTO();
			
			dto.setId(d.getId());
			dto.setTipo(d.getTipo());
			
			salida.add(dto);
		}
		
		return salida;
	}
	
	public List<PosicionIVADTO> buscarPosicionesIVA(){
		List<PosicionIVADTO> salida = new ArrayList<PosicionIVADTO>();
		
		AdministradorBDPasajero adminBD = new AdministradorBDPasajero();
		
		List<PosicionIVA> posiciones = adminBD.recuperarPosicionesIVA();
		
		salida = generarDTOPosicionesIVA(posiciones);
		
		return salida;
	}
	
	private List<PosicionIVADTO> generarDTOPosicionesIVA(List<PosicionIVA> posiciones){
		List<PosicionIVADTO> salida = new ArrayList<PosicionIVADTO>();
		
		for(PosicionIVA p : posiciones) {
			PosicionIVADTO dto = new PosicionIVADTO();
			
			dto.setId(p.getId());
			dto.setPosicion(p.getPosicion());
			
			salida.add(dto);
		}
		
		return salida;
	}
	
}
