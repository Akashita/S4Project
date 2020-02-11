
public class Date {
	private int annee;
	private int mois;
	private int jour;
	private int heure;
	private int minute;
	
	public Date(int annee, int mois, int jour, int heure, int minute) {
		this.annee = annee;
		this.mois = mois;
		this.jour = jour;
		this.heure = heure;
		this.minute = minute;
	}
	
	public boolean inferieur(Date date) {
		return toLongInt(this) < toLongInt(date);
	}
	
	public boolean superieur(Date date) {
		return toLongInt(this) > toLongInt(date);
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
	
	public int toLongInt(Date date) { //Permet la comparaison de dates (Concatenation d entier)
		return Integer.parseInt(""+annee+mois+jour+heure+minute);
	}
	
	@Override
	public String toString() { 
		return annee + " " + mois + " " + jour + " " + heure + " " + minute;
	}
}

