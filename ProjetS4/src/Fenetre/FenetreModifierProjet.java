package Fenetre;
import java.awt.BorderLayout;

import Model.Entreprise;
import Panel_Fenetre.PanelModifierProjet;
import Panel_Fenetre.PanelNouveauProjet;

/**
 * Cette fenetre affiche à l'utilisateur les données à rentrer pour
 * creer un nouveau projet 
 * 
 * @author damien planchamp
 *
 */
public class FenetreModifierProjet extends FenetreModal{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FenetreModifierProjet(Entreprise entreprise) {
		super(entreprise, "Modifier projet", 400, 300);
		panelInterface = new PanelModifierProjet(entreprise, this);
		this.setLayout(new BorderLayout());
		this.add(panelInterface, BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);
	}
}
