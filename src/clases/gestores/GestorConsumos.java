package clases.gestores;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import base_de_datos.AdministradorBDConsumos;
import clases.Consumo;
import clases.ConsumoFacturado;
import clases.dto.ConsumosDTO;

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
	public List<ConsumosDTO> buscarConsumos(Integer idEstadia){
		AdministradorBDConsumos admin = new AdministradorBDConsumos();
		List<Consumo> consumos = new ArrayList<Consumo>();
		consumos = admin.recuperarConsumos(idEstadia);
		return generarDTO(consumos);
	}
	private List<ConsumosDTO> generarDTO(List<Consumo> consumos){
		List<ConsumosDTO> salida =new ArrayList<ConsumosDTO>();
		
		for(Consumo cons: consumos) {
			ConsumosDTO consDto = new ConsumosDTO(); 
			consDto.setCantidad(cons.getCantidad());
			consDto.setDescripcion(cons.getDescripcion());
			consDto.setFecha(cons.getFecha());
			consDto.setIdConsumo(cons.getId());
			consDto.setMonto(cons.getMonto());
			consDto.setCantPagado(cons.getCantPaga());
			salida.add(consDto);
		}
		return salida;
	}
	public List<ConsumoFacturado> buscarConsumos(LinkedHashMap<ConsumosDTO, Integer> consumosDTO){
		
		List<ConsumoFacturado> salida = new ArrayList<ConsumoFacturado>();
		AdministradorBDConsumos admin = new AdministradorBDConsumos();
		
		for(ConsumosDTO cons: consumosDTO.keySet()) {
			Consumo consumo = admin.buscarConsumo(cons.getIdConsumo().get());
			ConsumoFacturado consFacturado = new ConsumoFacturado();
			consFacturado.setConsumo(consumo);
			consFacturado.setCantidad(consumosDTO.get(cons));
			salida.add(consFacturado);
		}
		return salida;
	}
	
}
