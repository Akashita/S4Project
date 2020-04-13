package Ressource;


public class Calculateur extends Ressource{
	
	private int capacite;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Calculateur(String nom, int numCalculateur) {
		super(numCalculateur, nom, "Calculateur");
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
}
