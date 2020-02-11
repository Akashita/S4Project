import javax.swing.*;

public class FenetreNouvelleRessource extends JFrame{
	Entreprise entreprise;
	
	public FenetreNouvelleRessource(Entreprise entreprise) {
		this.entreprise = entreprise;
		
		creationRessource(choixType());
	}
	
	private String choixType() {
	    String[] toutLesTypes = {Ressource.PERSONNE, Ressource.SALLE, Ressource.CALCULATEUR};
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
		if (type == Ressource.PERSONNE) {
			creationSalarie();
		}
		if (type == Ressource.SALLE) {
			creationSalle();
		}
		if (type == Ressource.CALCULATEUR) {
			creationCalculateur();
		}
	}
	
	private void creationSalarie() {
		String nom, prenom;
		String[] toutLesRoles = {Personne.ADMINISTRATEUR, Personne.CHEFDEPROJET, Personne.COLLABORATEUR};
	   
		prenom = JOptionPane.showInputDialog(null, "Veuillez ecrire son prenom", "Ajouter un salarié", JOptionPane.QUESTION_MESSAGE);
	    nom = JOptionPane.showInputDialog(null, "Veuillez ecrire son nom", "Ajouter un salarié", JOptionPane.QUESTION_MESSAGE);
	    
	    String roleChoisi = (String) JOptionPane.showInputDialog(null, 
	      "Veuillez indiquer le role de la ressource",
	      "Ajouter un salarié",
	      JOptionPane.QUESTION_MESSAGE,
	      null,
	      toutLesRoles,
	      toutLesRoles[2]);

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
