
public class Ressource {
	protected String nom;
	protected String type;
	protected Boolean dispo; //booléen si la ressource est utilisé ou vacante
	protected Projet Projet;
	protected int id;
	

	
	public String getNom() {//récupération du type
		return this.nom;
	}

	public String getTye() {//récupération du type
		return this.type;
	}
	
	public Boolean getDispo() {//récupération de la disponibilité d'une ressource
		return this.dispo;
	}
	public int getId() {//récupération de l'Id de chaque ressource pour les différencier
		return this.id;
	}
	public Projet getProjet() {
		return this.Projet;
	}
	
	public void setProjet(Projet projetCour) {
		this.Projet = projetCour;
	}
	
	public void unsetProjet() {
		this.Projet = null;
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
	
	//méthodes pour modifier la disponibilité des ressources
	public void rendDisponible() {
		this.dispo = true;
	}
	
	public void rendIndisponible() {
		this.dispo = false;
	}
	
}
