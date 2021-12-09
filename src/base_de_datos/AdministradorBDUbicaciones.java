package base_de_datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clases.Localidad;
import clases.Pais;
import clases.Provincia;
import clases.TipoDocumento;

public class AdministradorBDUbicaciones extends AdministradorBD{

	public AdministradorBDUbicaciones() {
		super();
	}
	
	public List<Pais> recuperarPaises(){
		List<Pais> salida = new ArrayList<Pais>();
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM tp_12c.pais ORDER BY nombre");
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Pais pais = new Pais(resultado.getInt(1),resultado.getString(2));
				salida.add(pais);
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
	
	public List<Provincia> recuperarProvincias(Integer paisFiltro){
		List<Provincia> salida = new ArrayList<Provincia>();
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM tp_12c.pais pa, tp_12c.provincia pr"
							+ " WHERE pa.id_pais = "+paisFiltro+" AND pa.id_pais = pr.id_pais"
							+ " ORDER BY pr.nombre");
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Integer idPais = resultado.getInt(1);
				String nombrePais = resultado.getString(2);
				Integer idProv = resultado.getInt(3);
				String nombreProv = resultado.getString(5);
				
				Pais pais = new Pais(idPais,nombrePais);
				Provincia prov = new Provincia(idProv,nombreProv,pais);
				
				salida.add(prov);
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
	
	public List<Localidad> recuperarLocalidades(Integer provFiltro){
		List<Localidad> salida = new ArrayList<Localidad>();
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM tp_12c.pais pa, tp_12c.provincia pr, tp_12c.localidad lo"
					+ " WHERE pr.id_provincia = "+provFiltro
					+ " AND pa.id_pais = pr.id_pais AND pr.id_provincia = lo.id_provincia"
					+ " ORDER BY lo.nombre");
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Integer idPais = resultado.getInt(1);
				String nombrePais = resultado.getString(2);
				Integer idProv = resultado.getInt(3);
				String nombreProv = resultado.getString(5);
				Integer idLoc = resultado.getInt(6);
				String nombreLoc = resultado.getString(8);
				String codigoPostal = resultado.getString(9);
				
				Pais pais = new Pais(idPais,nombrePais);
				Provincia prov = new Provincia(idProv,nombreProv,pais);
				Localidad loc = new Localidad(idLoc,nombreLoc,codigoPostal,prov);
				
				salida.add(loc);
				
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

	public Localidad localidadPorId(Integer id) {
		Localidad localidad = null;
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM tp_12c.pais pa, tp_12c.provincia pr, tp_12c.localidad lo"
					+ " WHERE lo.id_localidad = "+id
					+" AND lo.id_provincia = pr.id_provincia AND pr.id_pais = pa.id_pais");
			
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Integer idPais = resultado.getInt(1);
				String nombrePais = resultado.getString(2);
				
				Integer idProvincia = resultado.getInt(3);
				String nombreProvincia = resultado.getString(5);
				
				String nombreLocalidad = resultado.getString(8);
				String codPostal = resultado.getString(9);
				
				Pais pais = new Pais(idPais,nombrePais);
				Provincia prov = new Provincia(idProvincia, nombreProvincia, pais);
				localidad = new Localidad(id, nombreLocalidad, codPostal, prov);
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
		
		return localidad;
		
	}
}
