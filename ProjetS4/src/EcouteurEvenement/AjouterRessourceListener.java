package EcouteurEvenement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Fenetre.FenetreAjouterRessource;
import Model.Entreprise;

/**
 * Cette classe permet l'interaction utilisateur machine avec la barre du menu
 * pour pouvoir ajouter une ressource du projet selectionné
 * 
 * @author damien planchamp
 *
 */
public class AjouterRessourceListener implements ActionListener {
	private Entreprise entreprise;
	
	public AjouterRessourceListener(Entreprise entreprise) {
		this.entreprise = entreprise;
	}
	
	/**
	 * creer une fenetre pour l'utilisateur pour 
	 * qu'il choisisse quelle ressource à ajouter
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		new FenetreAjouterRessource(entreprise);
	}

}
