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
			this.setLayout(new BorderLayout());
			this.setBackground(couleurFond);
			afficheInterface();
		}
	}
	
	public void afficheInterface() {
		//affiche information du projet
		this.add(panelInfoProjet(), BorderLayout.NORTH);
		
		//affiche les activité
		/*gc.fill = GridBagConstraints.BOTH;			
		gc.gridx = 0;
		gc.gridy ++;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = GridBagConstraints.REMAINDER;*/
		this.add(panelActivite(), BorderLayout.CENTER);

	}
	
	private JPanel panelInfoProjet() {
		JPanel p = new JPanel(); 
		p.setBackground(couleurFond);
		p.setLayout(new GridBagLayout());		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.CENTER;
		gc.insets = new Insets(20, 5, 5, 5);
		
		gc.ipadx = gc.anchor = GridBagConstraints.WEST;

		gc.weightx = 5;
		
		gc.weighty = 3;
		
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridheight = 1;
		String nom = projet.getChefDeProjet().getPrenom() + " " + projet.getChefDeProjet().getNom();
		p.add(creerLabel("Chef de projet: "+ nom), gc);
		gc.gridx = 1;
		p.add(creerLabel("Priorite: "+(int)projet.getPriorite()), gc);
		gc.gridx = 2;
		
		LocalDate deadline = projet.getDeadline();
		String date = deadline.getDayOfMonth() + "/" + deadline.getMonthValue() + "/" + deadline.getYear();			

		p.add(creerLabel("DeadLine: "+date), gc);

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
			//gc.insets = new Insets(20, 100, 5, 20);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridx = i+3;
			gc.gridy = 0;
			p.add(creerLabelInterfaceRessource(type), gc);
		}	
		return p;
	}
		
	private JPanel creerLabelInterfaceRessource(int type) {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLANC);		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel labelIco = new JLabel();
		switch (type) {
		case Ressource.PERSONNE:
			labelIco = new JLabel(new ImageIcon("images/user_grey.png"));
			break;
		case Ressource.SALLE:
			labelIco = new JLabel(new ImageIcon("images/door_grey.png"));
			break;
		case Ressource.CALCULATEUR:
			labelIco = new JLabel(new ImageIcon("images/computer_grey.png"));	
			break;
		default:
			break;
		}
		panel.add(labelIco);
		return panel;
	}

	
		
	private JScrollPane panelActivite() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBackground(couleurFond);
		ArrayList<Activite> listeActivite = entreprise.getListeActiviteDuProjet(projet.getId());
		int taille = 5 - listeActivite.size();
		if (taille<0) {
			taille = 0;
		}
		p.setLayout(new GridLayout(listeActivite.size()+taille,1,10,10));

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
