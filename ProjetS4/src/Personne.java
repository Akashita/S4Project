
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
		//attributs de la classe mère.
		super.nom = nom;
		super.type = "Personne";
		super.id = numSalarie;
	}
	public String getRole() {//récupération du rôle de la personne
		return this.role;
	}
	public String getPrenom() {//récupération du prénom de la personne
		return this.prenom;
	}
	
	
	@Override
	public String toString() {
		return this.role + " de nom " + this.nom + " de prénom " + this.prenom + " immatriculé " + this.id + ". ";
	}
	
	
}
