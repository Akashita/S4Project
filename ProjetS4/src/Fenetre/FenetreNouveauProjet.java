package Fenetre;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.Entreprise;
import Panel.PanelProjet;

public class FenetreNouveauProjet extends JFrame{
	Entreprise entreprise;
	
	public FenetreNouveauProjet(Entreprise entreprise, PanelProjet pp) {
		this.entreprise = entreprise;
	    String nom = JOptionPane.showInputDialog(null, "Veuillez ecrire le nom du projet", "Creer un projet", JOptionPane.QUESTION_MESSAGE);
	    pp.nouveauProjet(entreprise,nom);
	}
}
