package Model;

import java.time.Duration;
import java.time.LocalDate;

public class Plage {
	private String nomProjet;
	private LocalDate debut;
	private Duration nbHeure;
	private int pourcentage;
	private LocalDate fin;
	
	public Plage(String nomProjet, LocalDate debut, int nbHeure, int pourcentage) {
		this.nomProjet = nomProjet;
		this.debut = debut;
		this.nbHeure = Duration.ofHours(nbHeure);
		this.pourcentage = pourcentage;
		
		fin = debut.plus(Duration.ofDays((nbHeure*100)/(pourcentage*8)));
		
	}
	
	public boolean estAvant(Plage pl) {
		return debut.isBefore(pl.debut);
	}
	
	public boolean contientJour(LocalDate jour) {
		return jour.isAfter(debut) && jour.isBefore(fin) || jour.isEqual(debut) || jour.isEqual(fin);
	}
	
	
	
	
	/*
	
	public LocalDateTime getDebut(){
		return debut;
	}
	
	public LocalDateTime getFin() {
		return fin;
	}
	
	public String getProjet() {
		return nomProjet;
	}
	
	public void resetDate(LocalDateTime debut, LocalDateTime fin) {
		this.debut = debut;
		this.fin= fin;
	}
	
	public void resetProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	
	public boolean estAvant(Plage pl) {
		return fin.isBefore(pl.debut) || fin.equals(pl.debut);
	}
	
	public boolean finiApres(LocalDateTime date) {
		return (fin.isAfter(date));
	}
	
	public boolean estSuperpose(Plage pl) {
		return (this.contient(pl.debut) || this.contient(pl.fin));
	}
	
	public boolean contient(LocalDateTime date) {
		return date.isAfter(debut) && date.isBefore(fin);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Plage && obj != null) {
			Plage res = (Plage)obj;
			return nomProjet == res.nomProjet
					&& debut.equals(res.debut)
					&& fin.equals(res.fin);
		} else {
			return false;
		}
		
	}*/
	
}
