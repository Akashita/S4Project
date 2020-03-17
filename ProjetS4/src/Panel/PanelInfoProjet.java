package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisActiviteListener;
import EcouteurEvenement.SourisProjetListener;
import EcouteurEvenement.SourisRessourceListener;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;
import Ressource.Personne;
import Ressource.Ressource;

public class PanelInfoProjet extends JPanel{
	
	Entreprise entreprise;
	
	public PanelInfoProjet(Entreprise entreprise) {
		this.entreprise = entreprise;
		if (entreprise.getProjetSelectionner() != null) {
			if (entreprise.getProjetSelectionner().getListe().size()>0) {//on vérifie qu'il y a au moins une activité
				afficheInterface();
			}
		}
	}
	
	private void afficheInterface() {
	    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	    
		this.add(afficheActivite());

		this.add(afficheRessource());
	}
	
	private JPanel afficheActivite() {
		JPanel panel = new JPanel ();
		panel.setBorder(BorderFactory.createTitledBorder("Liste des activités"));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);			

		Projet projet = entreprise.getProjetSelectionner();
		ArrayList<Activite> listeActivite = projet.getListe();

		for (int i=0; i<listeActivite.size(); i++) {
			Activite act = listeActivite.get(i);
			boolean selectionner = false;
			int idActiviteSelectionner = entreprise.getActiviteSelectionner().getId();
			if(act.getId() == idActiviteSelectionner) {
				selectionner = true;
			}
			panel.add(panelActivite(act, selectionner));
		}
		return panel;
	}


	private JPanel panelActivite(Activite act, boolean selectionner) {
		JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.addMouseListener(new SourisActiviteListener(this, act.getId()));
		if(selectionner) {
			panel.setBackground(Color.BLUE);
		}
		else {
			panel.setBackground(Color.WHITE);			
		}
	    panel.setBorder(BorderFactory.createTitledBorder(act.getTitre()));
	    panel.add(new JLabel("Charge de travail: " + act.getCharge() + " jour/homme"));
	    panel.add(new JLabel("Ordre: " + act.getOrdre()));
	    panel.add(new JLabel("Commence le: " + act.getJourDebut()));
		return panel;
	}
	
	private JPanel afficheRessource() {
		JPanel panel = new JPanel ();
		panel.setBorder(BorderFactory.createTitledBorder("Liste des ressources de l'activité"));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);		
		panel.add(creerLabel("                                    "));
		panel.add(personne());
		panel.add(salle());
		panel.add(calculateur());
		return panel;
	}

	private JPanel personne() {
		JPanel panel = new JPanel ();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);		
		if(entreprise.getActiviteSelectionner() != null) {
			panel.setBorder(BorderFactory.createTitledBorder("Liste des personnes"));
			panel.add(creerLabel("                                    "));
			Activite act = entreprise.getActiviteSelectionner();
			ArrayList<Ressource> listeR = act.getListeRessourceType(Ressource.PERSONNE);
			if (listeR.size() > 0) {
				for(int i=0; i<listeR.size(); i++) {
					Personne res = (Personne)listeR.get(i);
					panel.add(creerLabel(res.getPrenom() + " " + res.getNom()));
				}				
			}
		}		
		return panel;
	}

	private JPanel salle() {
		JPanel panel = new JPanel ();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);		
		if(entreprise.getActiviteSelectionner() != null) {
			panel.setBorder(BorderFactory.createTitledBorder("Liste des salles"));
			panel.add(creerLabel("                                    "));
			Activite act = entreprise.getActiviteSelectionner();
			ArrayList<Ressource> listeR = act.getListeRessourceType(Ressource.SALLE);
			if (listeR.size() > 0) {
				for(int i=0; i<listeR.size(); i++) {
					Ressource res = listeR.get(i);
					panel.add(creerLabel(res.getNom()));
				}
			}
		}		
		return panel;
	}

	private JPanel calculateur() {
		JPanel panel = new JPanel ();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(creerLabel("                                    "));
		panel.setBackground(Color.WHITE);		
		if(entreprise.getActiviteSelectionner() != null) {
			Activite act = entreprise.getActiviteSelectionner();
			ArrayList<Ressource> listeR = act.getListeRessourceType(Ressource.CALCULATEUR);
			if (listeR.size() > 0) {
				panel.setBorder(BorderFactory.createTitledBorder("Liste des calculateurs"));
				for(int i=0; i<listeR.size(); i++) {
					Ressource res = listeR.get(i);
					panel.add(creerLabel(res.getNom()));
					System.out.println(res.getNom());
				}			
			}
		}		
		return panel;
	}

	private JLabel creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		//label.addMouseListener(new SourisRessourceListener(this, label));
		return label;
	}

	public void selectionnerActivite(int idActiviteSelectionner) {
		entreprise.deselectionnerActivite();
		entreprise.selectionnerActivite(idActiviteSelectionner);
	} 
}
