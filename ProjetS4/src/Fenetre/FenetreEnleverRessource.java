package Fenetre;
import java.awt.BorderLayout;


import Model.Entreprise;
import Panel_Fenetre.PanelEnleverRessource;

/**
 * Cette fenetre affiche à l'utilisateur les données à rentrer pour
 * creer un nouveau projet 
 * 
 * @author damien planchamp
 *
 */
public class FenetreEnleverRessource extends FenetreModal{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FenetreEnleverRessource(Entreprise entreprise) {
		super(entreprise, "Enlever ressource", 300, 300);
		panelInterface = new PanelEnleverRessource(entreprise, this);
		this.setLayout(new BorderLayout());
		this.add(panelInterface, BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);
	}
}
