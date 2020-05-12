package Fenetre;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import GestionTicket.Ticket;
import Model.Entreprise;
import Panel.PanelPrincipal;

public class FenetreInfoTicket extends JDialog{
	
	private Entreprise entreprise;
	private Ticket ticket;
	private Color couleurFond;
	
	public FenetreInfoTicket(Entreprise entreprise, Ticket ticket) {
		super(entreprise.getFenetrePrincipale(), "Information du ticket");
		this.entreprise = entreprise;
		this.ticket  = ticket;
		couleurFond = PanelPrincipal.BLEU2;
		this.setSize(300, 400);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetre(this));
	    this.setResizable(false);
	    creationInterface();
		this.setVisible(true);

	}
	
	private void creationInterface() {
		this.setLayout(new GridBagLayout());
		this.setBackground(couleurFond);

		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weightx = 3;
		gc.weighty = 5;

		
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
		gc.gridwidth = 1;
		this.add(creerTexte("MESSAGE"), gc);
		gc.fill = GridBagConstraints.BOTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = GridBagConstraints.REMAINDER;
		gc.gridy ++;
		this.add(creerTextArea(ticket.getMessage()), gc);			

	}
	
	private JTextArea creerTextArea(String text) {
		JTextArea jta = new JTextArea();
		jta.setText(text);
		jta.setEditable(false);
		return jta;
	}
	
	protected JLabel creerTitre(String titre) {
		JLabel label = new JLabel(titre);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		return label;
	}
	
	protected JLabel creerTexte(String titre) {
		JLabel label = new JLabel(titre);
		label.setFont(new Font("Arial", Font.PLAIN, 15));
		label.setBackground(Color.BLUE);
		return label;
	}

	
	public int getIdTicket() {
		return ticket.getId();
	}

}
