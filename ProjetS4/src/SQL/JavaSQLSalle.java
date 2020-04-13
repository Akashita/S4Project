package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Ressource.Salle;

public class JavaSQLSalle extends JavaSQL{
	private int numero;
	private String nom;
	private int place;
	
	public JavaSQLSalle (int numero, String nom, int place) {
		super();
		this.numero= numero;
		this.nom = nom;
		this.place= place;
	}
	public JavaSQLSalle () {
		super();
	}
	
	
	public void connection() {
		super.connection();
	}
	
	public void creation() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS Salle(numero INT PRIMARY KEY, nom VARCHAR(30), place INT NOT NULL);";
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
	
	public ArrayList<Salle> affiche() throws SQLException{
		ArrayList<Salle> salletab = new ArrayList<Salle>();
		String sql = "SELECT * FROM place;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 salletab.add(new Salle(res.getInt("numero"),res.getString("nom"),res.getInt("place")));
						 System.out.println("numero = " + res.getString("numero") + ", nom = " + res.getString("nom") + ", place = " + res.getString("place"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return salletab;

	}
	
	public void insertion() throws SQLException{
		String sql = "INSERT INTO Salle(numero, nom, place) VALUE('" + this.numero+ "' ,  '"+this.nom+"' ,  '"+this.place+"');";
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
		return "nom : " + this.numero+this.nom+this.place; 
	}

}
