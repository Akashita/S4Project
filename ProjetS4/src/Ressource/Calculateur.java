package Ressource;


public class Calculateur extends Ressource{
	
	public Calculateur(String nom, int numCalculateur) {
		//attribut classe m�re
		super(numCalculateur, nom, "Calculateur");
	}
	public String toString() {
		return "Calculateur : " + super.nom;
	}
}
