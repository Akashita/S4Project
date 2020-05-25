package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Ressource.Calculateur;

public final class JavaSQLCalculateur extends JavaSQL{
	
//	private int code;
//	private String nom;
//	private int capacite;
//	
//	public JavaSQLCalculateur (int code, String nom, int capacite) {
//		super();
//		this.code= code;
//		this.nom = nom;
//		this.capacite = capacite;
//	}
//	
//	public JavaSQLCalculateur () {
//		super();
//	}
	
	
	public static void connection() {
		JavaSQL.connection();
	}
	
	
	public static ArrayList<Calculateur> affiche() throws SQLException{
		String sql = "SELECT * FROM Calculateur;";
		ArrayList<Calculateur> calcultab = new ArrayList<Calculateur>();
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 calcultab.add(new Calculateur(res.getInt("code"),res.getString("nom"),res.getInt("capacite")));
						 System.out.println("code = " + res.getString("code") + ", nom= " + res.getString("nom") + ", capacite= " + res.getString("capacite"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return calcultab;

	}
	
	public static void insertion(String nom, int capacite) throws SQLException{
		String sql = "INSERT INTO Calculateur( nom, capacite) VALUE('"+nom+"' ,  '"+capacite+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	public static void supprime(int code) throws SQLException{
		try{
			 String sql = "DELETE FROM ParticipeCalcul WHERE code =" + code;
			 Statement stmt = getCon().createStatement();
			 stmt.executeUpdate(sql);
			 sql = "DELETE FROM Calculateur WHERE code =" + code;
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void modifie(int code, String nom, int capacite) throws SQLException{
		try{
			 Statement stmt = getCon().createStatement();
			 String sql = "UPDATE Calculateur SET nom= '" + nom + "' ,capacite  = '" + capacite + "' WHERE code= '"+ code+"';";
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static String toString(int code, String nom, int capacite) {
		return "nom : " + code+nom+capacite; 
	}

}
