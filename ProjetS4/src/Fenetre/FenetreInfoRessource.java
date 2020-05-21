package Fenetre;

import java.awt.BorderLayout;

import javax.swing.JDialog;

import Model.EDT;
import Model.Entreprise;
import Panel.PanelEDTRessource;
import Panel.PanelInfoRessource;
import Ressource.Ressource;

/**
 * Cette fenetre affiche toute les informations de la ressource en fonction de l'user et son emploi du temps
 * @author Damien
 *
 */
public class FenetreInfoRessource extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private Ressource ressource;
	private int typeDeLaRessource;
	
	public FenetreInfoRessource(Entreprise entreprise, Ressource ressource, int typeDeLaRessource) {
		super(entreprise.getFenetrePrincipale(), "Information de la ressource");
		this.entreprise = entreprise;
		this.ressource = ressource;
		this.typeDeLaRessource = typeDeLaRessource;
		this.setSize(800,770);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetreModal(this));
	    this.setResizable(false);
	    creationInterface();
		this.setVisible(true);
	}
	

	private void creationInterface() {
		this.setLayout(new BorderLayout());
		this.add(new PanelInfoRessource(this, entreprise, ressource), BorderLayout.NORTH);
		EDT edtR = entreprise.getEDTRessource(typeDeLaRessource, ressource.getId());
		this.add(new PanelEDTRessource(ressource, edtR), BorderLayout.CENTER);
		//this.add(afficherEmploiDuTemps(), BorderLayout.CENTER);
		this.revalidate();

	}
	
	
	public int getIdRessource() {
		return ressource.getId();
	}
}