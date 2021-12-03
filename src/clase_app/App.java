package clase_app;

import java.awt.Image;
import java.time.LocalDate;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import clases.dto.*;
import componentes_swing.ventanas.*;

public class App {

	private static JFrame ventana = new JFrame();
	
	public static void main(String[] args) {
		
		//menuPrincipal();
		
		ocuparHabitacion(Optional.ofNullable(null));
		
		/*HabitacionDTO hab = new HabitacionFamilyDTO();
		hab.setNro(5);
		PasajeroDTO p = new PasajeroDTO();
		p.setId(1);
		p.setApellido("Jereb");
		p.setNombre("Mateo");
		p.setTipo("DNI");
		p.setNro_doc("43287075");
		cargarPasajeros(hab,null,null,Optional.of(p));*/
		
		ImageIcon icono = new ImageIcon("resources\\logo.png");
		Image imagen = icono.getImage();
		ventana.setIconImage(imagen);
		ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ventana.setSize(1000,600);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);

	}
	
	public static JFrame getVentana() {
		return ventana;
	}
	
	public static void logueo() {
		
	}
	
	public static void menuPrincipal() {
		MenuPrincipal panel = new MenuPrincipal();
		ventana.setContentPane(panel);
		ventana.setTitle("Hotel Premier - Menú Principal");
		ventana.revalidate();
		ventana.repaint();
	}
	
	public static void gestionarPasajeros() {
		GestionarPasajeros panel = new GestionarPasajeros();
		ventana.setContentPane(panel);
		ventana.setTitle("Hotel Premier - Gestionar Pasajeros");
		ventana.revalidate();
		ventana.repaint();
	}
	
	public static void gestionarPasajeros(String apellido, String nombre, TipoDocumentoDTO tipoDoc, String nroDoc) {
		GestionarPasajeros panel = new GestionarPasajeros(apellido,nombre,tipoDoc,nroDoc);
		ventana.setContentPane(panel);
		ventana.setTitle("Hotel Premier - Gestionar Pasajeros");
		ventana.revalidate();
		ventana.repaint();
	}
	
	public static void darAltaPasajero(String apellido, String nombre, TipoDocumentoDTO tipoDoc, String nroDoc) {
		DarAltaPasajero panel = new DarAltaPasajero(apellido,nombre,tipoDoc,nroDoc);
		ventana.setContentPane(panel);
		ventana.setTitle("Hotel Premier - Dar de Alta Pasajero");
		ventana.revalidate();
		ventana.repaint();
	}
	
	public static void ocuparHabitacion(Optional<PasajeroDTO> responsable) {
		OcuparHabitacion panel = new OcuparHabitacion(responsable);
		ventana.setContentPane(panel);
		ventana.setTitle("Hotel Premier - Reservar Habitación");
		ventana.revalidate();
		ventana.repaint();
	}
	
	public static void cargarPasajeros(HabitacionDTO habitacion, LocalDate fechaDesde, LocalDate fechaHasta, Optional<PasajeroDTO> responsable) {
		CargarPasajeros panel = new CargarPasajeros(habitacion,fechaDesde,fechaHasta,responsable);
		ventana.setContentPane(panel);
		ventana.setTitle("Hotel Premier - Cargar Pasajeros");
		ventana.revalidate();
		ventana.repaint();
	}

}
