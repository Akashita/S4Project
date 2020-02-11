

public class Calculateur extends Ressource{
	
	public Calculateur(String nom, int numCalculateur) {
		//attribut classe m�re
		super(numCalculateur, nom, "Calculateur");
	}
	public String toString() {
		String liste = "";
		if (this.getProjet() != null) {
			liste = "Le calculateur s'appelle : " + this.nom + ", il porte le num�ro : " + this.id  
					+   " il est utilis� dans " + this.getProjet().getNom();
		
		}
		else {
			liste = "Le calculateur s'appelle : " + this.nom + ", il porte le num�ro : " + this.id;
		}
		return liste;
	}
}
