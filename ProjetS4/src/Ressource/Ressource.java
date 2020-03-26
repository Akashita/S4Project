package Ressource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import Model.CreneauHoraire;
import Model.Entreprise;

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
	
	private ArrayList<CreneauHoraire> creeJourneeCreneauLibre() {
		ArrayList<CreneauHoraire> creneaux = new ArrayList<CreneauHoraire>();
		int heure = Entreprise.HEURE_DEBUT_MATIN;
		for (int i = 0; i < Entreprise.NB_HEURE_JOUR; i++) {
			if (heure == Entreprise.HEURE_FIN_MATIN) {
				heure = Entreprise.HEURE_DEBUT_APREM;
			} 
			creneaux.add(new CreneauHoraire(heure, true));
			heure += 1;
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

		for (int i = 0; i < listTMP.size(); i++) {
			if (listTMP.get(i).equals(creneau) && listTMP.get(i).getDispo()) {
				listTMP.set(i, creneau);
				place = true;
				jours.put(jour, listTMP); //On creee un nouveau jour avec une arraylist de creneaux (qui n'en contient que un seul pour le moment)
			}
		}
		return place;
	}
	
	//Dit si un creneau est dispo pour le jour et l'heure en parametre
	public boolean creneauDispo(LocalDate date, int heure) {
		boolean dispo = false;
		if(jours.containsKey(date)) {
			ArrayList<CreneauHoraire> jour = jours.get(date);
			for (int i = 0; i < jour.size(); i++) {
				if(jour.get(i).equals(new CreneauHoraire(heure, false))) {
					return jour.get(i).getDispo();
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
			if(jourCourant.get(j).getDispo()) {
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
