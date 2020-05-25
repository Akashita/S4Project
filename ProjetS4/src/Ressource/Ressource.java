package Ressource;

import Model.Entreprise;

public class Ressource implements Comparable<Ressource>{

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static final int RIEN = -1, PERSONNE = 0, SALLE = 1, CALCULATEUR = 2;

	protected String nom;
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
	public boolean equals(Object obj) {
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



	public static String creeLogin(int id, String nom) {
		return nom + Entreprise.SEPARATEUR + id;
	}

	public static int recupereIdDepuisLogin(String login) {
		String[] regex = login.split(Entreprise.SEPARATEUR, 2);
		String apresSeparateur = regex[1];
		return Integer.parseInt(apresSeparateur);
	}


	public static boolean estUnLogin(String s) {
		boolean b = false;
	    char ch;
		if (s.indexOf(Entreprise.SEPARATEUR)!=-1) {	//verifie qu'il y a '#'
		    for(int i=0;i < s.length();i++) {
		        ch = s.charAt(i);
		        if( Character.isDigit(ch)) { // si il y a un nombre
		            b = true;
		        }
		    }
		}
		return b;
	}
}
