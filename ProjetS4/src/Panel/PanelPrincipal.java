package Panel;
import java.awt.*;
import java.util.*;
import javax.swing.*;

import Model.Entreprise;

/**
 * Gere les 4 panels principaux:
 * - panel ressource
 *  - panel tache
 *  - panel projet
 *  - panel information projet
 * @author Damien
 *
 */
public class PanelPrincipal extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;

	public static final Color BLEU1 = new Color(32,86,174),
			BLEU2 = new Color(212,220,239),
			BLEU3 = new Color(245,246,251),
			GRIS1 = new Color(231,234,235),
			GRIS2 = Color.GRAY,
		    BLANC = new Color(255,255,255),
			NOIR = new Color(0,0,0),
			INDISPO = new Color(179,62,197),
			NOTIFICATION = new Color(255,0,0),
			CONGE = Color.RED,
			REUNION = Color.ORANGE,
			ACCEPTER = Color.GREEN,
			REFUSER = Color.RED;
	
	public static final int PANELRESSOURCE = 0, PANELTACHE = 1, PANELPROJET = 2, CENTRE = 3, ALL = Integer.MAX_VALUE;
	
	
	private Entreprise entreprise;
	
	public PanelPrincipal (Entreprise entreprise) {
		this.entreprise = entreprise;
		entreprise.addObserver(this);
        this.setLayout(new BorderLayout());
        this.setBackground(BLANC);
	}	
		
	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
        this.add(new PanelRessource(entreprise), BorderLayout.WEST);
        this.add(new PanelTache(entreprise), BorderLayout.EAST);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new PanelProjet(entreprise), BorderLayout.NORTH);
        panel.add(new PanelInfoProjet(entreprise), BorderLayout.CENTER);
        
        this.add(panel, BorderLayout.CENTER);
        
        
        if (entreprise.getActiviteSelectionner() != null) {
    		this.setFocusable(true);
    		this.requestFocus();
        }
		
		this.revalidate();	
	}
}
