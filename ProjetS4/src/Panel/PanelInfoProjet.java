package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import EcouteurEvenement.SourisActiviteListener;
import EcouteurEvenement.SourisRessourceListener;
//import Fenetre.FenetreEmploiDuTemps;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;
import Ressource.Calculateur;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;

public class PanelInfoProjet extends JPanel{
	
	private Entreprise entreprise;
	private Projet projet;
	private Activite activite;
	private JTabbedPane onglet = new JTabbedPane();
	private int largeurDimension = 220;

	public PanelInfoProjet(Entreprise entreprise, Projet projet) {
		this.entreprise = entreprise;
		this.projet = projet;
		afficheInterface();
		
	}
	
	public void afficheInterface() {
		this.removeAll();
		this.setLayout(new BorderLayout());
		this.add(panelProjetActivite(), BorderLayout.WEST);
		onglet.add(projet.getNom(), projet.getTextArea());
		//this.add(panelJournal(), BorderLayout.CENTER);
	}

	private JPanel panelProjetActivite() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(panelProjet(), BorderLayout.WEST);
		if(activite!=null) {
			panel.add(panelActivite(), BorderLayout.EAST);
		}
		return panel;
	}
	
//	==================PANEL PROJET============================================
	/**
	 * Creer le panel qui affiche les informations du projet et sa liste d'activite
	 * @return panel
	 */
	private JPanel panelProjet() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(largeurDimension,this.getHeight()));
		panel.setLayout(new BorderLayout());
		panel.add(infoProjet(), BorderLayout.NORTH);
		panel.add(listeActivite(), BorderLayout.CENTER);
		return panel;		
	}
	
	/**
	 * Creer le panel qui affiche les informations du projet
	 * @return panel
	 */
	private JPanel infoProjet() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);	
		panel.setBorder(BorderFactory.createTitledBorder("Information du projet"));
		panel.add(creerLabel("Nom: "+projet.getNom()));
		panel.add(creerLabel("Priorité: "+projet.getPriorite()));
		panel.add(creerLabel("Chef du projet: pas encore implementé"));
		return panel;
	}
	
	/**
	 * Creer le panel qui  la liste d'activite du projet
	 * @return panel
	 */
	private JPanel listeActivite() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);	
		panel.setBorder(BorderFactory.createTitledBorder("Liste des activités"));

		ArrayList<Activite> listeActivite = projet.getListe();
		if (listeActivite.size() > 0) {
			for (int i=0; i<listeActivite.size(); i++) {
				Activite act = listeActivite.get(i);
				panel.add(panelActivite(act));
			}				
		}
		else {
			panel.add(creerLabel("aucune activité présente"));
		}
		return panel;
	}
	
	/**
	 * Creer le label qui affiche le titre de l'activite et le lit avec un listener.
	 * @return panel
	 */
	private JLabel panelActivite(Activite activite) {
		JLabel label = new JLabel(activite.getTitre());
		label.addMouseListener(new SourisActiviteListener(this, activite));
		return label;
	}

	/**
	 * quand on clique à une activite dans la liste, 
	 * on reaffiche le panel avec le panelActivité qui à été modifié
	 */
	public void activiteSelectionner(Activite activite) {
		this.activite = activite;
		this.removeAll();
		this.afficheInterface();
		this.ajouterOngletJournal();
		this.revalidate();
	}
	
//	==================PANEL ACTIVITE============================================	
	
	/**
	 * Creer le panel qui affiche les informations de l'activite et sa liste de ressource
	 * @return panel
	 */
	private JPanel panelActivite() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(largeurDimension,this.getHeight()));
		panel.setLayout(new BorderLayout());
		panel.add(infoActivite(), BorderLayout.NORTH);
		panel.add(listeRessource(), BorderLayout.CENTER);
		return panel;		
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
	    panel.add(creerLabel("Ordre: " + activite.getOrdre()));
	    panel.add(creerLabel("Commence le: " + activite.getJourDebut()));

		return panel;
	}

	/**
	 * Creer le panel qui affiche la liste de ressource de l'activite
	 * @return panel
	 */
	private JPanel listeRessource() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);		

		panel.setBorder(BorderFactory.createTitledBorder("Liste des ressources de l'activité"));
		if (activite.getLRessource().size() > 0) {
			if (activite.getListeRessourceType(Ressource.PERSONNE).size() > 0) {
				panel.add(afficheRessourceType(Ressource.PERSONNE));
			}
			if (activite.getListeRessourceType(Ressource.SALLE).size() > 0) {
				panel.add(afficheRessourceType(Ressource.SALLE));
			}
			if (activite.getListeRessourceType(Ressource.CALCULATEUR).size() > 0) {
				panel.add(afficheRessourceType(Ressource.CALCULATEUR));
			}
		}
		else {
			panel.add(creerLabel("aucune ressource présente"));
		}
		return panel;
	}

	/**
	 * Creer le panel qui affiche la liste de la ressource choisie
	 * @return panel
	 */
	private JPanel afficheRessourceType (String type) {
		JPanel panel = new JPanel ();
		panel.setPreferredSize(new Dimension(largeurDimension,this.getHeight()));
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);	
		ArrayList<Ressource> listeR = activite.getListeRessourceType(type);
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
		return panel;
	}

//	==================PANEL JOURNAL============================================	
	
	/**
	 * Creer le panel qui affiche la liste des journals sous forme d'onglet 
	 * et le champ de texte du journal selectionner
	 * @return panel
	 */
	private JPanel panelJournal() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(onglet, BorderLayout.CENTER);
		return panel;
	}
	
	public void ajouterOngletJournal() {
		entreprise.selectionnerActivite(activite);
		onglet.add(activite.getTitre(), activite.getTextArea());
	}

	

//	==================METHODE GENERAL============================================	
	
	private JLabel creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		//label.setFont(new Font("Arial", Font.BOLD, 15));
		return label;
	}

	public void selectionnerActivite(int idActiviteSelectionner) {
		entreprise.deselectionnerActivite();
		entreprise.selectionnerActivite(activite);
	} 
	
	public String getProjetNom() {
		return projet.getNom();
	}
	
	public Projet getProjet() {
		return projet;
	}
}
