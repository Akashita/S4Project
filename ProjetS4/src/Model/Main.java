package Model;

import java.time.LocalDate;
import java.time.LocalTime;

import Fenetre.FenetrePrincipale;
import Ressource.Personne;


public class Main {
	public static void main (String[] args) {
		Entreprise entreprise = new Entreprise();
		//new FenetrePrincipale(entreprise);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		Personne p1 = new Personne("azert", "yolo", "chef", 1555);

		p1.ajouterCreneau(new CreneauHoraire(LocalTime.of(8, 0)), LocalDate.of(2020, 03, 26));
		p1.ajouterCreneau(new CreneauHoraire(LocalTime.of(10, 0)), LocalDate.of(2020, 03, 26));
		p1.ajouterCreneau(new CreneauHoraire(LocalTime.of(15, 0)), LocalDate.of(2020, 03, 26));
		p1.ajouterCreneau(new CreneauHoraire(LocalTime.of(11, 0)), LocalDate.of(2020, 03, 26));

		p1.ajouterCreneau(new CreneauHoraire(LocalTime.of(15, 0)), LocalDate.of(2020, 03, 27));
		p1.ajouterCreneau(new CreneauHoraire(LocalTime.of(10, 0)), LocalDate.of(2020, 03, 27));

		
		System.out.println(p1.getCreneauxLibres());
		
		Personne p2 = new Personne("az", "yo", "ch", 1554);
		p2.ajouterCreneau(new CreneauHoraire(LocalTime.of(9, 0)), LocalDate.of(2020, 03, 26));
		p2.ajouterCreneau(new CreneauHoraire(LocalTime.of(10, 0)), LocalDate.of(2020, 03, 26));
		p2.ajouterCreneau(new CreneauHoraire(LocalTime.of(13, 0)), LocalDate.of(2020, 03, 26));
			

		Activite act1 = new Activite(1, "titre", 25, "A", LocalDate.of(2020, 03, 26));	
		act1.ajouterRessource(p1);
		act1.ajouterRessource(p2);
		
		System.out.println(act1.creneauDispo(LocalDate.of(2020, 03, 26), LocalTime.of(16, 0)));
		
		Projet proj = new Projet("yolo", 1);
		proj.ajouter(act1);
		
		
		
	
	}		
}
