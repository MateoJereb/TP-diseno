package clase_app;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import componentes_swing.ventanas.*;

public class App {

	private static JFrame ventana = new JFrame();
	
	public static void main(String[] args) {
		
		//menuPrincipal();
		gestionarPasajeros();
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

}
