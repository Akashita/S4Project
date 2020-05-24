package Panel_Fenetre;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Fenetre.FenetreModal;
import GestionTicket.Ticket;
import Model.Entreprise;
import Model.Projet;
import Model.Temps;
import Ressource.Personne;

/**
 *  Affiche les information a saisir pour creer un ticket
 * @author Damien
 *
 */
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
		gc.fill = GridBagConstraints.CENTER;
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx ++;
		gc.gridy ++;
		gc.gridwidth = GridBagConstraints.RELATIVE;
		this.add(creerTitre("Type de la ressource"), gc);
		
		gc.gridx ++;
		initialiseComboBoxType(this); 
		this.add(comboBoxType, gc);
		
		gc.fill = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		this.add(creerTexte("Ressource concern� : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx ++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(textFieldLogin, gc);

	}
	
	private void afficheLibere(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.CENTER;
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx ++;
		gc.gridy ++;
		gc.gridwidth = GridBagConstraints.RELATIVE;
		this.add(creerTitre("Type de la ressource"), gc);
		
		gc.gridx ++;
		initialiseComboBoxType(this); 
		this.add(comboBoxType, gc);

		gc.fill = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		this.add(creerTexte("Ressource concern� : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx ++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(textFieldLogin, gc);

		gc.fill = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = 1;
		this.add(creerTexte("Projet concern� : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx ++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add( comboBoxProjet, gc);
		
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
				if (entreprise.getListeProjetDePersonneParLogin(textFieldLogin.getText()) != null) {
					entreprise.nouvTicket(actionChoisie, sujet(), textArea.getText(), entreprise.getUser().getId(), getIdFromLogin(), null);	
				}
				else {
				   	JOptionPane.showMessageDialog(null, "Ce destinataire n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
			   	JOptionPane.showMessageDialog(null, "Veillez ecrire le login du destinataire", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
			break;

		case Ticket.LIBERE:
			if (!textFieldLogin.getText().isEmpty()) {
				if (entreprise.getListeProjetDePersonneParLogin(textFieldLogin.getText()) != null) {
					Activite act 
					Projet p =  (Projet) comboBoxProjet.getSelectedItem();
					int type = comboBoxType.getSelectedIndex();
					String login = textFieldLogin.getText();
					entreprise.nouvTicket(actionChoisie, sujet(), textArea.getText(), entreprise.getUser().getId(), p.getChefDeProjet().getId(),
							entreprise.getRessourceParLogin(type, login));				
				}
				else {
				   	JOptionPane.showMessageDialog(null, "Cette ressource n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}

			}
			else {
			   	JOptionPane.showMessageDialog(null, "Veillez ecrire le login de la ressource", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
			break;

		case Ticket.TRANSFERT:
			if (!textFieldLogin.getText().isEmpty()) {
				if (entreprise.getListeProjetDePersonneParLogin(textFieldLogin.getText()) != null) {
					int type = comboBoxType.getSelectedIndex();
					String login = textFieldLogin.getText();
					ArrayList<Personne> lchef = entreprise.getListeDesChefDeProjetPossedantLaRessourceParLogin(type, login);
					
					for (int i=0; i<lchef.size(); i++) {
						entreprise.nouvTicket(actionChoisie, sujet(), textArea.getText(), entreprise.getUser().getId(),
								lchef.get(i).getId(), entreprise.getRessourceParLogin(type, login));
					}				
				}
				else {
				   	JOptionPane.showMessageDialog(null, "Cette ressource n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);			
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
	
	private String sujet() {
		String s = "";
		switch (actionChoisie) {
		case Ticket.MESSAGE:
			s = "message";
			break;
		case Ticket.LIBERE:
			s = "liberer" ;
			break;
		case Ticket.TRANSFERT:
			s = "transferer";
			break;

		default:
			break;
		}
		return s;
	}

	private int getIdFromLogin() {
		String[] regex = textFieldLogin.getText().split(entreprise.SEPARATEUR, 2); 
		int id = Integer.parseInt(regex[1]);
		return id;
	}

}
