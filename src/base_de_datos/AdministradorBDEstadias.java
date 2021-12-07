package base_de_datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import clases.Estadia;
import clases.Habitacion;
import clases.HabitacionDobleEstandar;
import clases.HabitacionDobleSuperior;
import clases.HabitacionFamily;
import clases.HabitacionIndividual;
import clases.HabitacionSuite;
import clases.Pasajero;
import clases.dto.PasajeroDTO;
import enums.EstadoHabitacion;
import excepciones.OcupanteEnOtraHabitacionException;

public class AdministradorBDEstadias extends AdministradorBD {

	public AdministradorBDEstadias() {
		super();
	}
	
	public List<Estadia> recuperarEstadias(LocalDateTime fechaDesde, LocalDateTime fechaHasta){
		List<Estadia> salida = new ArrayList<Estadia>();
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM tp_12c.estadia es, tp_12c.habitacion ha "
												+ "WHERE es.nro_habitacion = ha.nro_habitacion "
												+ "AND (es.hora_entrada BETWEEN ? AND ? OR es.hora_salida BETWEEN ? AND ?)");
			
			sentencia.setObject(1, fechaDesde);
			sentencia.setObject(2, fechaHasta);
			sentencia.setObject(3, fechaDesde);
			sentencia.setObject(4, fechaHasta);
			
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				//Estadia
				Integer id = resultado.getInt(1);
				LocalDateTime hora_entrada = LocalDateTime.parse(resultado.getString(4),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime hora_salida = LocalDateTime.parse(resultado.getString(5),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));;
				Double monto = resultado.getDouble(6);
				Boolean pagado = resultado.getBoolean(7);
				
				//Habitacion
				Habitacion hab = null;
				Integer nro = resultado.getInt(8);
				Integer planta = resultado.getInt(9);
				Integer capacidad = resultado.getInt(10);
				Double costo_noche = resultado.getDouble(11);
				String tipo = resultado.getString(12);
				String estado = resultado.getString(13);
				Double descuento = resultado.getDouble(14);
				Integer diasParaDescuento = resultado.getInt(15);
				
				EstadoHabitacion estado_actual = null;
				
				if(estado.equals("D")) estado_actual = EstadoHabitacion.DISPONIBLE;
				else {
					if(estado.equals("R")) estado_actual = EstadoHabitacion.RESERVADA;
					else {
						if(estado.equals("O")) estado_actual = EstadoHabitacion.OCUPADA;
						else estado_actual = EstadoHabitacion.FUERA_DE_SERVICIO;
					}
				}
				
