import java.util.ArrayList;

public class Salle extends Ressource{
	
	private ArrayList<Materiel> lMatos; 
	private int capacite;
	private int numSalle;
	
	//Diff�rents constructeurs:
	
	
	//cr�ation avec tous les �l�ments et avec une liste de Mat�riel
	public Salle(int numSalle, String nom, int capacite, ArrayList<Materiel> matos) {
		//attribut classse fille
		this.lMatos = matos;
		this.numSalle = numSalle;
		this.capacite = capacite;
		//attribut classe m�re
		super.nom = nom;
		super.type = "Salle";
		super.id = numSalle;
	}
	
	public Salle(int numSalle) {
		this(numSalle, "",0, new ArrayList<Materiel>());
	}
	
	public Salle(int numSalle, String nom, int capacite) {
		this(numSalle, nom,capacite, new ArrayList<Materiel>());
	}
	
	
	public String toString() {
		String liste = " Cette salle s'appelle : " + this.nom + ", elle porte le num�ro : " + this.numSalle  
				+ ", elle est immatricul� : " + this.numSalle + " elle a une capacit�e de :  " + this.capacite +  "elle contient le mat�riel suivant : " ;
		
		for(int i = 0; i < this.lMatos.size(); i++){
			liste += this.lMatos.get(i).toString();
		}
		
		return liste;
	}

}
