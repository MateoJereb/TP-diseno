package base_de_datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import clases.Consumo;
import clases.Pasajero;
import clases.PosicionIVA;
import clases.ResponsablePago;
import enums.TipoFactura;
import excepciones.RPInexistenteException;

public class AdministradorBDResponsablePago extends AdministradorBD{

	public AdministradorBDResponsablePago() {
		super();
	}
	public ResponsablePago recuperarResponsablePago(String cuit) throws RPInexistenteException{
		
		ResponsablePago salida = null;
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		String consulta = "SELECT *\r\n"
				+ "FROM tp_12c.responsable_pago rp\r\n"
				+ "where rp.cuit = '"+cuit+"';";
		try {
			sentencia = conexion.prepareStatement(consulta);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				salida = new ResponsablePago();
				salida.setCuit(cuit);
				salida.setRazon_social(resultado.getString(2));
				salida.setTelefono(resultado.getString(3));
				
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		if(salida==null ) throw new RPInexistenteException("No Existe un Responsable de Pago con el cuit ingresado");
		
		return salida;
	}
	public ResponsablePago buscarResponsablePago (String cuit) {
		ResponsablePago salida = new ResponsablePago();
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		String consulta ="SELECT cuit, razon_social, telefono\r\n"
				+ "FROM tp_12c.responsable_pago\r\n"
				+ "WHERE cuit = " +cuit;
		
		try {
			sentencia = conexion.prepareStatement(consulta);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				salida.setCuit(cuit);
				salida.setRazon_social(resultado.getString(2));
				salida.setTelefono(resultado.getString(3));
				
				
			}
			System.out.println("Consulta Realizada: "+consulta);
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
}
