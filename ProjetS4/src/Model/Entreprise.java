package Model;
import java.awt.Color;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.Hashtable;
import java.util.Observable;
import java.util.Set;
import javax.swing.JFrame;
import Connexion.FenetreConnexion;
import DebugFenetre.FenetreDebugBDD;
import Fenetre.FenetreInfoRessource;
import Fenetre.FenetreInfoTicket;
import Fenetre.FenetrePrincipale;
import GestionTicket.Ticket;
import Panel.PanelTache;
import Ressource.Calculateur;
import Ressource.Competence;
import Ressource.Materiel;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;
import SQL.JavaSQL;
import SQL.JavaSQLActivite;
import SQL.JavaSQLCalculateur;
import SQL.JavaSQLCompetence;
import SQL.JavaSQLConge;
import SQL.JavaSQLDomaine;
import SQL.JavaSQLMateriel;
import SQL.JavaSQLParticipe;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLProjet;
import SQL.JavaSQLRecherche;
import SQL.JavaSQLReunion;
import SQL.JavaSQLSalle;
import SQL.JavaSQLTicket;


public class Entreprise extends Observable{

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	public static final int HEURE_DEBUT_MATIN = 8;
	public static final int HEURE_FIN_MATIN = 12;
	public static final int HEURE_DEBUT_APREM = 13;
	public static final int HEURE_FIN_APREM = 17;

	public static final int NB_HEURE_JOUR = 8;

	private FenetreDebugBDD fenetreBDD;


	//=========== EDT
	private Hashtable<Pair<Integer, Integer>, EDT> listeEDT;




	//=========== Attribut graphique
	private FenetrePrincipale fenetrePrincipale;
	private ArrayList<FenetreInfoRessource> listeFenetreInfoRessource = new ArrayList<FenetreInfoRessource>();
	private ArrayList<FenetreInfoTicket> listeFenetreInfoTicket = new ArrayList<FenetreInfoTicket>();

	private Projet projetSelectionner;
	private Activite activiteSelectionner;
	private boolean afficheEDTActivite = false;
	private ArrayList<String> ressourceAfficher = new ArrayList<String>();

	private int afficheListeRessource = Ressource.RIEN, afficheTache = PanelTache.RIEN;
	//===============================


	public static final String SEPARATEUR = "#";

	private Personne user; //Personne qui utilise le programme
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//creeation de l'entreprise unique il faudra lui ajouter un nom si on deesire eetendre nos activitees
	public Entreprise() {
		fenetrePrincipale = new FenetrePrincipale(this);
		listeEDT = new Hashtable<Pair<Integer, Integer>, EDT>();
		this.update();
	}
	public Entreprise(String typeDebug) {
		if (typeDebug == "debugBDD") {
			fenetreBDD = new FenetreDebugBDD(this);
		}
		listeEDT = new Hashtable<Pair<Integer, Integer>, EDT>();
		this.update();
	}

