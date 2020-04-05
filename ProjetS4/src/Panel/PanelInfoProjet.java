package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import EcouteurEvenement.SourisActiviteListener;
import EcouteurEvenement.SourisProjetListener;
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

	public PanelInfoProjet(Entreprise entreprise) {
		this.entreprise = entreprise;
		this.projet = entreprise.getProjetSelectionner();
		if (projet != null) {
			afficheInterface();
		}
	}
	
	public void afficheInterface() {
		this.removeAll();
		this.setLayout(new BorderLayout());
		this.add(marge(), BorderLayout.WEST);
		this.add(marge(), BorderLayout.EAST);
		this.add(marge(), BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		panel.setLayout(new  BorderLayout());
		panel.add(infoProjet(), BorderLayout.NORTH);
		panel.add(panelActivite(), BorderLayout.CENTER);
		this.add(panel, BorderLayout.CENTER);

	}
	
	private JPanel marge() {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLANC);	
		panel.setPreferredSize(new Dimension(20,20));
		return panel;
	}

//	==================PANEL PROJET============================================
	
	/**
	 * Creer le panel qui affiche les informations du projet
	 * @return panel
	 */
	private JPanel infoProjet() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(this.getWidth(),200));
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.WHITE);	

		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 0;
		panel.add(creerLabel("Chef de projet: pas encore implementé"), c);

		c.gridx = 1;
		c.gridwidth = 1;
		c.gridy = 0;
		panel.add(creerLabel("Priorité: "+(int)projet.getPriorite()), c);

		c.gridx = 2;
		c.gridwidth = 1;
		c.gridy = 0;
		panel.add(creerLabel("DeadLine: pas encore implementé"), c);

		return panel;
	}
	
//	==================PANEL ACTIVITE============================================	
	
	private JPanel panelActivite() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(listeActivite(), BorderLayout.WEST);
		panel.add(new PanelInfoActivite(entreprise), BorderLayout.CENTER);
		return panel;
	}

	private JPanel listeActivite() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(180, this.getHeight()));
		panel.setBackground(PanelPrincipal.BLANC);	
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	
		ArrayList<Activite> listeActivite = projet.getListe();
		if (listeActivite.size() > 0) {
			for (int i=0; i<listeActivite.size(); i++) {
				Activite act = listeActivite.get(i);
				boolean selectionner = false;
				if (entreprise.getActiviteSelectionner() != null) {
					if (act.getId() == entreprise.getActiviteSelectionner().getId()) {
						selectionner = true;
					}					
				}
				panel.add(creerLabel(act, selectionner));
			}				
		}
		else {
			panel.add(new JLabel("aucune activité présente"));
		}
		return panel;
	}

	private JPanel creerLabel(Activite activite, boolean selectionner) {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLANC);
		//panel.setPreferredSize(new Dimension( this.getWidth(),130));
		panel.setLayout(new BorderLayout());
		JLabel label = new JLabel(activite.getTitre());
		if(selectionner) {	
			label.setFont(new Font("Arial", Font.BOLD, 30));
			label.setForeground(PanelPrincipal.BLEU1);
		}
		else {
			label.setFont(new Font("Arial", Font.PLAIN, 30));
			label.setForeground(PanelPrincipal.BLEU2);			
		}
		label.addMouseListener(new SourisActiviteListener(entreprise, activite));
	    label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		return panel;
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
