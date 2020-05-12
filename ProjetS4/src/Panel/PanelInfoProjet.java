package Panel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import EcouteurEvenement.KeyActiviteListener;
import EcouteurEvenement.SourisActiviteListener;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;
import Ressource.Ressource;

public class PanelInfoProjet extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private Projet projet;
	private int test;
	
	public PanelInfoProjet(Entreprise entreprise) {
		this.entreprise = entreprise;
		this.projet = entreprise.getProjetSelectionner();
		if (projet != null) {
			this.setBackground(PanelPrincipal.BLANC);
			this.setLayout(new BorderLayout());
			this.add(afficheInterface(), BorderLayout.CENTER);
		}
	}
	
	public JPanel afficheInterface() {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLANC);
		panel.setLayout(new GridBagLayout());
		
		
		/* Le gridBagConstraints va definir la position et la taille des elements */
		GridBagConstraints gc = new GridBagConstraints();
		
		/* le parametre fill sert à definir comment le composant sera rempli GridBagConstraints.BOTH permet d'occuper tout l'espace disponible
		 * horizontalement et verticalement GridBagConstraints.HORIZONTAL maximise horizontalement GridBagConstraints.VERTICAL maximise verticalement
		 */
		gc.fill = GridBagConstraints.BOTH;
		
		/* insets definir la marge entre les composant new Insets(margeSuperieure, margeGauche, margeInferieur, margeDroite) */
		gc.insets = new Insets(5, 5, 5, 5);
		
		/* ipady permet de savoir où on place le composant s'il n'occupe pas la totalite de l'espace disponnible */
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		/* weightx definit le nombre de cases en abscisse */
		gc.weightx = 5;
		
		/* weightx definit le nombre de cases en ordonnee */
		int nbActivite = 5;
		gc.weighty = nbActivite+1;
		
		
		gc.gridx = 0;
		gc.gridy = 0;
		String nom = projet.getChefDeProjet().getPrenom() + " " + projet.getChefDeProjet().getNom();
		panel.add(creerLabel("Chef de projet: "+ nom), gc);
		gc.gridx = 1;
		panel.add(creerLabel("Priorite: "+(int)projet.getPriorite()), gc);
		gc.gridx = 2;
		
		LocalDate deadline = projet.getDeadline();
		String date = deadline.getDayOfMonth() + "/" + deadline.getMonthValue() + "/" + deadline.getYear();			

		panel.add(creerLabel("DeadLine: "+date), gc);

		for (int i=0; i<3; i++) {
			String type = null;
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
				panel.add(creerLabelInterfaceRessource(type), gc);
		}
		
		//affiche les activit�
		if (nbActivite > 0) {
			gc.gridy = 0;
			
			ArrayList<Activite> listeActivite = projet.getListe();
			gc.gridx = 0;
			gc.gridwidth = GridBagConstraints.REMAINDER;
			for (int i=0; i<nbActivite; i++) {
				gc.gridy ++;
				if (i<projet.getListe().size()) {
					Activite activite = listeActivite.get(i);							
					PanelInfoActivite pia = new PanelInfoActivite(entreprise, activite);
					pia.addMouseListener(new SourisActiviteListener(entreprise, activite));	
					panel.add(pia, gc);								
				}
				else {
					JPanel caseVide = new JPanel();
					caseVide.setBackground(PanelPrincipal.BLANC);
					panel.add(caseVide, gc);					
				}
			}
		}
		

		return panel;
	}
		
		private JPanel creerLabelInterfaceRessource(String type) {
			JPanel panel = new JPanel();
			panel.setBackground(PanelPrincipal.BLANC);		
		    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			JLabel labelTxt = new JLabel();
			JLabel labelIco = new JLabel();
			if (type == Ressource.PERSONNE) {
				labelIco = new JLabel(new ImageIcon("images/user_grey.png"));
				labelTxt = creerLabel("  "+Ressource.PERSONNE+"s", true);
			}
			if (type == Ressource.SALLE) {
				labelIco = new JLabel(new ImageIcon("images/door_grey.png"));
				labelTxt = creerLabel("  "+Ressource.SALLE+"s", true);					
			}
			if (type == Ressource.CALCULATEUR) {
				labelIco = new JLabel(new ImageIcon("images/computer_grey.png"));	
				labelTxt = creerLabel("  "+Ressource.CALCULATEUR+"s", true);	
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
