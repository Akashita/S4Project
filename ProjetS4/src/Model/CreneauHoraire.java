package Model;

import java.awt.Color;

public class CreneauHoraire {
	private String titre;
	private int debut;
	private int fin;
	private int position;
	
	//Un creneau horaire est une plage de temps d'une heure qui a un debut et une fin
	//il s'agit d'un element qui est contenu dans une journee : une journee est composee de creneaux horaires
	public CreneauHoraire(String titre, int debut) {
		this.titre = titre;
		this.debut = debut;
		fin = debut + 1; //On calcul la fin du creneau
		
		int i = Entreprise.HEURE_DEBUT_MATIN;
		int pos = 0;
		boolean posTrouve = false;
		
		//On cherche la position du creneau dans une journe (les creneaux sont tous les uns apres mes autres)
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
	
	public int getPosition() {
		return position;
	}
	
	public boolean estAvant(CreneauHoraire horaire) {
		return fin <= horaire.debut;
	}
	
	public boolean estApres(CreneauHoraire horaire) {;
		return debut >= horaire.fin;
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

	
	@Override 
	public String toString() {
		return "Cr" + debut;
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

	
	
}
