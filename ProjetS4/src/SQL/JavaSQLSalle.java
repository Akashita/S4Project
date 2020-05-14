package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Ressource.Salle;

public final class JavaSQLSalle extends JavaSQL{
//	private int numero;
//	private String nom;
//	private int place;
//	
//	public JavaSQLSalle (int numero, String nom, int place) {
//		super();
//		this.numero= numero;
//		this.nom = nom;
//		this.place= place;
//	}
//	public JavaSQLSalle () {
//		super();
//	}
	
	
	public static void connection() {
		JavaSQL.connection();
	}
	
	
	
	public static ArrayList<Salle> affiche() throws SQLException{
		ArrayList<Salle> salletab = new ArrayList<Salle>();
		String sql = "SELECT * FROM Salle;";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 salletab.add(new Salle(res.getInt("numero"),res.getString("nom"),res.getInt("place")));
						 System.out.println("numero = " + res.getString("numero") + ", nom = " + res.getString("nom") + ", place = " + res.getString("place"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return salletab;

	}
	
	public static void insertion(int numero, String nom, int place) throws SQLException{
		String sql = "INSERT INTO Salle(numero, nom, place) VALUE('" + numero+ "' ,  '"+nom+"' ,  '"+place+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	 public static void supprime(int numero) throws SQLException{
			try{
				 String sql = "DELETE FROM Materiel WHERE numero =" + numero;
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 sql = "DELETE FROM Creneaux WHERE numero =" + numero;
				 stmt.executeUpdate(sql);
				 sql = "DELETE FROM ParticipeSalle WHERE numero =" + numero;
				 stmt.executeUpdate(sql);
				 sql = "DELETE FROM Salle WHERE numero =" + numero;
				 stmt.executeUpdate(sql);
			} catch(SQLException e){
				e.printStackTrace();
			}
	 }
	
	public static void modifie(int ancienNumero, int nouvNumero, String nom, int place) throws SQLException{
		try{
			 Statement stmt = getCon().createStatement();
			 String sql = "UPDATE Salle SET numero = '" + nouvNumero + "' ,nom  = '" + nom + "' ,place = '" + place + "' WHERE numero = '"+ ancienNumero+"';";
			 stmt.executeUpdate(sql);
			 sql = "UPDATE Materiel SET numero = '" + nouvNumero + "' WHERE numero = '"+ ancienNumero+"';";
			 stmt.executeUpdate(sql);
			 sql = "UPDATE Creneaux SET numero = '" + nouvNumero + "' WHERE numero = '"+ ancienNumero+"';";
			 stmt.executeUpdate(sql);
			 sql = "UPDATE ParticipeSalle SET numero = '" + nouvNumero + "' WHERE numero = '"+ ancienNumero+"';";
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static String toString(int numero, String nom, int place) {
		return "nom : " + numero+nom+place; 
	}

}
