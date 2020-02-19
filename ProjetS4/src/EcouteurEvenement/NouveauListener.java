package EcouteurEvenement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Fenetre.FenetreNouveau;
import Model.Entreprise;
import Panel.PanelProjet;
import Panel.PanelRessource;

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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new FenetreNouveau(entreprise, pp, pr);
	}

}
