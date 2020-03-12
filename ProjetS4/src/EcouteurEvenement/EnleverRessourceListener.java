package EcouteurEvenement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Fenetre.FenetreEnleverRessource;
import Model.Entreprise;

/**
 * Cette classe permet l'interaction utilisateur machine avec la barre du menu
 * pour pouvoir enlever une ressource du projet selectionné
 * 
 * @author damien planchamp
 *
 */
public class EnleverRessourceListener implements ActionListener {
	Entreprise entreprise;
	
	public EnleverRessourceListener(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	/**
	 * creer une fenetre pour l'utilisateur pour 
	 * qu'il choisisse quelle ressource à enlever
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		new FenetreEnleverRessource(entreprise);
	}

}
