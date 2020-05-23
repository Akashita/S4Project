package Ressource;

public class Materiel { // = Fourniture de bureau, compose un salle
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private int numMatos;
	private String type;
	private int quantite;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	

	public Materiel(int numMatos, String type, int quantite) {
		this.numMatos =numMatos;
		this.type = type;
		this.quantite = quantite;
	}
	
	public Materiel(String type, int quantite) {
		this(-1, type, quantite);
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	public int getId() {
		return this.numMatos;
	}
	
	//--------------------------------------------------------------------------------->>>>> toString
	public String creeAffiche() {
		return "id : " + this.numMatos + ", quantite : " +  quantite + ", type : " + type;
	}
	
	@Override
	public String toString() {
		return "Materiel : " +  quantite + " " + type;
	}

}
