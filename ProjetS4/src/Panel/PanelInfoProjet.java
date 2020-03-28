package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private JPanel paneCompletlInfoAct = new JPanel();
	private JPanel panelInfoAct = new JPanel();

	public PanelInfoProjet(Entreprise entreprise, Projet projet) {
		this.entreprise = entreprise;
		this.projet = projet;
		afficheInterface();
		
	}
	
	public void afficheInterface() {
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(infoProjet());
		paneCompletlInfoAct.setLayout(new BoxLayout(paneCompletlInfoAct, BoxLayout.X_AXIS));
		paneCompletlInfoAct.add(panelInfoAct);
		this.add(paneCompletlInfoAct);
		if (projet.getListe().size()>0) {//on vérifie qu'il y a au moins une activité
			infoAct();
		}
		this.revalidate();	

	}
	
	private JPanel infoProjet() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Information du projet"));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);		
		panel.add(creerLabel("Nom: "+projet.getNom()));
		panel.add(creerLabel("Priorité: "+projet.getPriorite()));
		panel.add(creerLabel("Chef du projet: pas encore implementé"));

		return panel;
	}
	
	private void infoAct() {
		paneCompletlInfoAct.remove(panelInfoAct);
		panelInfoAct = new JPanel();
		panelInfoAct.add(afficheActivite());
		panelInfoAct.add(afficheRessource());
		paneCompletlInfoAct.add(panelInfoAct);
		this.revalidate();
	}
	
	private JPanel afficheActivite() {
		JPanel panel = new JPanel ();
		panel.setBorder(BorderFactory.createTitledBorder("Liste des activités"));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);			

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
	    panel.add(new JLabel("Charge de travail: " + act.getChargeJHomme() + " jour/homme"));
	    panel.add(new JLabel("Ordre: " + act.getOrdre()));
	    panel.add(new JLabel("Commence le: " + act.getJourDebut()));
		return panel;
	}
	
	private JPanel afficheRessource() {
		JPanel panel = new JPanel ();
		if(entreprise.getActiviteSelectionner() != null) {
			panel.setBorder(BorderFactory.createTitledBorder("Liste des ressources de l'activité"));
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBackground(Color.WHITE);		
			//panel.add(creerLabel("                                    "));

			Activite act = entreprise.getActiviteSelectionner();
			if (act.getLRessource().size() > 0) {
				panel.add(afficheRessourceType(act, Ressource.PERSONNE));
				panel.add(afficheRessourceType(act, Ressource.SALLE));
				panel.add(afficheRessourceType(act, Ressource.CALCULATEUR));
			}
			else {
				panel.add(creerLabel("aucune ressource présente"));
			}
		}
		return panel;
	}

	private JPanel afficheRessourceType (Activite act, String type) {
		JPanel panel = new JPanel ();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);	
		ArrayList<Ressource> listeR = act.getListeRessourceType(type);
		if (listeR.size() > 0) {
			//panel.add(creerLabel("                                    "));
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
		return panel;
	}
	
	private JLabel creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		//label.addMouseListener(new SourisRessourceListener(this, label));
		return label;
	}

	/*public void afficheInfoRessource(Ressource res) {
		//this.removeAll();
		afficheInterface();
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Information de la ressource: "));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);	
		if(res.getType() == Ressource.PERSONNE) {
			panel.add(creerLabel("Nom: " + ((Personne) res).getPrenom() + " " + res.getNom()));
			panel.add(creerLabel("Compétence: "));			

		}
		if(res.getType() == Ressource.SALLE) {
			panel.add(creerLabel("Nom: " + res.getNom()));
			panel.add(creerLabel("Capacité: " + ((Salle)res).getCapacite()));			
		}
		if(res.getType() == Ressource.CALCULATEUR) {
			panel.add(creerLabel("Nom: " + res.getNom()));	
		}
		panel.add(bouttonEmploiDuTemps(res));
		panelInfoAct.add(panel);
		this.revalidate();
	}

	private JButton bouttonEmploiDuTemps(Ressource res) {
		JButton bouton = new JButton("Afficher emploi du temps");
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	new FenetreEmploiDuTemps(res);
	        }
	    });			
	    return bouton;
	}*/

	public void selectionnerActivite(int idActiviteSelectionner) {
		entreprise.deselectionnerActivite();
		entreprise.selectionnerActivite(idActiviteSelectionner);
	} 
	
	public String getProjetNom() {
		return projet.getNom();
	}
	
	public Projet getProjet() {
		return projet;
	}
}
