package EcouteurEvenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Fenetre.FenetreModifierActivite;
import Fenetre.FenetreModifierProjet;
import Fenetre.FenetrePrincipale;
import Model.Entreprise;


/**
 * Cette classe permet l'interaction utilisateur machine avec la barre du menu
 * pour pouvoir creer une nouvelle ressource, projet ou activité
 * 
 * @author damien planchamp
 *
 */

public class ModifierListener implements ActionListener{
	Entreprise entreprise;
	int choix;
	
	public ModifierListener(Entreprise entreprise, int choix) {
		this.entreprise = entreprise;
		this.choix = choix;
	}
	
	/**
	 * creer une fenetre qui laissera le choix à l'utlisateur
	 * entre la creation d'une ressource ou d'un projet
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (choix == FenetrePrincipale.ModifierProjet) {
			if (entreprise.getProjetSelectionner() != null) {
				new FenetreModifierProjet(entreprise);
			}
			else {
				JOptionPane.showMessageDialog(null, "Aucun projet selectionné", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		if (choix == FenetrePrincipale.ModifierActivite) {
			if (entreprise.getProjetSelectionner() != null) {
				new FenetreModifierActivite(entreprise);
			}
			else {
				JOptionPane.showMessageDialog(null, "Aucun projet selectionné", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		if (choix == FenetrePrincipale.ModifierRessource) {
		}
	}

}
