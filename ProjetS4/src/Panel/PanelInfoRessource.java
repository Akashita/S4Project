package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Projet;
import Ressource.Competence;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;

public class PanelInfoRessource extends JPanel{

	private Ressource ressource;
	private Color couleurFond;

	
	public PanelInfoRessource (Ressource ressource) {
		this.ressource = ressource;
		couleurFond = PanelPrincipal.BLEU3;
		this.setBackground(couleurFond);
		creerInterface();
	}
	
	public int getIdRessource() {
		return ressource.getId();
	}

	
	public void creerInterface() {
		this.setBackground(couleurFond);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();

		gcInfo(gc);
		
		if (ressource.getType() == Ressource.PERSONNE) {
			gcCompetence(gc);
			gcChefDeProjet(gc);
		}
		
		
	}
	
	private void gcInfo(GridBagConstraints gc) {
		gc.insets = new Insets(5, 5, 5, 0);
		
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		String nom;
		String role = "Collaborateur";
		String capacite = "0";
		if (ressource.getType() == Ressource.PERSONNE) {
			if (((Personne) ressource).getRole() == Personne.ADMINISTRATEUR) {
				role = "Administrateur";
			}
			nom = ((Personne) ressource).getPrenom() + " " + ressource.getNom();
		}
		else {
			nom = ressource.getNom();
		}
		
		if (ressource.getType() == Ressource.SALLE) {
			capacite = Integer.toString(((Salle) ressource).getCapacite());
		}

		this.add(labelInfo("Nom : " + nom), gc);
		
		if (ressource.getType() == Ressource.PERSONNE) {
			gc.gridy ++;
			this.add(labelInfo("Role : " + role), gc);			
		}
		
		if (ressource.getType() == Ressource.SALLE) {
			gc.gridy ++;
			this.add(labelInfo("Capacité : " + capacite), gc);			
		}
		

		gc.gridy ++;
		this.add(labelInfo("Type : " + ressource.getType()), gc);

	}
	
	private void gcCompetence(GridBagConstraints gc) {
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.gridx ++;
		gc.gridy = 0;
		
		this.add(labelTitreColonne("COMPETENCES"),gc);
		//this.add(panelTitreInfoColonne("COMPETENCES", "domaine", "niveau"), gc);

		ArrayList<Competence> competences = ((Personne) ressource).getListeDeCompetence();
		ArrayList<String> listeDomaine = new ArrayList<String>();
		ArrayList<String> listeNiveau = new ArrayList<String>();
		for (int i=0; i<competences.size(); i++) {
			listeDomaine.add(competences.get(i).getNom());
			listeNiveau.add(competences.get(i).getStringNiveau());
		}	
		gc.gridy =1;
		gc.gridheight = GridBagConstraints.REMAINDER;
		this.add(panelContenuColonne(listeDomaine, listeNiveau), gc);
	}
	
	private void gcChefDeProjet(GridBagConstraints gc) {
		gc.gridx ++;
		gc.gridy = 0;
		this.add(labelTitreColonne("Chef des projets"), gc);

		ArrayList<Projet> projets = ((Personne) ressource).getListeDeProjet();
		ArrayList<String> listeProjet = new ArrayList<String>();
		for (int i=0; i<projets.size(); i++) {
			listeProjet.add(projets.get(i).getNom());
		}	
		gc.gridy ++;
		gc.gridheight = GridBagConstraints.REMAINDER;
		this.add(panelContenuColonne(listeProjet), gc);		
	}
		
	
	private JPanel labelInfo(String nom) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);		
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
	
	private JLabel labelTitreColonne(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		return label;
	}
			
	private JLabel labelInfoColonne(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 14));
		return label;
	}

	private JPanel panelContenuColonne(ArrayList<String> liste1, ArrayList<String> liste2) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.insets = new Insets(0, 8, 0, 10);
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		panel.add(labelInfoColonne("Domaine"), gc);
		gc.gridx = 1;
		panel.add(labelInfoColonne("Niveau"), gc);

		gc.insets = new Insets(0, 8, 0, 5);
		gc.gridx = 0;
		gc.gridy = 1;
		panel.add(panelContenuColonne(liste1), gc);
		gc.gridx = 1;
		panel.add(panelContenuColonne(liste2), gc);
		return panel;
	}
	
	private JPanel panelContenuColonne(ArrayList<String> liste) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		for (int i=0; i<liste.size(); i++) {
			panel.add(labelContenusColonne(liste.get(i)));

		}
		return panel;
	}

	private JLabel labelContenusColonne(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		return label;
	}


}
