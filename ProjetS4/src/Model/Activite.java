package Model;

import java.time.LocalDate;
import java.util.ArrayList;

import Ressource.Ressource;

public class Activite {
	private int id;
	private String titre;
	private int charge;
	private String ordre;
	private LocalDate jourDebut;
	
		
	private ArrayList<Ressource> ressources; //Contient les cr�neaux horaires d'une journ�e
	
	public Activite(int id, String titre, int charge, String ordre, LocalDate jourDebut) {
		this.id = id;
		this.titre = titre;
		this.charge = charge;
		this.ordre = ordre;
		this.jourDebut = jourDebut;
		ressources = new ArrayList<Ressource>();
	}
	
	public void ajouterRessource(Ressource res) {
		ressources.add(res);
	}
	
	public String getNomProjet() {
		return nomProjet;
	}
	
	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	
	public boolean supprimerRessource(int id) {
		boolean suppr = false;
		int i = 0;
		Ressource ressourceCourrante;
		while(i < ressources.size() && !suppr) {
			ressourceCourrante = ressources.get(i);
			if (ressourceCourrante.getId() == id) {
				ressources.remove(i);
				suppr = true;
			}	
			i++;
		}
		return suppr;
	}
	
	public ArrayList<Ressource> getListeRessourceType(String type){
		ArrayList<Ressource> nouvelleListe = new ArrayList<Ressource>();
		for (int i=0; i<ressources.size(); i++) {
			Ressource ressource = ressources.get(i);
			if(ressource.getType() == type) {
				nouvelleListe.add(ressource);
			}
		}
		return nouvelleListe;
	}
	
	@Override
	public String toString() {
		return "Activite numero "+ id +" allant de " + jourDebut + " a " + jourFin;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Activite && obj != null) {
			Activite res = (Activite)obj;
			return id == res.id;
		} else {
			return false;
		}
	}
	

}