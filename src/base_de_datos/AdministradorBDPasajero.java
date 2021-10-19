package base_de_datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clases.Pasajero;
import clases.PosicionIVA;
import clases.TipoDocumento;
import clases.dto.PasajeroDTO;
import enums.TipoFactura;

public class AdministradorBDPasajero extends AdministradorBD {

	public AdministradorBDPasajero() {
		super();
	}
	
	public List<Pasajero> recuperarPasajeros(PasajeroDTO filtros){ //Recupera filtrando por Apellido, Nombre, Tipo documento y/o Nro documento
		
		List<Pasajero> salida = new ArrayList<Pasajero>();
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		String consulta = "SELECT pj.id_pasajero, pj.apellido, pj.nombre, pj.id_tipo_documento, td.tipo, pj.nro_doc"
						+ " FROM tp_12c.pasajero pj, tp_12c.tipo_documento td"
						+ " WHERE pj.id_tipo_documento = td.id_tipo_documento";
					
		
		consulta += " AND pj.apellido LIKE '"+filtros.getApellido().get()+"%' AND pj.nombre LIKE '"+filtros.getNombre().get()+"%'";
		
		if(filtros.getIdTipoDoc().isPresent()) {
			consulta+=" AND pj.id_tipo_documento = "+filtros.getIdTipoDoc().get();
		}
		
		if(filtros.getNro_doc().isPresent()) {
			consulta+=" AND pj.nro_doc = '"+filtros.getNro_doc().get()+"'";
		}
		
		consulta+=" ORDER BY 1";
		
		try {
			sentencia = conexion.prepareStatement(consulta);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Pasajero pasajero = new Pasajero();
				TipoDocumento tipoDoc = new TipoDocumento();
				
				pasajero.setId(resultado.getInt(1));
				pasajero.setApellido(resultado.getString(2));
				pasajero.setNombre(resultado.getString(3));
				pasajero.setNro_doc(resultado.getString(6));
				
				tipoDoc.setId(resultado.getInt(4));
				tipoDoc.setTipo(resultado.getString(5));
				
				pasajero.setTipo_doc(tipoDoc);
				
				salida.add(pasajero);
				
			}
			
			System.out.println("Consulta realizada: "+consulta);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return salida;
	}
	
	public List<TipoDocumento> recuperarTiposDocumento(){
		List<TipoDocumento> salida = new ArrayList<TipoDocumento>();
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM tp_12c.tipo_documento");
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				TipoDocumento tipoDoc = new TipoDocumento(resultado.getInt(1),resultado.getString(2));
				salida.add(tipoDoc);
			}
			
			System.out.println("Consulta realizada: "+sentencia.toString());
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return salida;
	}
	
	public List<PosicionIVA> recuperarPosicionesIVA(){
		List<PosicionIVA> salida = new ArrayList<PosicionIVA>();
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM tp_12c.posicion_iva");
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Integer id = resultado.getInt(1);
				String posicion = resultado.getString(2);
				Double porcentaje = resultado.getDouble(3);
				TipoFactura factura = null;
				
				if(resultado.getString(4) == "A") {
					factura = TipoFactura.A;
				}
				else {
					factura = TipoFactura.B;
				}
				
				PosicionIVA posIva = new PosicionIVA(id,posicion,porcentaje,factura);
				salida.add(posIva);
			}
			
			System.out.println("Consulta realizada: "+sentencia.toString());
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return salida;
	}
	
	public Boolean dniExistente(String dni, Integer idTipoDoc){
        Boolean salida = false;
		
		Connection conexion = getConnection();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        String consulta = "SELECT pj.apellido"
                        + " FROM tp_12c.pasajero pj , tp_12c.tipo_documento td"
                        + " WHERE pj.id_tipo_documento = td.id_tipo_documento";


        consulta += " AND pj.nro_doc =  '"+dni+"' AND td.id_tipo_documento = " + idTipoDoc;
        consulta+=" ORDER BY 1";

        try {
            sentencia = conexion.prepareStatement(consulta);
            resultado = sentencia.executeQuery();
            
            if(resultado.next())salida = true;
           
        }
        catch(SQLException e) {
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
