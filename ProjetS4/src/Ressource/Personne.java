package Ressource;

import java.util.ArrayList;

import Model.Projet;

public class Personne extends Ressource{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	private String prenom;
	public static final String COLLABORATEUR = "Collaborateur";
	public static final String CHEFDEPROJET = "Chef de projet";
	public static final String ADMINISTRATEUR = "Administrateur";
	private String role;
	private ArrayList<Projet> listeDeProjet = new ArrayList<Projet>();
	
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
	
	public void ajouterProjet(Projet projet) {
		listeDeProjet.add(projet);
	}
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public String getRole() {
		return this.role;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
	
	public ArrayList<Projet> getListeDeProjet() {
		return this.listeDeProjet;
	}
	
	//--------------------------------------------------------------------------------->>>>> toString
	@Override
	public String toString() {
			return prenom+" "+nom;
		}
	
	
}
