package Model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CreneauHoraire {
	private LocalTime debut;
	private Duration duree;
	private LocalTime fin;
	
	public CreneauHoraire(LocalTime debut, Duration duree) {
		this.debut = debut;
		this.duree = duree;
		fin = this.debut.plus(duree.toHours(), ChronoUnit.HOURS);
		fin.plus(duree.toMinutes(), ChronoUnit.MINUTES);
	}
	
	public boolean estAvant(CreneauHoraire horaire) { //On considère que les créneaux ne se superposent pas 
		System.out.println(debut.compareTo(horaire.fin));
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
	
	@Override 
	public String toString() {
		return "Creneau qui commence le " + debut.toString() + " et qui dure " + duree.toString();
	}

	
	
}
