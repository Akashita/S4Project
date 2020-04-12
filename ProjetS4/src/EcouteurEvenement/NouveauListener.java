package EcouteurEvenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Fenetre.FenetreNouveauProjet;
import Fenetre.FenetreNouvelleActivite;
import Fenetre.FenetreNouvelleRessource;
import Fenetre.FenetrePrincipale;
import Model.Entreprise;


/**
 * Cette classe permet l'interaction utilisateur machine avec la barre du menu
 * pour pouvoir creer une nouvelle ressource, projet ou activité
 * 
 * @author damien planchamp
 *
 */

public class NouveauListener implements ActionListener{
	Entreprise entreprise;
	int choix;
	
	public NouveauListener(Entreprise entreprise, int choix) {
		this.entreprise = entreprise;
		this.choix = choix;
	}
	
	/**
	 * creer une fenetre qui laissera le choix à l'utlisateur
	 * entre la creation d'une ressource ou d'un projet
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (choix == FenetrePrincipale.NouveauProjet) {
			new FenetreNouveauProjet(entreprise);
		}
		if (choix == FenetrePrincipale.NouvelleRessource) {
			new FenetreNouvelleRessource(entreprise);
		}
		if (choix == FenetrePrincipale.NouvelleActivite) {
			if (entreprise.getProjetSelectionner() != null) {
				new FenetreNouvelleActivite(entreprise);
			}
			else {
				JOptionPane.showMessageDialog(null, "Aucun projet selectionné", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
	}

}
