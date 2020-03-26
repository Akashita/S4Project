package Ressource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import Model.CreneauHoraire;
import Model.Entreprise;
import Model.Temps;

public class Ressource {
	protected String nom;
	public static final String PERSONNE = "Personne";
	public static final String SALLE = "Salle";
	public static final String CALCULATEUR = "Calculateur";
	protected String type;
	protected int id;
	
	private Hashtable<LocalDate, ArrayList<CreneauHoraire>> jours; 
	//Contient l'ensemble des jours qui possedent un creneau horaire, la cle est une LocalDate du jour choisi

	
	public Ressource(int id, String nom, String type) {
		this.id = id;
		this.nom = nom;
		this.type = type;
		this.jours = new Hashtable<LocalDate, ArrayList<CreneauHoraire>>(); 
		//La hashtable tri les creneaux horaires par jour (la clé est un LocalDate)
	}
	
	public CreneauHoraire[][] getSemaineEDT(int annee, int semaine) {
		CreneauHoraire[][] semaineEDT = new CreneauHoraire[5][Entreprise.NB_HEURE_JOUR];
		LocalDate[] dateJours = Temps.getJourSemaine(annee, semaine);
		CreneauHoraire creneauTMP;

		for (int i = 0; i < dateJours.length; i++) {
			if(jours.containsKey(dateJours[i])){
				for (int j = 0; j < Entreprise.NB_HEURE_JOUR; j++) {
					creneauTMP = jours.get(dateJours[i]).get(j);
					semaineEDT[i][j] = creneauTMP;				
				}
			} else {
				for (int j = 0; j < Entreprise.NB_HEURE_JOUR; j++) {
					semaineEDT[i][j] = null;
				}	
			}
		}
		return semaineEDT;
	}
	
	private ArrayList<CreneauHoraire> creeJourneeCreneauLibre() {
		ArrayList<CreneauHoraire> creneaux = new ArrayList<CreneauHoraire>();
		for (int i = 0; i < Entreprise.NB_HEURE_JOUR; i++) {
			creneaux.add(null);
		}
		return creneaux;
	}
	
	public boolean ajouterCreneau(CreneauHoraire creneau, LocalDate jour) {
		
		boolean place = false;
		ArrayList<CreneauHoraire> listTMP;
		
		if(jours.containsKey(jour)) { //Si le jour existe (il y a deja un creneau dedans)
			listTMP = jours.get(jour); //On recupere le creneau
		} else { //S'il n'existe pas on cree l'emplacement
			listTMP = creeJourneeCreneauLibre();
		}

		if(listTMP.get(creneau.getPosition()) == null) {
			listTMP.set(creneau.getPosition(), creneau);
			jours.put(jour, listTMP);
			place = true;

		}
		return place;
	}
	
	//Dit si un creneau est dispo pour le jour et l'heure en parametre
	public boolean creneauDispo(LocalDate date, int heure) {
		boolean dispo = true;
		if(jours.containsKey(date)) {
			ArrayList<CreneauHoraire> jour = jours.get(date);
			for (int i = 0; i < jour.size(); i++) {
				if(jour.get(i).getDebut() == heure){ //equals(new CreneauHoraire(heure, false))) {
					return jour.get(i) == null;
				}
			}
		}
		return dispo;
		
	}

	
	public Hashtable<LocalDate, ArrayList<CreneauHoraire>> getCreneauxLibres(){ //Retourne une hashtable de jour avec tous les crï¿½neaux libres
		Hashtable<LocalDate, ArrayList<CreneauHoraire>> creneauxLibres = new Hashtable<LocalDate, ArrayList<CreneauHoraire>>();
		
		Set<LocalDate> keys = jours.keySet(); //On recupere les cles de la hashtable jours
		Iterator<LocalDate> itt = keys.iterator();
		
		LocalDate key;
		ArrayList<CreneauHoraire> jourCourant;
		while(itt.hasNext()) { //On parcours les jours
			key = itt.next();
			jourCourant = jours.get(key); //On recupere le jour courant
			jourCourant = getCreneauxLibresJour(jourCourant); 
			//On le transforme en son negatif (avant : creneau occupe -> maintenant : creneaux libres)
			
			if (jourCourant != null) { //Si le jour possede des creneaux libres, on l'ajoute a la liste
				creneauxLibres.put(key, jourCourant);
			}
		}
		return creneauxLibres;
	}
	
	private ArrayList<CreneauHoraire> getCreneauxLibresJour(ArrayList<CreneauHoraire> jourCourant) { //Retourne les crï¿½neaux libres d'une journï¿½e
		ArrayList<CreneauHoraire> creneauxLibres = new ArrayList<CreneauHoraire>();
		for (int j = 0; j < jourCourant.size(); j++) {
			if(jourCourant.get(j) == null) {
				creneauxLibres.add(jourCourant.get(j));
			}
		}
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
