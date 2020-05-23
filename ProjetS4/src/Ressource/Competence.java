package Ressource;

public class Competence {
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public final static int DEBUTANT = 1, CONFIRME = 2, EXPERT = 3;
	
	private int numSalarie; //Set to -1 si undefined
	private String nom;
	private int niveau;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEURS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	public Competence (int numSalarie, String nom, int niveau) {
		this.nom = nom;
		this.niveau = niveau;
		this.numSalarie = numSalarie;
	}
	
	public Competence (String nom, int niveau) {
		this(-1, nom, niveau);
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public String getNom() {
		return nom;
	}
	
	public int getNiveau() {
		return niveau;
	}
	public int getNumSalarie() {
		return numSalarie;
	}
	
	public String getStringNiveau() {
		String niveau = "";
		if (this.niveau == DEBUTANT) {
			niveau = "DEBUTANT";
		}
		if (this.niveau == CONFIRME) {
			niveau = "CONFIRME";
		}
		if (this.niveau == EXPERT) {
			niveau = "EXPERT";
		}
		return niveau;
	}
	
	//--------------------------------------------------------------------------------->>>>> ToString
	public String toString() {
		return nom+" - "+getStringNiveau();
	}
	
	public String creeAffiche() {
		return "numero de la personne : " + numSalarie + ", niveau : " + getStringNiveau() + ", nom de la compétence : " + nom;
	}
}
