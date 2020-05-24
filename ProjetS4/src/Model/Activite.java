package Model;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import Ressource.Ressource;

public class Activite implements Comparable<Activite>{

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private int id;
	private String titre;
	private float chargeJHomme;
	private int chargeHeure;
	private LocalDate debut;
	private Color couleur;
	private int ordre;
	private ArrayList<Ressource> lRessources; //Contient les cr�neaux horaires d'une journ�e
	private ArrayList<String> listeDomaine;

	private boolean changeSens = false; 
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Activite(int id, String titre, float chargeJH, LocalDate debut, Color couleur, int ordre, ArrayList<String> listeDomaine) {
		this.id = id;
		this.titre = titre;
		this.chargeJHomme = chargeJH;
		this.chargeHeure = (int)(chargeJH * Entreprise.NB_HEURE_JOUR);
		this.ordre = ordre;
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

	public float getChargeJHomme() {
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


	public boolean getChangeSens() {
		return changeSens;
	}


	public int getOrdre() {
		return ordre;
	}
	
	public ArrayList<String> getListeDomaine(){
		return listeDomaine;
	}

	public boolean ressourcePresente(Ressource r) {
		boolean estPresent = false;
		for (int i=0; i<lRessources.size(); i++) {
			if (r.getId() == lRessources.get(i).getId()) {
				estPresent = true;
				break;
			}
		}
		return estPresent;
	}
	
	//--------------------------------------------------------------------------------->>>>> Setteur


	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setCharge(float charge) {
		this.chargeJHomme = charge;
		this.chargeHeure = (int)(charge * Entreprise.NB_HEURE_JOUR);
	}

	public void setDebut(LocalDate debut) {
		this.debut = debut;
	}


	public void setChangeSens(boolean b) {
		changeSens = b;
	}


	public void supprimerToutesRessources() {
		lRessources.clear();
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
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
		String res = titre;
		
		return res;
	}


	//--------------------------------------------------------------------------------->>>>> Gestion de l'EDT


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


	/**
	 * Selectionne des lRessources de l'activite par type
	 * @param type  Le type de la ressource
	 * @return la liste des lRessources selectionnees
	 */
	public ArrayList<Ressource> getListeRessourceType(int type){
		ArrayList<Ressource> nouvelleListe = new ArrayList<Ressource>();
		for (int i=0; i<lRessources.size(); i++) {
			Ressource ressource = lRessources.get(i);
			if(ressource.getType() == type) {
				nouvelleListe.add(ressource);
			}
		}
		return nouvelleListe;
	}

	public String creeAffiche() {
		// TODO Auto-generated method stub
		return "titre : " + this.titre + ", id : " + this.id + ", ordre : " + this.ordre + ", charge heure : " + this.chargeHeure + ", charge JHomme : " + this.chargeJHomme 
			+ ", couleur : " + this.couleur + ", debut : " + this.debut + ", liste de domaine : " + this.listeDomaine + ",liste de ressource : " + this.lRessources;
	}

}
