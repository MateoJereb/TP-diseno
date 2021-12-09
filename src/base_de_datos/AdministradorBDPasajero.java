package base_de_datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	
	public TipoDocumento tipoDocumentoPorId(Integer id) {
		TipoDocumento tipoDoc = null;
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT tipo FROM tp_12c.tipo_documento WHERE id_tipo_documento = "+id);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				String tipo = resultado.getString(1);
				
				tipoDoc = new TipoDocumento(id,tipo);
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
		
		return tipoDoc;
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
	
	public PosicionIVA posicionIvaPorId(Integer id) {
		PosicionIVA posIva = null;
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM tp_12c.posicion_iva WHERE id_posicion_iva = "+id);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				String posicion = resultado.getString(2);
				Double porcentaje = resultado.getDouble(3);
				TipoFactura tipoFactura = null;
				if(resultado.getString(4) == "A") {
					tipoFactura = TipoFactura.A;
				}
				else {
					tipoFactura = TipoFactura.B;
				}
				
				posIva = new PosicionIVA(id,posicion,porcentaje,tipoFactura);
				
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
		
		return posIva;
		
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

	public Integer registrarPasajero(Pasajero pasajero) {
		Integer id = null;
		
		Connection conexion = getConnection();
        Statement sentencia = null;
        ResultSet resultado = null;
        
        try {
            String consulta = "SELECT nextval('tp_12c.sec_pasajero')";
        	sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(consulta);
            
            while(resultado.next()) {
            	id = resultado.getInt(1);
            }
            
            System.out.println("Consulta realizada: "+consulta);
            
            conexion.setAutoCommit(false);
            
            //Pasajero
            String nroDoc = "'"+pasajero.getNro_doc()+"'";
            Integer idTipoDoc = pasajero.getTipo_doc().getId();
            Integer idPosIva = pasajero.getPosicion_iva().getId();
            String nombre = "'"+pasajero.getNombre()+"'";
            String apellido = "'"+pasajero.getApellido()+"'";
            String fechaNac = "'"+pasajero.getFecha_nacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"'";
            String telefono = "'"+pasajero.getTelefono()+"'";
            String cuit = "null";
            if(pasajero.getCuit() != null) cuit = "'"+pasajero.getCuit()+"'";
            String mail = "null";
            if(pasajero.getMail() != null) mail = "'"+pasajero.getMail()+"'";
            String ocupacion = "'"+pasajero.getOcupacion()+"'";
            String nacionalidad = "'"+pasajero.getNacionalidad()+"'";
            
            String insercionPasajero = "INSERT INTO tp_12c.pasajero VALUES "
            		+ "("+id+","+nroDoc+","+idTipoDoc+","+idPosIva+","+nombre+","+apellido+","+fechaNac+","+telefono+","+cuit+","+mail+","+ocupacion+","+nacionalidad+")";
            
            sentencia.executeUpdate(insercionPasajero);
            
            //Direccion
            Integer idLocalidad = pasajero.getDireccion().getLocalidad().getId(); 
            String calle = "'"+pasajero.getDireccion().getCalle()+"'";
            String numero = "'"+pasajero.getDireccion().getNumero()+"'";
            String piso = "null";
            if(pasajero.getDireccion().getPiso() != null) piso = pasajero.getDireccion().getPiso().toString();
            String departamento = "null";
            if(pasajero.getDireccion().getDepartamento() != null) departamento = "'"+pasajero.getDireccion().getDepartamento()+"'";
            
            String insercionDireccion = "INSERT INTO tp_12c.direccion VALUES "
            		+ "(nextval('tp_12c.sec_direccion'),"+id+",null,"+idLocalidad+","+calle+","+numero+","+piso+","+departamento+")";
            
            sentencia.executeUpdate(insercionDireccion);
            
            conexion.commit();
            
            System.out.println("Inserción realizada: "+insercionPasajero);
            System.out.println("Inserción realizada: "+insercionDireccion);
            
        }
        catch(SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			id = -1;
		}
        finally {
            if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
            if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
            if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
        }
		
		return id;
	}

	public LocalDate fechaNacPorId(Integer id) {
        LocalDate salida=null;

        Connection conexion = getConnection();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            sentencia = conexion.prepareStatement("SELECT fecha_nacimiento  FROM tp_12c.pasajero WHERE id_pasajero = "+id);
            resultado = sentencia.executeQuery();

            while(resultado.next()) {

                String fecha=  resultado.getString(1);
                salida = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

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

	public Pasajero buscarPasajero(Integer idPasajero) {
        Pasajero salida = new Pasajero();
        PosicionIVA posIva = new PosicionIVA();

        Connection conexion = getConnection();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        String consulta ="SELECT pas.id_pasajero, pas.nro_doc, pas.nombre, pas.apellido, pi.id_posicion_iva, pi.posicion, pi.porcentaje, pi.tipo_factura\n"
                + "FROM tp_12c.pasajero pas, tp_12c.posicion_iva pi\n"
                + "WHERE pas.id_posicion_iva = pi.id_posicion_iva AND pas.id_pasajero = "+idPasajero;

        try {
            sentencia = conexion.prepareStatement(consulta);
            resultado = sentencia.executeQuery();

            while(resultado.next()) {
                salida.setId(resultado.getInt(1));
                salida.setNro_doc(resultado.getString(2));
                salida.setNombre(resultado.getString(3));
                salida.setApellido(resultado.getString(4));

                posIva.setId(resultado.getInt(5));
                posIva.setPosicion(resultado.getString(6));
                posIva.setPorcentaje(resultado.getDouble(7));
                if(resultado.getString(8).equals(TipoFactura.A.getName())) posIva.setTipo_factura(TipoFactura.A);
                else posIva.setTipo_factura(TipoFactura.B);

                salida.setPosicion_iva(posIva);


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
