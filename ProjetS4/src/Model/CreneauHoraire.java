package Model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import Ressource.Ressource;

public class CreneauHoraire {
	private LocalTime debut;
	private Duration duree;
	private LocalTime fin;
	
	
	//Un creneau horaire est une plage de temps d'une heure qui a un debut et une fin
	//il s'agit d'un element qui est contenu dans une journee : une journee est composee de creneaux horaires
	public CreneauHoraire(LocalTime debut) {
		this.debut = debut;
		this.duree = Duration.of(1, ChronoUnit.HOURS);
		
		fin = this.debut.plus(duree.toHours(), ChronoUnit.HOURS); //On calcul la fin du creneau
	}
	
	public boolean estAvant(CreneauHoraire horaire) { //Les creneaux ne se superposent pas
		return fin.isBefore(horaire.debut);
	}
	
	public boolean estApres(CreneauHoraire horaire) {;
		return debut.isAfter(horaire.fin);
	}
	
	public Duration getDuree() {
		return duree;
	}
	
	public LocalTime getDebut() {
		return debut;
	}
	
	public LocalTime getFin() {
		return fin;
	}
	
	public void setDebut(LocalTime debut) {
		this.debut = debut;
		calculerFin();
	}
	
	public void decalerAvant(int heure) { //Decale le creneau d'un nombre d'heure donné dans le futur
		this.debut = this.debut.plus(heure, ChronoUnit.HOURS);
		calculerFin();
	}
	
	public CreneauHoraire creneauSuivant() { //Retourne le creneau suivant (un nouveau creneau)
		return new CreneauHoraire(this.debut.plus(1, ChronoUnit.HOURS));
	}
	
	public void decalerAriere(int heure) { //Decale le creneau d'un nombre d'heure donné dans le passe
		this.debut = this.debut.plus(heure, ChronoUnit.HOURS);
		calculerFin();
	}
	
	private void calculerFin() {
		fin = this.debut.plus(duree.toHours(), ChronoUnit.HOURS);
		fin.plus(duree.toMinutes(), ChronoUnit.MINUTES);
	}
	
	@Override 
	public String toString() {
		return "Creneau : " + debut.toString() + " ; " + duree.toString() + '\n';
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
