package Ressource;

public class Materiel { // = Fourniture de bureau, compose un salle
	private String type;
	private int quantite;
	
	public Materiel(String type, int quantite) {
		this.type = type;
		this.quantite = quantite;
	}
	
	@Override
	public String toString() {
		return "Mat�riel : " +  quantite + " " + type;
	}
}
