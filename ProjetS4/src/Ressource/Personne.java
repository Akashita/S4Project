package Ressource;


public class Personne extends Ressource{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	private String prenom;
	public static final String COLLABORATEUR = "Collaborateur";
	public static final String CHEFDEPROJET = "Chef de projet";
	public static final String ADMINISTRATEUR = "Administrateur";
	private String role;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEURS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Personne(String nom, String prenom, String role, int numSalarie){
		super(numSalarie, nom, "Personne");
		
		this.role = role; //Role dans l'entreprise (voir constante ci-dessus)
		this.prenom = prenom;
		
	}
	public Personne(String nom, String prenom, int numSalarie){
		//attributs de la classe m�re.
		super(numSalarie, nom, "Personne");
		//attribut de la classe fille.
		this.prenom = prenom;		
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public String getRole() {
		return this.role;
	}
	public String getPrenom() {
		return this.prenom;
	}
	
	//--------------------------------------------------------------------------------->>>>> toString
	@Override
	public String toString() {
			return "Personne : (" + id +") " + super.nom + " " + prenom + ", " + role + "";
		}
	
	
}
