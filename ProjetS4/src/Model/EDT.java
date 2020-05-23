package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class EDT {
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private int id_ressource;
	private int type_ressource;
	
	private Hashtable<LocalDate, ArrayList<CreneauHoraire>> listeCreneaux;
	
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	public EDT(int id_ressource, int type_ressource) {
		this.id_ressource = id_ressource;
		this.type_ressource = type_ressource;
		this.listeCreneaux = new Hashtable<LocalDate, ArrayList<CreneauHoraire>>();

	}
	
	public EDT(Pair<?, ?> duet) {
		this((int) duet.getLeft(), (int) duet.getRight());
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public int getIdRessource() {
		return id_ressource;
	}
	
	public int getTypeRessource() {
		return id_ressource;
	}
	
	public Pair<Integer, Integer> getPair() {
		return new Pair<Integer, Integer>(id_ressource, type_ressource);
	}

	//--------------------------------------------------------------------------------->>>>> Setteurs simples
	public void vider() {
		listeCreneaux.clear();
	}
	
	
	//--------------------------------------------------------------------------------->>>>> Méthodes complexes

	/**
	 * Ajoute un creneau ��� la ressource (si possible)
	 * 
	 * @param creneau Le creneau a ajouter
	 * @param jour    Le jour ou le creneau doit etre ajoute
	 * @return true si le creneau a ete ajoute
	 */
	public boolean ajouterCreneau(CreneauHoraire creneau, LocalDate jour) {

		boolean place = false;
		ArrayList<CreneauHoraire> listTMP;

		if (listeCreneaux.containsKey(jour)) { // Si le jour existe (il y a deja un creneau dedans)
			listTMP = listeCreneaux.get(jour); // On recupere le creneau
		} else { // S'il n'existe pas on cree l'emplacement
			listTMP = creeJourneeCreneauLibre();
		}

		if (listTMP.get(creneau.getPosition()) == null) {
			listTMP.set(creneau.getPosition(), creneau);
			listeCreneaux.put(jour, listTMP);
			place = true;

		}
		return place;
	}
	
	public void ajouterConge(CreneauHoraire creneau) {
		LocalDate date = creneau.getDate();
		ArrayList<CreneauHoraire> listeCr = new ArrayList<CreneauHoraire>();
		for (int i = 0; i < 8; i++) {
			listeCr.add(new CreneauHoraire(date));
		}
		listeCreneaux.put(date, listeCr);
	}

	/**
	 * Cree une journee entierement disponible
	 * 
	 * @return Un tableau de CreneauHoraire init ��� null
	 */
	private ArrayList<CreneauHoraire> creeJourneeCreneauLibre() {
		ArrayList<CreneauHoraire> creneaux = new ArrayList<CreneauHoraire>();
		for (int i = 0; i < Entreprise.NB_HEURE_JOUR; i++) {
			creneaux.add(null);
		}
		return creneaux;
	}

	/**
	 * Dit si le creneau (en parametre) est dispo pour la ressource courante
	 * 
	 * @param date  La date du creneau
	 * @param heure L'heure du creneau
	 * @return true si le creneau est dispo
	 */
	public boolean creneauDispo(LocalDate date, int heure) {
		boolean dispo = true;
		if (listeCreneaux.containsKey(date)) {
			ArrayList<CreneauHoraire> jour = listeCreneaux.get(date);
			for (int i = 0; i < jour.size(); i++) {
				if (jour.get(i) != null) {
					if (jour.get(i).getDebut() == heure) {
						dispo = false;
					}
				}
			}
		}

		return dispo;
	}

	/**
	 * Retourne l'EDT d'une ressource en fonction de la semaine et de l'annee.
	 * 
	 * @param annee   Annee pour laquelle on veut l'EDT
	 * @param semaine Semaine choisi pour afficher l'EDT
	 * @return Un tableau a deux dimensions compose de CreaneauxHoraires ou de la
	 *         valeur null
	 */
	public CreneauHoraire[][] getSemaineEDT(int annee, int semaine) {
		CreneauHoraire[][] semaineEDT = new CreneauHoraire[5][Entreprise.NB_HEURE_JOUR];
		LocalDate[] dateJours = Temps.getJourSemaine(annee, semaine);
		CreneauHoraire creneauTMP;

		for (int i = 0; i < dateJours.length; i++) {
			for (int j = 0; j < Entreprise.NB_HEURE_JOUR; j++) {
				if (listeCreneaux.containsKey(dateJours[i])) {
					creneauTMP = listeCreneaux.get(dateJours[i]).get(j);
					semaineEDT[i][j] = creneauTMP;
				} else {
					semaineEDT[i][j] = null;
				}
			}
		}
		return semaineEDT;
	}
	
	
	
	
	public LocalDateTime getPremiereCreneauApresAct(int ordre) {
		Set<LocalDate> keys = listeCreneaux.keySet(); //On recupere les cles de la hashtable jours
		Iterator<LocalDate> itt = keys.iterator();
		boolean trouve = false;		
		LocalDate key = null;
		LocalDateTime res = null;
		ArrayList<CreneauHoraire> jourCourant;
		int i = 0;
		while(itt.hasNext() && !trouve) { //On parcours les jours
			key = itt.next();
			jourCourant = listeCreneaux.get(key); //On recupere le jour courant
			for (i = 0; i < jourCourant.size(); i++) {
				if(jourCourant.get(i).getActivite() != null) {
					if(jourCourant.get(i).getActivite().getOrdre() >= ordre) {
						trouve = true;
						break;
					}
				}	
			}			
		}
		if(key == null) {
			res = null;
		} else if(!trouve) {
			res = getCreneauSuivant(key, i);
		} else {
			res =  LocalDateTime.of(key, LocalTime.of(indiceToHeure(i), 0));
		}
		
		return res;
	}
	
	private LocalDateTime getCreneauSuivant(LocalDate key, int indice) {
		if(indice == 7) {
			key = key.plus(1, ChronoUnit.DAYS);
			indice = indiceToHeure(indice);
		}
		return LocalDateTime.of(key, LocalTime.of(indice, 0));
	}
	
	private int indiceToHeure(int indice) {
		int heure = Entreprise.HEURE_DEBUT_MATIN + indice;
		if(indice > 4) {
			heure = heure + (Entreprise.HEURE_DEBUT_APREM - Entreprise.HEURE_FIN_MATIN);
		}
		return heure;
	}
	
	
	
	
	public LocalDateTime getPremiereDateActivite(Activite act) {
		List<LocalDate> listKeys = new ArrayList<LocalDate>(listeCreneaux.keySet());
		LocalDateTime premDate = null;
		Collections.sort(listKeys);
		boolean quitter = false;
		for (int i = 0; i < listKeys.size(); i++) {
			ArrayList<CreneauHoraire> jour = listeCreneaux.get(listKeys.get(i));
			for (int j = 0; j < jour.size(); j++) {
				CreneauHoraire crCourant = jour.get(j);
				if (crCourant.getActivite().equals(act)) {
					premDate = LocalDateTime.of(listKeys.get(i), LocalTime.of(crCourant.getDebut(), 0));
					quitter = true;
					break;
				}
			}
			if (quitter) {
				break;
			}
		}

		return premDate;	
	}
	
	
	public LocalDateTime getDerniereDateActivite(Activite act) {
		List<LocalDate> listKeys = new ArrayList<LocalDate>(listeCreneaux.keySet());
		LocalDateTime derDate = null;
		Collections.sort(listKeys);
		boolean quitter = false;
		for (int i = listKeys.size()-1; i >= 0; i--) {
			ArrayList<CreneauHoraire> jour = listeCreneaux.get(listKeys.get(i));
			for (int j = jour.size()-1; j >= 0 ; j--) {
				CreneauHoraire crCourant = jour.get(j);
				if (crCourant.getActivite().equals(act)) {
					derDate = LocalDateTime.of(listKeys.get(i), LocalTime.of(crCourant.getDebut(), 0));
					quitter = true;
					break;
				}
			}
			if (quitter) {
				break;
			}
		}
		return derDate;	
	}
	


}