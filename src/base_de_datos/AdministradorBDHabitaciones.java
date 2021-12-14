package base_de_datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clases.*;
import clases.dto.HabitacionIndividualDTO;
import enums.EstadoHabitacion;


public class AdministradorBDHabitaciones extends AdministradorBD {

	public AdministradorBDHabitaciones() {
		super();
	}
	
	public Habitacion habitacionPorNro(Integer nroBusqueda) {
		Habitacion salida = null;
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM tp_12c.habitacion WHERE nro_habitacion = ?");
			sentencia.setObject(1, nroBusqueda);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Integer nro = resultado.getInt(1);
				Integer planta = resultado.getInt(2);
				Integer capacidad = resultado.getInt(3);
				Double costo_noche = resultado.getDouble(4);
				String tipo = resultado.getString(5);
				String estado = resultado.getString(6);
				Double descuento = resultado.getDouble(7);
				Integer diasParaDescuento = resultado.getInt(8);
				
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
					salida = new HabitacionIndividual(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
				}
				else {
					if(tipo.equals("DE")) {
						salida = new HabitacionDobleEstandar(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
						
					}
					else {
						if(tipo.equals("DS")) {
							salida = new HabitacionDobleSuperior(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
						}
						else {
							if(tipo.equals("F")) {
								salida = new HabitacionFamily(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
							}
							else {
								salida = new HabitacionSuite(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
							}
						}
					}
				}
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
	
	public List<Habitacion> recuperarHabitaciones(){
		List<Habitacion> salida = new ArrayList<Habitacion>();
		
		Connection conexion = getConnection();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT * FROM tp_12c.habitacion ORDER BY 1");
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Habitacion hab = null;
				Integer nro = resultado.getInt(1);
				Integer planta = resultado.getInt(2);
				Integer capacidad = resultado.getInt(3);
				Double costo_noche = resultado.getDouble(4);
				String tipo = resultado.getString(5);
				String estado = resultado.getString(6);
				Double descuento = resultado.getDouble(7);
				Integer diasParaDescuento = resultado.getInt(8);
				
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
					salida.add(hab);
				}
				else {
					if(tipo.equals("DE")) {
						hab = new HabitacionDobleEstandar(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
						salida.add(hab);
					}
					else {
						if(tipo.equals("DS")) {
							hab = new HabitacionDobleSuperior(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
							salida.add(hab);
						}
						else {
							if(tipo.equals("F")) {
								hab = new HabitacionFamily(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
								salida.add(hab);
							}
							else {
								hab = new HabitacionSuite(nro,planta,capacidad,costo_noche,estado_actual,descuento,diasParaDescuento);
								salida.add(hab);
							}
						}
					}
				}
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
