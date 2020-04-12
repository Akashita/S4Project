package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import Fenetre.FenetreModal;
import Model.Entreprise;
import Ressource.Personne;
import Ressource.Ressource;

public class PanelModifierProjet extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public PanelModifierProjet(Entreprise entreprise, FenetreModal fm) {
		super(entreprise, fm);
		initialiseComboBoxAnnee(this);
		initialiseComboBoxMois(this);
		initialiseComboBoxJour();

		projet = entreprise.getProjetSelectionner();
		
		textFieldNom.setText(projet.getNom());
		int prio = (int)projet.getPriorite();
		textFieldPriorite.setText(Integer.toString(prio));

		
		initialiseComboBoxRessource(entreprise.getListeRessourceType(Ressource.PERSONNE));
		adapteComboBoxRessource(projet.getChefDeProjet());
		
		//initialseJMA(projet.getDeadline());

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
		gc.weighty = 6;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 3;		
		this.add(creerTitre("Modifiez ses informations"), gc);

		
		
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy = 1;
		this.add(creerTexte("Nom"), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		this.add(textFieldNom, gc);			

		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy = 2;
		this.add(creerTexte("Priorité"), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		this.add(textFieldPriorite, gc);			
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy = 3;
		this.add(creerTexte("Chef de projet"), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		this.add(comboBoxRessource, gc);			
		
		
		
		
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy = 4;
		gc.gridwidth = 3;
		this.add(creerTitre("Deadline"), gc);
		
		gc.gridx = 0;
		gc.gridy = 5;
		this.add(calendrier(), gc);
	
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 1;
		gc.gridy = 6;
		this.add(creerBoutonAnnuler(), gc);
		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		this.add(creerBoutonFin(this, "Modifier"), gc);

	}
	
	protected void actionFin() {
		if (!textFieldNom.getText().isEmpty()) {
			if (!textFieldPriorite.getText().isEmpty()) {
				if (estUnEntier(textFieldPriorite.getText())) {
					Personne chefDeProjet = (Personne) comboBoxRessource.getSelectedItem();
					String nom = textFieldNom.getText();
					int priorite = Integer.parseInt(textFieldPriorite.getText());
					LocalDate deadline =  creerLaDate();
					entreprise.modifierProjet(projet, nom, priorite, chefDeProjet, deadline);
					fm.dispose();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire un nombre pour charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veillez ecrire sa priorité", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veillez ecrire son nom", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
}
