package Model;
import java.awt.Color;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import javax.swing.JTextArea;

import Ressource.Personne;
import Ressource.Ressource;

public class Projet implements Comparable<Projet>{

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private ArrayList<Activite> lActivite;//liste des activites (ordonnees par ordre)

	private String nom;
	private float priorite; //Priorite du projet
	private int id;
	private LocalDate deadline;
	private Color couleur;
	private Personne chefDeProjet;
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Projet(ArrayList<Activite> Activite, Personne chefDeProjet, String nom, float priorite, LocalDate deadline, int id, Color couleur) {
		this.chefDeProjet = chefDeProjet;
		this.lActivite =  Activite;   // liste des activité du projet (demander pas Dams)
		this.nom = nom;
		this.priorite = priorite;
		this.deadline = deadline;
		this.id = id;
		this.couleur = couleur;
	}
	
	public Projet(Personne chefDeProjet, String nom, float priorite, LocalDate deadline, int id, Color couleur) {
		this(new ArrayList<Activite>(), chefDeProjet, nom, priorite, deadline, id, couleur);
	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public String getNom() {
		return this.nom;
	}

	public ArrayList<Activite> getListe(){
		return lActivite;
	}
	
	public float getPriorite() {
		return priorite;
	}
	
	public LocalDate getDeadline() {
		return deadline;
	}

	public int getId() {
		return id;
	}
	
	public Color getCouleur() {
		return couleur;
	}

	public Personne getChefDeProjet() {
		return chefDeProjet;
	}
	
	public Hashtable<String, Integer> getActiviteOrdre(){
		Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
		for (int i = 0; i < lActivite.size(); i++) {
			Activite actCC = lActivite.get(i);
			ht.put(actCC.getTitre(), actCC.getOrdre());
		}
		return ht;
	}
	
	public boolean ressourcePresente(Ressource r) {
		boolean b = false;
		for (int i=0; i<lActivite.size(); i++) {
			if (lActivite.get(i).ressourcePresente(r)) {
				b = true;
				break;
			}
		}
		return b;
	}
	
	//------------------------------------------------------------------------------->>>>>>> Setteurs
	
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPriorite(float priorite) {
		this.priorite = priorite;
	}

	public void setChefDeProjet(Personne chefDeProjet) {
		this.chefDeProjet = chefDeProjet;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public void setListeActivite(ArrayList<Activite> lActivite) {
		this.lActivite = lActivite;
	}
	
	//--------------------------------------------------------------------------------->>>>> Comparaison
	@Override
	public boolean equals(Object obj) {//permet de tester si deux projets ont le m�me nom.
		if(obj instanceof Projet && obj != null) {
			Projet res = (Projet)obj;
			return nom == res.nom;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Projet proj) {
		int res;
		if(priorite == proj.priorite) {
			res = 0;
		} else if(priorite < proj.priorite) {
			res = -1;
		} else {
			res = 1;
		}

		return res;
	}

	//--------------------------------------------------------------------------------->>>>> toString
	public String toString() {
		String liste = this.nom;
		
		return liste;
	}

	//--------------------------------------------------------------------------------->>>>> Gestion des activites

	/**
	 * Ajoute une activite au projet
	 * @param activite   L'activite a ajouter
	 * @return true si l'activite a ete ajoutee
	 */
	public boolean ajouter(Activite activite) {
		if(!lActivite.contains(activite)) {
			lActivite.add(activite);
			Collections.sort(lActivite);
			return true;
		} else {
			return false;
		}

	}
	
	public void vider() {
		for (int i = 0; i < lActivite.size(); i++) {
			lActivite.get(i).vider();
		}
	}


	/**
	 * Enleve une activite du projet
	 * @param activite   L'activite a enlever
	 * @return true si l'activite a ete enlevee
	 */
	public boolean enlever(Activite activite) {
		return lActivite.remove(activite);
	}
	
	
	public boolean contientActivite(Activite act) {
		boolean contient = false;
		for (int i = 0; i < lActivite.size(); i++) {
			if(lActivite.get(i).equals(act)) {
				contient = true;
			}
		}
		return contient;
	}

	public String creeAffiche() {
		// TODO Auto-generated method stub
		return "nom : " + this.nom + ",id :" + this.id + ",priorite : " + this.priorite + ", chef de projet : " + this.chefDeProjet + ", couleur : " + this.couleur + ", deadline : " + this.deadline;
	}



}
