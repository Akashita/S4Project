package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

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
		String sql = "CREATE TABLE IF NOT EXISTS Projet(nom VARCHAR(30) PRIMARY KEY, priorite INT, deadline DATE, couleur VARCHAR(30), numSalarie INT,"
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
	
	public ArrayList<Projet> affiche() throws SQLException{
		ArrayList<Projet> protab = new ArrayList<Projet>();
		String sql = "SELECT * FROM Projet;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 protab.add(new Projet(res.getString("nom"),res.getInt("priorite"),res.getDate("deadline"),res.getString("couleur"),res.getInt("numSalarie")));
						 System.out.println("nom = " + res.getString("nom") + ", priorite = " + res.getString("priorite") + ", deadline = " + res.getString("deadline") + ", couleur = " + res.getString("couleur") + ", numSalarie = " + res.getString("numSalarie"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return protab;

	}
	
	public void insertion() throws SQLException{
		String sql = "INSERT INTO Projet(nom, priorite, deadline, couleur, numSalarie) VALUE('" + this.nom + "' ,  '"+this.priorite+"' ,'"+this.deadline+"' , '"+this.couleur+"' , '"+this.numSalarie+"');";
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
