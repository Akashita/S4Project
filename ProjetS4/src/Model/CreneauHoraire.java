package Model;

import java.awt.Color;

public class CreneauHoraire {
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	public static final int CONGE = 0, REUNION = 1, TRAVAIL = 2;
	
	private String titre;
	private int debut;
	private int fin;
	private int position;
	private Color couleurActivite;
	private Activite activite;
	private int type;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			CONSTRUCTEUR
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	public CreneauHoraire(Activite activite, int debut, Color couleurActivite) { //Par défault un créneau horaire est de type "TRAVAIL"
		this.activite = activite;
		titre = "" + activite.getId(); //TODO A CHANGER (avec le nom du projet et le nom de l'activitÃ©)
		this.debut = debut;
		fin = debut + 1; //On calcul la fin du creneau
		this.couleurActivite = couleurActivite;
		this.type = CreneauHoraire.TRAVAIL;
		
		
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
	
	public CreneauHoraire(Activite activite, int debut, int type) {
		this.activite = activite;
		titre = "" + activite.getId();
		this.debut = debut;
		fin = debut + 1;
		if (type == CreneauHoraire.CONGE) {
			this.type = type;
			this.couleurActivite = Color.RED;
		} else if(type == CreneauHoraire.REUNION) {
			this.couleurActivite = Color.GRAY;
			this.type = type;
		} else {
			this.couleurActivite = null;
			this.type = CreneauHoraire.TRAVAIL;
		}
		
		
		
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
	
	public int getFin() {
		return fin;
	}
	
	public int getType() {
		return type;
	}
	

	public Color getCouleurActivite() {
		return couleurActivite;
	}

	//--------------------------------------------------------------------------------->>>>> Comparaison	
	public boolean estAvant(CreneauHoraire horaire) {
		return fin <= horaire.debut;
	}
	
	public boolean estApres(CreneauHoraire horaire) {;
		return debut >= horaire.fin;
	}
	
	@Override
	public boolean equals(Object obj) { //test si deux CreneauHoraire sont egaux (meme debuts)
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
