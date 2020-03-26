package EcouteurEvenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Fenetre.FenetreNouveau;
import Model.Entreprise;
import Panel.PanelProjet;
import Panel.PanelRessource;

/**
 * Cette classe permet l'interaction utilisateur machine avec la barre du menu
 * pour pouvoir creer une nouvelle ressource ou projet
 * 
 * @author damien planchamp
 *
 */

public class NouveauListener implements ActionListener{
	Entreprise entreprise;
	PanelProjet pp;
	PanelRessource pr;
	
	public NouveauListener(Entreprise entreprise, 
			PanelProjet pp, PanelRessource pr) {
		this.entreprise = entreprise;
		this.pp = pp;
		this.pr = pr;
	}
	
	/**
	 * creer une fenetre qui laissera le choix à l'utlisateur
	 * entre la creation d'une ressource ou d'un projet
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		new FenetreNouveau(entreprise, pp, pr);
	}

}
