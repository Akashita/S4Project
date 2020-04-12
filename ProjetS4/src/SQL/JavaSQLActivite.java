package SQL;

import java.sql.*;

public class JavaSQLActivite extends JavaSQL{
	private String titre;
	private Date debut;
	private Double charge;
	private Integer ordre;
	private String nom;
	
	public JavaSQLActivite (String titre, Date debut, Double charge, Integer ordre, String nom) {
		super();
		this.titre = titre;
		this.debut = debut;
		this.charge = charge;
		this.ordre = ordre;
		this.nom = nom;
	}
	
	
	public void connection() {
		super.connection();
	}
	
	//----------------------------------------------------------------test---------------------------------------------------------------
	
	//----------------------------------------------------------------fin test---------------------------------------------------------------
//	public void insertion (String titre, Date debut, Double charge, Integer ordre, String nom) throws SQLException {
//		try (this.connection()){
//			
//		} 
//	}
}