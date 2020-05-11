package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JavaSQLParticipe extends JavaSQL{
//	private int numSalarie;
//	private int code;
//	private int numero;
//	private int idA;
//	
//	public JavaSQLParticipe (int numSalarie, int code, int numero, int idA) {
//		super();
//		this.numero= numero;
//		this.numSalarie = numSalarie;
//		this.code = code;
//		this.idA = idA;
//	}
//	
	
	public static void connection() {
		JavaSQL.connection();
	}
	
	
	
//	public static void affiche() throws SQLException{
//		String sql = "SELECT * FROM Participe;";
//			try{
//				 connection();
//				 Statement stmt = getCon().createStatement();
//				 try (ResultSet res = stmt.executeQuery(sql)){
//					 while(res.next()) {
//						 System.out.println("numSalarie = " + res.getString("numSalarie") + ", code = " + res.getString("code") + ", numero = " + res.getString("numero") + ", idA = " + res.getString("idA"));
//					 }
//				 }
//				 con.close();
//			} catch(SQLException e){
//				e.printStackTrace();
//			}
//
//	}
	
	public static void insertionSalarie(int numSalarie, int idA) throws SQLException{
		String sql = "INSERT INTO ParticipesSalarie(numSalarie,idA) VALUE('" + numSalarie+ "' , '"+idA+"');";
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
	
	public static void insertionSalle(int numero, int idA) throws SQLException{
		String sql = "INSERT INTO ParticipesSalle(numSalarie, code, numero, idA) VALUE('"+numero+"' ,  '"+idA+"');";
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
	
	public static void insertionCalcul(int code, int idA) throws SQLException{
		String sql = "INSERT INTO ParticipeCalcul(numSalarie, code, numero, idA) VALUE('"+code+"' , '"+idA+"');";
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
	
	public static void supprime(int numSalarie, int code, int numero, int idA) throws SQLException{
		try{
			 connection();
			 String sql = "DELETE FROM ParticipeSalarie WHERE idA ='" + idA + "' AND numSalarie = '" + numSalarie + "';";
			 Statement stmt = getCon().createStatement();
			 stmt.executeUpdate(sql);
			 
			 sql = "DELETE FROM ParticipeCalcul WHERE idA ='" + idA + "' AND code = '" + code + "';";
			 stmt = getCon().createStatement();
			 stmt.executeUpdate(sql);
			 
			 sql = "DELETE FROM ParticipeSalle WHERE idA ='" + idA + "' AND numero = '" + numero+"';";
			 stmt = getCon().createStatement();
			 stmt.executeUpdate(sql);
			 
			 con.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static String toString(int numSalarie, int code, int numero, int idA) {
		return "nom : " + numSalarie+code+numero+idA; 
	}

}
