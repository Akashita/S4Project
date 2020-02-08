
public class Calculateur extends Ressource{
	
	public Calculateur(String nom, int numCalculateur) {
		//attributs de la classe mère.
			super.nom = nom;
			super.type = "Calculateur";
			super.id = numCalculateur;
	}
	public String toString() {
		String liste = "";
		if (this.getProjet() != null) {
			liste = "Le calculateur s'appelle : " + this.nom + ", il porte le numéro : " + this.id  
					+   " il est utilisé dans " + this.getProjet().getNom();
		
		}
		else {
			liste = "Le calculateur s'appelle : " + this.nom + ", il porte le numéro : " + this.id;
		}
		return liste;
	}
}
