package Ressource;
import java.time.LocalDate;
import java.util.ArrayList;

import Model.CreneauHoraire;
import Model.Plage;
import Model.Projet;

public class Ressource {
	protected String nom;
	public static final String PERSONNE = "Personne";
	public static final String SALLE = "Salle";
	public static final String CALCULATEUR = "Calculateur";
	protected String type;
	protected Projet Projet;
	protected int id;
		
	ArrayList<Package> edt;

	
	public Ressource(int id, String nom, String type) {
		this.id = id;
		this.nom = nom;
		this.type = type;
		this.edt = new ArrayList<Package>();
	}

	
	public String getNom() {//r�cup�ration du nom
		return this.nom;
	}

	public String getType() {//r�cup�ration du type
		return this.type;
	}

	public int getId() {//r�cup�ration de l'Id de chaque ressource pour les diff�rencier
		return this.id;
	}
	public Projet getProjet() {
		return this.Projet;
	}
	
	public void setProjet(Projet projetCour) {
		this.Projet = projetCour;
	}
	
	public void unsetProjet() {
		this.Projet = null;
	}
	
	@Override
	public boolean equals(Object obj) { //test si deux ressources sont �gales
		if(obj instanceof Ressource && obj != null) {
			Ressource res = (Ressource)obj;
			return id == res.id;
		} else {
			return false;
		}
		
	}
	
	
	// --------------------
	//Gestion du calendrier
	// --------------------
	
	private ArrayList<CreneauHoraire> getTousCreneauxLibres(LocalDate jour) {
		ArrayList<CreneauHoraire> creneauxLibres = new ArrayList<CreneauHoraire>();
		for (int i = 0; i < edt.size(); i++) {
			edt.get(i)
		}
		
		return;
	}
	

}
