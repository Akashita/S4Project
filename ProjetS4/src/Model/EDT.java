package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
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
	
	public void mergeEDT(EDT autre) {
		Hashtable<LocalDate, ArrayList<CreneauHoraire>> autreHT = autre.listeCreneaux;
		Set<LocalDate> keys = autreHT.keySet();
		for (LocalDate autreKey : keys) {
			if(listeCreneaux.contains(autreKey)) {
				mergeAL(listeCreneaux.get(autreKey), autreHT.get(autreKey));
			} else {
				listeCreneaux.put(autreKey, autreHT.get(autreKey));
			}
		}
	}
	
	private void mergeAL(ArrayList<CreneauHoraire> prem, ArrayList<CreneauHoraire> deux) {
		for (int i = 0; i < deux.size(); i++) {
			CreneauHoraire deuxCourant = deux.get(i);
			if(deuxCourant != null) {
				prem.add(i, deuxCourant);
			}
		}
	}
	
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
	
	public void ajouterReunion(CreneauHoraire creneau) {
		LocalDate date = creneau.getDate();
		if(listeCreneaux.containsKey(date)) {
			ArrayList<CreneauHoraire> tab = listeCreneaux.get(date);
			tab.add(creneau.getPosition(), new CreneauHoraire(date, creneau.getDebut(), "Reunion", creneau.getId()));
			listeCreneaux.put(date, tab);
		} else {
			ArrayList<CreneauHoraire> listeCr = new ArrayList<CreneauHoraire>();
			for (int i = 0; i < 8; i++) {
				if(i == creneau.getPosition()) {
					listeCr.add(i, new CreneauHoraire(date, creneau.getDebut(), "Reunion", creneau.getId()));
				} else {
					listeCr.add(i, null);
				}
			}
			listeCreneaux.put(date, listeCr);
		}
		

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
	
	
	
	
	public LocalDateTime getPremiereCreneauApresAct() {
		Hashtable<LocalDate, ArrayList<CreneauHoraire>> sansCongeEtReunion = enleverCongeReu(listeCreneaux);
		ArrayList<LocalDate> sortedKeys = new ArrayList<LocalDate>(sansCongeEtReunion.keySet());
		Collections.sort(sortedKeys);
		LocalDate dernierJour;
		LocalDateTime res = null;
		if(sortedKeys.size() != 0) {
			 dernierJour = sortedKeys.get(sortedKeys.size()-1);
			boolean trouve = false;
			
			ArrayList<CreneauHoraire> creneauxJour = sansCongeEtReunion.get(dernierJour);
			
			for (int i = 0; i < creneauxJour.size(); i++) {
				CreneauHoraire crCourant = creneauxJour.get(i);
				if(crCourant == null) {
					trouve = true;
					res = LocalDateTime.of(verifierJour(dernierJour), LocalTime.of(indiceToHeure(i), 0));
					break;
				}
			}
			if(!trouve) {
				res = getCreneauSuivant(verifierJour(dernierJour), 7);
			}
		}

			
		return res;
	}
	
	private Hashtable<LocalDate, ArrayList<CreneauHoraire>> enleverCongeReu(Hashtable<LocalDate, ArrayList<CreneauHoraire>> liste) {
		Hashtable<LocalDate, ArrayList<CreneauHoraire>> res = new Hashtable<LocalDate, ArrayList<CreneauHoraire>>();
		Set<LocalDate> keys = liste.keySet();
		for (LocalDate key : keys) {
			ArrayList<CreneauHoraire> jourCourant = liste.get(key);
			ArrayList<CreneauHoraire> tmp = new ArrayList<CreneauHoraire>();
			for (int i = 0; i < jourCourant.size(); i++) {
				CreneauHoraire crCourant = jourCourant.get(i);
				if(crCourant != null) {
					if(crCourant.getType() == CreneauHoraire.CONGE || crCourant.getType() == CreneauHoraire.REUNION) {
						tmp.add(null);
					} else {
						tmp.add(crCourant);
					}
				} else {
					tmp.add(null);
				}
				
			}
			if(!jourNull(tmp)) {
				res.put(key, tmp);
			}
		}
		return res;
	}
	
	private boolean jourNull(ArrayList<CreneauHoraire> jour) {
		boolean res = true;
		for (int i = 0; i < jour.size(); i++) {
			if(jour.get(i) != null) {
				res = false;
			}
		}
		return res;
	}
	
	private LocalDate verifierJour(LocalDate jourCourant) {
		LocalDate jourVerifie;
		switch (jourCourant.getDayOfWeek()) {
		case SATURDAY:
			jourVerifie = jourCourant.plus(2, ChronoUnit.DAYS);
			break;
		case SUNDAY:
			jourVerifie = jourCourant.plus(1, ChronoUnit.DAYS);
			break;
		default:
			jourVerifie = jourCourant;
			break;
		}
		return jourVerifie;
	}
	
	private LocalDateTime getCreneauSuivant(LocalDate key, int indice) {
		if(indice == 7) {
			key = key.plus(1, ChronoUnit.DAYS);
			indice = Entreprise.HEURE_DEBUT_MATIN;
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