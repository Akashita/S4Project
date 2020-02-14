package Model;

public class Date {
	private String annee;
	private String mois;
	private String jour;
	private String heure;
	private String minute;
	
	public Date(String annee, String mois, String jour, String heure, String minute) {
		this.annee = annee;
		this.mois = mois;
		this.jour = jour;
		this.heure = heure;
		this.minute = minute;
	}
	
	public boolean inferieur(Date date) {
		return toLong() < date.toLong();
	}
	
	public boolean superieur(Date date) {
		return toLong() > date.toLong();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Date && obj != null) {
			Date res = (Date)obj;
			return !this.superieur(res) && !this.inferieur(res);
		} else {
			return false;
		}
	}
	
	public long toLong() { //Permet la comparaison de dates (Concatenation d entier)
		return Long.parseLong(annee+mois+jour+heure+minute);
	}
	
	@Override
	public String toString() { 
		return annee + "/" + mois + "/" + jour + " " + heure + ":" + minute;
	}
}

