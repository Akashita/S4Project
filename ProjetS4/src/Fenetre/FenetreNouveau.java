package Fenetre;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.Entreprise;
import Panel.PanelProjet;
import Panel.PanelRessource;

/**
 * Cette fenetre indique le choix à l'utilisateur entre la creation 
 * d'une ressource ou d'un projet
 * @author damienplanchamp
 *
 */
public class FenetreNouveau extends JFrame{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FenetreNouveau(Entreprise entreprise, 
			PanelProjet pp, PanelRessource pr) {
		
		this.addWindowListener(new FermerFenetre(this));
		
		choix(entreprise, pp, pr);

	}
	
	private void choix(Entreprise entreprise, 
			PanelProjet pp, PanelRessource pr) {
	    String[] choix = {"Ressource", "Projet"};
	    String nom = (String)JOptionPane.showInputDialog(null, 
	      "Type de votre nouveauté",
	      "Nouveau",
	      JOptionPane.QUESTION_MESSAGE,
	      null,
	      choix,
	      choix[0]);
	    
	    if(nom == "Projet") {
	    	new FenetreNouveauProjet(entreprise, pp);
	    }
	    if(nom == "Ressource") {
	    	new FenetreNouvelleRessource(entreprise);
	    }
	    
	}
}
