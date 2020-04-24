package Fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Entreprise;
import Model.Projet;
import Panel.PanelEDTRessource;
import Panel.PanelInfoRessource;
import Ressource.Competence;
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
		this.add(new PanelInfoRessource(ressource), BorderLayout.NORTH);
		this.add(new PanelEDTRessource(ressource), BorderLayout.CENTER);
		//this.add(afficherEmploiDuTemps(), BorderLayout.CENTER);
		this.revalidate();

	}
	
	
	public int getIdRessource() {
		return ressource.getId();
	}
}