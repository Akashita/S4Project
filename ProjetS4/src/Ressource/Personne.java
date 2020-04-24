package Ressource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

import Model.CreneauHoraire;
import Model.Projet;

public class Personne extends Ressource{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	private String prenom;
	private String mdp;
	//private Hashtable<String, String> competences;
	public static final String COLLABORATEUR = "Collaborateur";
	public static final String CHEFDEPROJET = "Chef de projet";
	public static final String ADMINISTRATEUR = "Administrateur";
	private String role;
	
	private ArrayList<Competence> listeCompetence = new ArrayList<Competence>();
	private ArrayList<Projet> listeProjet = new ArrayList<Projet>();
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEURS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Personne(String nom, String prenom, String role, int numSalarie){
		super(numSalarie, nom, "Personne");
		
		this.role = role; //Role dans l'entreprise (voir constante ci-dessus)
		this.prenom = prenom;
		
	}
	
	/*public Personne(String nom, String prenom, String role, int numSalarie, String mdp, Hashtable<String, String> competences){
		this(nom, prenom, role, numSalarie);
		this.mdp = mdp;
		this.competences = competences;		
	}*/
	
	public Personne(String nom, String prenom, int numSalarie){
		//attributs de la classe m�re.
		super(numSalarie, nom, "Personne");
		//attribut de la classe fille.
		this.prenom = prenom;		
	}

	public Personne(String nom, String prenom, String role, int numSalarie, String mdp, ArrayList<Competence> listeCompetence){
		super(numSalarie, nom, "Personne");
		this.role = role; //Role dans l'entreprise (voir constante ci-dessus)
		this.prenom = prenom;
		this.listeCompetence = listeCompetence;
	}
	
	//public 
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	public void ajouterProjet(Projet projet) {
		listeProjet.add(projet);
	}
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public String getRole() {
		return this.role;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
	
	public ArrayList<Projet> getListeDeProjet() {
		return this.listeProjet;
	}

	public ArrayList<Competence> getListeDeCompetence() {
		return this.listeCompetence;
	}
	
	//--------------------------------------------------------------------------------->>>>>>> Setteur
	
	public void enleverProjet(Projet projet) {
		for (int i=0; i<listeProjet.size(); i++) {
			if (projet.getId() == listeProjet.get(i).getId()) {
				listeProjet.remove(i);
				break;
			}
		}
	}

	//--------------------------------------------------------------------------------->>>>> toString
	@Override
	public String toString() {
			return prenom+" "+nom;
		}
	
	
}
