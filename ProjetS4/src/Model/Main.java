package Model;


public class Main {
	public static void main (String[] args) {
		Entreprise e = new Entreprise();
		//e.creerProjet("projet 1", 1);
		e.nouvPersonne("planchamp", "damien");
		e.nouvPersonne("planchamp", "pauline");
		e.nouvSalle("Nivolet", 60);
		e.nouvCalculateur("hal-9000");
		//e.creerActivite(e.getProjetSelectionner(), "activité 1", 11, 0, Temps.getAujourdhui());
		//e.creerActivite(e.getProjetSelectionner(), "activité 2", 50, 1, Temps.getAujourdhui());
	}
}