				if(tipo.equals("I")) {
					hab = new HabitacionIndividual(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
				}
				else {
					if(tipo.equals("DE")) {
						hab = new HabitacionDobleEstandar(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
					}
					else {
						if(tipo.equals("DS")) {
							hab = new HabitacionDobleSuperior(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
						}
						else {
							if(tipo.equals("F")) {
								hab = new HabitacionFamily(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
							}
							else {
								hab = new HabitacionSuite(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
							}
						}
					}
				}
				
				Estadia estadia = new Estadia(id,hora_entrada,hora_salida,monto,pagado);
				estadia.setHabitacion(hab);
				salida.add(estadia);
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
	
	public void registrarEstadia(Estadia estadia) throws OcupanteEnOtraHabitacionException{
		Connection conexion = getConnection();
        Statement sentencia = null;
        Statement sentencia2 = null;
        ResultSet resultado = null;
        ResultSet resultado2 = null;
        
        try {
        	//Verificar que los pasajeros no estén ocupando otra habitación
        	List<PasajeroDTO> pasajerosConOcupacionesActuales = new ArrayList<PasajeroDTO>();
        	
        	for(Pasajero ocupante : estadia.getPasajeros()) {
        		String consulta = "SELECT es.hora_entrada, es.hora_salida "
        						+ "FROM tp_12c.estadia es, tp_12c.hospeda_en he "
        						+ "WHERE es.id_estadia = he.id_estadia "
        						+ "AND he.id_pasajero = "+ocupante.getId();
        		
        		sentencia = conexion.createStatement();
        		resultado = sentencia.executeQuery(consulta);
        		
        		while(resultado.next()) {
        			LocalDateTime comienzo = LocalDateTime.parse(resultado.getString(1),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        			LocalDateTime fin = LocalDateTime.parse(resultado.getString(2),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        			
        			if(rangosSuperpuestos(comienzo,fin,estadia.getHora_entrada(),estadia.getHora_salida())) {
        				PasajeroDTO dto = new PasajeroDTO();
        				dto.setApellido(ocupante.getApellido());
        				dto.setNombre(ocupante.getNombre());
        				dto.setTipo(ocupante.getTipo_doc().getTipo());
        				dto.setNro_doc(ocupante.getNro_doc());
        				pasajerosConOcupacionesActuales.add(dto);
        			}
        		}
        		
        		System.out.println("Consulta realizada: "+consulta);
        	}
        	
        	if(pasajerosConOcupacionesActuales.size() > 0) throw new OcupanteEnOtraHabitacionException(pasajerosConOcupacionesActuales);
        	else {       		
        		//Buscar la proxima id
        		Integer id = null;
        		String consultaProxId = "SELECT nextval('tp_12c.sec_estadia')";
        		sentencia2 = conexion.createStatement();
        		resultado2 = sentencia2.executeQuery(consultaProxId);
        		
        		while(resultado2.next()) id = resultado2.getInt(1);
        		
        		System.out.println("Consulta realiazda: "+consultaProxId);
        		
        		conexion.setAutoCommit(false);
        		
        		//Estadia
        		Integer nro_habitacion = estadia.getHabitacion().getNro();
        		Integer id_responsable = estadia.getResponsable().getId();
        		String hora_entrada = estadia.getHora_entrada().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        		String hora_salida = estadia.getHora_salida().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        		
        		String insercionEstadia = "INSERT INTO tp_12c.estadia VALUES "
        				+ "("+id+","+nro_habitacion+","+id_responsable+",'"+hora_entrada+"','"+hora_salida+"',null,false)";
        		
        		sentencia2.executeUpdate(insercionEstadia);
        		
        		//Hospeda en
        		String insercionHospeda = "INSERT INTO tp_12c.hospeda_en VALUES ";
        		
        		for(int i=0;i<estadia.getPasajeros().size()-1;i++) {
        			Pasajero ocupante = estadia.getPasajeros().get(i);
        			Integer id_pasajero = ocupante.getId();
        			insercionHospeda+="(currval('tp_12c.sec_estadia'),"+id_pasajero+"),";
        		}
        		insercionHospeda+="("+estadia.getPasajeros().get(estadia.getPasajeros().size()-1).getId()+","+id+")";
        		
        		sentencia2.executeUpdate(insercionHospeda);
        		
        		String cambiarEstadoHabitacion = "UPDATE tp_12c.habitacion SET estado_actual = 'O' WHERE nro_habitacion ="+nro_habitacion;
        		if(estadia.getHora_entrada().toLocalDate().equals(LocalDate.now())) { //Si es el dia actual, cambiar el estado de la habitacion a ocupada
        			sentencia2.execute(cambiarEstadoHabitacion);
        		}
        		
        		conexion.commit();
        		
        		System.out.println("Inserción realizada: "+insercionEstadia);
        		System.out.println("Inserción realizada: "+insercionHospeda);
        		if(estadia.getHora_entrada().toLocalDate().equals(LocalDate.now())) System.out.println("Actualización realizada: "+cambiarEstadoHabitacion);
        		
        	}
        	
        }catch(SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
        finally {
            if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
            if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
            if(resultado2!=null) try { resultado2.close();} catch(SQLException e) {e.printStackTrace();}
            if(sentencia2!=null) try { sentencia2.close();} catch(SQLException e) {e.printStackTrace();}
            if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
        }
	}
	
	private Boolean rangosSuperpuestos(LocalDateTime c, LocalDateTime f, LocalDateTime he, LocalDateTime hs) {
		if(he.isBefore(c) && hs.isBefore(c)) return false;
		if(he.isAfter(f) && hs.isAfter(f)) return false;
		
		return true;
	}
}
