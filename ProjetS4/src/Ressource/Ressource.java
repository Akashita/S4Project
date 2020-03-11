package Ressource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.time.Duration;

import Model.Activite;
import Model.CreneauHoraire;
import Model.Projet;

public class Ressource {
	protected String nom;
	public static final String PERSONNE = "Personne";
	public static final String SALLE = "Salle";
	public static final String CALCULATEUR = "Calculateur";
	protected String type;
	protected Projet Projet;
	protected int id;
	
	public final static LocalTime DEBUT_JOURNEE = LocalTime.of(8,0);
	public final static LocalTime FIN_JOURNEE = LocalTime.of(16,0);
	//Ajouter la pause du midi ?
	
	private Hashtable<LocalDate, ArrayList<CreneauHoraire>> jours; //Contient l'ensemble des jours qui possèdent un créneau horaire, la clé est une LocalDate du jour choisi

	
	public Ressource(int id, String nom, String type) {
		this.id = id;
		this.nom = nom;
		this.type = type;
		this.jours = new Hashtable<LocalDate, ArrayList<CreneauHoraire>>();
	}
	
	public boolean ajouterCreneau(CreneauHoraire creneau, LocalDate jour) {
		
		boolean place = false;
		
		if(jours.containsKey(jour)) { //Si le jour existe (il y a déja un créneau dedans)
			ArrayList<CreneauHoraire> creneauxExistant = jours.get(jour);
			int taille = creneauxExistant.size();
			int i = 0;
			place = false;
			while(i < taille && !place) {
				if(creneau.estApres(creneauxExistant.get(i))) {
					if(i == taille - 1) {
						jours.get(jour).add(i+1, creneau);
						place = true;
					}
				} else {
					jours.get(jour).add(i, creneau);
					place = true;
				}
				i++;
			}
			
		
		} else {
			ArrayList<CreneauHoraire> listTMP = new ArrayList<CreneauHoraire>();
			listTMP.add(creneau);
			jours.put(jour, listTMP); //On crée un nouveau jour avec une arraylist de créneaux (qui n'en contient que un seul pour le moment)
			place = true;
		}
		
		return place;
	}
	
	public Hashtable<LocalDate, ArrayList<CreneauHoraire>> getCreneauxLibres(){ //Retourne une hashtable de jour avec tous les créneaux libres
		Hashtable<LocalDate, ArrayList<CreneauHoraire>> creneauxLibres = new Hashtable<LocalDate, ArrayList<CreneauHoraire>>();
		
		Set<LocalDate> keys = jours.keySet(); //On récupère les clés de la hashtable jours
		Iterator<LocalDate> itt = keys.iterator();
		
		LocalDate key;
		ArrayList<CreneauHoraire> jourCourant;
		while(itt.hasNext()) { // on parcours les jours
			key = itt.next();
			jourCourant = jours.get(key);
			jourCourant = getCreneauxLibresJour(jourCourant);
			if (jourCourant != null) {
				creneauxLibres.put(key, jourCourant);
			}
		}
		
		return creneauxLibres;
	}
	
	private ArrayList<CreneauHoraire> getCreneauxLibresJour(ArrayList<CreneauHoraire> jourCourant) { //Retourne les créneaux libres d'une journée
		ArrayList<CreneauHoraire> creneauxLibres = new ArrayList<CreneauHoraire>();
		//TODO A COMPLETER
		return creneauxLibres;
		
	}
	

	public String getNom() {//rï¿½cupï¿½ration du nom
		return this.nom;
	}

	public String getType() {//rï¿½cupï¿½ration du type
		return this.type;
	}

	public int getId() {//rï¿½cupï¿½ration de l'Id de chaque ressource pour les diffï¿½rencier
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
	public boolean equals(Object obj) { //test si deux ressources sont ï¿½gales
		if(obj instanceof Ressource && obj != null) {
			Ressource res = (Ressource)obj;
			return id == res.id;
		} else {
			return false;
		}
		
	}

	

}
