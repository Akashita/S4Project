package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import Fenetre.FenetreModal;
import GestionTicket.Ticket;
import Model.Entreprise;
import Model.Projet;
import Model.Temps;

public class PanelNouveauTicket  extends PanelFenetre{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelNouveauTicket(Entreprise entreprise, FenetreModal fm) {
		super(entreprise,fm);
		initialiseTicket(this);
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
		gc.weighty = 9;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(creerTitre("Envoyer un ticket"), gc);
		
		gc.fill = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = 1;
		this.add(creerTexte("Action : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(comboBoxActionTicket, gc);

		if (actionChoisie == Ticket.MESSAGE) {
			afficheMessage(gc);
		}
		if (actionChoisie == Ticket.TRANSFERT) {
			afficheTransfert(gc);
		}
		if (actionChoisie == Ticket.LIBERE) {
			afficheLibere(gc);
		}
		
		afficheTextArea(gc);
		
		gc.fill = GridBagConstraints.CENTER;
		gc.gridwidth = 1;
		gc.ipadx = gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 1;
		gc.gridy ++;
		this.add(creerBoutonAnnuler(), gc);
		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		this.add(creerBoutonFin(this, "Envoyer"), gc);

	}
	
	private void afficheMessage(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.CENTER;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = 1;
		this.add(creerTexte("Destinataire : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx ++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(textFieldLogin, gc);

	}
	
	private void afficheTransfert(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.WEST;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		this.add(creerTexte("Ressource concerné : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx ++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(textFieldLogin, gc);

		gc.fill = GridBagConstraints.WEST;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		this.add(creerTexte("Du : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx ++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(panelCalendrier(calendrier1), gc);

		gc.fill = GridBagConstraints.WEST;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		this.add(creerTexte("Au : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx ++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(panelCalendrier(calendrier2), gc);
}
	
	private void afficheLibere(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		this.add(creerTexte("Ressource concerné : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx ++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(textFieldLogin, gc);

		gc.fill = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = 1;
		this.add(creerTexte("Projet concerné : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx ++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(comboBoxProjet, gc);
		
	}
	
	private void afficheTextArea(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.CENTER;
		gc.gridy ++;
		gc.gridx = 0;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.ipady = gc.anchor = GridBagConstraints.SOUTH;
		gc.gridwidth =1;
		gc.gridheight = 1;
		this.add(creerTexte("Sujet : "), gc);
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy ++;
		gc.gridx = 0;
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = GridBagConstraints.RELATIVE;
		this.add(textArea, gc);

	}
	
	protected void actionFin() {
		switch (actionChoisie) {
		
		case Ticket.MESSAGE:
			if (!textFieldLogin.getText().isEmpty()) {
				entreprise.nouvTicket(actionChoisie, null, textArea.getText(), )
			}
			else {
			   	JOptionPane.showMessageDialog(null, "Veillez ecrire le login du destinataire", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
			break;

		case Ticket.LIBERE:
			if (!textFieldLogin.getText().isEmpty()) {
				//ticket libere
			}
			else {
			   	JOptionPane.showMessageDialog(null, "Veillez ecrire le login de la ressource", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
			break;

		case Ticket.TRANSFERT:
			if (!textFieldLogin.getText().isEmpty()) {
				int jour = Integer.parseInt((String) calendrier1.getComboBoxJour().getSelectedItem());
				int mois = calendrier1.getComboBoxMois().getSelectedIndex()+1;
				int annee = Integer.parseInt((String) calendrier1.getComboBoxAnnee().getSelectedItem());
				LocalDate debut =  creerLaDate(jour, mois, annee);
				jour = Integer.parseInt((String) calendrier2.getComboBoxJour().getSelectedItem());
				mois = calendrier2.getComboBoxMois().getSelectedIndex()+1;
				annee = Integer.parseInt((String) calendrier2.getComboBoxAnnee().getSelectedItem());
				LocalDate fin =  creerLaDate(jour, mois, annee);
				if (Temps.dateUnEstSuperieurDateDeux(fin,debut)) {
					//ticket transfert
				}
				else {
				   	JOptionPane.showMessageDialog(null, "Veillez indiquer une date de fin supérieure à la date du début", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
			   	JOptionPane.showMessageDialog(null, "Veillez ecrire le login de la ressource", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
			break;

		default:
			break;
		}
	}

}
