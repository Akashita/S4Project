package Model;
import java.util.Calendar;

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

			
			/*
			 *Utilisation de la classe Date()
			Date date1 = new Date("2020", "02", "15", "12", "30"); 
			Date date2 = new Date("2020", "12", "15", "12", "29"); 
			
			System.out.println(date1.equals(date2));
			System.out.println(date1.superieur(date2));
			System.out.println(date1.inferieur(date2));
			System.out.println(date1.toLong());
			System.out.println(date1.toString());
			*/
			
			
			/*
			 * TEST ET DEMO DE LA CLASSE PLAGE ET DU TYPE CALENDAR
			 * 
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.set(2020, 02, 22, 22, 52);
			cal2.set(2020, 02, 22, 23, 52);
			
			Calendar cal3 = Calendar.getInstance();
			Calendar cal4 = Calendar.getInstance();
			cal3.set(2020, 02, 22, 23, 52);
			cal4.set(2020, 02, 22, 23, 55);
			// set(annee, mois, jour, heure, minute)

			Plage p1 = new Plage("Projet 1", cal1, cal2);
			Plage p2 = new Plage("Projet 1", cal3, cal4);
			
			System.out.println(p1.estAvant(p2)+ " " + p1.estSuperpose(p2));
			*/
			
			}

		
}
