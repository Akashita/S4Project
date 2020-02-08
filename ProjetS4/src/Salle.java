import java.util.ArrayList;

public class Salle extends Ressource{
	
	private ArrayList<Materiel> lMatos; 
	private int capacite;
	
	//Différents constructeurs:
	
	
	//création avec tous les éléments et avec une liste de Matériel
	public Salle(int numSalle, String nom, int capacite, ArrayList<Materiel> matos) {
		//attribut classse fille
		this.lMatos = matos;
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
	
	public int getCapacite() {//récupération de la capacité de la salle
		return this.capacite;
	}

	
	
	public String toString() {
		String liste = "Cette salle s'appelle : " + this.nom + ", elle porte le numéro : " + this.id  
				+  " elle a une capacitée de :  " + this.capacite +  " elle contient le matériel suivant : " ;
		
		if (this.lMatos.size() == 0) {
			liste += "aucun";
		}
		else {
		for(int i = 0; i < this.lMatos.size(); i++){
			liste += this.lMatos.get(i).toString();
		}
		}
		
		return liste;
	}

}
