package GestionTicket;

import java.time.LocalDate;

import Model.Projet;
import Ressource.Personne;
import Ressource.Ressource;

public class Ticket {
	private int id;
	private String sujet;
	private String message;
	private String modif;
	private LocalDate dateTicket;
	private int statut;
	private int idEnvoyeur;
	private int idReceveur;

	
	public static final int MESSAGE = 0, LIBERE= 1, TRANSFERT = 2,  REFUSE = 3,   ACCEPTEE= 4,  ENCOURS= 5;

	
	public Ticket(int id, String sujet,String message, String modif,LocalDate dateTicket, int statut, int idEnvoyeur, int idReceveur) {
		this.id = id;
		this.sujet = sujet;
		this.message = message;
		this.modif = modif;
		this.dateTicket = dateTicket;
		this.statut = statut;
		this.idEnvoyeur = idEnvoyeur;
		this.idReceveur = idReceveur;
	}
	
	
//	public Ticket(int id, int action, String sujet , Personne expediteur, Personne receveur, LocalDate dateCreation) {
//		
//	}
//	
//	public Ticket(int id, int action, Ressource r, Projet p, String sujet, Personne expediteur, Personne receveur, LocalDate dateCreation) {
//		super(id, action, sujet, expediteur, receveur, dateCreation);
//		
//	}
//	
//	public Ticket(int id, int action, Ressource r, LocalDate debut, LocalDate fin, String sujet, Personne expediteur, Personne receveur, LocalDate dateCreation) {
//		super(id, action, sujet, expediteur, receveur, dateCreation);
//		
//	}
	
	
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
	
	public String toString() {
		return "ticket n' = " + this.id + ", sujet = " + this.sujet + ", message = " + this.message + ", modification = " + this.modif + ",statut" + this.getStringStatut() + 
				", envoyeur = " + this.idEnvoyeur + ", receveur = " + this.idReceveur + ", cree le : " + dateTicket.toString();
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
}
