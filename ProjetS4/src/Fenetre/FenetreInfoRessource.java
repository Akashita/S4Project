package Fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Entreprise;
import Panel.PanelEDT;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;

public class FenetreInfoRessource extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ressource ressource;

	public FenetreInfoRessource(Entreprise entreprise, Ressource ressource) {
		super(entreprise.getFenetrePrincipale(), "Information de la ressource");
		this.ressource = ressource;
		this.setSize(800,770);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetre(this));
	    this.setResizable(false);
	    creationInterface();
		this.setVisible(true);

	}
	




	private void creationInterface() {
		this.setLayout(new BorderLayout());
		this.add(afficheInfoRessource(), BorderLayout.NORTH);
		this.add(new PanelEDT(ressource), BorderLayout.CENTER);
		//this.add(afficherEmploiDuTemps(), BorderLayout.CENTER);
		this.revalidate();

	}
	
	private JPanel afficheInfoRessource() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Information de la ressource: "));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);	
		if(ressource.getType() == Ressource.PERSONNE) {
			panel.add(creerLabel("Nom: " + ((Personne) ressource).getPrenom() + " " + ressource.getNom()));
			panel.add(creerLabel("Compétence: "));			
	
		}
		if(ressource.getType() == Ressource.SALLE) {
			panel.add(creerLabel("Nom: " + ressource.getNom()));
			panel.add(creerLabel("Capacité: " + ((Salle)ressource).getCapacite()));			
		}
		if(ressource.getType() == Ressource.CALCULATEUR) {
			panel.add(creerLabel("Nom: " + ressource.getNom()));	
		}
		return panel;
	}

	
	private JLabel creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		//label.addMouseListener(new SourisRessourceListener(this, label));
		return label;
	}
	
	public int getIdRessource() {
		return ressource.getId();
	}
}