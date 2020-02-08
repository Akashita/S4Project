
public class Ressource {
	protected String nom;
	protected String type;
	protected Boolean dispo; //bool�en si la ressource est utilis� ou vacante
	protected int id;
	
	public String getNom() {//r�cup�ration du type
		return this.nom;
	}

	public String getTye() {//r�cup�ration du type
		return this.type;
	}
	
	public Boolean getDispo() {//r�cup�ration de la disponibilit� d'une ressource
		return this.dispo;
	}
	public int getId() {//r�cup�ration de l'Id de chaque ressource pour les diff�rencier
		return this.id;
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
	
	//m�thodes pour modifier la disponibilit� des ressources
	public void rendDisponible() {
		this.dispo = true;
	}
	
	public void rendIndisponible() {
		this.dispo = false;
	}
	
}
