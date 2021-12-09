package clases.gestores;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import base_de_datos.AdministradorBDPasajero;
import base_de_datos.AdministradorBDUbicaciones;
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
	
	private GestorPasajeros() {
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
			
			dto.setId(p.getId());
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
	
	public Boolean dniExistente(PasajeroDTO pasajero) {
		
		AdministradorBDPasajero adminBD = new AdministradorBDPasajero();
        
		return adminBD.dniExistente(pasajero.getNro_doc().get(), pasajero.getIdTipoDoc().get());
	}
	
	public Boolean crearPasajero(PasajeroDTO pasajero) {
		AdministradorBDUbicaciones adminBDUbicaciones = new AdministradorBDUbicaciones();
		Localidad localidad = adminBDUbicaciones.localidadPorId(pasajero.getIdLocalidad().get());
		
		AdministradorBDPasajero adminBDPasajeros = new AdministradorBDPasajero();
		PosicionIVA posIva = adminBDPasajeros.posicionIvaPorId(pasajero.getIdPosIva().get());
		TipoDocumento tipoDoc = adminBDPasajeros.tipoDocumentoPorId(pasajero.getIdTipoDoc().get());
		Direccion direc = new Direccion(pasajero);
		
		Pasajero pasaj = new Pasajero(pasajero);
		pasaj.setDireccion(direc);
		direc.setPasajero(pasaj);
		direc.setLocalidad(localidad);
		pasaj.setTipo_doc(tipoDoc);
		pasaj.setPosicion_iva(posIva);
		
		Integer id = adminBDPasajeros.registrarPasajero(pasaj);
		
		if(id == -1) return false;
		
		return true;
	}
	
	public Pasajero convertirDTO(PasajeroDTO p) {
		TipoDocumento tipoDoc = new TipoDocumento();
		tipoDoc.setTipo(p.getTipo().get());
		
		Pasajero salida = new Pasajero();
		salida.setId(p.getId().get());
		salida.setApellido(p.getApellido().get());
		salida.setNombre(p.getNombre().get());
		salida.setTipo_doc(tipoDoc);
		salida.setNro_doc(p.getNro_doc().get());
		
		return salida;
	}
	
	public List<Pasajero> convertirDTO(List<PasajeroDTO> pasajeros){
		List<Pasajero> salida = new ArrayList<Pasajero>();
		
		for(PasajeroDTO p : pasajeros) salida.add(convertirDTO(p));
		
		return salida;
	}

	public Boolean esMayor(Integer idPasajero) {
		PasajeroDTO pasDto= new PasajeroDTO();
		pasDto.setId(idPasajero);
		AdministradorBDPasajero adminBD  = new AdministradorBDPasajero();
		LocalDate pas = adminBD.fechaNacPorId(idPasajero);
		
		
		 LocalDate hoy = LocalDate.now();

	        long diff = ChronoUnit.DAYS.between(pas, hoy);
	       return ((diff / 365 )>=18);
		
	}
	
	public Pasajero buscarPasajero(PasajeroDTO  pas) {
		AdministradorBDPasajero admin= new AdministradorBDPasajero();
		return admin.buscarPasajero(pas.getId().get());
		
	}
}
