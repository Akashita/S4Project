package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisRessourceListener;
import Model.Activite;
import Model.Entreprise;
import Ressource.Calculateur;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;

public class PanelInfoActivite extends JPanel{

	private Entreprise entreprise;
	private Activite activite;


	public PanelInfoActivite (Entreprise entreprise, Activite activite) {
		this.entreprise = entreprise;
		this.activite = activite;
		afficheInterface();
	}

	public void afficheInterface() {
		this.removeAll();
		this.setLayout(new BorderLayout());
		this.add(infoActivite(), BorderLayout.NORTH);
		this.add(listeRessource(), BorderLayout.CENTER);
		this.add(emploiDuTempsActivite(), BorderLayout.SOUTH);
	}
	
	/**
	 * Creer le panel qui affiche les informations de l'activite 
	 * @return panel
	 */
	private JPanel infoActivite() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);		
	    panel.setBorder(BorderFactory.createTitledBorder(activite.getTitre()));
	    panel.add(creerLabel("Charge de travail: " + activite.getChargeJHomme() + " jour/homme"));
	    //panel.add(creerLabel("Ordre: " + activite.getOrdre()));
	    panel.add(creerLabel("Commence le: " + activite.getJourDebut()));
	
		return panel;
	}
	
	/**
	 * Creer le panel qui affiche la liste de ressource de l'activite
	 * @return panel
	 */
	private JPanel listeRessource() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		panel.setBackground(Color.WHITE);		
	
		panel.setBorder(BorderFactory.createTitledBorder("Liste des ressources de l'activité"));
				panel.add(afficheRessourceType(Ressource.PERSONNE));
				panel.add(afficheRessourceType(Ressource.SALLE));
				panel.add(afficheRessourceType(Ressource.CALCULATEUR));

		return panel;
	}
	
	/**
	 * Creer le panel qui affiche la liste de la ressource choisie
	 * @return panel
	 */
	private JPanel afficheRessourceType (String type) {
		JPanel panel = new JPanel ();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);	
		ArrayList<Ressource> listeR = activite.getListeRessourceType(type);
		if (listeR.size()>0) {
			panel.setBorder(BorderFactory.createTitledBorder("Liste de " + type));
			for(int i=0; i<listeR.size(); i++) {
				Ressource res = null;
				JLabel label = null;
				if (type == Ressource.PERSONNE) {
					res = (Personne)listeR.get(i);
					label = creerLabel(((Personne) res).getPrenom() + " " + res.getNom());					
				}
				if (type == Ressource.SALLE) {
					res = (Salle)listeR.get(i);
					label = creerLabel(res.getNom());					
					
				}
				if (type == Ressource.CALCULATEUR) {
					res = (Calculateur)listeR.get(i);
					label = creerLabel(res.getNom());					
					
				}
				label.addMouseListener(new SourisRessourceListener(entreprise, res));
				panel.add(label);	
			}
		}
		else {
			panel.add(creerLabel("aucune "+type+" présente")); 
		}
		return panel;
	}
	
	private JPanel emploiDuTempsActivite() {
		JPanel panel = new JPanel ();
		panel.add(creerLabel("ici l'emploi du temps de l'activité"));
		return panel;
	}
	
	private JLabel creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		return label;
	}


	public Activite getActivite() {
		return activite;
	}
}
