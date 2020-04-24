package Model;

import java.awt.Color;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTextArea;

import Ressource.Ressource;

public class Activite implements Comparable<Activite>{

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private int id;
	private String titre;
	private double chargeJHomme;
	private int chargeHeure;
	private LocalDate debut;
	private Color couleur;
	private int ordre;
	private ArrayList<Ressource> lRessources; //Contient les cr�neaux horaires d'une journ�e
	private ArrayList<String> listeDomaine;
	
	private boolean afficheEDT = false; //pour le graphique
	private boolean changeSens = false; //pour le graphique
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Activite(int id, String titre, double chargeJH, LocalDate debut, Color couleur, int ordre, ArrayList<String> listeDomaine) {
		this.id = id;
		this.titre = titre;
		this.chargeJHomme = chargeJH; //La charge de travail de l'activitee en jourHomme
		this.chargeHeure = (int)(chargeJH * Entreprise.NB_HEURE_JOUR);
		this.debut = debut;
		this.couleur = couleur;
		this.listeDomaine = listeDomaine;
		lRessources = new ArrayList<Ressource>();
	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public String getTitre() {
		return titre;
	}

	public double getChargeJHomme() {
		return chargeJHomme;
	}

	public int getChargeHeure() {
		return chargeHeure;
	}

	public LocalDate getDebut() {
		return debut;
	}

	public ArrayList<Ressource> getLRessource(){
		return lRessources;
	}

	public String getJourDebut() {
		String jour = Integer.toString(debut.getDayOfMonth());
		String mois = Integer.toString(debut.getMonthValue());
		String annee = Integer.toString(debut.getYear());
		String date = jour + "/" + mois + "/" + annee;
		return date;
	}

	public int getId() {
		return id;
	}

	public Color getCouleur() {
		return couleur;
	}

	public boolean getAfficheEDT() {
		return afficheEDT;
	}

	public boolean getChangeSens() {
		return changeSens;
	}

	public int getOrdre() {
		return ordre;
	}

	public boolean hasRessource() {
		return lRessources.size() != 0;
 	}
	
	public ArrayList<String> getListeDomaine(){
		return listeDomaine;
	}

	//--------------------------------------------------------------------------------->>>>> Setteur


	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setCharge(int charge) {
		this.chargeJHomme = charge;
		this.chargeHeure = (int)(charge * Entreprise.NB_HEURE_JOUR);
	}

	public void setDebut(LocalDate debut) {
		this.debut = debut;
	}

	public void afficheEDT() {
		if (afficheEDT) {
			afficheEDT = false;
		}
		else {
			if (getListeRessourceType(Ressource.PERSONNE).size()>0) {
				afficheEDT = true;
			}
		}
	}

	public void setChangeSens(boolean b) {
		changeSens = b;
	}
	
	public void supprimerToutesRessources() {
		lRessources.clear();
	}


	//--------------------------------------------------------------------------------->>>>> Comparaison
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Activite && obj != null) {
			Activite res = (Activite)obj;
			return id == res.id;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Activite act) {
		int res;
		if(ordre == act.ordre) {
			res = 0;
		} else if(ordre < act.ordre) {
			res = -1;
		} else {
			res = 1;
		}

		return res;
	}


	//--------------------------------------------------------------------------------->>>>> toString
	@Override
	public String toString() {
		//String res = "Activite " + ordre;
		String res = "Activite " + titre + " ";
		for (int i = 0; i < lRessources.size(); i++) {
			res += "\n" + lRessources.get(i);
		}
		return res;
	}


	//--------------------------------------------------------------------------------->>>>> Gestion de l'EDT

	/**
	 * Dit si un creneau est dispo pour toutes lRessources associees
	 * @param date   La date du creneau
	 * @param heure  L'heure du creneau
	 * @return vrai si le creneau est dispo pour toutes les lRessources associees
	 */
	public boolean creneauDispo(String type, LocalDate date, int heure){
		Boolean dispo = true;
		for (int i = 0; i < lRessources.size(); i++) {
			if(lRessources.get(i).getType() == type) {
				if(!lRessources.get(i).creneauDispo(date, heure)) {
					dispo = false;
				}
			}
			
		}
		return dispo;
	}


	/**
	 * Ajoute un creneau a toutes les lRessources associees
	 * @param cr   	Le creneau a ajouter
	 * @param jour 	Le jour auquel ajouter le creneau
	 */
	public void ajouterCreneau(String type, CreneauHoraire cr, LocalDate jour) {
		for (int i = 0; i < lRessources.size(); i++) {
			if(lRessources.get(i).getType() == type) {
				lRessources.get(i).ajouterCreneau(cr, jour);
			}
		}
	}


	/**
	 * Supprime une ressource de l'activite
	 * @param id  L'ID de la ressource
	 * @return true si la ressource a ete supprimee
	 */
	public boolean enleverRessource(int id) {
		return lRessources.remove(new Ressource(id));
	}


	/**
	 * Ajouter une ressource a l'activite
	 * @param ressource  La ressource
	 * @return true si la ressource a ete ajoutee
	 */
	public boolean ajouterRessource(Ressource ressource) {
		if(!lRessources.contains(ressource)) {
			lRessources.add(ressource);
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Cherche une ressource dans le idProjet
	 * @param ressource   La ressource a chercher
	 * @return un entier qui indique si elle a ete trouvee et sa position
	 */
	public int[] chercherRessource(int id) {
		Boolean trouve = false; //Indique si l'indice a ete trouve
		Boolean depasse = false; //Indique si la position a ete depacee

		int[] res = {0,0};
		Iterator<Ressource> itt = lRessources.listIterator();
		Ressource resTMP;

		while(!trouve && itt.hasNext() && !depasse){
			resTMP = itt.next();
			if(id <= resTMP.getId()) {
				trouve = true;
				res[1] = 1;
			} else {
				res[0]++;
			}
		}

		return res;
	}

	public void vider() {
		for (int i = 0; i < lRessources.size(); i++) {
			lRessources.get(i).vider();
		}
	}


	/**
	 * Selectionne des lRessources de l'activite par type
	 * @param type  Le type de la ressource
	 * @return la liste des lRessources selectionnees
	 */
	public ArrayList<Ressource> getListeRessourceType(String type){
		ArrayList<Ressource> nouvelleListe = new ArrayList<Ressource>();
		for (int i=0; i<lRessources.size(); i++) {
			Ressource ressource = lRessources.get(i);
			if(ressource.getType() == type) {
				nouvelleListe.add(ressource);
			}
		}
		return nouvelleListe;
	}

}
