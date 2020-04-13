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
}
