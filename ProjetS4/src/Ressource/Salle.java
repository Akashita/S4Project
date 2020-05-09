package Ressource;

import java.util.ArrayList;

public class Salle extends Ressource{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	private ArrayList<Materiel> lMatos; 
	private int capacite;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEURS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Salle(int numSalle, String nom, int capacite, ArrayList<Materiel> matos) {
		super(numSalle, nom, "Salle");
		
		this.lMatos = matos; //La liste du matos qui compose la salle
		this.capacite = capacite; //Capacite de la salle en terme d'effectif
	}
	
	public Salle(int numSalle) {
		this(numSalle, "",0, new ArrayList<Materiel>());
	}
	
	public Salle(int numSalle, String nom, int capacite) {
		this(numSalle, nom,capacite, new ArrayList<Materiel>());
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public int getCapacite() {
		return this.capacite;
	}

	//--------------------------------------------------------------------------------->>>>> toString
	public String toString() {
		return "nom : " + nom + " numero : " + this.id + " capacite : " + capacite ;
	}

}
