
public class Ressource {
	protected String nom;
	protected String type;
	protected int id;
	
	
	public int getId() {//r�cup�ration de l'Id de chaque ressource pour les diff�rencier
		return id;
	}
	
	@Override
	public boolean equals(Object obj) { //test si deux ressources sont �gales
		if(obj instanceof Ressource && obj != null) {
			Ressource res = (Ressource)obj;
			return id == res.id;
		} else {
			return false;
		}
		
	}
	
}
