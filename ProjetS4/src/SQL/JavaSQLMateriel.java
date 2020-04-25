package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	
	
	public static void affiche() throws SQLException{
		String sql = "SELECT * FROM Materiel;";
			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("numSerie= " + res.getString("numSerie") + ", type= " + res.getString("type") + ", numero= " + res.getString("numero"));
					 }
				 }
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	public static void insertion(int numSerie, int type, int numero) throws SQLException{
		String sql = "INSERT INTO Materiel(numSerie, type, numero) VALUE('" + numSerie+ "' ,  '"+type+"' ,  '"+numero+"');";
			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	public static void modifie(int numSerie, int nouvNumSerie, int type, int numero) throws SQLException{
		try{
			 connection();
			 Statement stmt = getCon().createStatement();
			 String sql = "UPDATE Materiel SET numSerie= " + nouvNumSerie + " type  = " + type + " numero = " + numero + " WHERE numSerie= "+ numSerie;
			 stmt.executeUpdate(sql);
			 con.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static String toString(int numSerie, int type, int numero) {
		return "nom : " + numSerie+type+numero; 
	}

}
