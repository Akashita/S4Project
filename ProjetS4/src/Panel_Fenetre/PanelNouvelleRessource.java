package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

import Fenetre.FenetreModal;
import Model.Entreprise;
import Ressource.Personne;
import Ressource.Ressource;

public class PanelNouvelleRessource extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public PanelNouvelleRessource(Entreprise entreprise, FenetreModal fm) {
		super(entreprise, fm);
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
		int maxHauteur = 9;
		gc.weighty = maxHauteur;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 3;
		this.add(creerTitre("Indiquez le type de la ressource"), gc);
		
		gc.gridy = 1;
		initialiseComboBoxType(this); 
		this.add(comboBoxType, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		this.add(creerTitre("Indiquez ses informations"), gc);

		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		if (typeChoisi == Ressource.PERSONNE) {
			gc.gridwidth = 1;
			gc.gridx = 0;
			gc.gridy = 3;
			this.add(creerTexte("Indiquez son prénom"), gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridwidth = 2;
			gc.gridx = 1;
			this.add(textFieldPrenom, gc);

			gc.gridwidth = 1;
			gc.fill = GridBagConstraints.CENTER;
			gc.gridx = 0;
			gc.gridy = 4;
			this.add(creerTexte("Indiquez son nom"), gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridwidth = 2;
			gc.gridx = 1;
			this.add(textFieldNom, gc);	
			
			
			
			initialiseDomaine();
			initialiseNiveau();
			gc.gridwidth = 3;
			gc.fill = GridBagConstraints.CENTER;
			gc.gridx = 0;
			gc.gridy = 5;
			this.add(creerTitre("Indiquez ses compétences"), gc);

			gc.gridwidth = 2;
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridx = 0;
			gc.gridy = 6;
			this.add(comboBoxDomaine1, gc);
			gc.gridwidth = 1;
			gc.gridx = 2;
			this.add(comboBoxNiveau1, gc);
			
			gc.gridwidth = 2;
			gc.gridx = 0;
			gc.gridy = 7;
			this.add(comboBoxDomaine2, gc);
			gc.gridwidth = 1;
			gc.gridx = 2;
			this.add(comboBoxNiveau2, gc);

			gc.gridwidth = 2;
			gc.gridx = 0;
			gc.gridy = 8;
			this.add(comboBoxDomaine3, gc);
			gc.gridwidth = 1;
			gc.gridx = 2;
			this.add(comboBoxNiveau3, gc);
			
			gc.gridwidth = 1;
			gc.gridx = 0;
			gc.gridy = maxHauteur;
			this.add(checkBoxestAdmin, gc);
		}

		if (typeChoisi == Ressource.SALLE) {
			gc.gridwidth = 1;
			gc.gridx = 0;
			gc.gridy = 3;
			this.add(creerTexte("Indiquez son nom"), gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridwidth = 2;
			gc.gridx = 1;
			this.add(textFieldNom, gc);

			gc.gridwidth = 1;
			gc.fill = GridBagConstraints.CENTER;
			gc.gridx = 0;
			gc.gridy = 4;
			this.add(creerTexte("Indiquez sa capacité"), gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridwidth = 2;
			gc.gridx = 1;
			this.add(textFieldCapacite, gc);			
		}

		
		if (typeChoisi == Ressource.CALCULATEUR) {
			gc.gridwidth = 1;
			gc.fill = GridBagConstraints.CENTER;
			gc.gridx = 0;
			gc.gridy = 3;
			this.add(creerTexte("Indiquez son nom"), gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridwidth = 2;
			gc.gridx = 1;
			this.add(textFieldNom, gc);
			
			gc.gridwidth = 1;
			gc.fill = GridBagConstraints.CENTER;
			gc.gridx = 0;
			gc.gridy = 4;
			this.add(creerTexte("Indiquez sa capacité"), gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridwidth = 2;
			gc.gridx = 1;
			this.add(textFieldCapacite, gc);			
		}
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 1;
		gc.gridy = maxHauteur;
		this.add(creerBoutonAnnuler(), gc);
		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		this.add(creerBoutonFin(this, "Créer"), gc);

	}
	

	protected void actionFin() {
		if (!textFieldNom.getText().isEmpty()) {
			if (typeChoisi == Ressource.PERSONNE) {
				if (!textFieldPrenom.getText().isEmpty()) {
					String role = "";
					if (checkBoxestAdmin != null) {
						role = Personne.ADMINISTRATEUR;
					}
					else {
						role = Personne.COLLABORATEUR;
					}
					
					entreprise.nouvPersonne(textFieldNom.getText(), textFieldPrenom.getText(), role, convertToCompetence());	 
					fm.dispose();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire son prenom", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			if (typeChoisi == Ressource.SALLE) {
				if (estUnEntier(textFieldCapacite.getText())) {
					entreprise.nouvSalle(textFieldNom.getText(), Integer.parseInt(textFieldCapacite.getText()));
					fm.dispose();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire un nombre", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			if (typeChoisi == Ressource.CALCULATEUR) {
				if (estUnEntier(textFieldCapacite.getText())) {
					entreprise.nouvCalculateur(textFieldNom.getText());
					fm.dispose();
				}
			}	
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veillez ecrire son nom", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
}
