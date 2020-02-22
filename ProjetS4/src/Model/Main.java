package Model;
import java.time.LocalDateTime;

import Fenetre.FenetrePrincipale;
import Ressource.Plage;

//histoire de faire mes tests => j'aime faire des tests @jules 
public class Main {

	//static Entreprise test = new Entreprise();

		public static void main (String[] args) {
			Entreprise entreprise = new Entreprise();
			new FenetrePrincipale(entreprise);
			/*
			//test Entreprise et ses m�thodes
			 test.creerProjet("test, le projet");
			 test.creerProjet("test2, il est de retour");
			 test.nouvPersonne("test, le projet", "geyer", "jules", Personne.COLLABORATEUR);
			 test.nouvPersonne("test, le projet", "Planchamp", "Damien", Personne.ADMINISTRATEUR);
			 test.nouvPersonne("Dompnier", "Silvio", Personne.CHEFDEPROJET);
			 test.nouvSalle("test, le projet","salut a tous c'est la salle", 25);
			 test.nouvTypeRessource("typeTest");
			 test.nouvRessourceAutre("test, le projet", "el roulio telefo", "telephone");
			 test.ajouterRessourceProjet(2,"test, le projet");
			 test.nouvPersonne("Launay", "Swan", Personne.COLLABORATEUR);
			 test.supprimerRessource(5);
			 test.nouvPersonne("Monsieur", "5g", Personne.COLLABORATEUR);
			 //test.supprimerRessource(5);
			 test.enleverRessourceProjet(0);
			 test.deplacerRessourceProjet(1, "test2, il est de retour");



			 
			 
			 // Test methode equals de Ressource
			 Personne p1 = new Personne("azert", "yolo", "chef", 1555);
			 Personne p2 = new Personne("az", "yo", "ch", 1554);
			 Personne p3 = new Personne("azert", "yolo", "chef", 1555);

			 System.out.println(p1.equals(p2));
			 System.out.println(p1.equals(p3));

			 System.out.println(test.toString());
			*/


			//TEST ET DEMO DE LA CLASSE PLAGE ET DU TYPE CALENDAR

			LocalDateTime b = LocalDateTime.of(2012, 7, 25, 12, 30);
			LocalDateTime c = LocalDateTime.of(2012, 7, 25, 13, 30);
			
			LocalDateTime d = LocalDateTime.of(2012, 7, 25, 13, 00);
			LocalDateTime e = LocalDateTime.of(2012, 7, 25, 14, 30);

			Plage p1 = new Plage("Projet 1", b, c);
			Plage p2 = new Plage("Projet 1", d, e);
			
			System.out.println(p1.estAvant(p2)+ " " + p1.estSuperpose(p2));

			}

		
}
