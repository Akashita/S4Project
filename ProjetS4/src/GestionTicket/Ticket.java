package GestionTicket;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import Model.Projet;
import Ressource.Personne;
import Ressource.Ressource;
import SQL.JavaSQLPersonne;

public class Ticket {
	private int id;
	private int action;
	private String sujet;
	private String message;
	private String modif;
	private LocalDate dateTicket;
	private int statut;
	private int idEnvoyeur;
	private int idReceveur;
	private Ressource r;
	private Projet projetArrive;

	
	public static final int MESSAGE = 0, LIBERE= 1, TRANSFERT = 2,  REFUSE = 3,   ACCEPTEE= 4,  ENCOURS= 5, ERREUR = 6;
	private static final String SEPARATEUR = "|";

	
	
	//constructeur BDD
	public Ticket(int id, String sujet,String message,String modif,LocalDate dateTicket, int statut, int idEnvoyeur, int idReceveur) {
		this.id = id;
		this.sujet = sujet;
		this.message = message;
		this.modif = modif;
		this.dateTicket = dateTicket;
		this.statut = statut;
		this.idEnvoyeur = idEnvoyeur;
		this.idReceveur = idReceveur;
	}
	//constructeur message
	public Ticket(int id,int action, String sujet,String message,LocalDate dateTicket, int statut, int idEnvoyeur, int idReceveur) {
		this.id = id;
		this.action = action;
		this.sujet = sujet;
		this.message = message;
		this.modif = creeModif(action);
		this.dateTicket = dateTicket;
		this.statut = statut;
		this.idEnvoyeur = idEnvoyeur;
		this.idReceveur = idReceveur;
	}
	
	
	//constructeur libération
	public Ticket(int id,int action, String sujet,String message,LocalDate dateTicket, int statut, int idEnvoyeur, int idReceveur, Ressource r) {
		this(id, action, sujet, message, dateTicket, statut, idEnvoyeur, idReceveur);
		this.r = r;
		this.modif = creeModif(action);
		
	}
	
	
	//constructeur transfert
	public Ticket(int id,int action, String sujet,String message,LocalDate dateTicket, int statut, int idEnvoyeur, int idReceveur, Ressource r, Projet projet) {
		this(id, action, sujet, message, dateTicket, statut, idEnvoyeur, idReceveur,r);
		this.projetArrive = projet;
		this.modif = creeModif(action);
		
	}
	

	
	private String creeModif(int action) {
		if (action == MESSAGE) {
			return "message"+ SEPARATEUR;
		}
		else if (action == TRANSFERT) {
			return "transfert"+ SEPARATEUR + r.getId()+ SEPARATEUR + this.projetArrive.getId() ;
		}
		else if (action == LIBERE) {
			return "libere"+ SEPARATEUR + r.getId();
		}
		else {
			return "erreur"+ SEPARATEUR;
		}
	}
	
	public static int getTache(String modif) {
		String[] regex = modif.split(SEPARATEUR, 2); 
		String avantSeparateur = regex[0];
		if (avantSeparateur == "message") {
			return MESSAGE;
		}
		else if (avantSeparateur == "libere") {
			return LIBERE;

		}
		else if (avantSeparateur == "transfert") {
			return TRANSFERT;

		}
		else {
			return ERREUR;
		}
			
		}
	
	public int getId() {
		return this.id;
	}
	public int getStatut() {
		return this.statut;
	}
	
	public int getIdEnvoyeur() {
		return this.idEnvoyeur;
	}
	
	public int getIdReceveur() {
		return this.idReceveur;
	}
	
	public String getSujet() {
		return this.sujet;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getModif() {
		return this.modif;
	}
	public LocalDate getdateTicket() {
		return this.dateTicket;
	}
	
	public void setStatut(int s) {
		this.statut = s;
	}
	public String creerAffichage() {
		return "ticket n' = " + this.id + ", sujet = " + this.sujet + ", message = " + this.message + ", modification = " + this.modif + ",statut" + this.getStringStatut() + 
				", envoyeur = " + this.idEnvoyeur + ", receveur = " + this.idReceveur + ", cree le : " + dateTicket.toString();
	}
	
	public String creerAffichageReceveur() {
		return " sujet = " + this.sujet + ", envoyeur = " + this.getPersonneEnvoyeur().getPrenomNom()  + ", cree le : " + dateTicket.toString();
	}
	
	public String creerAffichageEnvoyeur() {
		return " sujet = " + this.sujet  + ", receveur = " + this.getPersonneReceveur().getPrenomNom() + ", cree le : " + dateTicket.toString();
	}
	
	public String toString() {
		return this.sujet + " - " + this.getPersonneEnvoyeur().getPrenomNom() + " - " + dateTicket.toString();
 
		
		
	}


	private String getStringStatut() {
		if (this.statut == ENCOURS ) {
		return "EN COURS";
		}
		else if (this.statut == ACCEPTEE) {
			return "ACCEPTEE";
		}
		else if (this.statut == REFUSE) {
			return "REFUSE";
		}
		else {
			return "ERREUR";
		}
	
		}
	
	public Personne getPersonneEnvoyeur() {
		ArrayList<Personne> personneTab = new ArrayList<Personne>();
		Personne res = null;
		try {
			personneTab = JavaSQLPersonne.affiche();

			for (int i = 0; i < personneTab.size(); i++) {
				if (this.idEnvoyeur ==(personneTab.get(i).getId())) {
					res =  personneTab.get(i);

				}
								}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	
	public Personne getPersonneReceveur() {
		ArrayList<Personne> personneTab = new ArrayList<Personne>();
		Personne res = null;
		try {
			personneTab = JavaSQLPersonne.affiche();

			for (int i = 0; i < personneTab.size(); i++) {
				if (this.idReceveur ==(personneTab.get(i).getId())) {
					res =  personneTab.get(i);

				}
								}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}
