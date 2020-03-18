package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import Ressource.Ressource;

public class Activite implements Comparable<Activite>{
	private int id;
	private String titre;
	private int charge;
	private String ordre;
	private LocalDate debut;
	private boolean selectionner = false;

		
	private ArrayList<Ressource> ressources; //Contient les cr�neaux horaires d'une journ�e
	
	public Activite(int id, String titre, int charge, String ordre, LocalDate debut) {
		this.id = id;
		this.titre = titre;
		this.charge = charge;
		this.ordre = ordre;
		this.debut = debut;
		ressources = new ArrayList<Ressource>();
	}
	
	public void ajouterRessource(Ressource res) {
		ressources.add(res);
	}
	
	public boolean creneauDispo(LocalDate date, LocalTime heure){
		Boolean dispo = false;
		for (int i = 0; i < ressources.size(); i++) {
			if(ressources.get(i).creneauDispo(date, heure) == true) {
				
			}
		}
		return dispo;
	}
	
	public void ajouterCreneau(CreneauHoraire cr, LocalDate jour) {
		for (int i = 0; i < ressources.size(); i++) {
			ressources.get(i).ajouterCreneau(cr, jour);
		}
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
	
	/*@Override
	public String toString() {
		return "Activite numero "+ id +" allant de " + debut + " a " + jourFin;
	}*/
	
	public void selectionner() {
		this.selectionner = true;
	}
	
	public void deselectionner() {
		this.selectionner = false;
	}
	
	public boolean getSelectionner() {
		return selectionner;
	}

	public String getTitre() {
		return titre;
	}
	
	public int getCharge() {
		return charge;
	}

	public String getOrdre() {
		return ordre;
	}
	
	public LocalDate getDebut() {
		return debut;
	}
	
	public ArrayList<Ressource> getLRessource(){
		return ressources;
	}
	
	public String getJourDebut() {
		String jour = Integer.toString(debut.getDayOfMonth());
		String mois = Integer.toString(debut.getMonthValue());
		String annee = Integer.toString(debut.getYear());
		String date = jour + "/" + mois + "/" + annee;
		return date;
	}

	public int getId() {
		return id;
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

	@Override
	public int compareTo(Activite act) {
		return ordre.compareTo(act.ordre);
	}
}
