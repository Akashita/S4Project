package Model;

import Fenetre.FenetrePrincipale;


public class Main {
	public static void main (String[] args) {
		Entreprise entreprise = new Entreprise();
		new FenetrePrincipale(entreprise);
	}		
}
