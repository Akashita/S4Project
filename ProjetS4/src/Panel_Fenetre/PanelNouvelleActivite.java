package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import Fenetre.FenetreModal;
import Model.Entreprise;
import Model.Projet;
import Model.Temps;

public class PanelNouvelleActivite extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelNouvelleActivite(Entreprise entreprise, FenetreModal fm) {
		super(entreprise, fm);
		initialiseDomaineActivite(this);
		initialiseCalendrier(Temps.getAujourdhui(), this);
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
		this.add(creerTexte("<html>Indiquez sa charge <br>en jour/homme</html>"), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		this.add(textFieldCharge, gc);			
				
		
		
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy = 4;
		this.add(creerTitre("Indiquez des domaines"), gc);

		gc.fill = GridBagConstraints.WEST;
		gc.gridy = 5;		
		this.add(afficherListeDomaineChoisi(), gc);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 0;
		gc.gridy = 6;
		this.add(comboBoxDomaine, gc);
		gc.gridx = 2;
		this.add(boutonAjoutCompetence, gc);

		
		
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy = 7;
		gc.gridwidth = 3;
		this.add(creerTitre("Commence a�partir du"), gc);
		
		gc.gridx = 0;
		gc.gridy = 8;
		this.add(panelCalendrier(calendrier1), gc);
	
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 1;
		gc.gridy = 9;
		this.add(creerBoutonAnnuler(), gc);
		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		this.add(creerBoutonFin(this, "Cr�er"), gc);

	}
	
	protected void actionFin() {
		if (!textFieldNom.getText().isEmpty()) {
			if (!textFieldCharge.getText().isEmpty()) {
				if (estUnEntier(textFieldCharge.getText())) {
					Projet projet = entreprise.getProjetSelectionner();
					int charge = Integer.parseInt(textFieldCharge.getText());
					int jour = Integer.parseInt((String) calendrier1.getComboBoxJour().getSelectedItem());
					int mois = calendrier1.getComboBoxMois().getSelectedIndex()+1;
					int annee = Integer.parseInt((String) calendrier1.getComboBoxAnnee().getSelectedItem());
					LocalDate date =  Temps.creerLaDate(jour, mois, annee);
					entreprise.creerActivite(projet, textFieldNom.getText(), charge, date, listeDomaineChoisi);
					fm.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Veillez ecrire un nombre pour charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Veillez ecrire sa charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
		   	JOptionPane.showMessageDialog(null, "Veillez ecrire son titre", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
}
