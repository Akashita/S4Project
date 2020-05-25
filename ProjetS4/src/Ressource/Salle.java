package Ressource;

public class Salle extends Ressource{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	private int capacite;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEURS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Salle(int numSalle, String nom, int capacite) {
		super(numSalle, nom, Ressource.SALLE);
		this.capacite = capacite;
	}
	
	public Salle(int numSalle) {
		this(numSalle, "",0);
	}
	
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	//--------------------------------------------------------------------------------->>>>> Getteurs 
	public int getCapacite() {
		return this.capacite;
	}

	//--------------------------------------------------------------------------------->>>>> Setteurs 

	public void setCapacite(int c) {
		this.capacite = c;
	}
	
	public String creeAffiche() {
		return "nom : " + nom + " numero : " + this.id + " capacite : " + capacite ;
	}
	
	//--------------------------------------------------------------------------------->>>>> toString
	public String toString() {
		return  nom ;
	}

}
