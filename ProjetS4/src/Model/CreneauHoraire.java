package Model;

public class CreneauHoraire {
	private int debut;
	private int fin;
	private boolean dispo;
	
	
	//Un creneau horaire est une plage de temps d'une heure qui a un debut et une fin
	//il s'agit d'un element qui est contenu dans une journee : une journee est composee de creneaux horaires
	public CreneauHoraire(int debut, boolean occupe) {
		this.debut = debut;
		this.dispo = occupe;
		fin = debut + 1; //On calcul la fin du creneau
	}
	
	public boolean estAvant(CreneauHoraire horaire) {
		return fin <= horaire.debut;
	}
	
	public boolean estApres(CreneauHoraire horaire) {;
		return debut >= horaire.fin;
	}

	public int getDebut() {
		return debut;
	}
	
	public int getFin() {
		return fin;
	}
	
	public boolean getDispo() {
		return dispo;
	}
	
	public void setDebut(int debut) {
		this.debut = debut;
		this.fin = debut+1;
	}
	
	public void decalerAvant(int heure) { //Decale le creneau d'un nombre d'heure donné dans le futur
		this.debut = this.debut + heure;
		this.fin = this.debut + 1;
	}
	
	public CreneauHoraire creneauSuivant(boolean occupe) { //Retourne le creneau suivant (un nouveau creneau)
		return new CreneauHoraire(debut + 1, occupe);
	}
	
	public void decalerAriere(int heure) { //Decale le creneau d'un nombre d'heure donné dans le passe
		this.debut = this.debut - heure;
		this.fin = this.debut + 1;
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
