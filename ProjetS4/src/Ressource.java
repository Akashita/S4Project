
public class Ressource {
	protected String nom;
	protected String type;
	protected int id;
	
	
	public int getId() {//récupération de l'Id de chaque ressource pour les différencier
		return id;
	}
	public boolean equals(Ressource ressource) { //test si deux ressources sont égales
		return (this.getId() == ressource.getId());
	}
	
}
