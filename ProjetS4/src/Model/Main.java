package Model;
import java.time.LocalDate;

import Fenetre.FenetrePrincipale;
import Ressource.Personne;
import Ressource.Salle;

//histoire de faire mes tests => j'aime faire des tests @jules 
public class Main {

	//static Entreprise test = new Entreprise();

		public static void main (String[] args) {
			Entreprise entreprise = new Entreprise();
			new FenetrePrincipale(entreprise);
			
			
			Projet proj = new Projet("ProjetTest");
			
			Activite act1 = new Activite(1, "ProjetTest", LocalDate.of(2020, 6, 13), LocalDate.of(2020, 7, 20));
			Activite act2 = new Activite(2, "ProjetTest", LocalDate.of(2020, 7, 20), LocalDate.of(2020, 8, 3));

			Personne p1 = new Personne("azert", "yolo", "ferg", 1555);
			Personne p2 = new Personne("aze", "yol", "rgt", 1554);
			Personne p3 = new Personne("az", "yo", "rthrth", 1553);
			
			Salle salle1 = new Salle(1, "2c108", 80);
			Salle salle2 = new Salle(2, "2c109", 40);
			
			act1.ajouterRessource(p1);
			act1.ajouterRessource(p2);
			act1.ajouterRessource(salle1);
			
			act2.ajouterRessource(p1);
			act2.ajouterRessource(p3);
			act2.ajouterRessource(salle2);
			
			proj.ajouter(act1);
			proj.ajouter(act2);
			
			
			System.out.println(proj.toString());
		}

		
}
