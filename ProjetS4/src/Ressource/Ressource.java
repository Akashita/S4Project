package Ressource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import Model.CreneauHoraire;
import Model.Entreprise;
import Model.Temps;

public class Ressource implements Comparable<Ressource>{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	protected String nom; 
	public static final String PERSONNE = "Personne";
	public static final String SALLE = "Salle";
	public static final String CALCULATEUR = "Calculateur";
	protected String type; //Type de la ressource (personne/salle/...)
	protected int id;
	
	private Hashtable<LocalDate, ArrayList<CreneauHoraire>> jours; 
	//Contient l'ensemble des jours qui possedent un creneau horaire, la cle est une LocalDate du jour choisi
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Ressource(int id, String nom, String type) {
		this.id = id;
		this.nom = nom;
		this.type = type;
		this.jours = new Hashtable<LocalDate, ArrayList<CreneauHoraire>>(); 
	}
	
	public Ressource(int id) {
		this(id, null, null);
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public String getNom() {
		return this.nom;
	}

	public String getType() {
		return this.type;
	}

	public int getId() {
		return this.id;
	}
	
	//--------------------------------------------------------------------------------->>>>> Comparaison
	@Override
	public boolean equals(Object obj) { //test si deux ressources sont �gales
		if(obj instanceof Ressource && obj != null) {
			Ressource res = (Ressource)obj;
			return id == res.id;
		} else {
			return false;
		}
	}
	
	@Override
	public int compareTo(Ressource ressource) {
		int res;
		if(id == ressource.id) {
			res = 0;
		} else if(id < ressource.id) {
			res = -1;
		} else {
			res = 1;
		}
		
		return res;
	}
	
	
	
	//--------------------------------------------------------------------------------->>>>> Gestion de l'EDT

	/**
	 * Retourne l'EDT d'une ressource en fonction de la semaine et de l'annee.
	 * @param annee   	Annee pour laquelle on veut l'EDT
	 * @param semaine   Semaine choisi pour afficher l'EDT
	 * @return Un tableau a deux dimensions compose de CreaneauxHoraires ou de la valeur null
	 */
	public CreneauHoraire[][] getSemaineEDT(int annee, int semaine) {
		CreneauHoraire[][] semaineEDT = new CreneauHoraire[5][Entreprise.NB_HEURE_JOUR];
		LocalDate[] dateJours = Temps.getJourSemaine(annee, semaine);
		CreneauHoraire creneauTMP;

		for (int i = 0; i < dateJours.length; i++) {
			for (int j = 0; j < Entreprise.NB_HEURE_JOUR; j++) {
				if(jours.containsKey(dateJours[i])){
					creneauTMP = jours.get(dateJours[i]).get(j);
					semaineEDT[i][j] = creneauTMP;	
				} else {
					semaineEDT[i][j] = null;
				}
			}
		}
		return semaineEDT;
	}
	
	public CreneauHoraire getMoisEDT(int numeroMois) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void vider() {
		jours.clear();
	}
	

	/**
	 * Cree une journee entierement disponible
	 * @return Un tableau de CreneauHoraire init � null
	 */
	private ArrayList<CreneauHoraire> creeJourneeCreneauLibre() {
		ArrayList<CreneauHoraire> creneaux = new ArrayList<CreneauHoraire>();
		for (int i = 0; i < Entreprise.NB_HEURE_JOUR; i++) {
			creneaux.add(null);
		}
		return creneaux;
	}
	

	/**
	 * Ajoute un creneau � la ressource (si possible)
	 * @param creneau   Le creneau a ajouter
	 * @param jour   	Le jour ou le creneau doit etre ajoute
	 * @return true si le creneau a ete ajoute
	 */
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
	
	
	/**
	 * Dit si le creneau (en parametre) est dispo pour la ressource courante 
	 * @param date   La date du creneau
	 * @param heure  L'heure du creneau
	 * @return true si le creneau est dispo
	 */
	public boolean creneauDispo(LocalDate date, int heure) {
		boolean dispo = true;
		if(jours.containsKey(date)) {
			ArrayList<CreneauHoraire> jour = jours.get(date);
			for (int i = 0; i < jour.size(); i++) {
				if(jour.get(i) != null) {
					if(jour.get(i).getDebut() == heure){
					dispo = false;
					}
				}
			}
		}
		return dispo;
	}

	
	/**
	 * Genere une hashtable compose des creneaux libres de la ressource 
	 * pour les journee qui possedent deja au moins un creneau (les autres 
	 * ont forcement tous leurs creneaux de libres)
	 * @return une hashtable contenant tous les creneaux libres
	 */
	public Hashtable<LocalDate, ArrayList<CreneauHoraire>> getCreneauxLibres(){ //Retourne une hashtable de jour avec tous les cr�neaux libres
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
	
	
	/**
	 * Methode utilisee par getCreneauxLibres qui donne la liste des creneaux libres de la journee en parametre
	 * @param jourCourant   Le jour a traiter
	 * @return la liste des creneaux libres de la journee
	 */
	private ArrayList<CreneauHoraire> getCreneauxLibresJour(ArrayList<CreneauHoraire> jourCourant) { //Retourne les cr�neaux libres d'une journ�e
		ArrayList<CreneauHoraire> creneauxLibres = new ArrayList<CreneauHoraire>();
		for (int j = 0; j < jourCourant.size(); j++) {
			if(jourCourant.get(j) == null) {
				creneauxLibres.add(jourCourant.get(j));
			}
		}
		return creneauxLibres;
		
	}




}
