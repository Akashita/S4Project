package Ressource;

import java.util.Calendar;

public class Plage {
	private String nomProjet;
	private Calendar debut;
	private Calendar fin;
	
	public Plage(String nomProjet, Calendar debut, Calendar fin) {
		this.nomProjet = nomProjet;
		this.debut = debut;
		this.fin = fin;
	}
	
	public Calendar getDebut(){
		return debut;
	}
	
	public Calendar getFin() {
		return fin;
	}
	
	public String getProjet() {
		return nomProjet;
	}
	
	public void resetDate(Calendar debut, Calendar fin) {
		this.debut = debut;
		this.fin= fin;
	}
	
	public void resetProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	
	public boolean estAvant(Plage pl) {
		return fin.before(pl.debut) || fin.equals(pl.debut);
	}
	
	
	public boolean estSuperpose(Plage pl) {
		return (this.contient(pl.debut) || this.contient(pl.fin));
	}
	
	public boolean contient(Calendar date) {
		return date.after(debut) && date.before(fin);
	}
	
}
