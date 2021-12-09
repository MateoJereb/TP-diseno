package base_de_datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import clases.Consumo;

public class AdministradorBDConsumos extends AdministradorBD{

	public AdministradorBDConsumos() {
		super();
	}
	public List<Consumo> recuperarConsumos(Integer idEstadia){
		List<Consumo> salida = new ArrayList<Consumo>();
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		String consulta = "SELECT * \r\n"
				+ "FROM tp_12c.consumo \r\n"
				+ "WHERE id_estadia ="+idEstadia;
		try {
			sentencia = conexion.prepareStatement(consulta);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Consumo cons = new Consumo();
				cons.setId(resultado.getInt(1));
				cons.setMonto(resultado.getDouble(3));
				cons.setCantidad(resultado.getInt(4));
				cons.setDescripcion(resultado.getString(6));
				cons.setFecha(LocalDateTime.parse(resultado.getString(7),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				cons.setCantPaga(resultado.getInt(8));
				salida.add(cons);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return salida;
	}
	public Consumo buscarConsumo(Integer idConsumo) {
		Consumo cons = new Consumo();
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		String consulta = "SELECT * \r\n"
				+ "FROM tp_12c.consumo \r\n"
				+ "WHERE id_consumo = "+idConsumo;
		try {
			sentencia = conexion.prepareStatement(consulta);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				
				cons.setId(resultado.getInt(1));
				cons.setMonto(resultado.getDouble(3));
				cons.setCantidad(resultado.getInt(4));
				cons.setDescripcion(resultado.getString(6));
				cons.setFecha(LocalDateTime.parse(resultado.getString(7),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				cons.setCantPaga(resultado.getInt(8));
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return cons;
	}
}
