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
	
	public boolean estAvant(CreneauHoraire horaire) {
		return debut.isBefore(horaire.debut);
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
	
	public boolean estEspaceDe(CreneauHoraire cr) {
		return !fin.equals(cr.debut); //TODO ATTENTION EQUALS
	}
	
	public Duration dureeJusque(CreneauHoraire cr) {
		return cr.debut.minus(fin);
	}
	
	
}
