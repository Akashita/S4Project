package SQL;

import java.sql.*;
import java.util.ArrayList;

public class JavaSQLPersonne extends JavaSQL{
//	private int numSalarie;
	private String nom;
	private String prenom;
	private String role;
	private String motDePasse;
	private ArrayList<String> tag;
	private ArrayList<Integer> niveau;

	public JavaSQLPersonne (String nom, String prenom, String role, String motDePasse, ArrayList<String> tag, ArrayList<Integer> niveau) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.role = role;
		this.motDePasse = motDePasse;
		this.tag = tag;
		this.niveau = niveau;
	}


	public void connection() {
		super.connection();
	}
	
	public void creation() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS Personne3(numSalarie INT PRIMARY KEY AUTO_INCREMENT,nom VARCHAR(30),prenom VARCHAR(30),role VARCHAR(30),motDePasse VARCHAR(30));";
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
	

	public void affiche() throws SQLException{
		String sql = "SELECT * FROM Personne3;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("numSalarie = " + res.getString("numSalarie") + ", nom = " + res.getString("nom") + ", prenom = " + res.getString("prenom") + ", role = " + res.getString("role") + ", motDePasse = " + res.getString("motDePasse"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}

	public void insertion() throws SQLException{
			String sql = "INSERT INTO Personne3(numSalarie, nom, prenom, role, motDePasse) VALUE(NULL, '" + this.nom + "' ,  '"+this.prenom+"' , '"+this.role+"' , '"+this.motDePasse+"');";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			String sql2 = "SELECT * FROM Personne3 ORDER BY numSalarie DESC;";
			try{
				 this.connection();
				 Statement stmt2 = getCon().createStatement();
				 try (ResultSet res2 = stmt2.executeQuery(sql2)){
					 res2.next();
					 for (int i=0; i<tag.size(); i++){
						 System.out.println(res2.getInt("numSalarie") + " " + this.tag.get(i) + " " + this.niveau.get(i));
						 JavaSQLCompetence jsc = new JavaSQLCompetence(res2.getInt("numSalarie"),this.tag.get(i), this.niveau.get(i));
						 jsc.insertion();
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	}

	public String toString() {
		return "nom : " + this.nom +this.prenom+this.role+this.motDePasse+this.tag+this.niveau;
	}

}
