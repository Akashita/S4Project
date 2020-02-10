

public class Personne extends Ressource{
	
	private String prenom;
	public static final String COLLABORATEUR = "Collaborateur";
	public static final String CHEFDEPROJET = "Chef de projet";
	public static final String ADMINISTRATEUR = "Administrateur";
	private String role;
	
	Personne(String nom, String prenom, String role, int numSalarie){
		
		//attribut de la classe fille.
		this.role = role;
		this.prenom = prenom;
		//attributs de la classe m�re.
		super.nom = nom;
		super.type = "Personne";
		super.id = numSalarie;
	}
	public String getRole() {//r�cup�ration du r�le de la personne
		return this.role;
	}
	public String getPrenom() {//r�cup�ration du pr�nom de la personne
		return this.prenom;
	}
	
	
	@Override
	public String toString() {
		if (this.getProjet() != null) {
			return this.role + " de nom " + this.nom + " de pr�nom " + this.prenom + " participe � " + this.getProjet().getNom() +  " immatricul� " + this.id + ". ";
		}
		else {
			return this.role + " de nom " + this.nom + " de pr�nom " + this.prenom +  " immatricul� " + this.id + ". ";

		}
		}
	
	
}
