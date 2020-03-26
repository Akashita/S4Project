package Fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

//import EcouteurEvenement.SourisEmploieDuTempsListener;
import Model.CreneauHoraire;
import Model.Entreprise;
import Model.Projet;
import Model.Temps;
import Ressource.Ressource;

public class FenetreEmploiDuTemps extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Ressource ressource;
	
	
	public FenetreEmploiDuTemps(Ressource ressource) {
		this.ressource = ressource;
		this.setSize(700, 550);
		this.setLayout(new BorderLayout());
		this.add(afficherEmploiDuTemps(), BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetre(this));
		this.setVisible(true);
	}

	
	private JPanel afficherEmploiDuTemps() {
		CreneauHoraire [][] tableauCreneau = ressource.getSemaineEDT(Temps.getAnnee(), Temps.getSemaine());
		JPanel panel = new JPanel();		
		panel.setLayout(new BorderLayout());
		panel.add(afficheJourDeLaSemaine(tableauCreneau.length), BorderLayout.NORTH);
		panel.add(afficheHeure(tableauCreneau[0].length), BorderLayout.WEST);
		panel.add(afficheSemaine(tableauCreneau), BorderLayout.CENTER);
		panel.setBackground(Color.BLACK);
		return panel;
	}
	
	
	private JPanel afficheJourDeLaSemaine(int nbJour) {
		LocalDate[] jours = Temps.getJourSemaine();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,nbJour));
		for (int i=0; i<nbJour; i++) {
			LocalDate jourActuel = jours[i];
			String jour = jourActuel.getDayOfWeek().toString();
			String date = jourActuel.getDayOfMonth() + "/" + jourActuel.getMonthValue() + "/" + jourActuel.getYear();
			
			panel.add(creerLabelInterface(jour));
			panel.add(creerLabelInterface(date));
		}
		return panel;
	}

	
	private JPanel afficheHeure(int nbHeure) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(nbHeure,0));
		for (int i=0; i<nbHeure; i++) {
			JPanel panelHeure = new JPanel();
			panelHeure.setLayout(new GridLayout(2,0));
			panelHeure.add(creerLabelInterface(Integer.toString(i+8)+"h"));
			//panelHeure.add(creerLabelInterface(Integer.toString(i+9)+"h"));
			panel.add(panelHeure);
		}
		return panel;
	}

	private JPanel afficheSemaine(CreneauHoraire [][] tableauCreneau) {
		JPanel panel = new JPanel();
		int nbJour = tableauCreneau.length;
		panel.setLayout(new GridLayout(0, nbJour));
		for (int i=0; i<nbJour; i++) {
			panel.add(afficheJour(tableauCreneau[i]));
		}
		return panel;
	}
	
	private JPanel afficheJour(CreneauHoraire [] tableauCreneau) {
		JPanel panel = new JPanel();
		int nbHeure = tableauCreneau.length;
		panel.setLayout(new GridLayout(nbHeure,0));
		for (int i=0; i<nbHeure; i++) {
			CreneauHoraire creneau = tableauCreneau[i];
			if (creneau != null) {
				panel.add(creerLabelCreneau(creneau));
			}
		}
		return panel;
	}


	private JLabel creerLabelCreneau(CreneauHoraire creneau) {
		JLabel label = new JLabel();
		if (creneau != null) {
			label.setText(creneau.getTitre());
			label.setOpaque(true);
			label.setBackground(Color.GREEN);			
		}
		return label;
	}
	
	

	private JLabel creerLabelInterface(String texte) {
		JLabel label = new JLabel(texte);
		label.setBackground(Color.GRAY);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		label.setBorder(border);
		return label;
	}


}
