package componentes_swing.retroalimentacion;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clase_app.App;
import componentes_swing.EtiquetaJ;

public class PresioneUnaTecla extends JDialog{

	private JPanel panel;
	private ImageIcon imagen;
	private EtiquetaJ icono;
	private EtiquetaJ mensaje;
	private EtiquetaJ textoFijo = new EtiquetaJ("PRESIONE UNA TECLA PARA CONTINUAR...");
	
	public PresioneUnaTecla(JFrame ventanaPadre, String textoMensaje) {
		
		super(ventanaPadre,"Presione una tecla");
		panel = new JPanel(new GridBagLayout());
		setBackground(Color.WHITE);
		setContentPane(panel);
		
		GridBagConstraints cons = new GridBagConstraints();
		
		imagen = new ImageIcon("resources\\presioneUnaTecla.png"); 
		icono = new EtiquetaJ();
		icono.setIcon(imagen);
		mensaje = new EtiquetaJ(textoMensaje);
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.WEST;
		cons.insets = new Insets(10,10,10,10);
		panel.add(icono,cons);
		
		cons.gridx = 1;
		cons.gridwidth = 2;
		cons.anchor = GridBagConstraints.CENTER;
		cons.weightx = 0.1;
		panel.add(mensaje,cons);
		
		cons.gridy = 1;
		cons.insets = new Insets(10,10,30,20);
		panel.add(textoFijo,cons);
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e){
				dispose();
				App.getVentana().setEnabled(true);
				App.getVentana().setVisible(true);
			}
		});
	}
	
}
