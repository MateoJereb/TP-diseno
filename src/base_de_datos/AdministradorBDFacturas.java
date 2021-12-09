package base_de_datos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import clases.Consumo;
import clases.ConsumoFacturado;
import clases.Direccion;
import clases.Estadia;
import clases.Factura;
import clases.Habitacion;
import clases.HabitacionDobleEstandar;
import clases.HabitacionDobleSuperior;
import clases.HabitacionFamily;
import clases.HabitacionIndividual;
import clases.HabitacionSuite;
import clases.Localidad;
import clases.Pasajero;
import clases.PosicionIVA;
import clases.ResponsablePago;
import enums.EstadoFactura;
import enums.TipoFactura;

public class AdministradorBDFacturas extends AdministradorBD{

	public AdministradorBDFacturas() {
		super();
	}
	public Integer registrarFactura(Factura factura){
		Integer id = null;
		String incercionConsumos="";
		
		Connection conexion = getConnection();
        Statement sentencia = null;
        ResultSet resultado = null;
        
        try {
            String consulta = "SELECT nextval('tp_12c.sec_factura')";
        	sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(consulta);
            
            while(resultado.next()) {
            	id = resultado.getInt(1);
            }
            
            System.out.println("Consulta realizada: "+consulta);
            
            conexion.setAutoCommit(false);
            
            //Factura
        	 String tipo = factura.getTipo().getName();
        	 String fecha= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy  hh:mm"));
        	 Double monto_neto = factura.getMonto_neto();
        	 Double iva =factura.getIva();
        	 Double monto_total = factura.getMonto_total();
        	 String estado = EstadoFactura.NO_IMPRESA.name();
        	 Integer estadia = null;
        	 Integer responsable_fisico ;
        	 if(factura.getResponsable_fisico()==null) responsable_fisico = null ;
        	 else responsable_fisico = factura.getResponsable_fisico().get().getId() ;
        	 String responsable_juridico;
        	 if(factura.getResponsable_juridico()==null) responsable_juridico = null;
        	 else responsable_juridico = factura.getResponsable_juridico().get().getCuit() ;
    
        	 if(factura.getEstadia() !=null) {
        	  estadia = factura.getEstadia().getId();
        	 }
 
            
            String insercionPasajero = "INSERT INTO tp_12c.factura VALUES "
            		+ "("+id+","+estadia+","+responsable_fisico+","+responsable_juridico+",'"+tipo+"',"+monto_neto+","+iva+","+monto_total+",'"+estado+"', '"+fecha+"')";
            
            System.out.println("Inserción realizada: "+insercionPasajero);
            sentencia.executeUpdate(insercionPasajero);
            
            //Factura_consumo
            
            if(!factura.getConsumos().isEmpty()) {
          incercionConsumos = "INSERT INTO tp_12c.factura_consumo VALUES \n";
          Boolean primerConsumo=true;
          for(ConsumoFacturado cons: factura.getConsumos()) {
        	  if(!primerConsumo)incercionConsumos+=",\n";
        	  incercionConsumos+="("+id+", "+cons.getConsumo().getId()+", "+cons.getCantidad()+")";
        	  primerConsumo=false;
          }
          sentencia.executeUpdate(incercionConsumos);
            }
            
           
            
            conexion.commit();
            
            System.out.println("Inserción realizada: "+insercionPasajero);
            
            if(!factura.getConsumos().isEmpty()) System.out.println("Inserción realizada: "+incercionConsumos);
            
        }
        catch(SQLException e) {
			e.printStackTrace();
			id = -1;
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        finally {
            if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
            if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
            if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
        }
		
		return id;
	}
	public Factura recuperarFacturaRespJuridico(Integer idFactura) {
		

		Factura factura = new Factura();
		
		Connection conexion = getConnection();
        Statement sentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT fac.id_factura, fac.fecha, fac.tipo, fac.monto_neto, fac.monto_total, fac.iva, rp.razon_social, rp.telefono, dir.calle, dir.numero, loc.nombre, loc.codigo_postal, es.hora_entrada, es.hora_salida, hab.nro_habitacion, hab.tipo, cons.descripcion, cons.monto, fc.cantidad\r\n"
        		+ "FROM tp_12c.estadia es,tp_12c.responsable_pago rp, tp_12c.factura fac, tp_12c.habitacion hab, tp_12c.consumo cons, tp_12c.direccion dir, tp_12c.localidad loc,  tp_12c.factura_consumo fc\r\n"
        		+ "WHERE fac.id_factura = fc.id_factura and cons.id_consumo = fc.id_consumo\r\n"
        		+ "AND fac.id_estadia = es.id_estadia\r\n"
        		+ "AND es.nro_habitacion = hab.nro_habitacion \r\n"
        		+ "AND fac.cuit_responsable_pago = rp.cuit\r\n"
        		+ "AND es.nro_habitacion = hab.nro_habitacion \r\n"
        		+ "AND dir.cuit_responsable_pago = rp.cuit\r\n"
        		+ "AND loc.id_localidad = dir.id_localidad\r\n"
        		+ "AND fac.id_factura ="+idFactura;
        
        try {
        	sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(consulta);
            
            Estadia estadia = new Estadia();
        	Habitacion habitacion=null ;
        	Direccion direccion = new Direccion();
        	Localidad localidad = new Localidad();
        	List<ConsumoFacturado> consumosFacurados = new ArrayList<ConsumoFacturado>();
        	ResponsablePago responsable = new ResponsablePago();

        	Boolean firstRow = true;
            while(resultado.next()) {
            	if(firstRow) {
            		factura.setId(resultado.getInt(1));
            		factura.setFecha(LocalDateTime.parse(resultado.getString(2), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            		
            		if(resultado.getString(3).equals("A"))   factura.setTipo(TipoFactura.A);
            		else factura.setTipo(TipoFactura.B);
            		
            		factura.setMonto_neto(resultado.getDouble(4));
            		factura.setMonto_total(resultado.getDouble(5));
            		factura.setIva(resultado.getDouble(6));
            		responsable.setRazon_social(resultado.getString(7));
            		responsable.setTelefono(resultado.getString(8));
            		direccion.setCalle(resultado.getString(9));
            		direccion.setNumero(resultado.getString(10));
            		localidad.setNombre(resultado.getString(11));
            		localidad.setCodigo_postal(resultado.getString(12));
            		estadia.setHora_entrada(LocalDateTime.parse(resultado.getString(13), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            		estadia.setHora_salida(LocalDateTime.parse(resultado.getString(14), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            		String TipoHabitacion = resultado.getString(16);
            		
            		if(TipoHabitacion.equals("I")) {
                        habitacion = new HabitacionIndividual();
                    }
                    else {
                        if(TipoHabitacion.equals("DE")) {
                           habitacion = new HabitacionDobleEstandar();

                        }
                        else {
                            if(TipoHabitacion.equals("DS")) {
                            	habitacion = new HabitacionDobleSuperior();                            }
                            else {
                                if(TipoHabitacion.equals("F")) {
                                	habitacion = new HabitacionFamily();                                }
                                else {
                                	habitacion = new HabitacionSuite();
                                }
                            }
                        }
                    }
            		habitacion.setNro(resultado.getInt(15));

            		firstRow=false;
            	ConsumoFacturado consumoFacturado = new ConsumoFacturado();
            	Consumo consumo = new Consumo();
            	consumo.setDescripcion(resultado.getString(18));
            	consumo.setMonto(resultado.getDouble(19));
            	
            	consumoFacturado.setFactura(factura);
            	consumoFacturado.setConsumo(consumo);
            	consumoFacturado.setCantidad(resultado.getInt(20));
            	consumosFacurados.add(consumoFacturado);
            	
            	
            }
            }
            factura.setConsumos(consumosFacurados);
            factura.setEstadia(estadia);
            factura.setResponsable_juridico(responsable);
            estadia.setHabitacion(habitacion);
            responsable.setDireccion(direccion);
            direccion.setLocalidad(localidad);
            
            
            
            System.out.println("Consulta realizada: "+consulta);
		
	} catch(SQLException e) {
		e.printStackTrace();
		
	}
    finally {
        if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
        if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
        if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
    }
        return factura;
	}
	
	public Factura recuperarFacturaRespFisico(Integer idFactura) {
		
		Factura factura = new Factura();
		
		Connection conexion = getConnection();
        Statement sentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT fac.id_factura, fac.fecha, fac.tipo, fac.monto_neto, fac.monto_total, fac.iva, pas.nombre, pas.apellido, pas.telefono, dir.calle, dir.numero, loc.nombre, loc.codigo_postal, es.hora_entrada, es.hora_salida, hab.nro_habitacion, hab.tipo, cons.descripcion, cons.monto, fc.cantidad, pi.posicion\r\n"
        		+ "FROM tp_12c.estadia es,tp_12c.pasajero pas, tp_12c.factura fac, tp_12c.habitacion hab, tp_12c.consumo cons, tp_12c.direccion dir, tp_12c.localidad loc, tp_12c.posicion_iva pi, tp_12c.factura_consumo fc\r\n"
        		+ "WHERE fac.id_factura = fc.id_factura and cons.id_consumo = fc.id_consumo\r\n"
        		+ "AND fac.id_estadia = es.id_estadia\r\n"
        		+ "AND es.nro_habitacion = hab.nro_habitacion \r\n"
        		+ "AND fac.id_pasajero = pas.id_pasajero\r\n"
        		+ "AND es.nro_habitacion = hab.nro_habitacion \r\n"
        		+ "AND dir.id_pasajero = pas.id_pasajero\r\n"
        		+ "AND loc.id_localidad = dir.id_localidad\r\n"
        		+ "AND pi.id_posicion_iva = pas.id_posicion_iva\r\n"
        		+ "AND fac.id_factura = "+idFactura;
        
        try {
        	sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(consulta);
            
            Estadia estadia = new Estadia();
        	Habitacion habitacion=null ;
        	Direccion direccion = new Direccion();
        	Localidad localidad = new Localidad();
        	List<ConsumoFacturado> consumosFacurados = new ArrayList<ConsumoFacturado>();
        	PosicionIVA posIva = new PosicionIVA();
        	Pasajero pasajero = new Pasajero();

        	Boolean firstRow = true;
            while(resultado.next()) {
            	if(firstRow) {
            		factura.setId(resultado.getInt(1));
            		factura.setFecha(LocalDateTime.parse(resultado.getString(2), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            		
            		if(resultado.getString(3).equals("A"))   factura.setTipo(TipoFactura.A);
            		else factura.setTipo(TipoFactura.B);
            		
            		factura.setMonto_neto(resultado.getDouble(4));
            		factura.setMonto_total(resultado.getDouble(5));
            		factura.setIva(resultado.getDouble(6));
            		pasajero.setNombre(resultado.getString(7));
            		pasajero.setApellido(resultado.getString(8));
            		pasajero.setTelefono(resultado.getString(9));
            		direccion.setCalle(resultado.getString(10));
            		direccion.setNumero(resultado.getString(11));
            		localidad.setNombre(resultado.getString(12));
            		localidad.setCodigo_postal(resultado.getString(13));
            		estadia.setHora_entrada(LocalDateTime.parse(resultado.getString(14), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            		estadia.setHora_salida(LocalDateTime.parse(resultado.getString(15), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            		String TipoHabitacion = resultado.getString(17);
            		
            		if(TipoHabitacion.equals("I")) {
                        habitacion = new HabitacionIndividual();
                    }
                    else {
                        if(TipoHabitacion.equals("DE")) {
                           habitacion = new HabitacionDobleEstandar();

                        }
                        else {
                            if(TipoHabitacion.equals("DS")) {
                            	habitacion = new HabitacionDobleSuperior();                            }
                            else {
                                if(TipoHabitacion.equals("F")) {
                                	habitacion = new HabitacionFamily();                                }
                                else {
                                	habitacion = new HabitacionSuite();
                                }
                            }
                        }
                    }
            		habitacion.setNro(resultado.getInt(16));
            		posIva.setPosicion(resultado.getString(21));
            	}
            	firstRow=false;
            	ConsumoFacturado consumoFacturado = new ConsumoFacturado();
            	Consumo consumo = new Consumo();
            	consumo.setDescripcion(resultado.getString(18));
            	consumo.setMonto(resultado.getDouble(19));
            	
            	consumoFacturado.setFactura(factura);
            	consumoFacturado.setConsumo(consumo);
            	consumoFacturado.setCantidad(resultado.getInt(20));
            	consumosFacurados.add(consumoFacturado);
            	
            	
            }
            factura.setConsumos(consumosFacurados);
            factura.setEstadia(estadia);
            factura.setResponsable_fisico(pasajero);
            estadia.setHabitacion(habitacion);
            pasajero.setPosicion_iva(posIva);
            pasajero.setDireccion(direccion);
            direccion.setLocalidad(localidad);
            
            
            
            System.out.println("Consulta realizada: "+consulta);
		
	} catch(SQLException e) {
		e.printStackTrace();
		
	}
    finally {
        if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
        if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
        if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
    }
        return factura;
	}
}
