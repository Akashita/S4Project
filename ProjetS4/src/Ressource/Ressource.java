package Ressource;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import Model.Activite;
import Model.CreneauHoraire;
import Model.Entreprise;
import Model.Projet;
import Model.Temps;

public class Ressource implements Comparable<Ressource>{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	protected String nom; 
	//public static final String PERSONNE = "Personne", SALLE = "Salle", CALCULATEUR = "Calculateur";
	public static final int RIEN = -1, PERSONNE = 0, SALLE = 1, CALCULATEUR = 2;
	protected int id;
	protected int type;
	
	//Contient l'ensemble des jours qui possedent un creneau horaire, la cle est une LocalDate du jour choisi
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Ressource(int id, String nom, int type) {
		this.id = id;
		this.nom = nom;
		this.type = type;
	}
	
	/*public Ressource(int id) {
		this(id, null, );
	}*/
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public String getNom() {
		return this.nom;
	}


	public int getId() {
		return this.id;
	}

	public int getType() {
		return type;
	}
	
	
	//--------------------------------------------------------------------------------->>>>>>> Setteur 
	
	public void setNom (String s) {
		this.nom = s;
	}
	
	//--------------------------------------------------------------------------------->>>>> Comparaison
	@Override
	public boolean equals(Object obj) { //test si deux ressources sont ���gales
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


	
	
	

	

	
	
								/*TODO : A SUPPRIMER*/	


	
	/**
	 * Genere une hashtable compose des creneaux libres de la ressource 
	 * pour les journee qui possedent deja au moins un creneau (les autres 
	 * ont forcement tous leurs creneaux de libres)
	 * @return une hashtable contenant tous les creneaux libres
	 */
	/*
	public Hashtable<LocalDate, ArrayList<CreneauHoraire>> getCreneauxLibres(){ //Retourne une hashtable de jour avec tous les cr���neaux libres
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
	*/
	
	/**
	 * Methode utilisee par getCreneauxLibres qui donne la liste des creneaux libres de la journee en parametre
	 * @param jourCourant   Le jour a traiter
	 * @return la liste des creneaux libres de la journee
	 */
	
	/*
	private ArrayList<CreneauHoraire> getCreneauxLibresJour(ArrayList<CreneauHoraire> jourCourant) { //Retourne les cr���neaux libres d'une journ���e
		ArrayList<CreneauHoraire> creneauxLibres = new ArrayList<CreneauHoraire>();
		for (int j = 0; j < jourCourant.size(); j++) {
			if(jourCourant.get(j) == null) {
				creneauxLibres.add(jourCourant.get(j));
			}
		}
		return creneauxLibres;
		
	}
	*/
	
							/*TODO : A SUPPRIMER*/	


	public static String creeLogin(int id, String nom) {
		return nom + Entreprise.SEPARATEUR + id;
	}
	
	public static int recupereIdDepuisLogin(String login) {
		String[] regex = login.split(Entreprise.SEPARATEUR, 2); 
		String apresSeparateur = regex[1];
		return Integer.parseInt(apresSeparateur);
	}
	

}




























