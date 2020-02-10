

public class Ressource {
	protected String nom;
	public static final String PERSONNE = "Personne";
	public static final String SALLE = "Salle";
	public static final String CALCULATEUR = "Calculateur";
	protected String type;
	protected Boolean dispo; //bool�en si la ressource est utilis� ou vacante
	protected Projet Projet;
	protected int id;
	

	
	public String getNom() {//r�cup�ration du type
		return this.nom;
	}

	public String getType() {//r�cup�ration du type
		return this.type;
	}
	
	public Boolean getDispo() {//r�cup�ration de la disponibilit� d'une ressource
		return this.dispo;
	}
	public int getId() {//r�cup�ration de l'Id de chaque ressource pour les diff�rencier
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
