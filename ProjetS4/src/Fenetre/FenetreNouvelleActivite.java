package Fenetre;
import java.awt.BorderLayout;


import Model.Entreprise;
import Panel_Fenetre.PanelNouvelleActivite;

/**
 * Cette fenetre affiche à l'utilisateur les données à rentrer pour
 * creer un nouveau projet 
 * 
 * @author damien planchamp
 *
 */
public class FenetreNouvelleActivite extends FenetreModal{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FenetreNouvelleActivite(Entreprise entreprise) {
		super(entreprise, "Creation activite", 400, 300);
		panelInterface = new PanelNouvelleActivite(entreprise, this);
		this.setLayout(new BorderLayout());
		this.add(panelInterface, BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);
	}
}
