package clases.gestores;

import java.util.ArrayList;
import java.util.List;

import base_de_datos.AdministradorBDUbicaciones;
import clases.*;
import clases.dto.LocalidadDTO;
import clases.dto.PaisDTO;
import clases.dto.ProvinciaDTO;

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
	
	private GestorGeografico() {
		listaLocalidades = new ArrayList<Localidad>();
		listaProvincias = new ArrayList<Provincia>();
		listaPaises = new ArrayList<Pais>();
	}
	
	public List<PaisDTO> buscarPaises(){
		List<PaisDTO> salida;
		
		AdministradorBDUbicaciones adminBD = new AdministradorBDUbicaciones();
		List<Pais> paises = adminBD.recuperarPaises();
		
		salida = generarDTOPaises(paises);
		
		return salida;
	}
	
	private List<PaisDTO> generarDTOPaises(List<Pais> paises){
		List<PaisDTO> salida = new ArrayList<PaisDTO>();
		
		for(Pais p : paises) {
			PaisDTO dto = new PaisDTO();
			
			dto.setIdPais(p.getId());
			dto.setNombre(p.getNombre());
			
			salida.add(dto);
		}
		
		return salida;
	}
	
	public List<ProvinciaDTO> buscarProvincias(Integer idPais){
		List<ProvinciaDTO> salida;
		
		AdministradorBDUbicaciones adminBD = new AdministradorBDUbicaciones();
		List<Provincia> provincias = adminBD.recuperarProvincias(idPais);
		
		salida = generarDTOProvincias(provincias);
		
		return salida;
	}
	
	private List<ProvinciaDTO> generarDTOProvincias(List<Provincia> provincias){
		List<ProvinciaDTO> salida = new ArrayList<ProvinciaDTO>();
		
		for(Provincia p : provincias) {
			ProvinciaDTO dto = new ProvinciaDTO();
			
			dto.setIdPais(p.getPais().getId());
			dto.setNombrePais(p.getPais().getNombre());
			dto.setIdProv(p.getId());
			dto.setNombre(p.getNombre());
			
			salida.add(dto);
		}
		
		return salida;
	}
	
	public List<LocalidadDTO> buscarLocalidades(Integer idProv){
		List<LocalidadDTO> salida;
		
		AdministradorBDUbicaciones adminBD = new AdministradorBDUbicaciones();
		List<Localidad> localidades = adminBD.recuperarLocalidades(idProv);
		
		salida = generarDTOLocalidades(localidades);
		
		return salida;
	}
	
	private List<LocalidadDTO> generarDTOLocalidades(List<Localidad> localidades){
		List<LocalidadDTO> salida = new ArrayList<LocalidadDTO>();
		
		for(Localidad l : localidades) {
			LocalidadDTO dto = new LocalidadDTO();
			
			dto.setIdLocalidad(l.getId());
			dto.setNombre(l.getNombre());
			dto.setCodigo_postal(l.getCodigo_postal());
			dto.setIdProv(l.getProvincia().getId());
			dto.setNombreProvincia(l.getProvincia().getNombre());
			dto.setIdPais(l.getProvincia().getPais().getId());
			dto.setNombrePais(l.getProvincia().getPais().getNombre());
			
			salida.add(dto);
		}
		
		return salida;
	}
}
