package base_de_datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import enums.EstadoHabitacion;

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
	
}
