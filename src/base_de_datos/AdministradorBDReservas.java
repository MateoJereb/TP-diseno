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

import clases.Habitacion;
import clases.HabitacionDobleEstandar;
import clases.HabitacionDobleSuperior;
import clases.HabitacionFamily;
import clases.HabitacionIndividual;
import clases.HabitacionSuite;
import clases.Reserva;
import enums.EstadoHabitacion;

public class AdministradorBDReservas extends AdministradorBD {

	public AdministradorBDReservas() {
		super();
	}
	
	public List<Reserva> recuperarReservas(LocalDate fechaDesde, LocalDate fechaHasta){
		List<Reserva> salida = new ArrayList<Reserva>();
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM tp_12c.reserva re, tp_12c.habitacion ha "
												+ "WHERE re.nro_habitacion = ha.nro_habitacion "
												+ "AND (re.entrada BETWEEN ? AND ? OR re.salida BETWEEN ? AND ?)");
			
			sentencia.setObject(1, fechaDesde);
			sentencia.setObject(2, fechaHasta);
			sentencia.setObject(3, fechaDesde);
			sentencia.setObject(4, fechaHasta);
			
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				//Reserva
				Integer id = resultado.getInt(1);
				LocalDate diaEntrada = LocalDate.parse(resultado.getString(3),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				LocalDate diaSalida = LocalDate.parse(resultado.getString(4),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				String nombre = resultado.getString(5);
				String apellido = resultado.getString(6);
				String telefono = resultado.getString(7);
				
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
				
				Reserva res = new Reserva(id,diaEntrada,diaSalida,nombre,apellido,telefono);
				res.setHabitacion(hab);
				salida.add(res);
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
