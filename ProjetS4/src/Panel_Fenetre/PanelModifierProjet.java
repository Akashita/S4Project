package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import Fenetre.FenetreModal;
import Model.Entreprise;
import Ressource.Personne;

/**
 * Affiche les information du projet et permet de les modifier
 * @author Damien
 *
 */
public class PanelModifierProjet extends PanelFenetre{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public PanelModifierProjet(Entreprise entreprise, FenetreModal fm) {
		super(entreprise, fm);
		projet = entreprise.getProjetSelectionner();
		initialiseCalendrier(projet.getDeadline(), this);
	
		textFieldNom.setText(projet.getNom());
		int prio = (int)projet.getPriorite();
		textFieldPriorite.setText(Integer.toString(prio));

		
		initialiseComboBoxRessource(entreprise.getListePersonneEntreprise());
		adapteComboBoxRessource(projet.getChefDeProjet());
		
		//initialseJMA(projet.getDeadline());

		creerInterface();
	}
	
	protected void creerInterface() {
	
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);
		/* Le gridBagConstraints va d�finir la position et la taille des �l�ments */
		GridBagConstraints gc = new GridBagConstraints();
		
		/* le parametre fill sert � d�finir comment le composant sera rempli GridBagConstraints.BOTH permet d'occuper tout l'espace disponible
		 * horizontalement et verticalement GridBagConstraints.HORIZONTAL maximise horizontalement GridBagConstraints.VERTICAL maximise verticalement
		 */
		gc.fill = GridBagConstraints.CENTER;
		
		/* insets d�finir la marge entre les composant new Insets(margeSup�rieure, margeGauche, margeInf�rieur, margeDroite) */
		gc.insets = new Insets(5, 5, 5, 5);
		
		/* ipady permet de savoir o� on place le composant s'il n'occupe pas la totalit� de l'espace disponnible */
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		/* weightx d�finit le nombre de cases en abscisse */
		gc.weightx = 3;
		
		/* weightx d�finit le nombre de cases en ordonn�e */
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
		this.add(panelCalendrier(calendrier1), gc);
	
		
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 0;
		gc.gridy = 6;
		this.add(creerBoutonSupprimer(this), gc);

		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 1;
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
					/*int jour = Integer.parseInt((String) calendrier1.getComboBoxJour().getSelectedItem());
					int mois = calendrier1.getComboBoxMois().getSelectedIndex()+1;
					int annee = Integer.parseInt((String) calendrier1.getComboBoxAnnee().getSelectedItem());*/
					LocalDate date =  calendrier1.getDate();
					entreprise.modifierProjet(projet, nom, priorite, chefDeProjet, date);
					fm.dispose();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veuillez écrire un nombre pour charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veuillez écrire sa priorité", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veuillez écrire son nom", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	protected void supprimer() {
		String texte = "<html> êtes-vous sur de vouloir supprimer ce projet ? <br> La suppression de ce projet supprimera tout son contenu. </html>";
		int res = JOptionPane.showConfirmDialog(null, texte, "Attention", JOptionPane.YES_NO_OPTION);			
		if (res == 0) { //0 = yes
			entreprise.supprimerProjet(entreprise.getProjetSelectionner());
			fm.dispose();
		}		
	}
}
