
public class Ressource {
	protected String nom;
	protected String type;
	protected int id;
	
	
	public int getId() {//r�cup�ration de l'Id de chaque ressource pour les diff�rencier
		return id;
	}
	public boolean equals(Ressource ressource) { //test si deux ressources sont �gales
		return (this.getId() == ressource.getId());
	}
	
}
