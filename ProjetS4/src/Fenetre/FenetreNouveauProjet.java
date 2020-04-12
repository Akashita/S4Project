package Fenetre;
import java.awt.BorderLayout;

import Model.Entreprise;
import Panel_Fenetre.PanelNouveauProjet;

/**
 * Cette fenetre affiche à l'utilisateur les données à rentrer pour
 * creer un nouveau projet 
 * 
 * @author damien planchamp
 *
 */
public class FenetreNouveauProjet extends FenetreModal{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FenetreNouveauProjet(Entreprise entreprise) {
		super(entreprise, "Creation projet", 400, 300);
		panelInterface = new PanelNouveauProjet(entreprise, this);
		this.setLayout(new BorderLayout());
		this.add(panelInterface, BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);
	}
}
