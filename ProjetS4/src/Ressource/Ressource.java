package Ressource;
import java.time.LocalDate;
import java.time.LocalTime;
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
	//Contient l'ensemble des jours qui possï¿½dent un crï¿½neau horaire, la clï¿½ est une LocalDate du jour choisi

	
	public Ressource(int id, String nom, String type) {
		this.id = id;
		this.nom = nom;
		this.type = type;
		this.jours = new Hashtable<LocalDate, ArrayList<CreneauHoraire>>(); 
		//La hashtable tri les creneaux horaires par jour (la clé est un LocalDate)
	}
	
	public boolean ajouterCreneau(CreneauHoraire creneau, LocalDate jour) {
		
		boolean place = false;
		
		if(jours.containsKey(jour)) { //Si le jour existe (il y a deja un creneau dedans)
			ArrayList<CreneauHoraire> creneauxExistant = jours.get(jour); //On recupere le creneau
			int taille = creneauxExistant.size();
			int i = 0;
			place = false;
			while(i < taille && !place) { //On fait une recherche optimisee de l'emplacement du creneau (pour l'ajouter)
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
		} else { //S'il n'existe pas on cree l'emplacement
			ArrayList<CreneauHoraire> listTMP = new ArrayList<CreneauHoraire>();
			listTMP.add(creneau);
			jours.put(jour, listTMP); //On creee un nouveau jour avec une arraylist de creneaux (qui n'en contient que un seul pour le moment)
			place = true;
		}
		return place;
	}
	
	//Dit si un creneau est dispo pour le jour et l'heure en parametre
	public boolean creneauDispo(LocalDate date, LocalTime heure) {
		if(jours.containsKey(date)) {
			ArrayList<CreneauHoraire> jour = jours.get(date);
			return !jour.contains(new CreneauHoraire(heure));
		} else {
			return false;
		}
		
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
		ArrayList<CreneauHoraire> creneauxTMP = listeCreneauTMP(); //Journee de creneaux libres (tmp)
		ArrayList<CreneauHoraire> creneauxLibres = new ArrayList<CreneauHoraire>();
		for (int j = 0; j < creneauxTMP.size(); j++) {
			if (!jourCourant.contains(creneauxTMP.get(j))) { //Si la journee contient le creneaulibre courant 
				creneauxLibres.add(creneauxTMP.get(j)); //On l'ajoute a la liste de retour
			}
		}
		return creneauxLibres;
		
	}
		
	private ArrayList<CreneauHoraire> listeCreneauTMP(){ //renvoie une journée type (avec que des creneaux libres)
		ArrayList<CreneauHoraire> creneauxLibres = new ArrayList<CreneauHoraire>();
		CreneauHoraire creneauTMP = new CreneauHoraire(Entreprise.HEURE_DEBUT_MATIN);
		creneauxLibres.add(creneauTMP);
		
		while (creneauTMP.getFin() != Entreprise.HEURE_FIN_APREM) { //Tant qu'on est pas a la fin de la journee

			//Si on arrive a une heure de fin de matinee, on saute la pause repas	
			if (creneauTMP.getFin() == Entreprise.HEURE_FIN_MATIN) {
				creneauTMP = new CreneauHoraire(Entreprise.HEURE_DEBUT_APREM);
			} else {
				creneauTMP = creneauTMP.creneauSuivant();
			}
			creneauxLibres.add(creneauTMP);
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
