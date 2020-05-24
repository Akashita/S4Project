package Fenetre;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import GestionTicket.Ticket;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;
import Panel.PanelPrincipal;
import Ressource.Ressource;


/**
 * Affiche les informations du ticket et permet de changer son statut en acceptant ou en modifiant le ticket
 * @author Damien
 *
 */
public class FenetreInfoTicket extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ticket ticket;
	private Color couleurFond;
	private JButton boutonAccepter, boutonRefuser;
	private Entreprise entreprise;
	private boolean envoye;
	
	public FenetreInfoTicket(Entreprise entreprise, Ticket ticket) {
		super(entreprise.getFenetrePrincipale(), "Information du ticket");
		this.entreprise = entreprise;
		this.ticket  = ticket;
		if (ticket.getIdEnvoyeur() == entreprise.getUser().getId()) {
			envoye = true;
		}
		else {
			envoye = false;
		}
		
		if (!envoye) {
			this.entreprise.setStatutTicket(Ticket.VU, this.ticket);			
		}

		couleurFond = PanelPrincipal.BLEU2;
		this.setSize(300, 400);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetreModal(this));
	    this.setResizable(false);
	    initialiseBouton();
	    creationInterface();
		this.setVisible(true);

	}
	
	private void initialiseBouton() {
		boutonAccepter = new JButton("Accepter");
		boutonAccepter.setBackground(PanelPrincipal.ACCEPTER);
		boutonAccepter.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	actionBoutonAccepter();
	        }
	    });			
		boutonRefuser = new JButton("Refuser");
		boutonRefuser.setBackground(PanelPrincipal.REFUSER);
		boutonRefuser.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	actionBoutonRefuser();
	        }
	    });			
	}
	
	public void actionBoutonAccepter() {
		entreprise.accepteTicket(ticket.getId());
		entreprise.setStatutTicket(Ticket.ACCEPTEE, ticket);
		this.dispose();
	}
	
	public void actionBoutonRefuser() {
		entreprise.setStatutTicket(Ticket.REFUSE, ticket);
		this.dispose();
	}
	
	private void creationInterface() {
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);

		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weightx = 3;
		gc.weighty = 9;

		
		gc.fill = GridBagConstraints.CENTER;
		
		gc.insets = new Insets(5, 5, 5, 5);
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(creerTitre("Ticket numero : "+ticket.getId()), gc);

		
		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy  ++;
		gc.gridwidth = 1;
		this.add(creerTexte("DE : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridx ++;
		this.add(creerTextArea(ticket.getPersonneEnvoyeur().getPrenomNom()), gc);			

		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy  ++;
		gc.gridwidth = 1;
		this.add(creerTexte("A : "), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridx ++;
		this.add(creerTextArea(ticket.getPersonneReceveur().getPrenomNom()), gc);			
		Ticket ticketTest = entreprise.getTicketTransfertQuiLibereParId(ticket.getId());

		if ((ticket.getAction() == Ticket.LIBERE && ticketTest == null) || ticket.getAction() == Ticket.TRANSFERT || ticketTest != null) {
			gc.ipadx = gc.anchor = GridBagConstraints.WEST;
			gc.gridx = 0;
			gc.gridy  ++;
			gc.gridwidth = 1;
			this.add(creerTexte("Ressource concerné : "), gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridwidth = GridBagConstraints.REMAINDER;
			gc.gridx ++;
			Ressource r = ticket.getRessource();
			this.add(creerTextArea(r.toString()), gc);						
			
			if (ticket.getAction() == Ticket.LIBERE && ticketTest == null) {
				gc.ipadx = gc.anchor = GridBagConstraints.WEST;
				gc.gridx = 0;
				gc.gridy  ++;
				gc.gridwidth = 1;
				this.add(creerTexte("Activité d'origine : "), gc);
				gc.fill = GridBagConstraints.HORIZONTAL;
				gc.gridwidth = GridBagConstraints.REMAINDER;
				gc.gridx ++;
				Activite act = entreprise.getActiviteDepartLiberation(ticket.getId());
				Projet proj = entreprise.getProjetDeActiviteParId(act.getId());
				this.add(creerTextArea(act.getTitre()+" - "+proj.getNom()), gc);						
			}
			
			if (ticket.getAction() == Ticket.TRANSFERT || ticketTest != null ) {
				
				Activite activiteArrive= entreprise.getActiviteDepartLiberation(ticketTest.getId());
				Activite activiteDepart = entreprise.getActiviteArriveTransfert(ticket.getId());

				
				gc.ipadx = gc.anchor = GridBagConstraints.WEST;
				gc.gridx = 0;
				gc.gridy  ++;
				gc.gridwidth = 1;
				this.add(creerTexte("Activité d'origine : "), gc);
				gc.fill = GridBagConstraints.HORIZONTAL;
				gc.gridwidth = GridBagConstraints.REMAINDER;
				gc.gridx ++;
				this.add(creerTextArea(activiteDepart.toString()), gc);						
				gc.ipadx = gc.anchor = GridBagConstraints.WEST;
				gc.gridx = 0;
				gc.gridy  ++;
				gc.gridwidth = 1;
				this.add(creerTexte("Activité d'arrivé : "), gc);
				gc.fill = GridBagConstraints.HORIZONTAL;
				gc.gridwidth = GridBagConstraints.REMAINDER;
				gc.gridx ++;
				this.add(creerTextArea(activiteArrive.toString()), gc);						
			}
			
		}

		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy  ++;
		gc.gridwidth = 1;
		this.add(creerTexte("SUJET"), gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridx ++;
		this.add(creerTextArea(ticket.getSujet()), gc);			

		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 0;
		gc.gridy  ++;
		gc.gridwidth = 2;
		this.add(creerTexte("MESSAGE"), gc);
		gc.fill = GridBagConstraints.BOTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = GridBagConstraints.RELATIVE;
		gc.gridy ++;
		this.add(creerTextArea(ticket.getMessage()), gc);			
		
		
		if (!envoye) {
			if (ticket.getStatut() == Ticket.VU || ticket.getStatut() == Ticket.NONVU) {
				gc.gridwidth = 1;
				gc.gridheight = 1;
				gc.ipadx = gc.anchor = GridBagConstraints.EAST;
				gc.gridx = 1;
				gc.gridy ++;
				this.add(boutonRefuser, gc);		
				gc.ipadx = gc.anchor = GridBagConstraints.WEST;
				gc.gridx = 2;
				this.add(boutonAccepter, gc);
			}			
		}
	}
	
	private JTextArea creerTextArea(String text) {
		JTextArea jta = new JTextArea();
		jta.setText(text);
		jta.setEditable(false);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		jta.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		return jta;
	}
	
	protected JLabel creerTitre(String titre) {
		JLabel label = new JLabel(titre);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		return label;
	}
	
	protected JLabel creerTexte(String titre) {
		JLabel label = new JLabel(titre);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		label.setBackground(Color.BLUE);
		return label;
	}

	
	public int getIdTicket() {
		return ticket.getId();
	}

}
