package Panel;
import java.awt.*;
import java.util.*;
import javax.swing.*;

import Model.Entreprise;

public class PanelPrincipal extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;

	public static final Color BLEU1 = new Color(32,86,174),
			BLEU2 = new Color(212,220,239),
			BLEU3 = new Color(245,246,251),
			GRIS1 = new Color(231,234,235),
			GRIS2 = Color.GRAY,
		    BLANC = new Color(255,255,255),
	        NOIR = new Color(0,0,0);
	
	private Entreprise entreprise;
	
	public PanelPrincipal (Entreprise entreprise) {
		this.entreprise = entreprise;
		entreprise.addObserver(this);
        this.setLayout(new BorderLayout());
        this.setBackground(BLANC);
	}	
	
	private void afficheInterface() {
		
	}
		
	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
        this.add(new PanelRessource(entreprise), BorderLayout.WEST);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new PanelProjet(entreprise), BorderLayout.NORTH);
        panel.add(new PanelInfoProjet(entreprise), BorderLayout.CENTER);
        this.add(panel, BorderLayout.CENTER);
		this.revalidate();	
	}
}
