package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import Fenetre.FenetreModal;
import Model.Entreprise;
import Ressource.Ressource;

/**
 * Affiche la liste des ressource de l'activite
 * Permet d'un choisir une pour l'enlever de l'activite
 * @author Damien
 *
 */
public class PanelEnleverRessource extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelEnleverRessource(Entreprise entreprise, FenetreModal fm) {
		super(entreprise, fm);
		creerInterface();
	}
	
	protected void creerInterface() {
	
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.CENTER;
		
		gc.insets = new Insets(5, 5, 5, 5);
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		gc.weightx = 3;
		
		gc.weighty = 7;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 3;
		this.add(creerTitre("Indiquez le type de la ressource"), gc);
		
		gc.fill = GridBagConstraints.CENTER;	
		gc.gridy = 1;
		initialiseComboBoxType(this); 
		this.add(comboBoxType, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 2;
		ArrayList<Ressource> l = entreprise.getListeRessourcedeActiviteParId(typeChoisi, entreprise.getActiviteSelectionner().getId());
		initialiseComboBoxRessource(l);
		this.add(comboBoxRessource, gc);			

		
		gc.fill = GridBagConstraints.CENTER;	
		gc.gridy = 3;
		this.add(creerTitre("Filtres"), gc);
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 1;
		gc.gridy = 7;
		this.add(creerBoutonAnnuler(), gc);
		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		this.add(creerBoutonFin(this, "Enlever"), gc);

	}
	
	protected void actionFin() {
		entreprise.enleverRessourceActivite(typeChoisi, (Ressource) comboBoxRessource.getSelectedItem(), entreprise.getActiviteSelectionner());
		fm.dispose();
	}
}
