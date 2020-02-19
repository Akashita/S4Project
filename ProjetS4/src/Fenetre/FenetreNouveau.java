package Fenetre;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.Entreprise;
import Panel.PanelProjet;
import Panel.PanelRessource;

public class FenetreNouveau extends JFrame{
		
	public FenetreNouveau(Entreprise entreprise, 
			PanelProjet pp, PanelRessource pr) {
		
		this.addWindowListener(new FermerFenetre(this));
		
		choix(entreprise, pp, pr);

	}
	
	private void choix(Entreprise entreprise, 
			PanelProjet pp, PanelRessource pr) {
	    String[] choix = {"Projet", "Ressource"};
	    String nom = (String)JOptionPane.showInputDialog(null, 
	      "Type de votre nouveauté",
	      "Nouveau",
	      JOptionPane.QUESTION_MESSAGE,
	      null,
	      choix,
	      choix[1]);
	    
	    if(nom == "Projet") {
	    	new FenetreNouveauProjet(entreprise, pp);
	    }
	    if(nom == "Ressource") {
	    	new FenetreNouvelleRessource(entreprise);
	    }
	    
	}
}
