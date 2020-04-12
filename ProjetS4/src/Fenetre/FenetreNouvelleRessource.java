package Fenetre;
import java.awt.BorderLayout;

import Model.Entreprise;
import Panel_Fenetre.PanelNouvelleRessource;

/**
 * Cette fenetre affiche à l'utilisateur les données à rentrer pour
 * creer une nouvelle ressource (Personne, Salle, Calculateur)
 * 
 * @author damien planchamp
 *
 */
public class FenetreNouvelleRessource extends FenetreModal{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FenetreNouvelleRessource(Entreprise entreprise) {
		super(entreprise, "Creation ressource", 400, 300);
		panelInterface = new PanelNouvelleRessource(entreprise, this);
		this.setLayout(new BorderLayout());
		this.add(panelInterface, BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);
	}
}
