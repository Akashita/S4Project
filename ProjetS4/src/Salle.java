import java.util.ArrayList;

public class Salle extends Ressource{
	
	private ArrayList<Materiel> lMatos; 
	private int capacite;
	private int numSalle;
	
	//Différents constructeurs:
	
	
	//création avec tous les éléments et avec une liste de Matériel
	public Salle(int numSalle, String nom, int capacite, ArrayList<Materiel> matos) {
		//attribut classse fille
		this.lMatos = matos;
		this.numSalle = numSalle;
		this.capacite = capacite;
		//attribut classe mère
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
		String liste = " Cette salle s'appelle : " + this.nom + ", elle porte le numéro : " + this.numSalle  
				+ ", elle est immatriculé : " + this.numSalle + " elle contient le matériel suivant : " ;
		
		for(int i = 0; i < this.lMatos.size(); i++){
			liste += this.lMatos.get(i).toString();
		}
		
		return liste;
	}

}
