package componentes_swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CampoJMascara extends CampoJ{

	private String placeholder = "";
    private Color phColor= new Color(0,0,0);
    private boolean band = true;
	
	public CampoJMascara() {
		super();
		setVisible(true);
		setFont(new Font("Microsoft Tai Le",Font.PLAIN,12));
		
		getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                band = (getText().length()>0) ? false:true ;
                setBackground(Color.WHITE);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                band = false;
                setBackground(Color.WHITE);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
            	setBackground(Color.WHITE);
            }
        });   
	}
	
	public void setPlaceholder(String placeholder){
        this.placeholder=placeholder;
    }

    public String getPlaceholder(){
        return placeholder;
    }

    public Color getPhColor() {
        return phColor;
    }

    public void setPhColor(Color phColor) {
        this.phColor = phColor;
    } 
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.setColor( new Color(phColor.getRed(),phColor.getGreen(),phColor.getBlue(),90));
        g.drawString((band)?placeholder:"",
                     getMargin().left+5,
                     (getSize().height)/2 + getFont().getSize()/2 - 3);
      }
	
}
