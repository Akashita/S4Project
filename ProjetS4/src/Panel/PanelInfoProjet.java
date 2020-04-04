package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import EcouteurEvenement.SourisRessourceListener;
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
	private int largeurDimension = 220;
	private JTabbedPane onglet = new JTabbedPane(JTabbedPane.LEFT);

	public PanelInfoProjet(Entreprise entreprise, Projet projet) {
		this.entreprise = entreprise;
		this.projet = projet;
		afficheInterface();
		
	}
	
	public void afficheInterface() {
		this.removeAll();
		this.setLayout(new BorderLayout());
		this.add(panelProjet(), BorderLayout.NORTH);
		this.add(panelActivite(), BorderLayout.CENTER);
	}
	
	private JPanel panelActivite() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(onglet, BorderLayout.CENTER);
		return panel;
	}

	
	public void ajouterActivite(PanelInfoActivite pia) {
		onglet.add(pia.getActivite().getTitre(), pia);
	}
	
	public PanelInfoActivite getPanelInfoActivite() {
		if (onglet.getSelectedComponent()!=null) {
			return (PanelInfoActivite) onglet.getSelectedComponent();
		}
		else {
			return null;
		}
	}
	/*private JPanel panelProjetActivite() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(panelProjet(), BorderLayout.WEST);
		if(activite!=null) {
			panel.add(panelActivite(), BorderLayout.EAST);
		}
		return panel;
	}*/
	
//	==================PANEL PROJET============================================
	/**
	 * Creer le panel qui affiche les informations du projet et sa liste d'activite
	 * @return panel
	 */
	private JPanel panelProjet() {
		JPanel panel = new JPanel();
		//panel.setPreferredSize(new Dimension(largeurDimension,this.getHeight()));
		panel.setLayout(new BorderLayout());
		panel.add(infoProjet(), BorderLayout.CENTER);
		//panel.add(listeActivite(), BorderLayout.CENTER);
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
	/*private JPanel listeActivite() {
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
	 *
	private JLabel panelActivite(Activite activite) {
		JLabel label = new JLabel(activite.getTitre());
		label.addMouseListener(new SourisActiviteListener(this, activite));
		return label;
	}

	/**
	 * quand on clique à une activite dans la liste, 
	 * on reaffiche le panel avec le panelActivité qui à été modifié
	 *
	public void activiteSelectionner(Activite activite) {
		this.activite = activite;
		this.removeAll();
		this.afficheInterface();
		this.revalidate();
	}*/
	
//	==================PANEL ACTIVITE============================================	
	
//	==================METHODE GENERAL============================================	
	
	private JLabel creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		return label;
	}

	
	public String getProjetNom() {
		return projet.getNom();
	}
	
	public Projet getProjet() {
		return projet;
	}
}
