package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import Fenetre.FenetreModal;
import Model.Entreprise;
import Model.Temps;
import Ressource.Personne;

public class PanelNouveauProjet extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelNouveauProjet(Entreprise entreprise, FenetreModal fm) {
		super(entreprise, fm);
		initialiseCalendrier(Temps.getAujourdhui(), this);
		creerInterface();
	}
	
	protected void creerInterface() {
	
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		/* Le gridBagConstraints va d�finir la position et la taille des �l�ments */
		GridBagConstraints gc = new GridBagConstraints();
		
		/* le parametre fill sert à d�finir comment le composant sera rempli GridBagConstraints.BOTH permet d'occuper tout l'espace disponible
		 * horizontalement et verticalement GridBagConstraints.HORIZONTAL maximise horizontalement GridBagConstraints.VERTICAL maximise verticalement
		 */
		gc.fill = GridBagConstraints.CENTER;
		
		/* insets d�finir la marge entre les composant new Insets(margeSup�rieure, margeGauche, margeInf�rieur, margeDroite) */
		gc.insets = new Insets(5, 5, 5, 5);
		
		/* ipady permet de savoir où on place le composant s'il n'occupe pas la totalit� de l'espace disponnible */
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		/* weightx d�finit le nombre de cases en abscisse */
		gc.weightx = 3;
		
		/* weightx d�finit le nombre de cases en ordonn�e */
		gc.weighty = 7;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 3;
		//this.add(creerTitre("Creer un projet"), gc);
		
		gc.gridy = 1;
		this.add(creerTitre("Indiquez ses informations"), gc);

		
		
		
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy = 2;
		this.add(creerTexte("Indiquez son nom"), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		this.add(textFieldNom, gc);			

		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy = 3;
		this.add(creerTexte("Indiquez sa priorit�"), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		this.add(textFieldPriorite, gc);			
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy = 4;
		this.add(creerTexte("Choisissez un chef de projet"), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		initialiseComboBoxRessource(entreprise.getListeRessourceEntrepriseParType(typeChoisi));
		this.add(comboBoxRessource, gc);			
		
		
		
		
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy = 5;
		gc.gridwidth = 3;
		this.add(creerTitre("Precisez sa deadline"), gc);
		
		gc.gridx = 0;
		gc.gridy = 6;
		this.add(panelCalendrier(calendrier1), gc);
	
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 1;
		gc.gridy = 7;
		this.add(creerBoutonAnnuler(), gc);
		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		this.add(creerBoutonFin(this, "Cr�er"), gc);

	}
	
	protected void actionFin() {
		if (!textFieldNom.getText().isEmpty()) {
			if (!textFieldPriorite.getText().isEmpty()) {
				if (estUnEntier(textFieldPriorite.getText())) {
					Personne pers = (Personne) comboBoxRessource.getSelectedItem();
					String titre = textFieldNom.getText();
					int prio = Integer.parseInt(textFieldPriorite.getText());
					int jour = Integer.parseInt((String) calendrier1.getComboBoxJour().getSelectedItem());
					int mois = calendrier1.getComboBoxMois().getSelectedIndex()+1;
					int annee = Integer.parseInt((String) calendrier1.getComboBoxAnnee().getSelectedItem());
					LocalDate date =  Temps.creerLaDate(jour, mois, annee);
					entreprise.creerProjet(pers, titre, prio, date);
					fm.dispose();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire un nombre pour charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veillez ecrire sa priorit�", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veillez ecrire son nom", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
}
