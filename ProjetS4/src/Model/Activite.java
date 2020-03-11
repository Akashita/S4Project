package Model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;

import Ressource.Ressource;

public class Activite {
	private String nomProjet;
	private LocalDate jourDebut;
	private int nbJour;
	private int pourcentage;
	
	private Ressource ressource;
	
	private Hashtable<LocalDate, ArrayList<CreneauHoraire>> jours; //Contient l'ensemble des jours qui possèdent un créneau horaire, la clé est une LocalDate du jour choisi
	private ArrayList<CreneauHoraire> creneaux; //Contient les créneaux horaires d'une journée
	
	public Activite(LocalDate Jourdebut, LocalDate Jourfin) {
		this.nomProjet = nomProjet;
		this.jourDebut = Jourdebut;
		this.nbJour = nbJour;
		this.pourcentage = pourcentage;
		
		this.creneaux = new ArrayList<CreneauHoraire>(); 

	    jours = new Hashtable(); 
		

	}
	

}
