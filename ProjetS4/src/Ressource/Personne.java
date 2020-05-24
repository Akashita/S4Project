package Ressource;

import java.util.ArrayList;

import Model.Projet;

public class Personne extends Ressource{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	public static final String COLLABORATEUR = "Collaborateur";
	public static final String CHEFDEPROJET = "Chef de projet";
	public static final String ADMINISTRATEUR = "Administrateur";
	public static final String DEBUG = "Debugger";
	
	private String prenom;
	private String mdp;
	private String role; //Role dans l'entreprise (voir constante ci-dessus)
	
	private ArrayList<Competence> listeCompetence = new ArrayList<Competence>();
	private ArrayList<Projet> listeProjet = new ArrayList<Projet>(); //Liste de projet que la ressource dirige
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEURS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Personne(String nom, String prenom, String role, int numSalarie, String mdp, ArrayList<Competence> listeCompetence){
		super(numSalarie, nom, Ressource.PERSONNE);
		this.mdp = mdp;
		this.role = role;
		this.prenom = prenom;
		this.listeCompetence = listeCompetence;
	}
	
	public Personne(String nom, String prenom, String role, int numSalarie){
		this(nom, prenom, role, numSalarie, "", null);
	}
	
	public Personne(String nom, String prenom, int numSalarie){
		this(nom, prenom, "", numSalarie);
	}

	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public String getRole() {
		return this.role;
	}

	
	public boolean estAdmin() {
		boolean b = false;
		if (role.equals(ADMINISTRATEUR)) {
			b = true;
		}
		return b;
	}
	
	public boolean estChef(Projet p) {
		boolean b = false;
		for (int i=0; i<listeProjet.size(); i++) {
			if (listeProjet.get(i).getId() == p.getId()) {
				b = true;
				break;
			}
		}
		return b;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
	
	public String getPrenomNom() {
		return this.prenom + " " + this.nom;
	}
	
	public String getLogin() {
		return nom+"#"+Integer.toString(id);
	}
	
	public String getMdp() {
		return mdp;
	}
	
	public ArrayList<Projet> getListeDeProjet() {
		return this.listeProjet;
	}

	public ArrayList<Competence> getListeDeCompetence() {
		return this.listeCompetence;
	}
	
	public boolean aDomaine(String domaine) {
		boolean b = false;
		for (int i=0; i<listeCompetence.size(); i++) {
			if (listeCompetence.get(i).getNom() == domaine) {
				b = true;
			}
		}
		return b;
	}
	

	
	//--------------------------------------------------------------------------------->>>>>>> Setteurs
	
	public void setPrenom(String p) {
		this.prenom = p;
	}

	public void setRole(String r) {
		this.role = r;
	}

	public void setMdp(String m) {
		this.mdp = m;
	}

	public void setListeCompetence(ArrayList<Competence> l) {
		this.listeCompetence = l;
	}

	
	public void enleverProjet(Projet projet) {
		for (int i=0; i<listeProjet.size(); i++) {
			if (projet.getId() == listeProjet.get(i).getId()) {
				listeProjet.remove(i);
				break;
			}
		}
	}
	
	public void ajouterProjet(Projet projet) {
		listeProjet.add(projet);
	}

	public String creeAffiche() {
		return "prenom : " + prenom+", nom : "+nom + ", id : " + this.id + ", role : " + this.role + ", mdp : " + this.mdp + ", login : " + this.getLogin();

	}
	
	//--------------------------------------------------------------------------------->>>>> toString
	
	@Override
	public String toString() {
			return prenom +" "+ nom ;
		}

	
	
}
