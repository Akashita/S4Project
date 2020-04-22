package SQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

import Ressource.Personne;

public class JavaSQLPersonne extends JavaSQL{
	private int numSalarie;
	private String nom;
	private String prenom;
	private String role;
	private String motDePasse;
	private ArrayList<String> tag;
	private ArrayList<Integer> niveau;

	public JavaSQLPersonne (int numSalarie, String nom, String prenom, String role, String motDePasse, ArrayList<String> tag, ArrayList<Integer> niveau) {
		super();
		this.numSalarie = numSalarie;
		this.nom = nom;
		this.prenom = prenom;
		this.role = role;
		this.motDePasse = motDePasse;
		this.tag = tag;
		this.niveau = niveau;
	}
	
	public JavaSQLPersonne () {
		super();
	}


	public void connection() {
		super.connection();
	}

	

	public ArrayList<Personne> affiche() throws SQLException{
		String sql = "SELECT * FROM Personne;";
		ArrayList<Personne> personnetab = new ArrayList<Personne>();

			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 Hashtable<String, String> tagtab = new Hashtable<String, String>();
						 String sqltag = "SELECT * FROM Competence WHERE numSalarie = " + res.getString("numSalarie") + ";";
						 Statement stmt2 = getCon().createStatement();
						 try (ResultSet res2 = stmt2.executeQuery(sqltag)){
							 while(res2.next()) {
								 tagtab.put(res2.getString("tag"), res2.getString("niveau"));
							 }
						 }
						 personnetab.add(new Personne(res.getString("nom"), res.getString("prenom"), res.getString("role"), res.getInt("numSalarie"), res.getString("motDePasse"), tagtab));
						 
						 
						 System.out.println("numSalarie = " + res.getString("numSalarie") + ", nom = " +
						  res.getString("nom") + ", prenom = " + res.getString("prenom") + ", role = " +
							 res.getString("role") + ", motDePasse = " + res.getString("motDePasse"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return personnetab;

	}

	public void insertion() throws SQLException{
			String sql = "INSERT INTO Personne(numSalarie, nom, prenom, role, motDePasse) VALUE(NULL, '" + this.nom + "' ,  '"+this.prenom+"' , '"+this.role+"' , '"+this.motDePasse+"');";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			String sql2 = "SELECT * FROM Personne ORDER BY numSalarie DESC;";
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
	
	public void supprime() throws SQLException{
		try{
			 this.connection();
			 Statement stmt = getCon().createStatement();
			 String sql = "DELETE FROM Creneaux WHERE numSalarie ="+ this.numSalarie;
			 stmt.executeUpdate(sql);					 
			 sql = "DELETE FROM Participe WHERE numSalarie =" + this.numSalarie ;
			 stmt.executeUpdate(sql);
			 sql = "DELETE FROM Competence WHERE numSalarie =" + this.numSalarie ;
			 stmt.executeUpdate(sql);
			 sql = "DELETE FROM Personne WHERE numSalarie =" + this.numSalarie ;
			 stmt.executeUpdate(sql);
			 this.con.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	public String toString() {
		return "nom : " + this.nom +this.prenom+this.role+this.motDePasse+this.tag+this.niveau;
	}

}
