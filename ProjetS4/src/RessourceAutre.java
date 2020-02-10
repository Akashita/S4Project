

public class RessourceAutre extends Ressource{
	
	public RessourceAutre(String nom,String type, int idRessource) {
		
		//Attribut classe m�re commune aux ressources.
		super.nom = nom;
		super.type = type;//ici on rajoute aussi un type car l'on rajoute un type qui n'est pas commun aux autres (il faudra d�finir un arraylist de type cr�er par l'utilisateur
		super.id = idRessource;
	}

	public String toString() {
		return "cette ressource personnalisable " + this.nom + " est un(e) " + this.type + " est matricul� " + this.id;
	}
	
}
