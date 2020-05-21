package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JOptionPane;

import Fenetre.FenetreModal;
import Model.Activite;
import Model.Entreprise;
import Ressource.Ressource;

public class PanelAjouterRessource extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelAjouterRessource(Entreprise entreprise, FenetreModal fm) {
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
		
		gc.gridy = 1;
		initialiseComboBoxType(this); 
		this.add(comboBoxType, gc);
		
		
		gc.gridy = 2;
		initialiseComboBoxRessource(entreprise.getListeRessourceEntrepriseParType(typeChoisi));
		this.add(comboBoxRessource, gc);			

		
		
		gc.gridy = 3;
		this.add(creerTitre("Filtres"), gc);
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 1;
		gc.gridy = 7;
		this.add(creerBoutonAnnuler(), gc);
		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		this.add(creerBoutonFin(this, "Ajouter"), gc);

	}
	
	protected void actionFin() {
		int type = comboBoxType.getSelectedIndex();
		Ressource r = (Ressource) comboBoxRessource.getSelectedItem();
		Activite a = entreprise.getActiviteSelectionner();
		entreprise.ajouterRessourceActivite(type, r,a);
		fm.dispose();
		/*if(!entreprise.ressourcePresenteDansActivite(type, r, a)) {
			entreprise.ajouterRessourceActivite(type, r,a);
			fm.dispose();
		}
		else {
		   	JOptionPane.showMessageDialog(null, "Cette ressource est deja présente dans cette activité", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}*/
	}
}
