package GestionTicket;

public class Ticket {
	private int id;
	private String sujet;
	private String message;
	private String modif;

	public Ticket(int id, String sujet,String message, String modif) {
		this.id = id;
		this.sujet = sujet;
		this.message = message;
		this.modif = "PAS ENCORE FAIT";
	}
	
	public int getId() {
		return this.id;
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
