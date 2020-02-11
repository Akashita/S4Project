

public class RessourceAutre extends Ressource{
	
	public RessourceAutre(String nom,String type, int idRessource) {
		
		//Attribut classe m�re commune aux ressources.
		super(idRessource, nom, type);
	}

	public String toString() {
		return "cette ressource personnalisable " + this.nom + " est un(e) " + this.type + " est matricul� " + this.id;
	}
	
}
