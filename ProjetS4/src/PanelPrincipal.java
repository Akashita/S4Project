import java.awt.*;
import java.util.*;
import javax.swing.*;

public class PanelPrincipal extends JPanel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	
	public PanelPrincipal (Entreprise entreprise) {
		this.entreprise = entreprise;
		this.setSize(this.getWidth(), this.getHeight());
		entreprise.addObserver(this);
	}
	
	public void paint(Graphics g){
		entreprise.dessineToi(g);
		
		//this.add(entreprise.ajoutProjet(this));
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.repaint();		
	}

}
