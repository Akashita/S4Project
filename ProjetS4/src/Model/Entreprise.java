package Model;
import java.awt.Color;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import Fenetre.FenetreInfoRessource;
import Fenetre.FenetrePrincipale;
import Panel.PanelInfoActivite;
import Panel.PanelInfoProjet;
import Ressource.Calculateur;
import Ressource.Competence;
import Ressource.Domaine;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.RessourceAutre;
import Ressource.Salle;


//model il sert a cr�er des projets puis leur donne des ressources.

public class Entreprise extends Observable{
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private ArrayList<Projet> lProjet;//liste qui contient tous les projets de l'entreprise
	private ArrayList<String> lType;//liste qui contient tous les types de ressourceAutre qui ont d�j� �t� cr�e pour les r�utiliser
	private ArrayList<Ressource> lRessource;//liste de toutes les differentes ressources de l’entrepris
	private int idCour;//id des ressources
	private int idAct; //id des activités
	private int idProjet;
	private ArrayList<JPanel> lPanel = new ArrayList<JPanel>();

	public static final int HEURE_DEBUT_MATIN = 8;
	public static final int HEURE_FIN_MATIN = 12;
	public static final int HEURE_DEBUT_APREM = 13;
	public static final int HEURE_FIN_APREM = 17;
	
	public static final int NB_HEURE_JOUR = 8;
	
	private FenetrePrincipale fenetrePrincipale;
	private ArrayList<FenetreInfoRessource> listeFenetreInfoRessource = new ArrayList<FenetreInfoRessource>();
	
	private Projet projetSelectionner;
	private Activite activiteSelectionner;
	private ArrayList<String> ressourceAfficher = new ArrayList<String>();

