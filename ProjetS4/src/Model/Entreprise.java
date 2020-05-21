package Model;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Random;
import java.util.Set;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Connexion.FenetreConnexion;
import DebugFenetre.FenetreDebugBDD;
import Fenetre.FenetreInfoRessource;
import Fenetre.FenetreInfoTicket;
import Fenetre.FenetrePrincipale;
import GestionTicket.Ticket;
import Panel.PanelInfoActivite;
import Panel.PanelInfoProjet;
import Panel.PanelTache;
import Ressource.Calculateur;
import Ressource.Competence;
import Ressource.Domaine;
import Ressource.Materiel;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;
import SQL.JavaSQL;
import SQL.JavaSQLActivite;
import SQL.JavaSQLCalculateur;
import SQL.JavaSQLCompetence;
import SQL.JavaSQLDomaine;
import SQL.JavaSQLMateriel;
import SQL.JavaSQLParticipe;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLProjet;
import SQL.JavaSQLRecherche;
import SQL.JavaSQLSalle;
import SQL.JavaSQLTicket;
import SQL.RecupInfoBDD;


//model il sert a creer des projets puis leur donne des ressources.

public class Entreprise extends Observable{

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	/*private ArrayList<Projet> lProjet;//liste qui contient tous les projets de l'entreprise
	private ArrayList<String> lType;//liste qui contient tous les types de ressourceAutre qui ont d��j�� ��t�� cr��e pour les r��utiliser
	private ArrayList<Ressource> lRessource;//liste de toutes les differentes ressources de l��entrepris
	private int idCour;//id des ressources
	private int idAct; //id des activit������s
	private int idProjet;
	private Domaine domaine;*/

	public static final int HEURE_DEBUT_MATIN = 8;
	public static final int HEURE_FIN_MATIN = 12;
	public static final int HEURE_DEBUT_APREM = 13;
	public static final int HEURE_FIN_APREM = 17;

	public static final int NB_HEURE_JOUR = 8;

	private FenetreDebugBDD fenetreBDD;
	
