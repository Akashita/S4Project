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
import Model.Entreprise;
import Model.Projet;
import Ressource.Plage;
import Ressource.Ressource;

public class FenetreEmploiDuTemps extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Ressource ressource;
	private Projet projet;
	private int jourHomme;
	private int pourcent;
	
	private final int nbHeure = 9, nbJour = 5; //on travaille de 8h à 17h et du lundi au vendredi
	private int numeroSemaine = Temps.getSemaine();
	
	public FenetreEmploiDuTemps(Ressource ressource, Projet projet, int jourHomme, int pourcent) {
		this.ressource = ressource;
		this.projet = projet;
		this.jourHomme = jourHomme;
		this.pourcent = pourcent;
		
		this.setSize(700, 550);
		this.setLayout(new BorderLayout());
		this.add(afficherEmploiDuTemps(), BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetre(this));
		this.setVisible(true);
	}

	
	public JPanel afficherEmploiDuTemps() {
		JPanel panel = new JPanel();		
		ArrayList<Plage>listePlage = ressource.getPlages();
		panel.setLayout(new BorderLayout());
		panel.add(afficheJour(), BorderLayout.NORTH);
		panel.add(afficheHeure(), BorderLayout.WEST);
		panel.add(afficheSemaine(listePlage), BorderLayout.CENTER);
		panel.setBackground(Color.BLACK);
		return panel;
	}
	
	
	private JPanel afficheSemaine(ArrayList<Plage>listePlage) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,nbJour));
		for (int i=0; i<nbJour; i++) {
			panel.add(afficheJour(listePlage));
		}
		return panel;
	}
	
	private JPanel afficheJour(ArrayList<Plage>listePlage) {
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
	
	private JPanel afficheHeure() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(nbHeure,0));
		for (int i=0; i<nbHeure; i++) {
			JPanel panelHeure = new JPanel();
			panelHeure.setLayout(new GridLayout(2,0));
			panelHeure.add(creerLabelInterface(Integer.toString(i+8)+"h"));
			panelHeure.add(creerLabelInterface(Integer.toString(i+9)+"h"));
			panel.add(panelHeure);
		}
		return panel;
	}
		
	private JPanel afficheJour() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,nbJour));
		for (int i=0; i<nbJour; i++) {
			panel.add(creerLabelInterface(afficheJour(i)));
		}
		return panel;
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

	private String afficheJour(int indiceJour) {
		String jour = null;
		switch(indiceJour) {
		case(0): jour = "lundi";
		break;
		case(1): jour = "mardi";
		break;
		case(2): jour = "mercredi";
		break;
		case(3): jour = "jeudi";
		break;
		case(4): jour = "vendredi";
		break;
		}
		return jour;
	}

}
