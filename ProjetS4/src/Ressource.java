
public class Ressource {
	protected String nom;
	protected String type;
	protected int id;
	
	
	public int getId() {//récupération de l'Id de chaque ressource pour les différencier
		return id;
	}
	
	@Override
	public boolean equals(Object obj) { //test si deux ressources sont égales
		if(obj instanceof Ressource && obj != null) {
			Ressource res = (Ressource)obj;
			return id == res.id;
		} else {
			return false;
		}
		
	}
	
}
