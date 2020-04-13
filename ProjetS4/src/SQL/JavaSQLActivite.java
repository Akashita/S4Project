package SQL;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import Model.Activite;
import Ressource.Salle;

public class JavaSQLActivite extends JavaSQL{
	private String titre;
	private LocalDate debut;
	private Double charge;
	private int ordre;
	private String couleur;
	private int idC;
	private String nom;
	
	public JavaSQLActivite (String titre, LocalDate debut, Double charge, int ordre, String couleur, int idC, String nom) {
		
		super();
		this.titre = titre;
		this.debut = debut;
		this.charge = charge;
		this.ordre = ordre;
		this.couleur = couleur;
		this.idC= idC;
		this.nom= nom;
	}
	public JavaSQLActivite () {
		
		super();
	}
	
	
	public void connection() {
		super.connection();
	}
	
	public void creation() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS Activite(idA INT PRIMARY KEY AUTO_INCREMENT, titre VARCHAR(30), debut DATE, charge DECIMAL(4,2), ordre INT, couleur VARCHAR(30), indC INT, nom VARCHAR(30), "
				+ "CONTRAINT fk_Activite_idC FOREIGN KEY(idC) REFERENCES Creneaux(idC),"
				+ "CONTRAINT fk_Activite_nom FOREIGN KEY(nom) REFERENCES Projet(nom));";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("creation fait");
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	public ArrayList<Activite> affiche() throws SQLException{
		ArrayList<Activite> acttab = new ArrayList<Activite>();
		String sql = "SELECT * FROM Activite;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 acttab.add(new Activite(res.getInt("idA"),res.getString("titre"), res.getDouble("charge"), res.getDate("debut"), res.getInt("couleur") /*COULEUR A CHANGER PAR UN INT*/, res.getInt("ordre") ,res.getInt("nom") /*A CHANGER POUR L'ID DU PROJET*/));
						 System.out.println("idA = " + res.getString("idA") + ", titre = " + res.getString("titre") + ", debut = " + res.getString("debut") + ", charge = " + res.getString("charge") 
						 + ", ordre = " + res.getString("ordre")+ ", couleur = " + res.getString("couleur") + ", idC = " + res.getString("idC") + ", nom = " + res.getString("nom"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return acttab;

	}
	
	public void insertion() throws SQLException{
		String sql = "INSERT INTO Activite(idA, titre, debut, charge, ordre, couleur, idC, nom) VALUE(NULL, '" + this.titre + "' ,  '"+this.debut+"' ,'"+this.charge+"' , '"+this.ordre+"' , '"+this.couleur+"' , '"+this.idC+"' , '"+this.nom+"');";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	public String toString() {
		return "nom : " + this.titre +this.debut+this.charge+this.ordre+this.couleur+this.idC+this.nom; 
	}

}