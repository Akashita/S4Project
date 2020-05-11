package Model;
import java.awt.Color;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import Connexion.FenetreConnexion;
import DebugFenetre.FenetreDebugBDD;
import Fenetre.FenetreInfoRessource;
import Fenetre.FenetrePrincipale;
import Panel.PanelInfoActivite;
import Panel.PanelInfoProjet;
import Panel.PanelTache;
import Ressource.Calculateur;
import Ressource.Competence;
import Ressource.Domaine;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.RessourceAutre;
import Ressource.Salle;
import SQL.JavaSQL;
import SQL.JavaSQLActivite;
import SQL.JavaSQLCalculateur;
import SQL.JavaSQLDomaine;
import SQL.JavaSQLParticipe;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLProjet;
import SQL.JavaSQLSalle;
import SQL.JavaSQLTicket;
import SQL.RecupInfoBDD;


//model il sert a creer des projets puis leur donne des ressources.

public class Entreprise extends Observable{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private ArrayList<Projet> lProjet;//liste qui contient tous les projets de l'entreprise
	private ArrayList<String> lType;//liste qui contient tous les types de ressourceAutre qui ont déjé été crée pour les réutiliser
	private ArrayList<Ressource> lRessource;//liste de toutes les differentes ressources de léentrepris
	private int idCour;//id des ressources
	private int idAct; //id des activit��s
	private int idProjet;
	private ArrayList<JPanel> lPanel = new ArrayList<JPanel>();

	public static final int HEURE_DEBUT_MATIN = 8;
	public static final int HEURE_FIN_MATIN = 12;
	public static final int HEURE_DEBUT_APREM = 13;
	public static final int HEURE_FIN_APREM = 17;
	
	public static final int NB_HEURE_JOUR = 8;
	
	private FenetrePrincipale fenetrePrincipale;
	private FenetreDebugBDD fenetreBDD;
	private ArrayList<FenetreInfoRessource> listeFenetreInfoRessource = new ArrayList<FenetreInfoRessource>();
	
	private Projet projetSelectionner;
	private Activite activiteSelectionner;
	private ArrayList<String> ressourceAfficher = new ArrayList<String>();

	private boolean afficheTicket = false;
	
	private Domaine domaine;
	
	public final String SEPARATEUR = "#";
	
	private Personne user; //Personne qui utilise le programme
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//création de l'entreprise unique il faudra lui ajouter un nom si on désire étendre nos activités
	public Entreprise() {
		this.lProjet =  new ArrayList<Projet>();
		this.lType =  new ArrayList<String>();
		this.lRessource =  new ArrayList<Ressource>();
		this.idCour = 0;
		this.domaine = new Domaine();
		recupInfoBdd();
		
		fenetrePrincipale = new FenetrePrincipale(this);
		this.update();
	}
	public Entreprise(String typeDebug) {
		this.lProjet =  new ArrayList<Projet>();
		this.lType =  new ArrayList<String>();
		this.lRessource =  new ArrayList<Ressource>();
		this.idCour = 0;
		this.domaine = new Domaine();
		recupInfoBdd();
		
		if (typeDebug == "debugBDD") {
			fenetreBDD = new FenetreDebugBDD(this);
		}
		this.update();
	}
	
	public Entreprise(Personne p) {
		user = p;
		this.lProjet =  new ArrayList<Projet>();
		this.lType =  new ArrayList<String>();
		this.lRessource =  new ArrayList<Ressource>();
		this.idCour = 0;
		this.domaine = new Domaine();
		recupInfoBdd();
		
		fenetrePrincipale = new FenetrePrincipale(this);
		this.update();
	}
	
