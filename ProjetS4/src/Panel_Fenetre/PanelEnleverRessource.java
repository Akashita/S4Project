package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import Fenetre.FenetreModal;
import Model.Entreprise;
import Ressource.Ressource;

public class PanelEnleverRessource extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelEnleverRessource(Entreprise entreprise, FenetreModal fm) {
		super(entreprise, fm);
		initialiseComboBoxAnnee(this);
		initialiseComboBoxMois(this);
		creerInterface();
	}
	
	protected void creerInterface() {
	
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		/* Le gridBagConstraints va définir la position et la taille des éléments */
		GridBagConstraints gc = new GridBagConstraints();
		
		/* le parametre fill sert à définir comment le composant sera rempli GridBagConstraints.BOTH permet d'occuper tout l'espace disponible
		 * horizontalement et verticalement GridBagConstraints.HORIZONTAL maximise horizontalement GridBagConstraints.VERTICAL maximise verticalement
		 */
		gc.fill = GridBagConstraints.CENTER;
		
		/* insets définir la marge entre les composant new Insets(margeSupérieure, margeGauche, margeInférieur, margeDroite) */
		gc.insets = new Insets(5, 5, 5, 5);
		
		/* ipady permet de savoir où on place le composant s'il n'occupe pas la totalité de l'espace disponnible */
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		/* weightx définit le nombre de cases en abscisse */
		gc.weightx = 3;
		
		/* weightx définit le nombre de cases en ordonnée */
		gc.weighty = 7;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 3;
		this.add(creerTitre("Indiquez le type de la ressource"), gc);
		
		gc.gridy = 1;
		initialiseComboBoxType(this); 
		this.add(comboBoxType, gc);
		
		
		gc.gridy = 2;
		initialiseComboBoxRessource(entreprise.getActiviteSelectionner().getListeRessourceType(typeChoisi));
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
		this.add(creerBoutonFin(this, "Enlever"), gc);

	}
	
	protected void actionFin() {
		entreprise.enleverRessourceActivite((Ressource) comboBoxRessource.getSelectedItem());
		fm.dispose();
	}
}