	public Entreprise(Personne p) {
		user = p;
		fenetrePrincipale = new FenetrePrincipale(this);
		listeEDT = new Hashtable<Pair<Integer, Integer>, EDT>();
		this.update();
	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	//------------------------------------------------------------------------>>>>>>>> Changement de compte

	public void changementUtilisateur() {

		JavaSQL.finCo();
		System.out.println("fintest");
		fenetrePrincipale.dispose();
		new FenetreConnexion();
	}

	//------------------------------------------------------>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> gestion user

	public Personne getUser() {
		return this.user;
	}

	public void setUser(Personne user) {
		this.user = user;
	}

	//---------------------------------------------------------------------------------------------------->>>>>>>>> EDT



	/**
	 * Met à jour l'EDT de toutes les ressources en recréant tous les créneaux horaires (en prenant en compte les nouvelles containtes)
	 * Fonction principale de la gestion des EDT
	 */
	public void majEDT() {
		viderRessources();
		listeEDT = generationEDT();
	}

	/**
	 * Vide les créneaux horaires déjà existants dans les EDT de toutes les ressources
	 */
	private void viderRessources() {
		Set<Pair<Integer, Integer>> keys = listeEDT.keySet();
		for (Pair<Integer, Integer> key : keys) {
			listeEDT.get(key).vider();
		}
	}


	/**
	 * Méthode de génération de l'EDT des ressources de l'entreprise
	 * @return Renvoie l'EDT de toutes les ressources de l'entreprise
	 */
	public Hashtable<Pair<Integer, Integer>, EDT> generationEDT(){
		ArrayList<Projet> lProjet = getListeProjetDeEntreprise(); //On récupère les projets de toute l'entreprise
		ArrayList<Activite> lActivite;

		ArrayList<Personne> listeAllPersonne = castListeRessourceEnPersonnes(getListePersonneEntreprise()); //On récupère toutes les personnes l'entreprise
		Hashtable<Personne, ArrayList<CreneauHoraire>> listeConge = creerHTConge(listeAllPersonne); //Création de la liste des congés pour chaque personne

		Hashtable<Pair<Integer, Integer>, EDT> listeEDTPersonnes = creerLCongePersonnes(listeConge); //On écrase la listeEDTPersonnes avec les congés

		Hashtable<Pair<Integer, Integer>, EDT> listeEDTSalles = new Hashtable<Pair<Integer, Integer>, EDT>();
		Hashtable<Pair<Integer, Integer>, EDT> listeEDTCalculateurs = new Hashtable<Pair<Integer, Integer>, EDT>();

		Hashtable<Pair<Integer, Integer>, EDT> listeEDTComplete = new Hashtable<Pair<Integer, Integer>, EDT>();

		 for (int i = 0; i < lProjet.size(); i++) {
			 Projet projetCourant = lProjet.get(i);
			 lActivite = getListeActiviteDuProjet(projetCourant.getId());
			 Hashtable<Activite, ArrayList<CreneauHoraire>> htReunion = creerALReunion(lActivite); //Création de la liste des congés pour chaque activité
			 for (int j = 0; j < lActivite.size(); j++) {
				 Activite activiteCourante = lActivite.get(j);

				 ArrayList<Personne> listePersonnes = castListeRessourceEnPersonnes(this.getListeRessourcedeActiviteParId(Ressource.PERSONNE, activiteCourante.getId()));
				 //Recupération de la liste des personne faisant partie de l'activité courante

				 ArrayList<Salle> listeSalles = castListeRessourceEnSalles(this.getListeRessourcedeActiviteParId(Ressource.SALLE, activiteCourante.getId()));
				//Recupération de la liste des salles faisant partie de l'activité courante

				 ArrayList<Calculateur> listeCalculateurs = castListeRessourceEnCalculateurs(this.getListeRessourcedeActiviteParId(Ressource.CALCULATEUR, activiteCourante.getId()));
				//Recupération de la liste des calculateurs faisant partie de l'activité courante


				 //Création des EDT pour l'activité courante (concaténation avec les EDT des autres activités)
				 listeEDTPersonnes.putAll(creerLCreneauxPersonnes(projetCourant, activiteCourante, listePersonnes, listeEDTPersonnes, listeConge, htReunion.get(activiteCourante)));
				 listeEDTSalles.putAll(creerLCreneauxSalles(projetCourant, activiteCourante, listeSalles, listeEDTSalles, listeEDTPersonnes));
				 listeEDTCalculateurs.putAll(creerLCreneauxCalculateurs(projetCourant, activiteCourante, listeCalculateurs, listeEDTCalculateurs, listeEDTPersonnes));

			 }
		}

		 //Ajout des EDT de toutes les ressources à la liste finale
		 listeEDTComplete.putAll(listeEDTPersonnes);
		 listeEDTComplete.putAll(listeEDTSalles);
		 listeEDTComplete.putAll(listeEDTCalculateurs);

		return listeEDTComplete;
	}

	/**
	 * Renvoie les bornes de l'EDT prévisionel de l'activité passée en paremètre
	 *
	 * Même principe que generationEDT() sauf qu'on effectue l'action en paramètre avant de générer l'EDT
	 *
	 *
	 * @param L'opération à effectuer (ajout ou suppresion), voir l'enum Operation
	 * @param La ressource concernée par la modification
	 * @param L'activité concernée par la modification
	 * @return Deux localDateTime qui correspondent aux bornes
	 */
	private LocalDateTime[] generationPrevisionEDT(Operation operation, Ressource res, Activite act) {
		ArrayList<Projet> lProjet = getListeProjetDeEntreprise();
		ArrayList<Activite> lActivite;

		ArrayList<Personne> listeAllPersonne = castListeRessourceEnPersonnes(getListePersonneEntreprise());
		Hashtable<Personne, ArrayList<CreneauHoraire>> listeConge = creerHTConge(listeAllPersonne);

		Hashtable<Pair<Integer, Integer>, EDT> listeEDTPersonnes = creerLCongePersonnes(listeConge);
		Hashtable<Pair<Integer, Integer>, EDT> listeEDTSalles = new Hashtable<Pair<Integer, Integer>, EDT>();
		Hashtable<Pair<Integer, Integer>, EDT> listeEDTCalculateurs = new Hashtable<Pair<Integer, Integer>, EDT>();

		Hashtable<Pair<Integer, Integer>, EDT> listeEDTComplete = new Hashtable<Pair<Integer, Integer>, EDT>();

		 for (int i = 0; i < lProjet.size(); i++) {
			 Projet projetCourant = lProjet.get(i);
			 lActivite = projetCourant.getListe();
			 Hashtable<Activite, ArrayList<CreneauHoraire>> htReunion = creerALReunion(lActivite);
			 for (int j = 0; j < lActivite.size(); j++) {

				 Activite activiteCourante = lActivite.get(j);

				 ArrayList<Personne> listePersonnes = castListeRessourceEnPersonnes(this.getListeRessourcedeActiviteParId(Ressource.PERSONNE, activiteCourante.getId()));
				 ArrayList<Salle> listeSalles = castListeRessourceEnSalles(this.getListeRessourcedeActiviteParId(Ressource.SALLE, activiteCourante.getId()));
				 ArrayList<Calculateur> listeCalculateurs = castListeRessourceEnCalculateurs(this.getListeRessourcedeActiviteParId(Ressource.CALCULATEUR, activiteCourante.getId()));

				 if(activiteCourante.equals(act)) {
						 switch (res.getType()) {
						case Ressource.PERSONNE:
							Personne pers = (Personne)res;
							if (operation == Operation.ENLEVER) {
								listePersonnes.remove(pers);
							} else {
								listePersonnes.add(pers);
							}
							break;
						case Ressource.SALLE:
							Salle salle = (Salle)res;
							if (operation == Operation.ENLEVER) {
								listeSalles.remove(salle);
							} else {
								listeSalles.add(salle);
							}
							break;
						case Ressource.CALCULATEUR:
							Calculateur calc = (Calculateur)res;
							if (operation == Operation.ENLEVER) {
								listeCalculateurs.remove(calc);
							} else {
								listeCalculateurs.add(calc);
							}
							break;

						default:
							break;
						}
					 }

				 listeEDTPersonnes.putAll(creerLCreneauxPersonnes(projetCourant, activiteCourante, listePersonnes, listeEDTPersonnes, listeConge, htReunion.get(activiteCourante)));
				 listeEDTSalles.putAll(creerLCreneauxSalles(projetCourant, activiteCourante, listeSalles, listeEDTSalles, listeEDTPersonnes));
				 listeEDTCalculateurs.putAll(creerLCreneauxCalculateurs(projetCourant, activiteCourante, listeCalculateurs, listeEDTCalculateurs, listeEDTPersonnes));
			}
		}

		 listeEDTComplete.putAll(listeEDTPersonnes);
		 listeEDTComplete.putAll(listeEDTSalles);
		 listeEDTComplete.putAll(listeEDTCalculateurs);

		 LocalDateTime[] ret = new LocalDateTime[2];
		 Pair<LocalDateTime, LocalDateTime> bornes = getDebutFinActivite(listeEDTComplete, act);
		 ret[0] = bornes.getLeft();
		 ret[1] = bornes.getRight();
		 return ret;
	}


	/**
	 * Créé un tableau associatif : key -> personne ; value : liste de congés
	 * @param La liste des personnes pour lesquels ont créé les congés
	 * @return Le tableau associatif
	 */
	private Hashtable<Personne, ArrayList<CreneauHoraire>> creerHTConge(ArrayList<Personne> listePersonnes){
		Hashtable<Personne, ArrayList<CreneauHoraire>> res = new Hashtable<Personne, ArrayList<CreneauHoraire>>();
		for (int i = 0; i < listePersonnes.size(); i++) {
			Personne persCourante = listePersonnes.get(i);
			res.put(persCourante, this.getListeCongeDePersonne(persCourante.getId()));
		}
		return res;
	}


	/**
	 * Créé un tableau associatif : key -> activite ; value : liste des réunions
	 * @param La liste des personnes activités pour lesquels on créé les réunions
	 * @return Le tableau associatif
	 */
	private Hashtable<Activite, ArrayList<CreneauHoraire>> creerALReunion(ArrayList<Activite> listeAct){
		Hashtable<Activite, ArrayList<CreneauHoraire>> res = new Hashtable<Activite, ArrayList<CreneauHoraire>>();
		for (int i = 0; i < listeAct.size(); i++) {
			ArrayList<CreneauHoraire> listeCrCourant = getListeReunionDeActivite(listeAct.get(i).getId());
			res.put(listeAct.get(i), listeCrCourant);
		}
		return res;
	}


	/**
	 * Créé un tableau associatif : key -> identRessource ; value : EDT de la personne
	 * @param La liste des congés pour chaque personne
	 * @return Le tableau associatif
	 */
	private Hashtable<Pair<Integer, Integer>, EDT> creerLCongePersonnes(Hashtable<Personne, ArrayList<CreneauHoraire>> listeConge){
		Hashtable<Pair<Integer, Integer>, EDT> listeEDT = new Hashtable<Pair<Integer, Integer>, EDT>();
		Set<Personne> keys = listeConge.keySet();
		CreneauHoraire chCourant;
		for (Personne personne : keys) {
			Pair<Integer, Integer> ident = new Pair<Integer, Integer>(0, personne.getId());
			EDT EDTCourant = new EDT(ident);
			for (int i = 0; i < listeConge.get(personne).size(); i++) {
				chCourant = listeConge.get(personne).get(i);
				EDTCourant.ajouterConge(chCourant);

			}
			listeEDT.put(ident, EDTCourant);
		}


		return listeEDT;
	}

	/**
	 * Créée les créneaux horaires de l'EDT de toutes les personnes participant à l'activité courante
	 * @param L'activité courante
	 * @param La liste des personnes qui participent à l'activité
	 * @param La liste des EDT
	 * @return  La liste des EDT
	 */
	private Hashtable<Pair<Integer, Integer>, EDT> creerLCreneauxPersonnes(Projet proj, Activite act, ArrayList<Personne> listePersonnes, Hashtable<Pair<Integer, Integer>, EDT> listeEDTPersonnes, Hashtable<Personne, ArrayList<CreneauHoraire>> listeConge, ArrayList<CreneauHoraire> listeReunion) {
		if(listePersonnes.size() > 0) {
			int charge = act.getChargeHeure();
			int chargeAloue = 0;

			String titreCreneau;

			LocalDate jourCourant = verifierJour(act.getDebut());
			int heureCourante = HEURE_DEBUT_MATIN;

			while (chargeAloue < charge) { //Tant que tous les créneaux ne sont pas placés on continue
				@SuppressWarnings("unchecked")
				Hashtable<Pair<Integer, Integer>, EDT> listeEDTPersonnesCourante = (Hashtable<Pair<Integer, Integer>, EDT>) listeEDTPersonnes.clone();
				for (int i = 0; i < listePersonnes.size(); i++) {
					Personne persCourante = listePersonnes.get(i);
					EDT edtCourant = getEDTRessource(persCourante.getType(), persCourante.getId(), listeEDTPersonnes);

					if(!estEnConge(listeConge.get(persCourante), jourCourant)) { //On regarde si la personne est en congé, si oui on n'ajoute pas de creneau (il sont déjà présents)
						if(!estEnReunion(listeReunion, jourCourant, heureCourante)) { //On regarde si la personne est en réunion, si oui on ajoute un creneaux de type réunion
							if(verifierOrdre(listeEDTPersonnesCourante, act, jourCourant, heureCourante)) { //On vérifie que le crenneau n'est pas placé avant des creneaux d'une activité avec un ordre supérieur
								if(edtCourant.creneauDispo(jourCourant, heureCourante)) { //Enfin on regarde si la personne n'a pas déjà quelque chose de prévu à ce moment la
									titreCreneau = proj.getNom() + " | " + act.getTitre();
									edtCourant.ajouterCreneau(new CreneauHoraire(titreCreneau, act, heureCourante, act.getCouleur()), jourCourant);
									chargeAloue++;
								}
							}
						} else {
							titreCreneau = proj.getNom() + " | " + act.getTitre();
							edtCourant.ajouterCreneau(new CreneauHoraire(jourCourant, heureCourante, "Réunion : "+ titreCreneau), jourCourant);
						}
					}
					Pair<Integer, Integer> ident = new Pair<Integer, Integer>(persCourante.getType(), persCourante.getId());
					listeEDTPersonnesCourante.remove(ident);
				}

				heureCourante = heureSuivante(heureCourante);
				if(heureCourante == HEURE_DEBUT_MATIN) {
					jourCourant = verifierJour(jourCourant.plus(1, ChronoUnit.DAYS));
				}
			}
		}
		return listeEDTPersonnes;
	}


	/**
	 * Indique si le jourCourant correspond à un jour de congé
	 * @param La liste des congés d'une personne
	 * @param Le jour courant
	 * @return True si le jour est un congé
	 */
	private boolean estEnConge(ArrayList<CreneauHoraire> listeConge, LocalDate jourCourant) {
		boolean res = false;
		if(listeConge != null) {
			for (int i = 0; i < listeConge.size(); i++) {
				CreneauHoraire congeCourant = listeConge.get(i);
				if(congeCourant.getDate().equals(jourCourant)) {
					res = true;
					break;
				}
			}
		}
		return res;
	}


	/**
	 * Indique si le creneau courant est une réunion
	 * @param La liste des congés réunion d'une activité
	 * @param Le jour courant
	 * @param L'heure courante
	 * @return True si le creneau est une réunion
	 */
	private boolean estEnReunion(ArrayList<CreneauHoraire> listeReunion, LocalDate jourCourant, int heureCourante) {
		boolean res = false;
		for (int i = 0; i < listeReunion.size(); i++) {
			CreneauHoraire reuCourante = listeReunion.get(i);
			if(reuCourante.getDate().equals(jourCourant) && reuCourante.getDebut() == heureCourante) {
				res = true;
				break;
			}
		}
		return res;
	}


	/**
	 * Créée les créneaux horaires de l'EDT de toutes les salles participant à l'activité courante
	 * @param L'activité courante
	 * @param La liste des salles qui participent à l'activité
	 * @param La liste des EDT
	 * @return La liste des EDT
	 */
	private Hashtable<Pair<Integer, Integer>, EDT> creerLCreneauxSalles(Projet proj, Activite act, ArrayList<Salle> listeSalles, Hashtable<Pair<Integer, Integer>, EDT> listeEDTSalles, Hashtable<Pair<Integer, Integer>, EDT> listeEDTPersonnes) {
		if(listeSalles.size() > 0) {
			Pair<LocalDateTime, LocalDateTime> dateDebutFin = getDebutFinActivite(listeEDTPersonnes, act);
			if(dateDebutFin.getLeft() != null && dateDebutFin.getRight() != null) {
				LocalDate jourCourant = verifierJour(dateDebutFin.getLeft().toLocalDate());
				int heureCourante = dateDebutFin.getLeft().toLocalTime().getHour();

				LocalDate jourFin = verifierJour(dateDebutFin.getRight().toLocalDate());
				int heureFin = dateDebutFin.getRight().toLocalTime().getHour();

				while (!(jourCourant.equals(jourFin) && (heureCourante == heureFin))) {
					for (int i = 0; i < listeSalles.size(); i++) {
						Salle salleCourante = listeSalles.get(i);
						String titreCreneau = " " + proj.getNom() + " | " + act.getTitre()+ " ";
						EDT EDTCourant = getEDTRessource(salleCourante.getType(), salleCourante.getId(), listeEDTSalles);
						if(EDTCourant.creneauDispo(jourCourant, heureCourante)) {
							EDTCourant.ajouterCreneau(new CreneauHoraire(titreCreneau, act, heureCourante, act.getCouleur()), jourCourant);
						}
					}
					heureCourante = heureSuivante(heureCourante);
					if(heureCourante == HEURE_DEBUT_MATIN) {
						jourCourant = verifierJour(jourCourant.plus(1, ChronoUnit.DAYS));
					}
				}
				for (int i = 0; i < listeSalles.size(); i++) {
					Salle salleCourante = listeSalles.get(i);
					String titreCreneau = proj.getNom() + " | " + act.getTitre();
					EDT EDTCourant = getEDTRessource(salleCourante.getType(), salleCourante.getId(), listeEDTSalles);
					if(EDTCourant.creneauDispo(jourCourant, heureCourante)) {
						EDTCourant.ajouterCreneau(new CreneauHoraire(titreCreneau, act, heureCourante, act.getCouleur()), jourCourant);
					}
				}
			}
		}
		return listeEDTSalles;
	}



	/**
	 * Créée les créneaux horaires de l'EDT de tous les calculateurs participant à l'activité courante
	 * @param L'activité courante
	 * @param La liste des calculateurs qui participent à l'activité
	 * @param La liste des EDT
	 * @return La liste des EDT
	 */
	private Hashtable<Pair<Integer, Integer>, EDT> creerLCreneauxCalculateurs(Projet proj, Activite act, ArrayList<Calculateur> listeCalc, Hashtable<Pair<Integer, Integer>, EDT> listeEDTCalc, Hashtable<Pair<Integer, Integer>, EDT> listeEDTPersonnes) {
		if(listeCalc.size() > 0) {
			Pair<LocalDateTime, LocalDateTime> dateDebutFin = getDebutFinActivite(listeEDTPersonnes, act);
			if(dateDebutFin.getLeft() != null && dateDebutFin.getRight() != null) {
				LocalDate jourCourant = verifierJour(dateDebutFin.getLeft().toLocalDate());
				int heureCourante = dateDebutFin.getLeft().toLocalTime().getHour();

				LocalDate jourFin = verifierJour(dateDebutFin.getRight().toLocalDate());
				int heureFin = dateDebutFin.getRight().toLocalTime().getHour();

				while (!(jourCourant.equals(jourFin) && (heureCourante == heureFin))) {
					for (int i = 0; i < listeCalc.size(); i++) {
						Calculateur calcCourante = listeCalc.get(i);
						String titreCreneau = " " + proj.getNom() + " | " + act.getTitre()+ " ";
						EDT EDTCourant = getEDTRessource(calcCourante.getType(), calcCourante.getId(), listeEDTCalc);
						if(EDTCourant.creneauDispo(jourCourant, heureCourante)) {
							EDTCourant.ajouterCreneau(new CreneauHoraire(titreCreneau, act, heureCourante, act.getCouleur()), jourCourant);
						}
					}
					heureCourante = heureSuivante(heureCourante);
					if(heureCourante == HEURE_DEBUT_MATIN) {
						jourCourant = verifierJour(jourCourant.plus(1, ChronoUnit.DAYS));
					}
				}
				for (int i = 0; i < listeCalc.size(); i++) {
					Calculateur calcCourante = listeCalc.get(i);
					String titreCreneau = proj.getNom() + " | " + act.getTitre();
					EDT EDTCourant = getEDTRessource(calcCourante.getType(), calcCourante.getId(), listeEDTCalc);
					if(EDTCourant.creneauDispo(jourCourant, heureCourante)) {
						EDTCourant.ajouterCreneau(new CreneauHoraire(titreCreneau, act, heureCourante, act.getCouleur()), jourCourant);
					}
				}
			}
		}
		return listeEDTCalc;
	}

	public EDT getEDTRessource(Ressource res) {
		int id_ressource = res.getId();
		int type_ressource = res.getType();
		Pair<Integer, Integer> ident = new Pair<Integer, Integer>(id_ressource, type_ressource);
		return listeEDT.get(ident);
	}


	public EDT getEDTRessource(int type, int id){
		Pair<Integer, Integer> ident = new Pair<Integer, Integer>(id, type);
		return listeEDT.get(ident);
	}


	 private ArrayList<Personne> castRessourceEnPersonnes(ArrayList<Ressource> src) {
		 ArrayList<Personne> res = new ArrayList<Personne>();
		 for (int i = 0; i < src.size(); i++) {
			res.add((Personne)src.get(i));
		}
		return res;
	 }


	/**
	 * Retrourne les date de debut et de fin d'une activité
	 * @param La liste d'EDT dans laquelle chercher
	 * @param l'acivitée concernée
	 * @return un tuple avec le debut et la fin de l'activité
	 */
	public Pair<LocalDateTime, LocalDateTime> getDebutFinActivite(Hashtable<Pair<Integer, Integer>, EDT> listeEDT, Activite act) {
		ArrayList<EDT> res = new ArrayList<EDT>();
		Set<Pair<Integer, Integer>> keys = listeEDT.keySet();
		for (Pair<Integer, Integer> key : keys) {
			res.add(listeEDT.get(key));
		}
		return getDebutFinActivite(res, act);
	}


	/**
	 * Retrourne les date de debut et de fin d'une activité
	 * @param La liste d'EDT dans laquelle chercher
	 * @param l'acivitée concernée
	 * @return un tuple avec le debut et la fin de l'activité
	 */
	public Pair<LocalDateTime, LocalDateTime> getDebutFinActivite(ArrayList<EDT> listeEDT, Activite act) {
		LocalDateTime debut = null;
		LocalDateTime fin = null;

		LocalDateTime prem = null;
		LocalDateTime der = null;

		for (int i = 0; i < listeEDT.size(); i++) {
			EDT EDTCourant = listeEDT.get(i);

			prem = EDTCourant.getPremiereDateActivite(act);
			der = EDTCourant.getDerniereDateActivite(act);

			if(prem != null) {
				if(debut == null) {
					debut = prem;
				}else if(prem.isBefore(debut)) {
					debut = prem;
				}
			}
			if (der != null) {
				if(fin == null) {
					fin = der;
				} else if(der.isAfter(fin)) {
					fin = der;
				}
			}
		}


		return new Pair<LocalDateTime, LocalDateTime>(prem, der);
	}

	/**
	 * Retourne la dernière date de travail d'une activité
	 * @param listeEDT contient toute les info de creneau de l'activite par ressource
	 * @param act activite concernée
	 * @return La fin d'une activité
	 */
	public LocalDate getLocalDateFinDuneActivite(ArrayList<EDT> listeEDT, Activite act) {
		Pair<LocalDateTime, LocalDateTime> dates = getDebutFinActivite(listeEDT, act);
		return dates.getRight().toLocalDate();
	}

	/**
	 * Retourne la dernière date de travail d'une activité
	 * @param listeEDT contient toute les info de creneau de l'activite par ressource
	 * @param act activite concernée
	 * @return La fin d'une activité
	 */
	public static LocalDate getLocalDateFinDunProjet(Projet p) {

	}

	/**
	 * Initialise un EDT (vide) pour une ressource
	 * @param l'identifiant de la ressource concernée
	 */
	public void ajouterEDTRessource(Pair<Integer, Integer> ident) {
		listeEDT.put(ident, new EDT(ident));
	}


	/**
	 * Renvoie l'EDT de la ressource dont les identifiants ont été passés en paramètre (pour le graphique)
	 * @param Identifiants de la ressource
	 * @return l'EDT de la ressource
	 */
	public EDT getEDTRessource(int type, int id){
		majEDT();
		Pair<Integer, Integer> ident = new Pair<Integer, Integer>(type, id);
		if(!listeEDT.containsKey(ident)) {
			ajouterEDTRessource(ident);
		}
		return listeEDT.get(ident);
	}

	/**
	 * Renvoie l'EDT de la ressource dont les identifiants ont été passés en paramètre en cherchant dans une liste spécifique
	 * @param Identifiants de la ressource
	 * @return l'EDT de la ressource
	 * @return La liste dans laquelle chercher
	 */
	private EDT getEDTRessource(int type, int id, Hashtable<Pair<Integer, Integer>, EDT> listeEDT){
		Pair<Integer, Integer> ident = new Pair<Integer, Integer>(type, id);
		if(!listeEDT.containsKey(ident)) {
			listeEDT.put(ident, new EDT(ident));
		}
		return listeEDT.get(ident);
	}

	/**
	 * Transforme une liste de Ressources en liste de Personnes
	 * @param La liste de Ressources
	 * @return a liste de Personnes
	 */
	private ArrayList<Personne> castListeRessourceEnPersonnes(ArrayList<Ressource> src) {
		 ArrayList<Personne> res = new ArrayList<Personne>();
		 for (int i = 0; i < src.size(); i++) {
			res.add((Personne)src.get(i));
		}
		return res;
	 }

	/**
	 * Transforme une liste de Ressources en liste de Salle
	 * @param La liste de Ressources
	 * @return a liste de Salle
	 */
	private ArrayList<Salle> castListeRessourceEnSalles(ArrayList<Ressource> src) {
		 ArrayList<Salle> salles = new ArrayList<Salle>();
		 for (int i = 0; i < src.size(); i++) {
			 salles.add((Salle)src.get(i));
		}
		return salles;
	 }

	/**
	 * Transforme une liste de Ressources en liste de Calculateur
	 * @param La liste de Ressources
	 * @return a liste de Calculateur
	 */
	private ArrayList<Calculateur> castListeRessourceEnCalculateurs(ArrayList<Ressource> src) {
		 ArrayList<Calculateur> calc = new ArrayList<Calculateur>();
		 for (int i = 0; i < src.size(); i++) {
			calc.add((Calculateur)src.get(i));
		}
		return calc;
	 }

	/**
	 * Vérifie qu'une activité d'ordre n+1 soit placée après une activité d'ordre n
	 * @param L'EDT, l'activité, le jour et l'heure concernés
	 * @return true si c'est le cas
	 */
	private boolean verifierOrdre(Hashtable<Pair<Integer, Integer>, EDT> listeEDT, Activite act, LocalDate jour, int heure) {
		LocalDateTime courant = LocalDateTime.of(jour, LocalTime.of(heure, 0));
		LocalDateTime tmp = null;
		LocalDateTime premierLibre = null;
		Set<Pair<Integer, Integer>> keys = listeEDT.keySet();
		for (Pair<Integer, Integer> key : keys) {
			tmp = listeEDT.get(key).getPremiereCreneauApresAct();
			if(premierLibre != null && tmp != null) {
				if(premierLibre.isBefore(tmp)) {
					premierLibre = tmp;
				}
			} else if (premierLibre == null && tmp != null) {
				premierLibre = tmp;
			}
		}
		return premierLibre == null || (premierLibre.isEqual(courant) || premierLibre.isBefore(courant));
	}
	*/


	/**
	 * Vérifie que le jour courant est un jour ouvrable, si ce n'est pas le cas il renvoie le jour ouvrable le plus proche
	 * @param Le jour courant
	 * @return Le jour vérifié et/ou modifié
	 */
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

	/**
	 * Donne l'heure suivant en tenant compte des horaires de l'entreprise
	 * @param L'heure courante
	 * @return L'heure suivante modifiée ou non
	 */
	private int heureSuivante(int heureCourante) {
		int heureSuivante = heureCourante + 1;
		if(heureSuivante == HEURE_FIN_MATIN) {
			heureSuivante = HEURE_DEBUT_APREM;
		} else if (heureSuivante == HEURE_FIN_APREM){
			heureSuivante = HEURE_DEBUT_MATIN;
		}
		return heureSuivante;
	}



	//-------------------------------------------------------------------------------------------------------------->>>>>>>>>>> Recherche dans la bdd

	//------------------------------>>>>> Retourne element de la bdd


	public ArrayList<Activite> getListeActivite(){
		ArrayList<Activite> activiteTab = new ArrayList<Activite>();
		try {
			activiteTab = JavaSQLRecherche.recupereListeActivite();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return activiteTab ;
	}



	/**
	 * Cherche toute les ressource du type choisi de l'entreprise de la bdd
	 * @param type de la ressource
	 * @return liste de ressource
	 */
	public  ArrayList<Ressource> getListeRessourceEntrepriseParType(int type){
		ArrayList<Ressource> l = new ArrayList<Ressource>();
		switch (type) {
		case Ressource.PERSONNE:
			l = getListePersonneEntreprise();
			break;
		case Ressource.SALLE:
			l = getListeSalleEntreprise();
			break;
		case Ressource.CALCULATEUR:
			l = getListeCalculateurEntreprise();
			break;
		default:
			break;
		}
		return l;

	}

	/**
	 * Cherche toutes les ressource de l'entreprise de la bdd
	 * @return la liste des ressources de l'entreprise
	 */
	public ArrayList<Ressource> getListeRessourceEntreprise(){
		ArrayList<Ressource> ressourceTab = new ArrayList<Ressource>();
		ressourceTab.addAll(getListePersonneEntreprise());
		ressourceTab.addAll(getListeSalleEntreprise());
		ressourceTab.addAll(getListeCalculateurEntreprise());


		return ressourceTab;
	}




	/**
	 * Cherche toutes les personnes de l'entreprise de la bdd
	 * @return la liste des personnes de l'entreprise
	 */
	public ArrayList<Ressource> getListePersonneEntreprise(){
		ArrayList<Ressource> personneTab = new ArrayList<Ressource>();
		try {
			personneTab = JavaSQLRecherche.recuperePersonne();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return personneTab ;
	}

	/**
	 * Cherche toute les salles de l'entreprise de la bdd
	 * @return la liste des salles de l'entreprise
	 */
	public ArrayList<Ressource> getListeSalleEntreprise(){
		ArrayList<Ressource> salleTab = new ArrayList<Ressource>();
		try {
			salleTab = JavaSQLRecherche.recupereSalle();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return salleTab;
	}

	/**
	 * Cherche toute les calculateurs de l'entreprise de la bdd
	 * @return la liste des calculateurs de l'entreprise
	 */
	public ArrayList<Ressource> getListeCalculateurEntreprise(){

		ArrayList<Ressource> calculateurTab = new ArrayList<Ressource>();
        try {
        	calculateurTab = JavaSQLRecherche.recupereCalculateur();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return calculateurTab;
	}

	/**
	 * Cherche toute les domaines de l'entreprise de la bdd
	 * @return la liste des domaines de l'entreprise
	 */
	public ArrayList<String> getListeDomaineEntreprise(){
		ArrayList<String> tagTab = new ArrayList<String>();
		try {
			tagTab = JavaSQLDomaine.affiche();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tagTab;
	}

	/**
	 * Cherche toute les materiels de l'entreprise de la bdd
	 * @return la liste des materiels de l'entreprise
	 */
	public ArrayList<Materiel> getListeMaterielEntreprise(){
		 ArrayList<Materiel> materielTab = new ArrayList<Materiel>();
         try {
         	materielTab = JavaSQLMateriel.affiche();

         } catch (SQLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
		return materielTab;
	}



	public ArrayList<Projet> getListeProjetDeUser(int idUser){
		ArrayList<Projet> projetTab = new ArrayList<Projet>();
        try {
        	projetTab = JavaSQLRecherche.recupereProjetParIdPersonne(idUser);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return projetTab;
		}

	/**
	 * Cherche tous les projets dont l'user est le chef
	 * @param id de l'user
	 * @return la liste de projet
	 */
	public ArrayList<Projet> getListeProjetparIdChef(int idUser){
		ArrayList<Projet> projetTab = new ArrayList<Projet>();
        try {
        	projetTab = JavaSQLRecherche.recupereProjetParIdChef(idUser);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return projetTab;
		}
	/**
	 * Cherche tout les projets de l'entreprise dans la bdd
	 * @return la liste de projet
	 */
	public ArrayList<Projet> getListeProjetDeEntreprise(){
		 ArrayList<Projet> projetTab = new ArrayList<Projet>();
         try {
         	projetTab = JavaSQLProjet.affiche();

         } catch (SQLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
		return projetTab;
		}

	/**
	 * Cherche tous les tickets envoyé par l'user
	 * @param id de l'user
	 * @return la liste de ticket envoyé
	 */
	public ArrayList<Ticket> getListeTicketEnvoyeDeUser(int idUser){
		ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();
        try {
        	ticketTab = JavaSQLRecherche.recupereTicketEnvUser(idUser);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return ticketTab;
		}

	public ArrayList<Ticket> getListeTicketEnvoyeDeUserSaufMemeReceveurEnvoyeurPasTransfert(int idUser){
		ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();
        try {
        	ticketTab = JavaSQLRecherche.recupereTicketEnvUserSaufMemeReceveurEnvoyeurPasTransfert(idUser);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return ticketTab;
		}


	/**
	 * Cherche tout les tickets recu par l'user
	 * @param id de l'user
	 * @return la liste de ticket recu
	 */
	public ArrayList<Ticket> getListeTicketRecuDeUser(int idUser){
		ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();
        try {
        	ticketTab = JavaSQLRecherche.recupereTicketRecUser(idUser);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return ticketTab;
		}

	public ArrayList<Ticket> getListeTicketRecuDeUserSaufMemeReceveurEnvoyeurPasTransfert(int idUser){
		ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();
        try {
        	ticketTab = JavaSQLRecherche.recupereTicketRecUserSaufMemeReceveurEnvoyeurPasTransfert(idUser);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return ticketTab;
		}


	//------------------------------>>>>> Recherche element dans la bdd

	/**
	 * cherche dans la bdd la personne avec l'id correspondant
	 * @param id de la ressource
	 * @return personne concerné, sinon null
	 */
	public Ressource getPersonneParId(int id) {
		Ressource personne= null;

        try {
        	personne = JavaSQLRecherche.recuperePersonneParId(id);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return personne;
	}

	public ArrayList<Ressource> getListePersonneParTag(String tag) {
		ArrayList<Ressource> personneTab= null;

        try {
        	personneTab = JavaSQLRecherche.recupereListePersonneParTag(tag);

        } catch (SQLException e) {
            e.printStackTrace();
        }

		return personneTab;
	}

	public ArrayList<Ressource> getListePersonneTrieAlphabetiquement() {
		ArrayList<Ressource> personneTab= null;

        try {
        	personneTab = JavaSQLRecherche.recupereListePersonneParOrdreAlphabetique();

        } catch (SQLException e) {
            e.printStackTrace();
        }

		return personneTab;
	}



	/**
	 * cherche  dans la bdd la Salle avec l'id correspondant
	 * @param id de la ressource
	 * @return Salle concerné, sinon null
	 */
	public Ressource getSalleParId(int id) {
		Ressource salle= null;

        try {
        	salle = JavaSQLRecherche.recupereSalleParId(id);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return salle;
	}

	/**
	 * cherche dans la bdd la Calculateur avec l'id correspondant
	 * @param id de la ressource
	 * @return Calculateur concerné, sinon null
	 */
	public Ressource getCalculateurParId(int id) {
		Ressource calcul = null;

        try {
        	calcul = JavaSQLRecherche.recupereCalculateurParId(id);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return calcul;
	}


	/**
	 * cherche dans la bdd la ressource avec le login correspondant
	 * @param type de la ressource
	 * @param login de la ressource
	 * @return la ressource
	 */
	public Ressource getRessourceParLogin(int type, String login) {
		Ressource r = null;
		switch (type) {
		case Ressource.PERSONNE:
			r = getPersonneParLogin(login);
			break;
		case Ressource.SALLE:
			r = getSalleParLogin(login);
			break;
		case Ressource.CALCULATEUR:
			r = getCalculateurParLogin(login);
			break;
		default:
			break;
		}
		return r;
	}

	/**
	 * cherche dans la bdd la personne avec le login correspondant
	 * @param login de la ressource
	 * @return personne concerné, sinon null
	 */
	public Ressource getPersonneParLogin(String login) {
		int id = Ressource.recupereIdDepuisLogin(login);


		return getPersonneParId(id);
	}
	/**
	 * cherche  dans la bdd la Salle avec le login correspondant
	 * @param login de la ressource
	 * @return Salle concernée, sinon null
	 */
	public Ressource getSalleParLogin(String login) {
		int id = Ressource.recupereIdDepuisLogin(login);


		return getSalleParId(id);
	}

	/**
	 * cherche dans la bdd la Calculateur avec le login correspondant
	 * @param login de la ressource
	 * @return Calculateur concerné, sinon null
	 */
	public Ressource getCalculateurParLogin(String login) {
		int id = Ressource.recupereIdDepuisLogin(login);


		return getCalculateurParId(id);
	}


	public Ressource getRessourceParId(int type, int id) {
		Ressource r = null;
		switch (type) {
		case Ressource.PERSONNE:
			r = getPersonneParId(id);
			break;
		case Ressource.SALLE:
			r = getSalleParId(id);
			break;
		case Ressource.CALCULATEUR:
			r = getCalculateurParId(id);
			break;
		default:
			break;
		}
		return r;
	}



	/**
	 * cherche dans la bdd l'activite associer a son id
	 * @param id de l'activite
	 * @return l'activite associer
	 */
	public Activite getActiviteParId(int idA) {
		Activite act = null;
		try {
			act = JavaSQLRecherche.recupereActiviteParId(idA);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return act;
	}

	/**
	 * cherche dans la bdd le projet associer a l'activite
	 * @param id de l'activite
	 * @return le projet associer
	 */
	public Projet getProjetDeActiviteParId(int idA) {
		Projet projCour = null;

		try {
			projCour = JavaSQLRecherche.recupereProjetParIdActivite(idA);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return projCour;
	}


	/**
	 * Cherche tout les ressource contenut par l'activite par son id
	 * @param type de la ressource
	 * @param id de l'activite
	 * @return la liste de ressource de l'activite
	 */
	public ArrayList<Ressource> getListeRessourcedeActiviteParId(int type, int id){
		ArrayList<Ressource> l = new ArrayList<Ressource>();
		switch (type) {
		case Ressource.PERSONNE:
			l = getListePersonnedeActiviteParId(id);
			break;
		case Ressource.SALLE:
			l = getListeSalledeActiviteParId(id);
			break;
		case Ressource.CALCULATEUR:
			l = getListeCalculateurdeActiviteParId(id);
			break;
		default:
			break;
		}
		return l;
	}

	/**
	 * Cherche toutes les personnes contenue par l'activite par son id
	 * @param id de l'activite
	 * @return la liste de personnes de l'activite
	 */
	public ArrayList<Ressource> getListePersonnedeActiviteParId(int id){

		ArrayList<Ressource> liste= new ArrayList<Ressource>();
		try {
			liste =  JavaSQLRecherche.recuperePersonneDansActiviteParId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}

	/**
	 * Cherche toutes les salle contenut par l'activite par son id
	 * @param id de l'activite
	 * @return la liste de salle de l'activite
	 */
	public ArrayList<Ressource> getListeSalledeActiviteParId(int id){
		ArrayList<Ressource> liste= new ArrayList<Ressource>();

		try {
			liste = JavaSQLRecherche.recupereSalleDansActiviteParId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}
	/**
	 * Cherche tous les calculateurs contenut par l'activite par son id
	 * @param id de l'activite
	 * @return la liste de calculateur de l'activite
	 */
	public ArrayList<Ressource> getListeCalculateurdeActiviteParId(int id){
		ArrayList<Ressource> liste= new ArrayList<Ressource>();

		try {
			liste =  JavaSQLRecherche.recupereCalculateurDansActiviteParId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}

	/**
	 * Cherche tous les projets concerné de la ressource par son login
	 * @param type de la ressource
	 * @param login de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Projet> getListeProjetdeRessourceParLogin(int type, String login){
		ArrayList<Projet> l = new ArrayList<Projet>();
		switch (type) {
		case Ressource.PERSONNE:
			l = getListeProjetDePersonneParLogin(login);
			break;
		case Ressource.SALLE:
			l = getListeProjetDeSalleParLogin(login);
			break;
		case Ressource.CALCULATEUR:
			l = getListeProjetDeCalculateurParLogin(login);
			break;
		default:
			break;
		}
		return l;
	}

	/**
	 * Cherche tous les projets concerné de la personne par son login
	 * @param login de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Projet> getListeProjetDePersonneParLogin(String login){
		int id = Ressource.recupereIdDepuisLogin(login);
		ArrayList<Projet> liste= new ArrayList<Projet>();


		try {
			liste =  JavaSQLRecherche.recupereProjetParIdPersonne(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}

	/**
	 * Cherche tous les projets concerné de la salle par son login
	 * @param login de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Projet> getListeProjetDeSalleParLogin(String login){
		int id = Ressource.recupereIdDepuisLogin(login);
		ArrayList<Projet> liste= new ArrayList<Projet>();

		try {
			liste =  JavaSQLRecherche.recupereProjetParIdSalle(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}

	/**
	 * Cherche tous les projets concerné de la calculateur par son login
	 * @param login de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Projet> getListeProjetDeCalculateurParLogin(String login){
		int id = Ressource.recupereIdDepuisLogin(login);
		ArrayList<Projet> liste= new ArrayList<Projet>();


		try {
			liste =  JavaSQLRecherche.recupereProjetParIdCalculateur(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
	}

	/**
	 * Cherche tous les activites concerné de la ressource par son login
	 * @param type de la ressource
	 * @param login de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Activite> getListeActivitetdeRessourceParLogin(int type, String login){
		ArrayList<Activite> l = new ArrayList<Activite>();
		switch (type) {
		case Ressource.PERSONNE:
			l = getListeActiviteDePersonneParLogin(login);
			break;
		case Ressource.SALLE:
			l = getListeActiviteDeSalleParLogin(login);
			break;
		case Ressource.CALCULATEUR:
			l = getListeActiviteDeCalculateurParLogin(login);
			break;
		default:
			break;
		}
		return l;
	}

	/**
	 * Cherche tous les activites concerné de la personne par son login
	 * @param login de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Activite> getListeActiviteDePersonneParLogin(String login){
		int id = Ressource.recupereIdDepuisLogin(login);
		ArrayList<Activite> l = new ArrayList<Activite>();


		try {
			l =  JavaSQLRecherche.recupereActiviteParIdPersonne(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Cherche tous les activites concerné de la salle par son login
	 * @param login de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Activite> getListeActiviteDeSalleParLogin(String login){
		int id = Ressource.recupereIdDepuisLogin(login);
		ArrayList<Activite> l = new ArrayList<Activite>();


		try {
			l =  JavaSQLRecherche.recupereActiviteParIdSalle(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Cherche tous les activites concerné de la calculateur par son login
	 * @param login de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Activite> getListeActiviteDeCalculateurParLogin(String login){
		int id = Ressource.recupereIdDepuisLogin(login);
		ArrayList<Activite> l = new ArrayList<Activite>();


		try {
			l =  JavaSQLRecherche.recupereActiviteParIdCalculateur(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Cherche tous les projets concerné de la ressource par son id
	 * @param type de la ressource
	 * @param id de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Projet> getListeProjetdeRessourceParId(int type, int id){
		ArrayList<Projet> l = new ArrayList<Projet>();
		switch (type) {
		case Ressource.PERSONNE:
			l = getListeProjetDePersonneParId(id);
			break;
		case Ressource.SALLE:
			l = getListeProjetDeSalleParId(id);
			break;
		case Ressource.CALCULATEUR:
			l = getListeProjetDeCalculateurParId(id);
			break;
		default:
			break;
		}
		return l;
	}

	/**
	 * Cherche tout les projets concerné de la personne par son id
	 * @param login de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Projet> getListeProjetDePersonneParId(int id){
		ArrayList<Projet> l = new ArrayList<Projet>();

		try {
			l =  JavaSQLRecherche.recupereProjetParIdPersonne(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Cherche tout les projets concerné de la salle par son id
	 * @param login de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Projet> getListeProjetDeSalleParId(int id){
		ArrayList<Projet> l = new ArrayList<Projet>();

		try {
			l = JavaSQLRecherche.recupereProjetParIdSalle(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Cherche tout les projets concerné de la calculateur par son od
	 * @param login de la ressource
	 * @return la liste de projet de la ressource
	 */
	public ArrayList<Projet> getListeProjetDeCalculateurParId(int id){
		ArrayList<Projet> l = new ArrayList<Projet>();

		try {
			l =  JavaSQLRecherche.recupereProjetParIdCalculateur(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Cherche tout les activites concerné de la ressource par son id
	 * @param type de la ressource
	 * @param login de la ressource
	 * @return la liste d'activite de la ressource
	 */
	public ArrayList<Activite> getListeActivitetdeRessourceParId(int type, int id){
		ArrayList<Activite> l = new ArrayList<Activite>();
		switch (type) {
		case Ressource.PERSONNE:
			l = getListeActiviteDePersonneParId(id);
			break;
		case Ressource.SALLE:
			l = getListeActiviteDeSalleParId(id);
			break;
		case Ressource.CALCULATEUR:
			l = getListeActiviteDeCalculateurParId(id);
			break;
		default:
			break;
		}
		return l;
	}

	/**
	 * Cherche tout les activites concerné de la personne par son id
	 * @param login de la ressource
	 * @return la liste d'activite de la ressource
	 */
	public ArrayList<Activite> getListeActiviteDePersonneParId(int id){
		ArrayList<Activite> l = new ArrayList<Activite>();

		try {
			l =  JavaSQLRecherche.recupereActiviteParIdPersonne(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
		}

	/**
	 * Cherche tout les activites concerné de la salle par son id
	 * @param login de la ressource
	 * @return la liste d'activite de la ressource
	 */
	public ArrayList<Activite> getListeActiviteDeSalleParId(int id){
		ArrayList<Activite> l = new ArrayList<Activite>();

		try {
			l =  JavaSQLRecherche.recupereActiviteParIdSalle(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
		}

	/**
	 * Cherche tout les activites concerné de la calculateur par son id
	 * @param login de la ressource
	 * @return la liste d'activite de la ressource
	 */
	public ArrayList<Activite> getListeActiviteDeCalculateurParId(int id){
		ArrayList<Activite> l = new ArrayList<Activite>();

		try {
			l =  JavaSQLRecherche.recupereActiviteParIdCalculateur(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
		}

	/**
	 * Cherche tout les activites du projet
	 * @param id du projet
	 * @return la liste d'activite du projet
	 */
	public ArrayList<Activite> getListeActiviteDuProjet(int idP){
		ArrayList<Activite> activiteTab = new ArrayList<Activite>();
		try {
			activiteTab = JavaSQLRecherche.recupereListeActiviteParIdProjet(idP);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activiteTab;
	}


	/**
	 * Cherche dans la bdd tout les chefs de projets qui gere la ressource tester
	 * @param type de la ressource
	 * @param login de la ressource
	 * @return la liste de personne
	 */
	public ArrayList<Ressource> getListeDesChefDeProjetPossedantLaRessourceParLogin(int type, String login){
		int id = Ressource.recupereIdDepuisLogin(login);
		ArrayList<Ressource> l = new ArrayList<Ressource>();
		switch (type) {
		case Ressource.PERSONNE:
			l = getListeDesChefDeProjetPossedantLaPersonneParId(id);
			break;
		case Ressource.SALLE:
			l = getListeDesChefDeProjetPossedantLaSalleParId(id);
			break;
		case Ressource.CALCULATEUR:
			l = getListeDesChefDeProjetPossedantLeCalculateurParId(id);
			break;
		default:
			break;
		}
		return l;

	}
	private ArrayList<Ressource> getListeDesChefDeProjetPossedantLeCalculateurParId(int id) {
		ArrayList<Ressource> chefListe = new ArrayList<Ressource>();
		try {
			chefListe = JavaSQLRecherche.recupereChefDeProjetParIdCalculateur(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chefListe;
	}
	private ArrayList<Ressource> getListeDesChefDeProjetPossedantLaSalleParId(int id) {
		ArrayList<Ressource> chefListe = new ArrayList<Ressource>();
		try {
			chefListe = JavaSQLRecherche.recupereChefDeProjetParIdSalle(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chefListe;
	}
	private ArrayList<Ressource> getListeDesChefDeProjetPossedantLaPersonneParId(int id) {
		ArrayList<Ressource> chefListe = new ArrayList<Ressource>();
		try {
			chefListe = JavaSQLRecherche.recupereChefDeProjetParIdPersonne(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chefListe;
	}


	/**
	 * Cherche dans la bdd les domaines de l'activite
	 * @param idA de l'activite
	 * @return la liste domaine de l'activite
	 */
	public ArrayList<String> getListeDomaineParIdActivite(int idA) {
		ArrayList<String> liste = new ArrayList<String>();
		try {
			liste = JavaSQLRecherche.recupereListeDomaineParIdActivite(idA);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}

	/**
	 * Cherche dans la bdd toute les conges de la personnes
	 * @param id de la personne
	 * @return liste de conge de la personne
	 */
	public ArrayList<CreneauHoraire> getListeCongeDePersonne(int id) {
		ArrayList<CreneauHoraire> l = new ArrayList<CreneauHoraire>();
		try {
			l = JavaSQLRecherche.getConge(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Cherche dans la bdd toute les reunion d'une activite
	 * @param id du projet
	 * @return liste des reunions du projet
	 */
	public ArrayList<CreneauHoraire> getListeReunionDeActivite(int idA) {
		ArrayList<CreneauHoraire> l = new ArrayList<CreneauHoraire>();
		try {
			l = JavaSQLRecherche.getReunion(idA);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}




	//------------------------------>>>>> Verifie condition dans la bdd



	/**
	 * Cherche dans la bdd si la ressource est dans le projet
	 * @param type de la ressource
	 * @param r ressource tester
	 * @param p projet  tester
	 * @return vrai si la ressource est presente sinon faux
	 */
	public boolean ressourcePresenteDansProjet(int type, Ressource r, Projet p) {
		boolean b = false;
		switch (type) {
		case Ressource.PERSONNE:
			b = personnePresenteDansProjet((Personne) r, p);
			break;
		case Ressource.SALLE:
			b = sallePresenteDansProjet((Salle) r, p);
			break;
		case Ressource.CALCULATEUR:
			b = calculateurPresenteDansProjet((Calculateur) r, p);
			break;
		default:
			break;
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la personne est presente dans le projet tester
	 * Permet d'eviter de placer deux fois la meme ressource
	 * @param personne à tester
	 * @param projet à tester
	 * @return vrai si la personne est presente sinon faux
	 */
	public boolean personnePresenteDansProjet(Personne r, Projet p) {
		Boolean b = false;
		try {
			b = JavaSQLRecherche.presenceProjetParIdPersonne(r.getId(),p.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la salle est presente dans le projet tester
	 * Permet d'eviter de placer deux fois la meme ressource
	 * @param salle à tester
	 * @param projet à tester
	 * @return vrai si la salle est presente sinon faux
	 */
	public boolean sallePresenteDansProjet(Salle r, Projet p) {
		Boolean b = false;
		try {
			b = JavaSQLRecherche.presenceProjetParIdSalle(r.getId(),p.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la calculateur est presente dans le projet tester
	 * Permet d'eviter de placer deux fois la meme ressource
	 * @param calculateur à tester
	 * @param projet à tester
	 * @return vrai si la calculateur est presente sinon faux
	 */
	public boolean calculateurPresenteDansProjet(Calculateur r, Projet p) {
		Boolean b = false;
		try {
			b = JavaSQLRecherche.presenceProjetParIdCalculateur(r.getId(),p.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * Cherche dans la bdd si la ressource est dans l'activite
	 * @param type de la ressource
	 * @param r ressource tester
	 * @param a activite  tester
	 * @return vrai si la ressource est presente dans l'activite sinon faux
	 */
	public boolean ressourcePresenteDansActivite(int type, Ressource r, Activite a) {
		boolean b = false;
		switch (type) {
		case Ressource.PERSONNE:
			b = personnePresenteDansActivite((Personne) r, a);
			break;
		case Ressource.SALLE:
			b = sallePresenteDansActivite((Salle) r, a);
			break;
		case Ressource.CALCULATEUR:
			b = calculateurPresenteDansActivite((Calculateur) r, a);
			break;
		default:
			break;
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la personne est presente dans le activiter tester
	 * Permet d'eviter de placer deux fois la meme ressource
	 * @param personne à tester
	 * @param activite à tester
	 * @return vrai si la personne est presente sinon faux
	 */
	public boolean personnePresenteDansActivite(Personne r, Activite a) {
		Boolean b = false;
		try {
			if (JavaSQLRecherche.presenceActivitePersonne(r,a.getId())) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la salle est presente dans le activiter tester
	 * Permet d'eviter de placer deux fois la meme ressource
	 * @param salle à tester
	 * @param activite à tester
	 * @return vrai si la salle est presente sinon faux
	 */
	public boolean sallePresenteDansActivite(Salle r, Activite a) {
		Boolean b = false;
		try {
			b = JavaSQLRecherche.presenceActiviteSalle(r,a.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la calculateur est presente dans le activiter tester
	 * Permet d'eviter de placer deux fois la meme ressource
	 * @param calculateur à tester
	 * @param activite à tester
	 * @return vrai si la calculateur est presente sinon faux
	 */
	public boolean calculateurPresenteDansActivite(Calculateur r, Activite a) {
		Boolean b = false;
		try {
			b = JavaSQLRecherche.presenceActiviteCalculateur(r,a.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * cherche dans la bdd si il y a une personne ou une activite qui possede le domaine tester
	 * @param d domaine tester
	 * @return vrai si une personne ou une activite à ce domaine, sinon faux
	 */
	public boolean PersonneOuActiviteACeDomaine(String d) {
		Boolean b = false;
		try {
			if (JavaSQLRecherche.presenceDomaineDansCompetence(d)|| JavaSQLRecherche.presenceDomaineDansListeDomaine(d)) {
			b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la ressource est dans une activite
	 * @param id de la ressource
	 * @return vrai si la ressource travaille dans une activité
	 */
	public boolean ressourceTravailleDansUneActiviteParId(int type, int id) {
		boolean b = false;
		switch (type) {
		case Ressource.PERSONNE:
			b = personneTravailleDansUneActiviteParId(id);
			break;
		case Ressource.SALLE:
			b = salleTravailleDansUneActiviteParId(id);
			break;
		case Ressource.CALCULATEUR:
			b = calculateurTravailleDansUneActiviteParId(id);
			break;

		default:
			break;
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la personne est dans une activite
	 * @param d domaine tester
	 * @return vrai si la personne travaille dans une activité
	 */
	public boolean personneTravailleDansUneActiviteParId(int id) {
		Boolean b = false;
		try {
			if (JavaSQLRecherche.presenceDansUneActivitePersonne(id)) {
			b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la salle est dans une activite
	 * @param id de la ressource
	 * @return vrai si la salle travaille dans une activité
	 */
	public boolean salleTravailleDansUneActiviteParId(int id) {
		Boolean b = false;
		try {
			if (JavaSQLRecherche.presenceDansUneActiviteSalle(id)) {
			b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la calculateur est dans une activite
	 * @param id de la ressource
	 * @return vrai si la calculateur travaille dans une activité
	 */
	public boolean calculateurTravailleDansUneActiviteParId(int id) {
		Boolean b = false;
		try {
			if (JavaSQLRecherche.presenceDansUneActiviteCalculateur(id)) {
			b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
     * cherche l'user avec son login et son mdp
     * @param login du compte
     * @param mdp du compte
     * @return la personne asssocie sinon null
     */
	public static Ressource chercheUser(String login, String mdp) {
    	Ressource p = null;
		int id = Ressource.recupereIdDepuisLogin(login);
		try {
			p = JavaSQLRecherche.recuperePersonneParId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!(((Personne)p).getMdp().equals(mdp))) {
			p = null;
		}


    	return p;
    }


	/**
	 * verifie que la ressource travaille dans une activite ou est chef de projet
	 * @param type de la ressource
	 * @param id de la ressource
	 * @return vrai si la ressource travaille ou est chef de projet sinon faux
	 */
	public boolean ressourceTravailleDansUnProjetParId(int type, int id) {
		boolean b = false;
		switch (type) {
		case Ressource.PERSONNE:
			b = personneTravailleDansUnProjetParId(id);
			break;
		case Ressource.SALLE:
			b = salleTravailleDansUnProjetParId(id);
			break;
		case Ressource.CALCULATEUR:
			b = calculateurTravailleDansUnProjetParId(id);
			break;

		default:
			break;
		}
		return b;
	}
	/**
	 * cherche dans la bdd si la personne est dans une activite
	 * @param d domaine tester
	 * @return vrai si la personne travaille dans une activité
	 */
	public boolean personneTravailleDansUnProjetParId(int id) {
		Boolean b = false;
		try {
			if (JavaSQLRecherche.presenceDansUnProjetPersonne(id)) {
			b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la salle est dans une activite
	 * @param id de la ressource
	 * @return vrai si la salle travaille dans une activité
	 */
	public boolean salleTravailleDansUnProjetParId(int id) {
		Boolean b = false;
		try {
			if (JavaSQLRecherche.presenceDansUnProjetSalle(id)) {
			b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * cherche dans la bdd si la calculateur est dans une activite
	 * @param id de la ressource
	 * @return vrai si la calculateur travaille dans une activité
	 */
	public boolean calculateurTravailleDansUnProjetParId(int id) {
		Boolean b = false;
		try {
			if (JavaSQLRecherche.presenceDansUnProjetCalculateur(id)) {
			b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}


	//------------------------------------------------------------------------------------------------------------------------------->>>>>>>>>> Gestion projet

	public void creerProjet(Personne chefDeProjet, String nom, int priorite, LocalDate deadline) {//creee un projet si son nom n'est pas deejee utilisee
		try {
			JavaSQLProjet.insertion(nom, priorite, deadline, 0, chefDeProjet.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	//	selectionnerProjet(newProjet);
		update();
	}

	/*public void ajouterProjet(Projet proj) { //Les projets sont ajouteees eee la liste en les triant par ordre de priorite
		Boolean place = false;
		int i = 0;
		while (i < lProjet.size() && !place) {
			if (proj.compareTo(lProjet.get(i)) == 1) { //Si proj > lProjet.egt(i)
				lProjet.add(i, proj);
				place = true;
			}
		}
		if (place = false) {
			lProjet.add(proj);
		}
	}*/

	public void modifierProjet(Projet projet, String nom, int priorite, Personne chefDeProjet, LocalDate deadline) {
		projet.setNom(nom);
		projet.setPriorite(priorite);
		projet.setChefDeProjet(chefDeProjet);
		projet.setDeadline(deadline);
		try {
			JavaSQLProjet.modifie(projet.getId(), nom, priorite, deadline, 0, chefDeProjet.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//majEDT();
		update();
	}


	public void supprimerProjet(Projet projet) {

		for (int i=0; i<projet.getListe().size(); i++) { // on supprime toutes ses activiteeeeees
			supprimerActiviter(projet.getListe().get(i));
		}

		projet.getChefDeProjet().enleverProjet(projet);  // on enleve au chef de projet le projet supprimer

		ArrayList<Projet> lp = getListeProjetDeUser(user.getId());
		for (int i=0; i<lp.size(); i++) { // on enleve le projet de l'entreprise
			if (projet.getId() == lp.get(i).getId()) {
				lp.remove(i);
				break;
			}
		}
		try {
			JavaSQLProjet.supprime(projet.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		projetSelectionner = null;// enleve la selection projet
		//majEDT(); // remet eeeeee jour les emplois du temps
		update(); // remet eeeeee jour l'interface
	}


	//------------------------------------------------------------------------------------------------------------------------------->>>>>>>>>> Gestion activite

	public void creerActivite(Projet projet, String titre, float charge, LocalDate debut, ArrayList<String> listeDomaine) {
		int ordre = getListeActiviteDuProjet(projet.getId()).size();
		Color couleur = couleurAleatoire();
		//selectionnerActivite(act);
		try {
			JavaSQLActivite.insertion(titre, debut, charge, ordre, couleur.getRGB(), projet.getId(), listeDomaine);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}

	public void modifierActivite(Activite activite, String nom, float charge, LocalDate date) {
		activite.setTitre(nom);
		activite.setCharge(charge);
		activite.setDebut(date);

		try {
			JavaSQLActivite.modifie(activite.getId(), nom, date, charge, activite.getOrdre(),
					activite.getCouleur().getRGB(), projetSelectionner.getId(), activite.getListeDomaine());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//majEDT();
		update();
	}


	public void supprimerActiviter(Activite activite) {
		Projet projet = getProjetSelectionner();

		for (int i=0; i<projet.getListe().size(); i++) { // on enleve l'act du projet
			if (activite.getId() == projet.getListe().get(i).getId()) {
				projet.getListe().remove(i);
				break;
			}
		}
		try {
			JavaSQLActivite.supprime(activite.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		activite.supprimerToutesRessources(); //on enleve toute ses ressources

		activiteSelectionner = null; // on le deselectionne
		//majEDT(); // met a jour l'emploi du temps
		update(); // met a jour l'interface
	}


	/*public void modifieListeActivite(boolean monte) {
		Activite activite = activiteSelectionner;
		Projet projet = chercherProjetParActivite(activite);
		ArrayList<Activite> listeActivite = projet.getListe();
		int index = 0;
		for (int i=0; i<listeActivite.size(); i++) {
			if (listeActivite.get(i).getId() == activite.getId()) {
				index = i;
				listeActivite.remove(i);
				break;
			}
		}
		if (monte) {
			if (index>0) {
				index--;
			}
		}
		else {
			if (index<listeActivite.size()) {
				index++;
			}
		}
		listeActivite.add(index, activite);
		projet.setListeActivite(listeActivite);
		for (int i=0; i<listeActivite.size(); i++) {
			Activite a = listeActivite.get(i);
			a.setOrdre(i);
			try {
				JavaSQLActivite.modifie(a.getId(), a.getTitre(), a.getDebut(), a.getChargeJHomme(), a.getOrdre(),
						a.getCouleur().getRGB(), projetSelectionner.getId(), a.getListeDomaine());
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		majEDT();
		update();
	}*/

	public void ajouterRessourceActivite(int type, Ressource res, Activite a) {
		switch (type) {
		case Ressource.PERSONNE:
			try {
				JavaSQLParticipe.insertionSalarie(res.getId(), a.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case Ressource.SALLE:
			try {
				JavaSQLParticipe.insertionSalle(res.getId(), a.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case Ressource.CALCULATEUR:
			try {
				JavaSQLParticipe.insertionCalcul(res.getId(), a.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

		//majEDT();
		update();
	}

	public void enleverRessourceActivite(int type, Ressource res, Activite a) {
		switch (type) {
		case Ressource.PERSONNE:
			try {
				JavaSQLParticipe.supprimeParticipeSalarie(res.getId(), a.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case Ressource.SALLE:
			try {
				JavaSQLParticipe.supprimeParticipeSalle(res.getId(), a.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case Ressource.CALCULATEUR:
			try {
				JavaSQLParticipe.supprimeParticipeCalcul(res.getId(), a.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

		//majEDT();
		update();
	}

	public Ticket getTicketTransfertQuiLibereParId(int idT) {
		String modif;
		Ticket t = null;
		try {
			modif = JavaSQLRecherche.recupereModifTicketParId(idT);

		String[] regex = modif.split(Ticket.SEPARATEUR);
		if (regex[0].equals("libere")){
		int idTicket = Integer.parseInt(regex[4]);
		if (idTicket != -1) {
			t = JavaSQLRecherche.recupereTicketParId(idTicket);

		}
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}


	public Ressource getRessourceTicket(int idT) {
		String modif;
		Ressource r = null;
		try {
			modif = JavaSQLRecherche.recupereModifTicketParId(idT);

		String[] regex = modif.split(Ticket.SEPARATEUR);
		if (regex[0].equals("libere")){
			int typeRessource = Integer.parseInt(regex[1]);
			int idRessource = Integer.parseInt(regex[2]);
			r = this.getRessourceParId(typeRessource, idRessource);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}




	public Activite getActiviteDepartLiberation(int idT) {
		String modif;
		Activite a = null;
		try {
			modif = JavaSQLRecherche.recupereModifTicketParId(idT);

		String[] regex = modif.split(Ticket.SEPARATEUR);
		int idActiviteDepart = Integer.parseInt(regex[3]);
		a = this.getActiviteParId(idActiviteDepart);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}




	public Activite getActiviteArriveTransfert(int idT) {
		String modif;
		Activite a = null;
		try {
			modif = JavaSQLRecherche.recupereModifTicketParId(idT);

		String[] regex = modif.split(Ticket.SEPARATEUR);
		int idActiviteDepart = Integer.parseInt(regex[3]);
		a = this.getActiviteParId(idActiviteDepart);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

	public ArrayList<Ticket> getListeTicketRecuDeUserDeEntreprise(int numSalarie) {
		ArrayList<Ticket> liste = new ArrayList<Ticket>();

		try {
			liste = JavaSQLRecherche.recupereListeTicketRecuDeUserDeEntreprise(numSalarie);
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste ;
	}



	//---------------------------------------------------------------------------------------------------------------------------------->>>>>>> Gestion ticket


	public void nouvTicketMessage(String sujet,String message,int numSalarieEnv, int numSalarieRec) {

		try {
			JavaSQLTicket.insertion(Ticket.MESSAGE, sujet, message, numSalarieEnv, numSalarieRec, null, null, null, null);
			update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void nouvTicketLiberation(String sujet,String message,int numSalarieEnv, int numSalarieRec,Ressource r,Activite activiteDepart) {

		try {
			JavaSQLTicket.insertion(Ticket.LIBERE, sujet, message, numSalarieEnv, numSalarieRec, r ,null, activiteDepart, null);
			update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void nouvTicketTransfert(String sujet,String message,int numSalarieEnv, int numSalarieRec,Ressource r,Activite activiteArrive) {

		try {
			JavaSQLTicket.insertion(Ticket.TRANSFERT, sujet, message, numSalarieEnv, numSalarieRec, r,activiteArrive,null,null);
			update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}






	public void afficheInfoTicket(Ticket t) {
		int exist = -1;
		for (int i=0; i<listeFenetreInfoTicket.size(); i++) {
			if (t.getId() == listeFenetreInfoTicket.get(i).getIdTicket()) {
				exist = i;
			}
		}
		if (exist > -1) {
			listeFenetreInfoTicket.get(exist).dispose();
			listeFenetreInfoTicket.remove(exist);
		}
		listeFenetreInfoTicket.add(new FenetreInfoTicket(this, t));

	}

	/**
	 * Change dans la bdd le statut du ticket
	 * On passe le statut VU uniquement si le ticket est NONVU
	 * On passe le statut ACCEPTER ou REFUSER uniquement si le ticket est VU
	 * @param statut sera le nouveau statut du ticket
	 * @param ticket
	 */
	public void setStatutTicket(int statut, Ticket ticket) {
		try {
			JavaSQLTicket.modifieStatut(ticket, statut);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		update();
	}




	public void accepteTicket(int idT) {
		String modif;
		try {
			modif = JavaSQLRecherche.recupereModifTicketParId(idT);

		String[] regex = modif.split(Ticket.SEPARATEUR);
		int typeRessource = Integer.parseInt(regex[1]);
		int idRessource = Integer.parseInt(regex[2]);
		int idActiviteDepart = Integer.parseInt(regex[3]);
		int idTicketTransfert = Integer.parseInt(regex[4]);
		Activite a = this.getActiviteParId(idActiviteDepart);
		Ressource r = this.getRessourceParId(typeRessource,idRessource);
		this.enleverRessourceActivite(typeRessource, r,a);
		this.setStatutTicket(Ticket.ACCEPTEE, JavaSQLRecherche.recupereTicketParId(idT));
		if (idTicketTransfert != -1 ) {
			this.accepteTicketTransfert(idTicketTransfert);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void accepteTicketTransfert(int idT) {

		String modif;
		try {
		Ticket ticketCour = JavaSQLRecherche.recupereTicketParId(idT);
			if (ticketCour.getStatut() == Ticket.ACCEPTEE) {
				modif = JavaSQLRecherche.recupereModifTicketParId(idT);
				String[] regex = modif.split(Ticket.SEPARATEUR);
				int typeRessource = Integer.parseInt(regex[1]);
				int idRessource = Integer.parseInt(regex[2]);
				int idActiviteDepart = Integer.parseInt(regex[3]);
				Activite a = this.getActiviteParId(idActiviteDepart);
				Ressource r = this.getRessourceParId(typeRessource,idRessource);

				this.ajouterRessourceActivite(typeRessource, r, a);
				this.setStatutTicket(Ticket.ACCEPTEE, ticketCour);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//---------------------------------------------------------------------------------------------------------------------------------->>>>>>> Gestion ressource

	public void nouvPersonne (String nom, String prenom, String role, String mdp, ArrayList<Competence> listeComp) {
		ArrayList<String> tag = CompeToTag(listeComp);
		ArrayList<Integer> niveau = CompeToNiv(listeComp);
		try {
			JavaSQLPersonne.insertion(nom, prenom, role, mdp, tag, niveau);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		update();

	}

	public boolean personneEstChefDuProjet(Personne personne, Projet projet) {
		boolean b = false;
		ArrayList<Projet> listeProjet = getListeProjetDePersonneParId(personne.getId());
		for (int i=0; i<listeProjet.size(); i++) {
			if (listeProjet.get(i).getId() == projet.getId()) {
				b = true;
				break;
			}
		}
		return b;
	}

	public void nouvSalle (String nom, int capacite) {
		try {
			JavaSQLSalle.insertion(nom, capacite);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		update();

		}


	public void nouvCalculateur (String nom) {
		try {
			JavaSQLCalculateur.insertion( nom, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		update();
	}


	public void modifPersonne(Personne p, String nom, String prenom, String role, String mdp, ArrayList<Competence> listeComp) {
		ArrayList<String> tag = CompeToTag(listeComp);
		ArrayList<Integer> niveau = CompeToNiv(listeComp);
		try {
			JavaSQLPersonne.modifie(p.getId(), nom, prenom, role, mdp, tag, niveau);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		update();
	}


	public void supprimeCompetence(int numSalarie,String tag ) {
		try {
			JavaSQLCompetence.supprime(numSalarie, tag);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}



	public void modifSalle(Salle s, String nom, int capacite) {
		s.setNom(nom);
		s.setCapacite(capacite);
		try {
			JavaSQLSalle.modifie(s.getId(), nom, capacite);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		update();
	}

	public void modifCalculateur(Calculateur c, String nom) {
		c.setNom(nom);
		try {
			JavaSQLCalculateur.modifie(c.getId(), nom, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		update();
	}



	public void suppRessource(int type, Ressource r) {
		switch (type) {
		case Ressource.PERSONNE:
			try {
				JavaSQLPersonne.supprime(r.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case Ressource.SALLE:
			try {
				JavaSQLSalle.supprime(r.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case Ressource.CALCULATEUR:
			try {
				JavaSQLCalculateur.supprime(r.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		update();
	}

	public void setAfficheListeRessource(int type) {
		if (type == afficheListeRessource) {
			afficheListeRessource = Ressource.RIEN;
		}
		else {
			afficheListeRessource = type;
		}
		update();
	}

	public int getAfficheListeRessource() {
		return afficheListeRessource;
	}

	//---------------------------------------------------------------------------------------------------------------------------------->>>>>>> Gestion domaine

	public void ajoutDomaine (String d) {
		try {
			JavaSQLDomaine.insertion(d);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public ArrayList<String> personneAvecCeDomaine(String domaine){ // recherche sql de toute les personnes de avec ce domaine
		ArrayList<String> l = new ArrayList<String>();
		return l;
	}

	public void supprimerDomaine (String d) {
		try {
			JavaSQLDomaine.supprime(d);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//---------------------------------------------------------------------------------------------------------------------------------->>>>>>> Gestion tache

	public void setAfficheTache(int tache) {
		if (tache == afficheTache) {
			afficheTache = PanelTache.RIEN;
		}
		else {
			afficheTache = tache;
		}
		update();
	}

	public int getAfficheTache() {
		return afficheTache;
	}

	//---------------------------------------------------------------------------------------------------------------------------------->>>>>>> Gestion Conge

	public void nouveauConge(LocalDate date, int numSalarie) {
		try {
			JavaSQLConge.insertion(date, numSalarie);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void supprimerConge(int idC) {
		try {
			JavaSQLConge.supprime(idC);;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//---------------------------------------------------------------------------------------------------------------------------------->>>>>>> Gestion Reunion

	public void nouvelleReunion(int debut, LocalDate date, String titre, int idA) {
		try {
			JavaSQLReunion.insertion(debut, date, titre, idA);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void supprimerReunion(int idR) {
		try {
			JavaSQLReunion.supprime(idR);;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	//================ Partie Graphique ==========//
	public void selectionnerProjet(Projet projet) {
		projetSelectionner = projet;
		activiteSelectionner = null;
		update();
	}

	public Projet getProjetSelectionner() {
		return projetSelectionner;
	}



	public void selectionnerActivite(Activite activite) {
		if (activiteSelectionner == null) {
			activiteSelectionner = activite;
		}
		else {
			if (activiteSelectionner.getId() == activite.getId()) {
				if (afficheEDTActivite) {
					afficheEDTActivite = false;
				}
				else {
					afficheEDTActivite = true;
				}
			}
			else {
				activiteSelectionner = activite;
			}
		}
		update();
	}

	public Activite getActiviteSelectionner() {
		return activiteSelectionner;
	}

	public boolean getAfficheEDTActivite() {
		return afficheEDTActivite;
	}



	private Color couleurAleatoire() {
		Color tab [] = {new Color(255,57,51), new Color(0,204,51),
				new Color(0,204,255), new Color(204,255,102),
				new Color(255,102,255), new Color(143,233,168),
				new Color(187,210,255), new Color(177,150,255),
				new Color(243,130,130), new Color(74,255,206)};
		int r = (int) (Math.random() * tab.length) ;
		return tab[r];
	}


	public ArrayList<String> getListeRessourceAfficher(){
		return ressourceAfficher;
	}

	public JFrame getFenetrePrincipale() {
		return fenetrePrincipale;
	}

	public void afficheInfoRessource(Ressource res) {
		int exist = -1;
		for (int i=0; i<listeFenetreInfoRessource.size(); i++) {
			if (res.getId() == listeFenetreInfoRessource.get(i).getIdRessource()) {
				exist = i;
			}
		}
		if (exist > -1) {
			listeFenetreInfoRessource.get(exist).dispose();
			listeFenetreInfoRessource.remove(exist);
		}
		listeFenetreInfoRessource.add(new FenetreInfoRessource(this, res));
	}



	private ArrayList<String> CompeToTag(ArrayList<Competence> lc){
		ArrayList<String> lt = new ArrayList<String>();
		for (int i=0; i<lc.size(); i++) {
			lt.add(lc.get(i).getNom());
		}
		return lt;
	}

	private ArrayList<Integer> CompeToNiv(ArrayList<Competence> lc){
		ArrayList<Integer> ln = new ArrayList<Integer>();
		for (int i=0; i<lc.size(); i++) {
			ln.add(lc.get(i).getNiveau());
		}
		return ln;
	}


	public void update() {
		this.setChanged();
		this.notifyObservers();
	}
}
