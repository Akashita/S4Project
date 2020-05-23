package Ressource;


public class Calculateur extends Ressource{
	
	private int capacite;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	public Calculateur(int numCalculateur, String nom, int capacite) {
		super(numCalculateur, nom, Ressource.CALCULATEUR);
		this.capacite = capacite;
	}
	
	public Calculateur(int numCalculateur, String nom) {
		this(numCalculateur, nom, 0);
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
