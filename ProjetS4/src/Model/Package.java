package Model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;

import Ressource.Ressource;

public class Package {
	private String nomProjet;
	private LocalDate jourDebut;
	private int nbJour;
	private int pourcentage;
	
	private Ressource ressource;
	
	private Hashtable<LocalDate, ArrayList<CreneauHoraire>> jours; //Contient l'ensemble des jours qui possèdent un créneau horaire, la clé est une LocalDate du jour choisi
	private ArrayList<CreneauHoraire> creneaux; //Contient les créneaux horaires d'une journée
	
	public Package(String nomProjet, LocalDate Jourdebut, int nbJour, int pourcentage, Ressource ressource) {
		this.nomProjet = nomProjet;
		this.jourDebut = Jourdebut;
		this.nbJour = nbJour;
		this.pourcentage = pourcentage;
		
		this.creneaux = new ArrayList<CreneauHoraire>(); 

	    jours = new Hashtable(); 
		

	}
	
	private void generationPackage() {
		int heureMaxParJour = (pourcentage * 8)/100; // Le pourcentage est forcément de 25,50,75 ou 100% donc la valeur est forcément entière;
		int nbMinCreneaux = (int) Math.ceil((nbJour * 8)/heureMaxParJour); // On arrondi à l'entier supérieur
		
		
		LocalDate jourCourant = jourDebut;
		Duration duree = Duration.ofHours(heureMaxParJour);
		CreneauHoraire creneau;
		ArrayList<CreneauHoraire> creneauxTmp;
		
		
		for (int i = 1; i < nbMinCreneaux; i++) {
			
			if(jours.containsKey(jourCourant)) { //Si le jour existe (il y a déja un créneau dedans)
				creneauxTmp = jours.get(jourCourant);
				ajouterCreneau(creneauxTmp, jourCourant, duree); //On ajoute le créneau à partir de la première heure dispo de la journée
			} else {
				creneau = new CreneauHoraire(LocalTime.of(8,0), duree);
				creneauxTmp = new ArrayList<CreneauHoraire>();
				creneauxTmp.add(creneau);
				jours.put(jourCourant, creneauxTmp); //On crée un nouveau jour avec une arraylist de créneaux (qui n'en contient que un seul pour le moment)
			}

			jourCourant.plusDays(1); //On change de jour
		}
	}

	private void ajouterCreneau(ArrayList<CreneauHoraire> tab, LocalDate jourCourant, Duration duree) { // TODO A MODIFIER POUR ACCUEILIR L4ARRAYLIST et creer le creneau
		ArrayList<CreneauHoraire> creneauxLibres = getCreneauxLibres(jourCourant);
		Boolean place = false;
		int i = 0;
		int taille = creneauxLibres.size();
		while(i < taille && !place) {
			Duration dureeLibre = creneauxLibres.get(i).getDuree();
			if(dureeLibre.compareTo(duree) > 0 || dureeLibre.equals(duree)) { //Si dureeLibre >= duree
				LocalTime debut = creneauxLibres.get(i).getDebut();
				tab.add(i, new CreneauHoraire(debut, duree));
				place = true;
			} 
			i++;
		}
	}
		

	private ArrayList<CreneauHoraire> getCreneauxLibres(LocalDate jour) { //NE MARCHE QUE SI CRENEAUX EST UN TAB ORDONNE
		ArrayList<CreneauHoraire> creneaux = jours.get(jour);
		ArrayList<CreneauHoraire> creneauxLibres = new ArrayList<CreneauHoraire>();
		CreneauHoraire crLibre;
		CreneauHoraire creneauCourant1;
		CreneauHoraire creneauCourant2;
		for (int i = 0; i < creneaux.size(); i++) { 
			if(creneaux.get(i).estEspaceDe(creneaux.get(i+1))){
				creneauCourant1 = creneaux.get(i);
				creneauCourant2 = creneaux.get(i + 1);
				crLibre = new CreneauHoraire(creneauCourant1.getFin(), creneauCourant1.dureeJusque(creneauCourant2));
				creneauxLibres.add(crLibre);
			}
		}
		return creneauxLibres;
	}

}
