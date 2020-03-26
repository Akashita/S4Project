package Model;

import java.time.LocalDate;
import java.util.ArrayList;

import Ressource.Ressource;

public class Activite implements Comparable<Activite>{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private int id;
	private String titre;
	private int charge;
	private String ordre;
	private LocalDate debut;
	private boolean selectionner = false;

		
	private ArrayList<Ressource> ressources; //Contient les cr�neaux horaires d'une journ�e
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Activite(int id, String titre, int charge, String ordre, LocalDate debut) {
		this.id = id;
		this.titre = titre; 
		this.charge = charge; //La charge de travail de l'activitee en jourHomme
		this.ordre = ordre; //Permet d'etablir une relation d'ordre entre toutes les activites
		this.debut = debut;
		ressources = new ArrayList<Ressource>();
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
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
	
	
	//--------------------------------------------------------------------------------->>>>> Setteurs simples
	public void selectionner() {
		this.selectionner = true;
	}
	
	public void deselectionner() {
		this.selectionner = false;
	}
	
	
	//--------------------------------------------------------------------------------->>>>> Comparaison
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
	
	
	//--------------------------------------------------------------------------------->>>>> Gestion de l'EDT
	
	public void ajouterRessource(Ressource res) {
		ressources.add(res);
	}
	
	
	/**
	 * Dit si un creneau est dispo pour toutes ressources associees
	 * @param date   La date du creneau
	 * @param heure  L'heure du creneau
	 * @return vrai si le creneau est dispo pour toutes les ressources associees
	 */
	public boolean creneauDispo(LocalDate date, int heure){
		Boolean dispo = true;
		for (int i = 0; i < ressources.size(); i++) {
			if(!ressources.get(i).creneauDispo(date, heure)) {
				dispo = false;
			}
		}
		return dispo;
	}
	
	
	/**
	 * Ajoute un creneau a toutes les ressources associees
	 * @param cr   	Le creneau a ajouter
	 * @param jour 	Le jour auquel ajouter le creneau
	 */
	public void ajouterCreneau(CreneauHoraire cr, LocalDate jour) {
		for (int i = 0; i < ressources.size(); i++) {
			ressources.get(i).ajouterCreneau(cr, jour);
		}
	}

	
	/**
	 * Supprime une ressource de l'activite
	 * @param id  L'ID de la ressource
	 * @return vrai si la ressource a ete supprimee
	 */
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
	
	
	/**
	 * Selectionne des ressources de l'activite par type
	 * @param type  Le type de la ressource
	 * @return la liste des ressources selectionnees
	 */
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

}
