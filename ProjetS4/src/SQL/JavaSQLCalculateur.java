package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Ressource.Calculateur;
import Ressource.Personne;

public class JavaSQLCalculateur extends JavaSQL{
	
	private int code;
	private String nom;
	private int capacite;
	
	public JavaSQLCalculateur (int code, String nom, int capacite) {
		super();
		this.code= code;
		this.nom = nom;
		this.capacite = capacite;
	}
	
	public JavaSQLCalculateur () {
		super();
	}
	
	
	public void connection() {
		super.connection();
	}
	
	public void creation() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS Calculateur(code INT PRIMARY KEY, nom VARCHAR(30), capacite INT NOT NULL);";
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
	
	public ArrayList<Calculateur> affiche() throws SQLException{
		String sql = "SELECT * FROM Calculateur;";
		ArrayList<Calculateur> calcultab = new ArrayList<Calculateur>();
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 calcultab.add(new Calculateur(res.getInt("code"),res.getString("nom"),res.getInt("capacite")));
						 System.out.println("code = " + res.getString("code") + ", nom= " + res.getString("nom") + ", capacite= " + res.getString("capacite"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return calcultab;

	}
	
	public void insertion() throws SQLException{
		String sql = "INSERT INTO Calculateur(code, nom, capacite) VALUE('" + this.code + "' ,  '"+this.nom+"' ,  '"+this.capacite+"');";
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
		return "nom : " + this.code+this.nom+this.capacite; 
	}

}
