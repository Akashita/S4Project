package Panel;
import java.awt.*;
import java.util.*;
import javax.swing.*;

import Model.Entreprise;
import Model.Projet;
import Ressource.Calculateur;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;

public class PanelPrincipal extends JPanel implements Observer{

	private Entreprise entreprise;
	private PanelRessource panelRessource;
	private PanelProjet panelProjet;
	
	public PanelPrincipal (Entreprise entreprise,
			PanelRessource panelRessource, PanelProjet panelProjet) {
		this.entreprise = entreprise;
		this.panelRessource = panelRessource;
		this.panelProjet = panelProjet;
        this.setLayout(new BorderLayout());
		this.setSize(this.getWidth(), this.getHeight());
		entreprise.addObserver(this);
	}	
		
	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		panelProjet.afficherProjet();
		panelRessource.afficherRessource();
		this.add(new PanelInfoProjet(entreprise), BorderLayout.WEST);
		this.repaint();	
	}


}
