package EcouteurEvenement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Fenetre.FenetreNouvelleRessource;
import Model.Entreprise;

public class NouvelleRessourceListener implements ActionListener {
	Entreprise entreprise;
	
	public NouvelleRessourceListener (Entreprise entreprise) {
		this.entreprise = entreprise;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		new FenetreNouvelleRessource(entreprise);
		// TODO Auto-generated method stub

	}

}
