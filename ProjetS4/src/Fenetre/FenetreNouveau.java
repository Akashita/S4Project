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

	public FenetreNouveau(Entreprise entreprise) {
		
		this.addWindowListener(new FermerFenetre(this));
		
		choix(entreprise);

	}
	
	private void choix(Entreprise entreprise) {
	    String[] choix = {"Ressource", "Projet"};
	    String nom = (String)JOptionPane.showInputDialog(null, 
	      "Type de votre nouveauté",
	      "Nouveau",
	      JOptionPane.QUESTION_MESSAGE,
	      null,
	      choix,
	      choix[0]);
	    
	    if(nom == "Projet") {
	    	new FenetreNouveauProjet(entreprise);
	    }
	    if(nom == "Ressource") {

	    	new FenetreNouvelleRessource(entreprise);
	    }
	    
	}
}
