
public class Personne extends Ressource{
	
	private String prenom;
	public final static String tabRole[] = {"collaborateur","chef de projet","administrateur"};
	private String role;
	
	Personne(String nom, String prenom, String role, int numSalarie){
		
		//attribut de la classe fille.
		this.role = role;
		this.prenom = prenom;
		//attributs de la classe mère.
		super.nom = nom;
		super.type = "personne";
		super.id = numSalarie;
	}
	
	
}
