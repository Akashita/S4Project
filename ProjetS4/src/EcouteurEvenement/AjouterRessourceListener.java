package EcouteurEvenement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Fenetre.FenetreAjouterRessource;
import Model.Entreprise;

public class AjouterRessourceListener implements ActionListener {
	private Entreprise entreprise;
	private String type;
	
	public AjouterRessourceListener(Entreprise entreprise) {
		this.entreprise = entreprise;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		new FenetreAjouterRessource(entreprise);
	}

}
