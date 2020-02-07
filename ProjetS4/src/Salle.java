import java.util.ArrayList;

public class Salle extends Ressource{
	
	private ArrayList<Materiel> matos; 
	private int capacite;
	private int numSalle;
	
	
	public Salle(int numSalle, String nom, int capacite, ArrayList<Materiel> matos) {
		this.matos = matos;
		this.numSalle = numSalle;
		this.capacite = capacite;
		
		super.nom = nom;
		super.type = "Salle";
		super.id = numSalle;
	}
	
	public Salle(int numSalle) {
		this(numSalle, "",0, new ArrayList<Materiel>());
	}

}
