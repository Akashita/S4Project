package EcouteurEvenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Fenetre.FenetreNouvelleActivite;
import Model.Entreprise;

/**
 * Cette classe permet l'interaction utilisateur machine avec la barre du menu
 * pour pouvoir creer une activité au projet selectionner
 * 
 * @author damien planchamp
 *
 */
public class nouvelleActiviteListener implements ActionListener {
	private Entreprise entreprise;
	
	public nouvelleActiviteListener(Entreprise entreprise) {
		this.entreprise = entreprise;
	}
	
	/**
	 * creer une fenetre pour l'utilisateur pour 
	 * qu'il choisisse quelle ressource à ajouter
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
			new FenetreNouvelleActivite(entreprise);
		
	}
}
