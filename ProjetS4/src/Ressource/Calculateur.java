package Ressource;


public class Calculateur extends Ressource{
	
	private int capacite;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Calculateur(String nom, int numCalculateur) {
		super(numCalculateur, nom, Ressource.CALCULATEUR);
	}
	
	public Calculateur(int code, String nom, int capacite) {
		this(nom, code);
		this.capacite = capacite;
	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public String toString() {
		return nom;
	}

	public String creeAffiche() {
		return "id : " + this.id + ", nom : " + this.nom + ", capacite calculateur : " + this.capacite;
	}
}
