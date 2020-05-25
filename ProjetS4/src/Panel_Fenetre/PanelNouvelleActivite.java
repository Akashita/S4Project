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

/**
 *  Affiche les information a saisir pour creer une activite
 * @author Damien
 *
 */
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
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.CENTER;
		
		gc.insets = new Insets(5, 5, 5, 5);
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		gc.weightx = 3;
		
		gc.weighty = 7;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 3;
		
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
		this.add(creerTitre("Commence à partir du"), gc);
		
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
		this.add(creerBoutonFin(this, "Créer"), gc);

	}
	
	protected void actionFin() {
		if (!textFieldNom.getText().isEmpty()) {
			if (!textFieldCharge.getText().isEmpty()) {
				if (estUnEntier(textFieldCharge.getText())) {
					Projet projet = entreprise.getProjetSelectionner();
					int charge = Integer.parseInt(textFieldCharge.getText());
					LocalDate date =  calendrier1.getDate();
					entreprise.creerActivite(projet, textFieldNom.getText(), charge, date, listeDomaineChoisi);
					fm.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Veuillez écrire un nombre pour charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Veuillez écrire sa charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
		   	JOptionPane.showMessageDialog(null, "Veuillez écrire son titre", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
}
