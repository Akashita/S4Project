import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FenetreNouveauProjet extends JFrame{
	Entreprise entreprise;
	
	public FenetreNouveauProjet(Entreprise entreprise) {
		this.entreprise = entreprise;
	    String nom = JOptionPane.showInputDialog(null, "Veuillez ecrire le nom du projet", "Creer un projet", JOptionPane.QUESTION_MESSAGE);
	    entreprise.creerProjet(nom);
	}
}
