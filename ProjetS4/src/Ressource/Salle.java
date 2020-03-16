package Ressource;

import java.util.ArrayList;

public class Salle extends Ressource{
	
	private ArrayList<Materiel> lMatos; 
	private int capacite;
	
	//Diff�rents constructeurs:
	
	
	//cr�ation avec tous les �l�ments et avec une liste de Mat�riel
	public Salle(int numSalle, String nom, int capacite, ArrayList<Materiel> matos) {
		//attribut classe m�re
		super(numSalle, nom, "Salle");
		//attribut classse fille
		this.lMatos = matos;
		this.capacite = capacite;
	}
	
	public Salle(int numSalle) {
		this(numSalle, "",0, new ArrayList<Materiel>());
	}
	
	public Salle(int numSalle, String nom, int capacite) {
		this(numSalle, nom,capacite, new ArrayList<Materiel>());
	}
	
	public int getCapacite() {//r�cup�ration de la capacit� de la salle
		return this.capacite;
	}

	
	
	public String toString() {
		return "PAS FAIT";
	}

}