	private void recupInfoBdd() {
		try {
//			testSqlDomaine.insertion();
//			JavaSQLDomaine.insertion("php");
//			JavaSQLPersonne.insertion("Geyer","Jules","Chef","putheu", testP, testN);
//			JavaSQLProjet.insertion("test1",1,date,1, 1);
//			testprojet.insertion();
//			testact.insertion();
			RecupInfoBDD.recupBDDProjet(this);
			RecupInfoBDD.recupBDDDomaine(this);
			RecupInfoBDD.recupBDDRessource(this);
//			test.affiche();
//			test.drop();
//			java.creation();
//			test.affiche();
			

		}catch(SQLException f){
			f.printStackTrace();
		}
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//classe de base qui permettent de voir la cha��ne et r��cup��rer les infos de la classe
	@Override
	public String toString() {
		String chaineActProjet = "Voici la liste des projets ainsi que leurs activites : ";
		for (int i = 0; i < this.lProjet.size(); i++) {
			chaineActProjet += this.lProjet.get(i).toString(); 
			
		}
		return chaineActProjet;
	}

	//------------------------------------------------------------------------>>>>>>>> Changement de compte
	
	public void changementUtilisateur() {
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
	
	//----------------------------------------------------------------------------------------------------
	
	public void majEDT() {
		ArrayList<Activite> lActivite;
		viderRessources();
		 for (int i = 0; i < lProjet.size(); i++) {
			 lActivite = lProjet.get(i).getListe();
			 for (int j = 0; j < lActivite.size(); j++) {
				 if(lActivite.get(j).hasRessource()) {
					creerLCreneauxPersonnes(lActivite.get(j));
					creerLCreneauxSalles(lActivite.get(j));
				 }
			}
		}
	}
	
	private void viderRessources() {
		for (int i = 0; i < lRessource.size(); i++) {
			lRessource.get(i).vider();
		}
	}

	
	private void creerLCreneauxPersonnes(Activite act) {
		int charge = act.getChargeHeure();
		int chargeAloue = 0;
		
		LocalDate jourCourant = verifierJour(act.getDebut());
		int heureCourante = HEURE_DEBUT_MATIN;
		ArrayList<Ressource> res = act.getListeRessourceType("Personne");

		while (chargeAloue < charge) {	
			for (int i = 0; i < res.size(); i++) {
				if(verifierOrdre(res.get(i), act, jourCourant, heureCourante)) {
					if(res.get(i).creneauDispo(jourCourant, heureCourante)) {
						res.get(i).ajouterCreneau(new CreneauHoraire(act, heureCourante, act.getCouleur()), jourCourant);
						chargeAloue++;
					}
				}
			}
	
			heureCourante = heureSuivante(heureCourante);
			if(heureCourante == HEURE_DEBUT_MATIN) {
				jourCourant = verifierJour(jourCourant.plus(1, ChronoUnit.DAYS));
			}
		}
	}
	
	/*Verifie qu'un activit�� d'ordre n+1 soit plac��e apr��s une activite d'ordre n*/
	private boolean verifierOrdre(Ressource res, Activite act, LocalDate jour, int heure) {
		LocalDateTime tmp = LocalDateTime.of(jour, LocalTime.of(heure, 0));
		int ordre = act.getOrdre();
		LocalDateTime premierLibre = res.getPremiereCreneauApresAct(ordre);
		
		return premierLibre == null || (premierLibre.isEqual(tmp) || premierLibre.isBefore(tmp));
	}
	
	private void creerLCreneauxSalles(Activite act) {
		int charge = act.getChargeHeure();
		int chargeAloue = 0;
		LocalDate jourCourant = verifierJour(act.getDebut());
		int heureCourante = HEURE_DEBUT_MATIN;

		while (chargeAloue < charge) {	
				if(act.creneauDispo("Salle", jourCourant, heureCourante)) {
					act.ajouterCreneau("Salle", new CreneauHoraire(act, heureCourante, act.getCouleur()), jourCourant);
					chargeAloue++;
			}
	
			heureCourante = heureSuivante(heureCourante);
			if(heureCourante == HEURE_DEBUT_MATIN) {
				jourCourant = verifierJour(jourCourant.plus(1, ChronoUnit.DAYS));
			}
		}
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

	private int heureSuivante(int heureCourante) {
		int heureSuivante = heureCourante + 1;
		if(heureSuivante == HEURE_FIN_MATIN) {
			heureSuivante = HEURE_DEBUT_APREM;
		} else if (heureSuivante == HEURE_FIN_APREM){
			heureSuivante = HEURE_DEBUT_MATIN;
		} 
		return heureSuivante;
	}
	
	
	public void incrementId (){ //fonction a utiliser sur chaque nouvelle ressource pour leur attribuer un iD
		this.idCour = this.idCour +1 ;
		//		this.idCour = this.lRessource.size() +1 ;

	}
	
	public int getId() {
		return this.idCour;
	}
	
	public Domaine getDomaine() {
		return this.domaine;
	}

	public Ressource getRessource(int id) {
		Ressource r = null;
		for(int i=0; i<lRessource.size(); i++) {
			if (lRessource.get(i).getId() == id) {
				r = lRessource.get(i);
			}
		}
		return r;
	}
	
	public ArrayList<Personne> getChefDeProjetConcerner(Ressource r){
		ArrayList<Personne> lp = new ArrayList<Personne>();
		for (int i=0; i<lProjet.size(); i++) {
			if (lProjet.get(i).ressourcePresente(r)) {
				lp.add(lProjet.get(i).getChefDeProjet());
			}
		}
		return lp;
	}
	
	public Ressource ressourceExiste(String login) {
		Ressource r = null;
	      String[] regex = login.split("#", 2); 
	      int id = Integer.parseInt(regex[1]);
	      for (int i=0; i<lRessource.size(); i++) {
	      if (lRessource.get(i).getId() == id) {
	        r = lRessource.get(i);
	       }
	    }			
		
		return r;
	}
	
	public boolean ressourceEstDansAct(Ressource r, Activite a) {
		return a.ressourcePresente(r);
	}

	
	public ArrayList<Activite> getActRes(Ressource r){ //retourne tout les activit��s d'une ressource
		ArrayList<Activite> lA = new ArrayList<Activite>();
		for (int i=0; i<lProjet.size(); i++) {
			ArrayList<Activite> lAP = lProjet.get(i).getListe();
			for (int j=0; j<lAP.size(); j++) {
				if (lAP.get(j).ressourcePresente(r)) {
					lA.add(lAP.get(j));
				}
			}
		}
		return lA;
	}

	public ArrayList<Projet> getProjetPRes(Ressource r){ //retourne tout les projets d'une ressource
		ArrayList<Projet> lP = new ArrayList<Projet>();
		for (int i=0; i<lProjet.size(); i++) {
			ArrayList<Activite> lAP = lProjet.get(i).getListe();
			for (int j=0; j<lAP.size(); j++) {
				if (lAP.get(j).ressourcePresente(r)) {
					lP.add(lProjet.get(i));
				}
			}
		}		
		return lP;
	}
	
	public ArrayList<Ressource> getListeRessourceType(String type){
		ArrayList<Ressource> nouvelleListe = new ArrayList<Ressource>();
		for (int i=0; i<lRessource.size(); i++) {
			Ressource ressource = lRessource.get(i);
			if(ressource.getType() == type) {
				nouvelleListe.add(ressource);
			}
		}
		return nouvelleListe;
	}
			
	public ArrayList<Projet> getProjetDeLaRessource(Ressource r){ //retourne la liste des projet d'une ressource
		ArrayList<Projet> lp = new ArrayList<Projet>();
		for (int i=0; i<lProjet.size(); i++) {
			Projet p = lProjet.get(i);
			if (p.ressourcePresente(r)) {
				lp.add(p);
			}
		}
		return lp;
	}
	
	public boolean ajouterRessource(Ressource ressource) {
		if(!lRessource.contains(ressource)) {
			lRessource.add(ressource);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean supprimerRessource(int idRessource) {
		return lRessource.remove(new Ressource(idRessource));
	}

	public int[] chercheProjet(String nomProjet) {
		
		Boolean pasTrouve = true;//sert a sortir plus vite de la boucle
		int[] res = {0,0};//a droite la place du projet cherché et a gauche si il est trouvé 0 non/1 oui
		
		if (this.lProjet.size()== 0) {//si l'arrayList est vide il n'y a pas déjé ce projet.
			
			return res;
		}
		else {
			
			do{
				if (this.lProjet.get(res[1]).getNom() == nomProjet) {
					res[0] = 1;
					pasTrouve = false;
				}
				else {
					res[1] = res[1] + 1; //on incrémente res pour accéder é chercher plus loin.
				}
				
			}
			while((pasTrouve) && (res[1] < this.lProjet.size()));
			return res ;
		}
		
	}
	
	//méthode pour rajouter un type de RessourceAutre
	public void nouvTypeRessource(String nouvType) {
		Boolean pasTrouve = true;//sert a sortir plus vite de la boucle
		int i = 0;
		if (this.lType.size()== 0) {//si l'arrayList est vide il n'y a pas déjé ce projet.
			this.lType.add(nouvType);

		}
		else {
			
			do{
				if (this.lType.get(i) == nouvType) {//teste si le nom est déjé présent dans les types de ressources
					pasTrouve = false;//sort de la boucle sans rien faire
				}
				else {
					i++; //on incrémente i pour accéder é chercher plus loin.
				}
				
			}
			while((pasTrouve) && (i < this.lType.size()));
			this.lType.add(nouvType);
		}

	}
							
	//fonctions de créations d'éléments de l'entreprise, les ressources ainsi que les projets
	//les méthodes sont doublés -> direct dans un projet ou dans l'entreprise

	
	//------------------------------------------------------------------------------------------------------------------------------->>>>>>>>>> Gestion projet

	public void nouvProjet (Projet proj) {
		this.lProjet.add(proj);
	}

	
	public void creerProjet(Personne chefDeProjet, String nom, int priorite, LocalDate deadline) {//crée un projet si son nom n'est pas déjé utilisé
		idProjet ++;
		Projet newProjet = new Projet(chefDeProjet, nom, priorite, deadline, idProjet, couleurAleatoire());// --------------------------------------------ATTENTION null pour le moment
		chefDeProjet.ajouterProjet(newProjet);
		lProjet.add(newProjet);
		Collections.sort(lProjet);
		lPanel.add(new JPanel());
		try {
			JavaSQLProjet.insertion(nom, priorite, deadline, 0, chefDeProjet.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		selectionnerProjet(newProjet);
		update();
	}
	
	public void ajouterProjet(Projet proj) { //Les projets sont ajout�s � la liste en les triant par ordre de priorite
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
	}
		
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
		
		for (int i=0; i<projet.getListe().size(); i++) { // on supprime toutes ses activit��s
			supprimerActiviter(projet.getListe().get(i));
		}
				
		projet.getChefDeProjet().enleverProjet(projet);  // on enleve au chef de projet le projet supprimer

		
		for (int i=0; i<getListeProjet().size(); i++) { // on enleve le projet de l'entreprise
			if (projet.getId() == getListeProjet().get(i).getId()) {
				getListeProjet().remove(i);
				break;
			}
		}
		try {
			JavaSQLProjet.supprime(projet.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		projetSelectionner = null;// enleve la selection projet
		majEDT(); // remet �� jour les emplois du temps
		update(); // remet �� jour l'interface
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------------->>>>>>>>>> Gestion activite
	
	public void creerActivite(Projet projet, String titre, float charge, LocalDate debut, ArrayList<String> listeDomaine) {
		this.idAct++;
		int ordre = projet.getListe().size();
		Color couleur = couleurAleatoire();
		Activite act = new Activite(idAct, titre, charge, debut, couleur, /*projetSelectionner,*/ ordre, listeDomaine); // ------------------------------ATTENTION projet plus stock�� dans activit��, ref Dams
		projet.ajouter(act);		
		selectionnerActivite(act);
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
		majEDT(); // met �� jour l'emploi du temps
		update(); // met �� jour l'interface
	}
	
	
	public void modifieListeActivite(boolean monte) {
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
	}
	//---------------------------------------------------------------------------------------------------------------------------------->>>>>>> Gestion ticket

	
	public void nouvTicket(int action,String sujet, String message,int numSalarieEnv, int numSalarieRec,Ressource r,LocalDate dateDebut,LocalDate dateFin) {
		try {
			JavaSQLTicket.insertion(action, sujet, message, numSalarieEnv, numSalarieRec, r, dateDebut, dateFin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------->>>>>>> Gestion ressource
	public void nouvPersonne (Personne pers) {
		this.ajouterRessource(pers);
		this.incrementId();
	}

	public void nouvPersonne (String nom, String prenom, String role, String mdp, ArrayList<Competence> listeComp) {
		this.incrementId();
		Personne nouvPersonne = new Personne(nom,prenom, role, this.idCour, mdp, listeComp);
		this.ajouterRessource(nouvPersonne);
		ArrayList<String> tag = CompeToTag(listeComp);
		ArrayList<Integer> niveau = CompeToNiv(listeComp);
		try {
			JavaSQLPersonne.insertion(nom, prenom, role, mdp, tag, niveau);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		update();

	}
		
	public void nouvSalle(Salle salle) {
		this.incrementId();
		this.ajouterRessource(salle);
	}
	
	public void nouvSalle (String nom, int capacite) {
		this.incrementId();
		Salle nouvSalle = new Salle(this.idCour,nom, capacite);
		this.ajouterRessource(nouvSalle);
		try {
			JavaSQLSalle.insertion(this.idCour, nom, capacite);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		update();

		}
	
	public void nouvCalculateur (Calculateur calc) {
		this.incrementId();
		this.ajouterRessource(calc);
	}
	
	public void nouvCalculateur (String nom) {
		this.incrementId();
		Calculateur nouvCalculateur = new Calculateur(nom, this.idCour);
		this.ajouterRessource(nouvCalculateur);
		try {
			JavaSQLCalculateur.insertion(this.idCour, nom, 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	
	public void modifPersonne(Personne p, String nom, String prenom, String role, String mdp, ArrayList<Competence> listeComp) {
		p.setNom(nom);
		p.setPrenom(prenom);
		p.setRole(role);
		p.setMdp(mdp);
		p.setListeCompetence(listeComp);
		ArrayList<String> tag = CompeToTag(listeComp);
		ArrayList<Integer> niveau = CompeToNiv(listeComp);
		try {
			JavaSQLPersonne.modifie(p.getId(), nom, prenom, role, mdp, tag, niveau);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		update();
	}
	
	public void modifSalle(Salle s, String nom, int capacite) {
		s.setNom(nom);
		s.setCapacite(capacite);
		try {
			JavaSQLSalle.modifie(s.getId(), s.getId(), nom, capacite);
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

	public void ajouterRessourceActivite(Ressource res, Activite a) {
		Activite act = getActiviteSelectionner();
		act.ajouterRessource(res);
		if (res.getType() == Ressource.PERSONNE) {
			try {
				JavaSQLParticipe.insertionSalarie(res.getId(), a.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (res.getType() == Ressource.SALLE) {
			try {
				JavaSQLParticipe.insertionSalle(res.getId(), a.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (res.getType() == Ressource.CALCULATEUR) {
			try {
				JavaSQLParticipe.insertionCalcul(res.getId(), a.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		majEDT();
		//JavaSQLParticipe.insertion(numSalarie, code, numero, idA);
		update();
	}
	
	public void enleverRessourceActivite(Ressource res) {
		Activite act = getActiviteSelectionner();
		act.enleverRessource(res.getId());
		majEDT();
		update();
	}
	

	public boolean ressourceEstLibre(Ressource r) { //v�rifier qu'une ressource est attacher à aucune act ou projet
		return true;
	}
	
	public void suppRessource(Ressource r) {
		for (int i=0; i<lRessource.size(); i++) {
			if (lRessource.get(i).getId() == r.getId()) {
				lRessource.remove(i);
				update();
				break;
			}
		}
		
		if (r.getType().equals(Ressource.PERSONNE)) {
			try {
				JavaSQLPersonne.supprime(r.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (r.getType().equals(Ressource.SALLE)) {
			try {
				JavaSQLSalle.supprime(r.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (r.getType().equals(Ressource.CALCULATEUR)) {
			try {
				JavaSQLCalculateur.supprime(r.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------->>>>>>> Gestion domaine

	/*public void nouvDomaine (Domaine domaine) {
		this.domaine = domaine;
	}*/
	
	public void ajoutDomaine (String d) {
		this.domaine.ajoutDomaine(d);
		try {
			JavaSQLDomaine.insertion(d);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*public void setDomaine(ArrayList<String> listeDomaine) {
		this.domaine.setDomaine(listeDomaine);
	}*/
	
	public boolean aucuneRessourceACeDomaine(String domaine) {
		boolean b = true;
		ArrayList<Ressource> lP = getListeRessourceType(Ressource.PERSONNE);
		for (int i=0; i<lP.size(); i++) {
			Personne p  = (Personne) lP.get(i);
			if (p.aDomaine(domaine)) {
				b = false;
			}
		}
		return b;
	}
	
	public ArrayList<String> personneAvecCeDomaine(String domaine){ // recherche sql de toute les personnes de avec ce domaine
		ArrayList<String> l = new ArrayList<String>();
		return l;
	}
	
	public void supprimerDomaine (String d) {
		this.domaine.enleverDomaine(d);
		try {
			JavaSQLDomaine.supprime(d);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//---------------------------------------------------------------------------------------------------------------------------------->>>>>>> Gestion tache

	public void selectionnerTache(int tache) {
		switch (tache) {
		case PanelTache.TICKET:
			if (afficheTicket) {
				afficheTicket = false;
			}
			else {
				afficheTicket = true;
			}
			break;

		default:
			break;
		}
		update();
	}
	
	public boolean getAfficheTicket() {
		return afficheTicket;
	}
	
	//================ Partie Graphique ==========//
	
	private Color couleurAleatoire() {
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color couleur = new Color(r, g, b);
		return couleur;
	}
	
	public Projet getDernierProjet() { //retourne le dernier projet creer, pour PanelProjet
		return lProjet.get(lProjet.size()-1);
	}
	
	public ArrayList<Projet> getListeProjet(){
		return lProjet;
	}

	public void selectionnerProjet(Projet projet) {
		projetSelectionner = projet;
		activiteSelectionner = null;
		update();
	}
	
	public Projet getProjetSelectionner() {
		return projetSelectionner;
	}

	public void selectionnerActivite(Activite activite) {
		if (activiteSelectionner != null) {
			if (activite.getId() == activiteSelectionner.getId()) {
				activite.afficheEDT();
			}
			else {
				activiteSelectionner = activite;
			}			
		}
		else {
			activiteSelectionner = activite;
		}			
		update();
	}
	
	public Activite getActiviteSelectionner() {
		return activiteSelectionner;
	}

	public void selectionnerListeRessource(String type) {
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
	
	public void afficheEDTActivite(Activite activite) {
		activite.afficheEDT();
		update();
	}
	
	private Projet chercherProjetParActivite(Activite act) {
		for (int i = 0; i < lProjet.size(); i++) {
			if(lProjet.get(i).contientActivite(act)) {
				return lProjet.get(i);
			}
		}
		return null;
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

