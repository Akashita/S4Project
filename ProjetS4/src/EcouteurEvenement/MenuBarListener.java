package EcouteurEvenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Ressource.Personne;
import Fenetre.FenetreInfoRessource;
import Fenetre.FenetreModal;
import Fenetre.FenetrePrincipale;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;


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
		Personne user = entreprise.getUser();
		boolean estAdmin = user.estAdmin();
		
		if (choix == FenetrePrincipale.NouvelleRessource || choix == FenetrePrincipale.NouveauDomaine) {	
			if (estAdmin) { //fonction autoris� pour les admin uniquement
				creerFenetre();
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Vous n'avez pas la permission d'acceder � cette fonctionnalit�", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}					
		}	
		if (choix == FenetrePrincipale.NouveauProjet) {
			if (entreprise.getListePersonneEntreprise().size()>0) {
				if (estAdmin) { //fonction autoris� pour les admin uniquement
					creerFenetre();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Vous n'avez pas la permission d'acceder � cette fonctionnalit�", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}	
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veuillez creer une personne", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		
			
			
		if (choix == FenetrePrincipale.ModifierProjet || choix == FenetrePrincipale.NouvelleActivite) {
			Projet p = entreprise.getProjetSelectionner();
			if (p != null) {
				if (estAdmin || user.estChef(p)) { //fonction autoris� pour les admin et chef de ce projet
					creerFenetre();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Vous n'avez pas la permission d'acceder � cette fonctionnalit�", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}					
			}
			else {
			    JOptionPane.showMessageDialog(null, "Aucun projet selectionn�", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
			
		
		

		if (choix == FenetrePrincipale.ModifierActivite || choix == FenetrePrincipale.AjouterRessource || choix == FenetrePrincipale.EnleverRessource) {
			Projet p = entreprise.getProjetSelectionner();
			if (p != null) {
				Activite a = entreprise.getActiviteSelectionner();
				if (a != null) {
					if (estAdmin || user.estChef(p)) {//fonction autoris� pour les admin et chef de ce projet
						creerFenetre();
					}
					else {
				    	JOptionPane.showMessageDialog(null, "Vous n'avez pas la permission d'acceder � cette fonctionnalit�", "Erreur", JOptionPane.ERROR_MESSAGE);			
					}					
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Aucune activit� selectionn�e", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}

			}
			else {
		    	JOptionPane.showMessageDialog(null, "Aucun projet selectionn�", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}

		
		
		if (choix == FenetrePrincipale.InformationCompte) {
			new FenetreInfoRessource(entreprise, entreprise.getUser());
		}
		if (choix == FenetrePrincipale.Deconnexion) {
			String texte = "<html>�tes vous sur de vouloir vous deconnecter ?</html>";
			int res = JOptionPane.showConfirmDialog(null, texte, "Attention", JOptionPane.YES_NO_OPTION);			
			if (res == 0) { //0 = yes
				entreprise.changementUtilisateur();
			}
		}
	}
	
	private void creerFenetre() {
		new FenetreModal(entreprise, choix);
	}
	
	
}
