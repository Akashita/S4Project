package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Ressource.Materiel;
import Ressource.Personne;

public final class JavaSQLMateriel extends JavaSQL{

//	private int numSerie;
//	private String type;
//	private int numero;
//	
//	public JavaSQLMateriel (int numSerie, String type,int numero ) {
//		super();
//		this.numSerie = numSerie;
//		this.type= type;
//		this.numero= numero;
//	}
	
	
	public static void connection() {
		JavaSQL.connection();
	}
	
	
	
	public static  ArrayList<Materiel> affiche() throws SQLException{
		
        ArrayList<Materiel> materielTab = new ArrayList<Materiel>();
		String sql = "SELECT * FROM Materiel;";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 materielTab.add(new Materiel(res.getInt("numSerie"), res.getString("type"), res.getInt("numero")));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return materielTab;

	}
	
	public static void insertion(String type, int numero) throws SQLException{
		String sql = "INSERT INTO Materiel(type, numero) VALUE('"+type+"' ,  '"+numero+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	 public static void supprime(int numSerie) throws SQLException{
			try{
				 String sql = "DELETE FROM Materiel WHERE numSerie =" + numSerie;
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
			} catch(SQLException e){
				e.printStackTrace();
			}
	 }
	 
	public static void modifie(int numSerie, String type, int numero) throws SQLException{
		try{
			 Statement stmt = getCon().createStatement();
			 String sql = "UPDATE Materiel SET type  = '" + type + "' ,numero = '" + numero + "' WHERE numSerie= '"+ numSerie+"';";
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static String toString(int numSerie, String type, int numero) {
		return "nom : " + numSerie+type+numero; 
	}

}