	//=========== EDT
	private Hashtable<Pair<Integer, Integer>, EDT> listeEDT;
	
	
	
	
	//=========== Attribut graphique
	private ArrayList<JPanel> lPanel = new ArrayList<JPanel>();
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
	//cr��ation de l'entreprise unique il faudra lui ajouter un nom si on d��sire ��tendre nos activit��s
	public Entreprise() {
		/*this.lProjet =  new ArrayList<Projet>();
		this.lType =  new ArrayList<String>();
		this.lRessource =  new ArrayList<Ressource>();
		this.idCour = 0;
		this.domaine = new Domaine();*/
		//recupInfoBdd();

		fenetrePrincipale = new FenetrePrincipale(this);
		listeEDT = new Hashtable<Pair<Integer, Integer>, EDT>();
		this.update();
	}
	public Entreprise(String typeDebug) {
		/*this.lProjet =  new ArrayList<Projet>();
		this.lType =  new ArrayList<String>();
		this.lRessource =  new ArrayList<Ressource>();
		this.idCour = 0;
		this.domaine = new Domaine();*/
		//recupInfoBdd();

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

//	private void recupInfoBdd() {
//		try {
//			testSqlDomaine.insertion();
//			JavaSQLDomaine.insertion("php");
//			JavaSQLPersonne.insertion("Geyer","Jules","Chef","putheu", testP, testN);
//			JavaSQLProjet.insertion("test1",1,date,1, 1);
//			testprojet.insertion();
//			testact.insertion();
//			RecupInfoBDD.recupBDDProjet(this);
//			RecupInfoBDD.recupBDDDomaine(this);
//			RecupInfoBDD.recupBDDRessource(this);
//			test.affiche();
//			test.drop();
//			java.creation();
//			test.affiche();

//
//		}catch(SQLException f){
//			f.printStackTrace();
//		}
//	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//classe de base qui permettent de voir la cha������ne et r������cup������rer les infos de la classe
	/*@Override
	public String toString() {
		String chaineActProjet = "Voici la liste des projets ainsi que leurs activites : ";
		for (int i = 0; i < this.lProjet.size(); i++) {
			chaineActProjet += this.lProjet.get(i).toString();

		}
		return chaineActProjet;
	}*/

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
	 * Renvoie une prédiction de l'EDT de ressources d'une activitée en fonction de l'ajout ou d'une supression d'une ressource dans une activitée
	 * @param L'opération à effectuer (ajout ou suppresion)
	 * @param La ressource concernée par la modification
	 * @param L'activitée concernée par la modification
	 * @return la liste des EDT prévisionels
	 */
	public Hashtable<Pair<Integer, Integer>, EDT> getPrevisionEDT(int operation, Ressource res, Activite act) {
		return null;
	}
	
	public Hashtable<Pair<Integer, Integer>, EDT> generationEDT(){
		ArrayList<Projet> lProjet = getListeProjetDeEntreprise();
		ArrayList<Activite> lActivite;
		
		Hashtable<Pair<Integer, Integer>, EDT> listeEDTPersonnes = null;
		Hashtable<Pair<Integer, Integer>, EDT> listeEDTSalles = null;
		Hashtable<Pair<Integer, Integer>, EDT> listeEDTCalculateurs = null;
		
		Hashtable<Pair<Integer, Integer>, EDT> listeEDTComplete = null;
		
		 for (int i = 0; i < lProjet.size(); i++) {
			 lActivite = lProjet.get(i).getListe();
			 for (int j = 0; j < lActivite.size(); j++) {
				 if(lActivite.get(j).hasRessource()) {
					 listeEDTPersonnes.putAll(creerLCreneauxPersonnes(lActivite.get(j)));
					 //listeEDTSalles.putAll(creerLCreneauxSalles(lActivite.get(j)));
					 //listeEDTCalculateurs.putAll(creerLCreneauxCalculateurs(lActivite.get(j)));
					 
					 listeEDTComplete.putAll(listeEDTPersonnes);
					 listeEDTComplete.putAll(listeEDTSalles);
					 listeEDTComplete.putAll(listeEDTCalculateurs);
				 }
			}
		}
		 
		 return listeEDTComplete;
	}
	
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
	 * Créée les créneaux horaires de l'EDT de toutes les personnes participant à l'activitée courante
	 * @param L'activité courante
	 * @return 
	 */
	private Hashtable<Pair<Integer, Integer>, EDT> creerLCreneauxPersonnes(Activite act) {
		int charge = act.getChargeHeure();
		int chargeAloue = 0;

		LocalDate jourCourant = verifierJour(act.getDebut());
		int heureCourante = HEURE_DEBUT_MATIN;
		
		ArrayList<Personne> personnes = castListeRessourceEnPersonnes(this.getListeRessourcedeActiviteParId(Ressource.PERSONNE, act.getId()));
		Hashtable<Pair<Integer, Integer>, EDT> listeEDTPersonnes = null;
		
		while (chargeAloue < charge) {
			for (int i = 0; i < personnes.size(); i++) {
				
				Personne persCourante = personnes.get(i);
				EDT edtCourant = getEDTRessource(persCourante.getId(), persCourante.getType(), listeEDTPersonnes);
				
				if(verifierOrdre(edtCourant, act, jourCourant, heureCourante)) {
					if(!persCourante.enConge(jourCourant)) {
						if(edtCourant.creneauDispo(jourCourant, heureCourante)) {
							edtCourant.ajouterCreneau(new CreneauHoraire(act, heureCourante, act.getCouleur()), jourCourant);
							chargeAloue++;
						}
					}
				}
			}

			heureCourante = heureSuivante(heureCourante);
			if(heureCourante == HEURE_DEBUT_MATIN) {
				jourCourant = verifierJour(jourCourant.plus(1, ChronoUnit.DAYS));
			}
		}
		
		return listeEDTPersonnes;
	}
	
	
	/**
	 * Initialise un EDT (vide) pour une ressource
	 * @param l'identifiant de la ressource concernée
	 */
	public void ajouterEDTRessource(Pair<Integer, Integer> ident) {
		listeEDT.put(ident, new EDT(ident));
	}
	
	
	/**
	 * Renvoie l'EDT de la ressource passée en paramètre
	 * @param La ressource concernée (de type de Ressource)
	 * @return l'EDT de la ressource
	 */
	public EDT getEDTRessource(Ressource res) {
		int id_ressource = res.getId();
		int type_ressource = res.getType();
		Pair<Integer, Integer> ident = new Pair<Integer, Integer>(id_ressource, type_ressource);
		if(!listeEDT.containsKey(ident)) {
			ajouterEDTRessource(ident);
		}
		return listeEDT.get(ident);
	}
	
	
	/**
	 * Renvoie l'EDT de la ressource dont les identifiants ont été passés en paramètre
	 * @param Identifiants de la ressource
	 * @return l'EDT de la ressource
	 */
	public EDT getEDTRessource(int type, int id){
		Pair<Integer, Integer> ident = new Pair<Integer, Integer>(id, type);
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
	public EDT getEDTRessource(int type, int id, Hashtable<Pair<Integer, Integer>, EDT> listeEDT){
		Pair<Integer, Integer> ident = new Pair<Integer, Integer>(id, type);
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
	 * Vérifie qu'une activité d'ordre n+1 soit placée après une activitée d'ordre n
	 * @param L'EDT, l'activitée, le jour et l'heure concernés
	 * @return true si c'est le cas
	 */
	private boolean verifierOrdre(EDT edtCourant, Activite act, LocalDate jour, int heure) {
		LocalDateTime tmp = LocalDateTime.of(jour, LocalTime.of(heure, 0));
		int ordre = act.getOrdre();
		LocalDateTime premierLibre = edtCourant.getPremiereCreneauApresAct(ordre);

		return premierLibre == null || (premierLibre.isEqual(tmp) || premierLibre.isBefore(tmp));
	}
	
	
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
	
	
	private ArrayList<String> getListeDomaineParIdActivite(int ida) {
		ArrayList<String> liste = new ArrayList<String>();
		try {
			liste = JavaSQLRecherche.recupereListeDomaineParIdActivite(idA);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
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
     * @return la personne asssoci� sinon null
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

	public void creerProjet(Personne chefDeProjet, String nom, int priorite, LocalDate deadline) {//cr��e un projet si son nom n'est pas d��j�� utilis��
		try {
			JavaSQLProjet.insertion(nom, priorite, deadline, 0, chefDeProjet.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	//	selectionnerProjet(newProjet);
		update();
	}

	/*public void ajouterProjet(Projet proj) { //Les projets sont ajout���s ��� la liste en les triant par ordre de priorite
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
		majEDT();
		update();
	}


	public void supprimerProjet(Projet projet) {

		for (int i=0; i<projet.getListe().size(); i++) { // on supprime toutes ses activit������s
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
		majEDT(); // remet ������ jour les emplois du temps
		update(); // remet ������ jour l'interface
	}


	//------------------------------------------------------------------------------------------------------------------------------->>>>>>>>>> Gestion activite

	public void creerActivite(Projet projet, String titre, float charge, LocalDate debut, ArrayList<String> listeDomaine) {
		int ordre = projet.getListe().size();
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
		majEDT();
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
		majEDT(); // met ������ jour l'emploi du temps
		update(); // met ������ jour l'interface
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

		majEDT();
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

		majEDT();
		update();
	}


	//---------------------------------------------------------------------------------------------------------------------------------->>>>>>> Gestion ticket

	public void nouvTicket(int action,String sujet,String message,int numSalarieEnv, int numSalarieRec,Ressource r) {

		try {
			JavaSQLTicket.insertion(action, sujet, message, numSalarieEnv, numSalarieRec, r);
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
		Random rand = new Random();
		float r = (float) (rand.nextFloat() / 2f + 0.5);
		float g = (float) (rand.nextFloat() / 2f + 0.5);
		float b = (float) (rand.nextFloat() / 2f + 0.5);
		Color couleur = new Color(r, g, b);
		return couleur;
	}




	/*public void selectionnerListeRessource(String type) {
		boolean estPresent = false;
		for (int i=0; i<ressourceAfficher.size(); i++) {
			if (ressourceAfficher.get(i) == type) {
				estPresent = true;
				ressourceAfficher.remove(i);
				break;
			}
		}
		if (!estPresent) {
			ressourceAfficher.add(type);
		}
		adapteListeRessourceAfficher();
		update();
	}

	private void adapteListeRessourceAfficher() {
		int taille = ressourceAfficher.size();
		for (int i=0; i<taille; i++) {
			if (ressourceAfficher.get(i) == Ressource.PERSONNE) {
				ressourceAfficher.remove(i);
				ressourceAfficher.add(0, Ressource.PERSONNE);
			}
			if (ressourceAfficher.get(i) == Ressource.SALLE) {
				ressourceAfficher.remove(i);
				if (taille==1) {
					ressourceAfficher.add(0, Ressource.SALLE);
				}
				else {
					ressourceAfficher.add(1, Ressource.SALLE);
				}
			}
			if (ressourceAfficher.get(i) == Ressource.CALCULATEUR) {
				ressourceAfficher.remove(i);
				if (taille==1) {
					ressourceAfficher.add(0, Ressource.CALCULATEUR);
				}
				else {
					if (taille==2) {
						ressourceAfficher.add(1, Ressource.CALCULATEUR);
					}
					else {
						ressourceAfficher.add(2, Ressource.CALCULATEUR);
					}
				}
			}
		}
	}*/


	public ArrayList<String> getListeRessourceAfficher(){
		return ressourceAfficher;
	}

	public JFrame getFenetrePrincipale() {
		return fenetrePrincipale;
	}

	public void afficheInfoRessource(Ressource res, int typeDeLaRessource) {
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
		listeFenetreInfoRessource.add(new FenetreInfoRessource(this, res, typeDeLaRessource));
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
