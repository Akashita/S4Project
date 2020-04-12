package EcouteurEvenement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Fenetre.FenetreAjouterRessource;
import Fenetre.FenetreEnleverRessource;
import Fenetre.FenetrePrincipale;
import Model.Entreprise;

/**
 * Cette classe permet l'interaction utilisateur machine avec la barre du menu
 * pour pouvoir ajouter une ressource à l'activité selectionner
 * 
 * @author damien planchamp
 *
 */
public class AjouterEnleverRessourceListener implements ActionListener {
	private Entreprise entreprise;
	private int choix;
	
	public AjouterEnleverRessourceListener(Entreprise entreprise, int choix) {
		this.entreprise = entreprise;
		this.choix = choix;
	}
	
	/**
	 * creer une fenetre pour l'utilisateur pour 
	 * qu'il choisisse quelle ressource à ajouter
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (entreprise.getProjetSelectionner() != null) {
			if (entreprise.getActiviteSelectionner() != null) {
				switch (choix) {
				case FenetrePrincipale.AjouterRessource: new FenetreAjouterRessource(entreprise);
				break;
				case FenetrePrincipale.EnleverRessource: new FenetreEnleverRessource(entreprise);
				break;
				}	
			}
			else {
				JOptionPane.showMessageDialog(null, "Aucune activité selectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Aucun projet selectionné", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}

}
