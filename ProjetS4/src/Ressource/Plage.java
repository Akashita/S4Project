package Ressource;

import java.time.LocalDateTime;

public class Plage {
	private String nomProjet;
	private LocalDateTime debut;
	private LocalDateTime fin;
	
	public Plage(String nomProjet, LocalDateTime debut, LocalDateTime fin) {
		this.nomProjet = nomProjet;
		this.debut = debut;
		this.fin = fin;
	}
	
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
					&& debut == res.debut
					&& fin == res.fin;
		} else {
			return false;
		}
		
	}
	
}
