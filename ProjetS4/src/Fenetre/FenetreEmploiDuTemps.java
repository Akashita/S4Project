package Fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
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

import EcouteurEvenement.SourisEmploieDuTempsListener;
import Model.CreneauHoraire;
import Model.Entreprise;
import Model.Projet;
import Model.Temps;
import Ressource.Ressource;

public class FenetreEmploiDuTemps extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Ressource ressource;
	private int nbJour = 5;
	private	int nbHeure = 9;
	
	
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
		JPanel panel = new JPanel();		
		panel.setLayout(new BorderLayout());
		panel.add(afficheJour(), BorderLayout.NORTH);
		panel.add(afficheHeure(), BorderLayout.WEST);
		panel.add(afficheSemaine(), BorderLayout.CENTER);
		panel.setBackground(Color.BLACK);
		return panel;
	}
	
		
	private JPanel afficheJour() {
		nbJour = 5;
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
	
	private JPanel afficheHeure() {
		nbHeure = 9;
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

	private JPanel afficheSemaine() {
		ArrayList<CreneauHoraire>listeCreneau = ressource.getCreneauTravail(Temps.getJourSemaine());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,nbJour));
		for (int i=0; i<nbJour; i++) {
			panel.add(afficheJour(listeCreneau));
		}
		return panel;
	}
	
	private JPanel afficheJour(ArrayList<CreneauHoraire>listeCreneau) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(nbHeure,0));
		for (int i=0; i<nbHeure; i++) {
			int heure = i+8;
			LocalDateTime debut = null;
			LocalDateTime fin = null;
			if (listePlage.size() > i) {
				if(listePlage.get(i).getDebut().getHour() == heure) {
					panel.add(creerLabelAffiche(ressource.getNom(), ressource.getType(), projet.getNom()));
				}
				else {
					panel.add(creerLabelAjout("", "aucun", projet.getNom(), debut, fin));
				}				
			}
			else {
				panel.add(creerLabelAjout("", "aucun", projet.getNom(), debut, fin));			
			}
		}
		return panel;
	}
	
	private JLabel creerLabelAjout(String texte, String type, String nomProjet, LocalDate date) {
		JLabel label = new JLabel();
		label.setText(texte);
		label.setBackground(donneCouleur(type));
		label.addMouseListener(new SourisEmploieDuTempsListener(this, ressource, nomProjet, date));
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		label.setBorder(border);
		return label;
	}
	
	private JLabel creerLabelAffiche(String texte, String type, String nomProjet) {
		JLabel label = new JLabel();
		label.setText(texte);
		label.setBackground(donneCouleur(type));
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		label.setBorder(border);
		return label;
	}
	
	private Color donneCouleur(String type) {
		Color couleur = null;
		if (type == Ressource.PERSONNE) {
			couleur = Color.CYAN;
		}
		if (type == Ressource.SALLE) {
			couleur = Color.GREEN;
		}
		if (type == Ressource.CALCULATEUR) {
			couleur = Color.MAGENTA;
		}
		else {
			couleur = Color.WHITE;
		}
		return couleur;
	}
	

	private JLabel creerLabelInterface(String texte) {
		JLabel label = new JLabel();
		LocalDate tabDate[] = Temps.getJourSemaine(numeroSemaine);
		label.setText(texte + );
		label.setBackground(Color.GRAY);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		label.setBorder(border);
		return label;
	}


}
