package clases.gestores;

import java.util.List;

import base_de_datos.*;
import clases.ResponsablePago;
import clases.dto.ResponsablePagoDTO;
import excepciones.*;

public class GestorResponsablePago {
	
private static GestorResponsablePago instancia;	

	public static GestorResponsablePago getInstance() {
		if(instancia == null) {
			instancia = new GestorResponsablePago();
		}
		
		return instancia;
	}
	private GestorResponsablePago() {
		super();
	}
	public ResponsablePagoDTO buscarResponsable(String cuit)  throws RPInexistenteException{
		AdministradorBDResponsablePago admin = new AdministradorBDResponsablePago();
		ResponsablePago respP = admin.recuperarResponsablePago(cuit);
		ResponsablePagoDTO respDTO = new ResponsablePagoDTO();
		
		respDTO.setCuit(respP.getCuit());
		respDTO.setTelefono(respP.getTelefono());
		respDTO.setRazon_social(respP.getRazon_social());
		
		return respDTO;
	}
	public ResponsablePago buscarResponsable(ResponsablePagoDTO resp) {
		AdministradorBDResponsablePago admin = new AdministradorBDResponsablePago();
		return admin.buscarResponsablePago(resp.getCuit().get());
	}
}
