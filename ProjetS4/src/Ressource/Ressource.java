package Ressource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import Model.CreneauHoraire;

public class Ressource {
	protected String nom;
	public static final String PERSONNE = "Personne";
	public static final String SALLE = "Salle";
	public static final String CALCULATEUR = "Calculateur";
	protected String type;
	protected int id;
	
	public final static LocalTime DEBUT_JOURNEE = LocalTime.of(8,0);
	public final static LocalTime FIN_JOURNEE = LocalTime.of(16,0);
	//Ajouter la pause du midi ?
	
	private Hashtable<LocalDate, ArrayList<CreneauHoraire>> jours; //Contient l'ensemble des jours qui poss�dent un cr�neau horaire, la cl� est une LocalDate du jour choisi

	
	public Ressource(int id, String nom, String type) {
		this.id = id;
		this.nom = nom;
		this.type = type;
		this.jours = new Hashtable<LocalDate, ArrayList<CreneauHoraire>>();
	}
	
	public boolean ajouterCreneau(CreneauHoraire creneau, LocalDate jour) {
		
		boolean place = false;
		
		if(jours.containsKey(jour)) { //Si le jour existe (il y a d�ja un cr�neau dedans)
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
			jours.put(jour, listTMP); //On cr�e un nouveau jour avec une arraylist de cr�neaux (qui n'en contient que un seul pour le moment)
			place = true;
		}
		
		return place;
	}
	
	public Hashtable<LocalDate, ArrayList<CreneauHoraire>> getCreneauxLibres(){ //Retourne une hashtable de jour avec tous les cr�neaux libres
		Hashtable<LocalDate, ArrayList<CreneauHoraire>> creneauxLibres = new Hashtable<LocalDate, ArrayList<CreneauHoraire>>();
		
		Set<LocalDate> keys = jours.keySet(); //On r�cup�re les cl�s de la hashtable jours
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
	
	private ArrayList<CreneauHoraire> getCreneauxLibresJour(ArrayList<CreneauHoraire> jourCourant) { //Retourne les cr�neaux libres d'une journ�e
		ArrayList<CreneauHoraire> creneauxLibres = new ArrayList<CreneauHoraire>();
		//TODO A COMPLETER
		return creneauxLibres;
		
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

	
	@Override
	public boolean equals(Object obj) { //test si deux ressources sont �gales
		if(obj instanceof Ressource && obj != null) {
			Ressource res = (Ressource)obj;
			return id == res.id;
		} else {
			return false;
		}
		
	}

	

}
