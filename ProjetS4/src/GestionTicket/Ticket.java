package GestionTicket;

public class Ticket {
	private int id;
	private String sujet;
	private String message;
	private String modif;
	private int idEnvoyeur;
	private int idReceveur;

	public Ticket(int id, String sujet,String message, String modif, int idEnvoyeur, int idReceveur) {
		this.id = id;
		this.sujet = sujet;
		this.message = message;
		this.modif = "PAS ENCORE FAIT";
		this.idEnvoyeur = idEnvoyeur;
		this.idReceveur = idReceveur;
	}
	
	public int getId() {
		return this.id;
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

}
