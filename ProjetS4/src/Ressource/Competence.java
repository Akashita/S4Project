package Ressource;

public class Competence {

	public final static int DEBUTANT = 1, CONFIRME = 2, EXPERT = 3;
	
	private String nom;
	private int niveau;
	
	public Competence (String nom, int niveau) {
		this.nom = nom;
		this.niveau = niveau;
	}
	
	public String getNom() {
		return nom;
	}
	
	public int getNiveau() {
		return niveau;
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
}
