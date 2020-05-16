package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import EcouteurEvenement.SourisActiviteListener;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;
import Ressource.Ressource;

/**
 * Affiche les info du projet et sa liste d'activite
 * @author Damien
 *
 */
public class PanelInfoProjet extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private Projet projet;
	private Color couleurFond;
	
	
	public PanelInfoProjet(Entreprise entreprise) {
		this.entreprise = entreprise;
		couleurFond = PanelPrincipal.BLANC;
		this.projet = entreprise.getProjetSelectionner();
		if (projet != null) {
			this.setBackground(couleurFond);
			this.setLayout(new BorderLayout());
			afficheInterface();
		}
	}
	
	public void afficheInterface() {
		this.setBackground(couleurFond);
		this.setLayout(new GridBagLayout());		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(5, 5, 5, 5);
		
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		gc.weightx = 5;
		
		gc.weighty = 3;
		
		
		gc.gridx = 0;
		gc.gridy = 0;
		String nom = projet.getChefDeProjet().getPrenom() + " " + projet.getChefDeProjet().getNom();
		this.add(creerLabel("Chef de projet: "+ nom), gc);
		gc.gridx = 1;
		this.add(creerLabel("Priorite: "+(int)projet.getPriorite()), gc);
		gc.gridx = 2;
		
		LocalDate deadline = projet.getDeadline();
		String date = deadline.getDayOfMonth() + "/" + deadline.getMonthValue() + "/" + deadline.getYear();			

		this.add(creerLabel("DeadLine: "+date), gc);

		for (int i=0; i<3; i++) {
			int type = Ressource.RIEN;
			switch (i){
			case 0: type = Ressource.PERSONNE;
			break;
			case 1: type = Ressource.SALLE;
			break;
			case 2: type = Ressource.CALCULATEUR;
			break;
			}
				gc.gridx = i+3;
				gc.gridy = 0;
				this.add(creerLabelInterfaceRessource(type), gc);
		}
		
		//affiche les activité
			
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = GridBagConstraints.REMAINDER;
		this.add(panelActivite(), gc);

	}
		
		private JPanel creerLabelInterfaceRessource(int type) {
			JPanel panel = new JPanel();
			panel.setBackground(PanelPrincipal.BLANC);		
		    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			JLabel labelTxt = new JLabel();
			JLabel labelIco = new JLabel();
			switch (type) {
			case Ressource.PERSONNE:
				labelIco = new JLabel(new ImageIcon("images/user_grey.png"));
				labelTxt = creerLabel("  "+Ressource.PERSONNE+"s", true);			
				break;
			case Ressource.SALLE:
				labelIco = new JLabel(new ImageIcon("images/door_grey.png"));
				labelTxt = creerLabel("  "+Ressource.SALLE+"s", true);					
				break;
			case Ressource.CALCULATEUR:
				labelIco = new JLabel(new ImageIcon("images/computer_grey.png"));	
				labelTxt = creerLabel("  "+Ressource.CALCULATEUR+"s", true);	
				break;

			default:
				break;
			}
			panel.add(labelIco);
			panel.add(labelTxt);
			return panel;
		}

		private JLabel creerLabel(String nom, boolean estGras) {
			JLabel label = new JLabel(nom);
			if(estGras) {
				label.setFont(new Font("Arial", Font.BOLD, 15));
			}
			else {
				label.setFont(new Font("Arial", Font.PLAIN, 15));
			}
			return label;
		}
	
		
		private JScrollPane panelActivite() {
			JPanel p = new JPanel();
			p.setBackground(couleurFond);
			ArrayList<Activite> listeActivite = projet.getListe();
			p.setLayout(new GridLayout(listeActivite.size(),1,10,10));

			for (int i=0; i<listeActivite.size(); i++) {
				Activite activite = listeActivite.get(i);							
				PanelInfoActivite pia = new PanelInfoActivite(entreprise, activite);
				pia.addMouseListener(new SourisActiviteListener(entreprise, activite));	
				p.add(pia);								
			}
			JScrollPane jsp = new JScrollPane(p);
			jsp.setViewportView(p);
			jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			return jsp;
		}
		
//	==================METHODE GENERAL============================================	
	

	private JPanel creerLabel(String nom) {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLANC);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        String[] regex = nom.split(":", 2); 

		JLabel labelTitre = new JLabel(regex[0]+":");
		labelTitre.setFont(new Font("Arial", Font.BOLD, 15));
		labelTitre.setHorizontalAlignment(JLabel.CENTER);
		panel.add(labelTitre);
		
		JLabel labelResultat = new JLabel(regex[1]);
		labelResultat.setFont(new Font("Arial", Font.PLAIN, 15));
		labelResultat.setForeground(PanelPrincipal.GRIS2);
		labelResultat.getFont().deriveFont(Font.ITALIC);
		labelResultat.setHorizontalAlignment(JLabel.CENTER);
		panel.add(labelResultat);

		return panel;
	}

	
	public String getProjetNom() {
		return projet.getNom();
	}
	
	public Projet getProjet() {
		return projet;
	}
}
