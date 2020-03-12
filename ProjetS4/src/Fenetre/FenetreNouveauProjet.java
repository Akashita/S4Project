package Fenetre;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.Entreprise;
import Panel.PanelProjet;

/**
 * Cette fenetre permet la creation d'un projet
 * 
 * @author damien planchamp
 *
 */
public class FenetreNouveauProjet extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Entreprise entreprise;
	
	public FenetreNouveauProjet(Entreprise entreprise, PanelProjet pp) {		
		this.addWindowListener(new FermerFenetre(this));

		this.entreprise = entreprise;
	    String nom = JOptionPane.showInputDialog(null, "Veuillez ecrire le nom du projet", "Creer un projet", JOptionPane.QUESTION_MESSAGE);
	    pp.nouveauProjet(nom);
	}
}
