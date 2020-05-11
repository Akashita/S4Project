package SQL;

import java.sql.*;
import java.util.ArrayList;

import Ressource.Competence;
import Ressource.Personne;

public final class JavaSQLPersonne extends JavaSQL{
//	private int numSalarie;
//	private String nom;
//	private String prenom;
//	private String role;
//	private String motDePasse;
//	private ArrayList<String> tag;
//	private ArrayList<Integer> niveau;
//
//	public JavaSQLPersonne (int numSalarie, String nom, String prenom, String role, String motDePasse, ArrayList<String> tag, ArrayList<Integer> niveau) {
//		super();
//		this.numSalarie = numSalarie;
//		this.nom = nom;
//		this.prenom = prenom;
//		this.role = role;
//		this.motDePasse = motDePasse;
//		this.tag = tag;
//		this.niveau = niveau;
//	}
//	
//	public JavaSQLPersonne () {
//		super();
//	}


	public static void connection() {
		JavaSQL.connection();
	}

	

	public static ArrayList<Personne> affiche() throws SQLException{
		String sql = "SELECT * FROM Personne;";
		ArrayList<Personne> personnetab = new ArrayList<Personne>();

			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 ArrayList<Competence> tagtab = new ArrayList<Competence>();
						 String sqltag = "SELECT * FROM Competence WHERE numSalarie = " + res.getString("numSalarie") + ";";
						 Statement stmt2 = getCon().createStatement();
						 try (ResultSet res2 = stmt2.executeQuery(sqltag)){
							 while(res2.next()) {
								 tagtab.add(new Competence(res2.getString("tag"), res2.getInt("niveau")));
							 }
						 }
						 personnetab.add(new Personne(res.getString("nom"), res.getString("prenom"), res.getString("role"), res.getInt("numSalarie"), res.getString("motDePasse"), tagtab));
						 
						 
						 System.out.println("numSalarie = " + res.getString("numSalarie") + ", nom = " +
						  res.getString("nom") + ", prenom = " + res.getString("prenom") + ", role = " +
							 res.getString("role") + ", motDePasse = " + res.getString("motDePasse"));
					 }
				 }
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return personnetab;

	}

	public static void insertion(String nom, String prenom, String role, String motDePasse, ArrayList<String> tag, ArrayList<Integer> niveau) throws SQLException{
			String sql = "INSERT INTO Personne(numSalarie, nom, prenom, role, motDePasse) VALUE(NULL, '" + nom + "' ,  '"+prenom+"' , '"+role+"' , '"+motDePasse+"');";
			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			String sql2 = "SELECT * FROM Personne ORDER BY numSalarie DESC;";
			try{
				 connection();
				 Statement stmt2 = getCon().createStatement();
				 try (ResultSet res2 = stmt2.executeQuery(sql2)){
					 res2.next();
					 for (int i=0; i<tag.size(); i++){
						 System.out.println(res2.getInt("numSalarie") + " " + tag.get(i) + " " + niveau.get(i));
						 JavaSQLCompetence.insertion(res2.getInt("numSalarie"),tag.get(i), niveau.get(i));
					 }
				 }
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	public static void supprime(int numSalarie) throws SQLException{
		try{
			 connection();
			 Statement stmt = getCon().createStatement();
			 String sql = "DELETE FROM Creneaux WHERE numSalarie ="+ numSalarie;
			 stmt.executeUpdate(sql);					 
			 sql = "DELETE FROM Participe WHERE numSalarie =" + numSalarie ;
			 stmt.executeUpdate(sql);
			 sql = "DELETE FROM Competence WHERE numSalarie =" + numSalarie ;
			 stmt.executeUpdate(sql);
			 sql = "DELETE FROM Personne WHERE numSalarie =" + numSalarie ;
			 stmt.executeUpdate(sql);
			 con.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void modifie(int numSalarie, String nom, String prenom, String role, String motDePasse, ArrayList<String> tag, ArrayList<Integer> niveau) throws SQLException{
		try{
			 connection();
			 Statement stmt = getCon().createStatement();
			 String sql = "UPDATE Personne SET nom = '" + nom + "' ,prenom  = '" + prenom + "' ,role = '" + role + "' ,motDePasse = '" + motDePasse + "' WHERE numSalarie = '"+ numSalarie + "';";
			 stmt.executeUpdate(sql);
			 for (int i=0; i<tag.size(); i++){
				 sql  = "UPDATE Competence SET tag = " + tag.get(i) + " ,niveau  = " + niveau.get(i)+ "  WHERE numSalarie = "+ numSalarie;
				 stmt.executeUpdate(sql);
			 }
			 con.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	

	public static String toString(String nom, String prenom, String role, String motDePasse, ArrayList<String> tag, ArrayList<Integer> niveau) {
		return "nom : " + nom +prenom+role+motDePasse+tag+niveau;
	}

}
