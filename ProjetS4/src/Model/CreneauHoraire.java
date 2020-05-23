package Model;

import java.awt.Color;

public class CreneauHoraire {
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	public static final int CONGE = 0, REUNION = 1, TRAVAIL = 2;
	
	private String titre;
	private int debut;
	private int position;
	private Color couleur;
	private Activite activite;
	private int type;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	public CreneauHoraire(Activite activite, int debut, int type, String titre, Color couleur) { //Par défault un créneau horaire est de type "TRAVAIL"
		this.activite = activite;
		this.debut = debut;
		this.type = type;
		this.titre = titre;
		this.couleur = couleur;

		//On cherche la position du creneau dans une journe (les creneaux sont tous les uns apres mes autres)
		int i = Entreprise.HEURE_DEBUT_MATIN;
		int pos = 0;
		boolean posTrouve = false;
		
		while(i != Entreprise.HEURE_FIN_APREM && !posTrouve) {
			if (i == Entreprise.HEURE_FIN_MATIN) {
				i = Entreprise.HEURE_DEBUT_APREM;
			} 
			if (i == debut) {
				this.position = pos;
				posTrouve = true;
			}
			pos++;
			i++;
		}	
	}
	
	public CreneauHoraire(String titre, Activite activite, int debut, Color couleur) {
		this(activite, debut, TRAVAIL, titre, couleur);
	}
	
	public CreneauHoraire(Activite activite, int debut, int type, String titre) {
		this(activite, debut, -1, titre, null);
		if(type == CONGE) {
			this.titre = "Conge";
			this.couleur = Color.RED;
		} else if(type == REUNION) {
			this.titre = "Reunion";
			this.couleur = Color.ORANGE;
		}
	}
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	public Activite getActivite() {
		return activite;
	}
	
	public int getPosition() {
		return position;
	}

	public String getTitre() {
		return titre;
	}
	
	public int getDebut() {
		return debut;
	}
	
	public int getType() {
		return type;
	}
	

	public Color getCouleurActivite() {
		return couleur;
	}

	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CreneauHoraire && obj != null) {
			CreneauHoraire res = (CreneauHoraire)obj;
			return debut == res.debut;
		} else {
			return false;
		}
		
	}
	
	//--------------------------------------------------------------------------------->>>>> toString
	@Override 
	public String toString() {
		return "Cr" + debut;
	}

}