	private Domaine domaine;
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//cr�ation de l'entreprise unique il faudra lui ajouter un nom si on d�sire �tendre nos activit�s
	public Entreprise() {
		this.lProjet =  new ArrayList<Projet>();
		this.lType =  new ArrayList<String>();
		this.lRessource =  new ArrayList<Ressource>();
		this.idCour = 0;
		this.domaine = new Domaine();
		fenetrePrincipale = new FenetrePrincipale(this);
		this.update();
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//classe de base qui permettent de voir la chaîne et récupérer les infos de la classe
	@Override
	public String toString() {
		String chaineActProjet = "Voici la liste des projets ainsi que leurs activites : ";
		for (int i = 0; i < this.lProjet.size(); i++) {
			chaineActProjet += this.lProjet.get(i).toString(); 
			
		}
		return chaineActProjet;
	}

	public void majEDT() {
		ArrayList<Activite> lActivite;
		viderProjets();
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
	
	private void viderProjets() {
		for (int i = 0; i < lProjet.size(); i++) {
			lProjet.get(i).vider();
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
				if(res.get(i).creneauDispo(jourCourant, heureCourante)) {
					res.get(i).ajouterCreneau(new CreneauHoraire(act, heureCourante, act.getProjet().getCouleur(), act.getCouleur()), jourCourant);
					chargeAloue++;
				}
			}
	
			heureCourante = heureSuivante(heureCourante);
			if(heureCourante == HEURE_DEBUT_MATIN) {
				jourCourant = verifierJour(jourCourant.plus(1, ChronoUnit.DAYS));
			}
		}
	}
	
	private void creerLCreneauxSalles(Activite act) {
		int charge = act.getChargeHeure();
		int chargeAloue = 0;
		
		LocalDate jourCourant = verifierJour(act.getDebut());
		int heureCourante = HEURE_DEBUT_MATIN;

		while (chargeAloue < charge) {	
				if(act.creneauDispo("Salle", jourCourant, heureCourante)) {
					act.ajouterCreneau("Salle", new CreneauHoraire(act, heureCourante, act.getProjet().getCouleur(), act.getCouleur()), jourCourant);
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
	}
	
	public int getId() {
		return this.idCour;
	}
	
	public Domaine getDomaine() {
		return this.domaine;
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
		int[] res = {0,0};//a droite la place du projet cherch� et a gauche si il est trouv� 0 non/1 oui
		
		if (this.lProjet.size()== 0) {//si l'arrayList est vide il n'y a pas d�j� ce projet.
			
			return res;
		}
		else {
			
			do{
				if (this.lProjet.get(res[1]).getNom() == nomProjet) {
					res[0] = 1;
					pasTrouve = false;
				}
				else {
					res[1] = res[1] + 1; //on incr�mente res pour acc�der � chercher plus loin.
				}
				
			}
			while((pasTrouve) && (res[1] < this.lProjet.size()));
			return res ;
		}
		
	}
	
	//m�thode pour rajouter un type de RessourceAutre
	public void nouvTypeRessource(String nouvType) {
		Boolean pasTrouve = true;//sert a sortir plus vite de la boucle
		int i = 0;
		if (this.lType.size()== 0) {//si l'arrayList est vide il n'y a pas d�j� ce projet.
			this.lType.add(nouvType);

		}
		else {
			
			do{
				if (this.lType.get(i) == nouvType) {//teste si le nom est d�j� pr�sent dans les types de ressources
					pasTrouve = false;//sort de la boucle sans rien faire
				}
				else {
					i++; //on incr�mente i pour acc�der � chercher plus loin.
				}
				
			}
			while((pasTrouve) && (i < this.lType.size()));
			this.lType.add(nouvType);
		}

	}
							
	//fonctions de cr�ations d'�l�ments de l'entreprise, les ressources ainsi que les projets
	//les m�thodes sont doubl�s -> direct dans un projet ou dans l'entreprise
	
	public void creerProjet(Personne chefDeProjet, String nom, float priorite, LocalDate deadline) {//cr�e un projet si son nom n'est pas d�j� utilis�
		idProjet ++;
		Projet newProjet = new Projet(chefDeProjet, nom, priorite, deadline, idProjet, couleurAleatoire());
		chefDeProjet.ajouterProjet(newProjet);
		lProjet.add(newProjet);
		Collections.sort(lProjet);
		lPanel.add(new JPanel());
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
		
	public void modifierProjet(Projet projet, String nom, float priorite, Personne chefDeProjet, LocalDate deadline) {	
		projet.setNom(nom);
		projet.setPriorite(priorite);
		projet.setChefDeProjet(chefDeProjet);
		projet.setDeadline(deadline);
		majEDT();
		update();
	}

	
	public void creerActivite(Projet projet, String titre, int charge, LocalDate debut) {
		this.idAct++;
		int ordre = projet.getListe().size();
		Activite act = new Activite(idAct, titre, charge, debut, couleurAleatoire(), projetSelectionner, ordre);
		projet.ajouter(act);		
		selectionnerActivite(act);
		update();
	}

	public void modifierActivite(Activite activite, String nom, int charge, LocalDate date) {	
		activite.setTitre(nom);
		activite.setCharge(charge);
		activite.setDebut(date);
		majEDT();
		update();
	}

	public void nouvPersonne (String nom, String prenom, String role, ArrayList<Competence> listeComp) {
		Personne nouvPersonne = new Personne(nom,prenom, role, this.idCour, listeComp);
		this.incrementId();
		this.ajouterRessource(nouvPersonne);
		update();

	}
	
	public void nouvSalle (String nom, int capacite) {
		Salle nouvSalle = new Salle(this.idCour,nom, capacite);
		this.incrementId();
		this.ajouterRessource(nouvSalle);
		update();

		}
	
	
	public void nouvRessourceAutre (String nom, String type) {
		RessourceAutre nouvRessourceAutre = new RessourceAutre(nom, type, this.idCour);
		this.incrementId();
		this.ajouterRessource(nouvRessourceAutre);
		this.nouvTypeRessource(type);//ajout du type a la liste de type personnalisable

		update();

	}
	
	public void nouvCalculateur (String nom) {
		Calculateur nouvCalculateur = new Calculateur(nom, this.idCour);
		this.incrementId();
		this.ajouterRessource(nouvCalculateur);
		update();
	}
	
	public void nouvDomaine (String domaine) {
		this.domaine.ajoutDomaine(domaine);
	}

	public void enleverDomaine (String domaine) {
		this.domaine.enleverDomaine(domaine);
	}
	

	public void ajouterRessourceActivite(Ressource res) {
		Activite act = getActiviteSelectionner();
		act.ajouterRessource(res);
		majEDT();
		update();
	}
	
	public void enleverRessourceActivite(Ressource res) {
		Activite act = getActiviteSelectionner();
		act.enleverRessource(res.getId());
		majEDT();
		update();
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

	public void modifieListeActivite(boolean monte) {
		Activite activite = activiteSelectionner;
		Projet projet = activite.getProjet();
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
		update();
	}

	
	public void update() {
		this.setChanged();
		this.notifyObservers();	
	}
}

