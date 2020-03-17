package Model;

import Fenetre.FenetrePrincipale;


public class Main {
	public static void main (String[] args) {
		String a = "A";
		String aa = "BB";
		
		
		System.out.println(aa.compareTo(a));
		
		Entreprise entreprise = new Entreprise();
		new FenetrePrincipale(entreprise);
	}		
}
