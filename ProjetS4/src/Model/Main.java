package Model;

import java.time.LocalDate;

import Fenetre.FenetrePrincipale;
import Ressource.Personne;


public class Main {
	public static void main (String[] args) {
		Entreprise entreprise = new Entreprise();
		new FenetrePrincipale(entreprise);
	}		
}
