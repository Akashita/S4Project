import javax.swing.*;

public class FenetreNouvelleRessource extends JFrame{
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	Entreprise entreprise;
	
	public FenetreNouvelleRessource(Entreprise entreprise) {
		this.entreprise = entreprise;
		
		creationRessource(choixType());
	}
	
	private String choixType() {
	    String[] toutLesTypes = {"Salarié", "Salle", "Calculateur"};
	    String typeChoisi = (String) JOptionPane.showInputDialog(null, 
	      "Veuillez indiquer le type de la ressource!",
	      "Creer une ressource",
	      JOptionPane.QUESTION_MESSAGE,
	      null,
	      toutLesTypes,
	      toutLesTypes[0]);
	    return typeChoisi;
	}
	
	private void creationRessource(String type) {
		if (type == "Salarié") {
			creationSalarie();
		}
		if (type == "Salle") {
			creationSalle();
		}
		if (type == "Calculateur") {
			creationCalculateur();
		}
	}
	
	private void creationSalarie() {
		String nom, prenom;
		String[] toutLesRoles = {"Administrateur", "Chef de Projet", "Collaborateur"};
	    
	    nom = JOptionPane.showInputDialog(null, "Veuillez ecrire son nom", "Ajouter un salarié", JOptionPane.QUESTION_MESSAGE);
	    prenom = JOptionPane.showInputDialog(null, "Veuillez ecrire son prenom", "Ajouter un salarié", JOptionPane.QUESTION_MESSAGE);
	    
	    String roleChoisi = (String) JOptionPane.showInputDialog(null, 
	      "Veuillez indiquer le role de la ressource",
	      "Ajouter un salarié",
	      JOptionPane.QUESTION_MESSAGE,
	      null,
	      toutLesRoles,
	      toutLesRoles[0]);

		entreprise.nouvPersonne(nom, prenom, roleChoisi);	 
	}
	
	private void creationSalle() {
		String nom;
		int capacite;
		
	    nom = JOptionPane.showInputDialog(null, "Veuillez ecrire son nom", "Ajouter une salle", JOptionPane.QUESTION_MESSAGE);
	    capacite = Integer.parseInt((String)JOptionPane.showInputDialog(null, "Veuillez indiquer sa capacité", "Ajouter une salle", JOptionPane.QUESTION_MESSAGE));
		entreprise.nouvSalle(nom, capacite);	 
	}

	private void creationCalculateur() {
		String nom;
		
	    nom = JOptionPane.showInputDialog(null, "Veuillez ecrire son nom", "Ajouter un calculateur", JOptionPane.QUESTION_MESSAGE);
		entreprise.nouvCalculateur(nom);;	 
	}

}
