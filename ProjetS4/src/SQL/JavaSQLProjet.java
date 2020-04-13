package SQL;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import Model.Activite;
import Model.Projet;

public class JavaSQLProjet extends JavaSQL{
	private String nom;
	private int priorite;
	private LocalDate deadline;
	private String couleur;
	private int numSalarie;
	
	public JavaSQLProjet (String nom, int priorite, LocalDate deadline, String couleur, int numSalarie) {
		super();
		this.nom = nom;
		this.priorite = priorite;
		this.deadline = deadline;
		this.couleur = couleur;
		this.numSalarie = numSalarie;
	}
	
	public JavaSQLProjet () {
		super();
	}
	
	
	public void connection() {
		super.connection();
	}
	
	public void creation() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS Projet(idP INT PRIMARY KEY, nom VARCHAR(30), priorite INT, deadline DATE, couleur VARCHAR(30), numSalarie INT,"
				+ "CONSTRAINT fk_Projet_numSalarie FOREIGN KEY(numSalarie) REFERENCES Personne(numSalarie) );";
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
	
	@SuppressWarnings("deprecation")
	public ArrayList<Projet> affiche() throws SQLException{
		ArrayList<Projet> protab = new ArrayList<Projet>();
		String sql = "SELECT * FROM Projet;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 Date deadl = res.getDate("deadline");
						 protab.add(new Projet(/*ajouter une personnes (chef de projet)*/null, res.getString("nom"), res.getFloat("priorite"), LocalDate.of(deadl.getYear(), deadl.getMonth(), deadl.getDay()), res.getInt("idP"), new Color(res.getInt("couleur"))));
						 System.out.println("idP = " + res.getString("idP") +"nom = " + res.getString("nom") + ", priorite = " + res.getString("priorite") + ", deadline = " + res.getString("deadline") + ", couleur = " + res.getString("couleur") + ", numSalarie = " + res.getString("numSalarie"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return protab;

	}
	
	public void insertion() throws SQLException{
		String sql = "INSERT INTO Projet(idP, nom, priorite, deadline, couleur, numSalarie) VALUE(NULL,'" + this.nom + "' ,  '"+this.priorite+"' ,'"+this.deadline+"' , '"+this.couleur+"' , '"+this.numSalarie+"');";
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
		return "nom : " + this.nom +this.priorite+this.deadline+this.couleur+this.numSalarie; 
	}

}
