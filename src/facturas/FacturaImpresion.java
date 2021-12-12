package facturas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import clases.dto.FacturaDTO;
import clases.gestores.GestorFacturas;
import componentes_swing.*;

public class FacturaImpresion extends JDialog implements Printable{
	
	private JPanel panelImpresion;
	
	public FacturaImpresion(JFrame ventanaPadre,JPanel factura, FacturaDTO facturaDTO) {
		super(ventanaPadre,"Imprimir factura");
		
		JPanel panelPrincipal = new JPanel(new GridBagLayout());
		panelPrincipal.setBackground(Color.WHITE);
		JPanel panelBotones = new JPanel(new GridBagLayout());
		panelBotones.setBackground(Color.WHITE);
		BotonJ imprimir = new BotonJ("Imprimir");
		BotonJ salir = new BotonJ("Salir");
		
		imprimir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imprimirFactura(e);
				GestorFacturas gestor = GestorFacturas.getInstance();
				gestor.imprimirFactura(facturaDTO.getId_factura().get());
			}			
		});
		
		salir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ventanaPadre.setEnabled(true);
				ventanaPadre.setVisible(true);
			}			
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ventanaPadre.setEnabled(true);
				ventanaPadre.setVisible(true);
			}
		});
		
		panelImpresion = new JPanel(new GridBagLayout());
		
		GridBagConstraints cons = new GridBagConstraints();
		
		panelImpresion = factura;
		
		setContentPane(panelPrincipal);
		cons.gridy = 0;
		cons.insets = new Insets(10,10,10,10);
		cons.weightx = 0.1;
		cons.weighty = 0.1;
		cons.fill = GridBagConstraints.BOTH;
		panelPrincipal.add(panelImpresion,cons);
		cons.weightx = 0;
		cons.weighty = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.gridy = 1;
		panelPrincipal.add(panelBotones,cons);
		
		cons.gridy = 0;
		cons.insets = new Insets(0, 10, 5, 10);
		panelBotones.add(imprimir,cons);
		
		cons.gridx = 1;
		panelBotones.add(salir,cons);
	}
	
	private void imprimirFactura(ActionEvent e) {
		try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(this);
            job.printDialog();
            job.print();
        } catch (PrinterException ex) { }    
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) return NO_SUCH_PAGE;
      
		Graphics2D g2d = (Graphics2D)graphics;
        //Punto donde empezará a imprimir dentro la pagina (100, 50)
        g2d.translate(  pageFormat.getImageableX()+25,
                        pageFormat.getImageableY()+25);
        g2d.scale(1,1); //Reducción de la impresión al 50%
        
        panelImpresion.printAll(graphics);
        
        return PAGE_EXISTS;
	}

	
}
