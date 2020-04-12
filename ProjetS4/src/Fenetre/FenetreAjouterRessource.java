package Fenetre;
import java.awt.BorderLayout;


import Model.Entreprise;
import Panel_Fenetre.PanelAjouterRessource;

/**
 * Cette fenetre affiche à l'utilisateur les données à rentrer pour
 * creer un nouveau projet 
 * 
 * @author damien planchamp
 *
 */
public class FenetreAjouterRessource extends FenetreModal{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FenetreAjouterRessource(Entreprise entreprise) {
		super(entreprise, "Ajouter ressource", 300, 300);
		panelInterface = new PanelAjouterRessource(entreprise, this);
		this.setLayout(new BorderLayout());
		this.add(panelInterface, BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);
	}
}
