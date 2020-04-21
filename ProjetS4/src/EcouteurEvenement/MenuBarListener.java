package EcouteurEvenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Ressource.Ressource;
import Fenetre.FenetreModal;
import Fenetre.FenetrePrincipale;
import Model.Entreprise;


/**
 * Cette classe permet l'interaction utilisateur machine avec la barre du menu
 * 
 * @author damien planchamp
 *
 */

public class MenuBarListener implements ActionListener{
	Entreprise entreprise;
	int choix;
	
	public MenuBarListener(Entreprise entreprise, int choix) {
		this.entreprise = entreprise;
		this.choix = choix;
	}
	
	/**
	 * creer une fenetre qui laissera le choix à l'utlisateur
	 * entre la creation d'une ressource ou d'un projet
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (choix == FenetrePrincipale.NouvelleRessource || choix == FenetrePrincipale.NouveauDomaine) {
			creerFenetre();
		}
		
		if (choix == FenetrePrincipale.NouveauProjet) {
			if (entreprise.getListeRessourceType(Ressource.PERSONNE).size()>0) {
				creerFenetre();
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veuillez creer une personne", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		
		if (choix == FenetrePrincipale.NouvelleActivite || choix == FenetrePrincipale.ModifierProjet) {
			if (entreprise.getProjetSelectionner() != null) {
				creerFenetre();
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Aucun projet selectionné", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}

		if (choix == FenetrePrincipale.ModifierActivite || choix == FenetrePrincipale.AjouterRessource || choix == FenetrePrincipale.EnleverRessource) {
			if (entreprise.getActiviteSelectionner() != null) {
				creerFenetre();
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Aucun activité selectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
	}
	
	private void creerFenetre() {
		new FenetreModal(entreprise, choix);
	}
}
