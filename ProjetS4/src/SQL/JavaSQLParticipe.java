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
	
	
	
	public static void affiche() throws SQLException{
		String sql = "SELECT * FROM Participe;";
			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("numSalarie = " + res.getString("numSalarie") + ", code = " + res.getString("code") + ", numero = " + res.getString("numero") + ", idA = " + res.getString("idA"));
					 }
				 }
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	public static void insertion(int numSalarie, int code, int numero, int idA) throws SQLException{
		String sql = "INSERT INTO Participes(numSalarie, code, numero, idA) VALUE('" + numSalarie+ "' ,  '"+code+"' ,  '"+numero+"' ,  '"+idA+"');";
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
			 String sql = "DELETE FROM Participe WHERE idA ='" + idA + "' AND numSalarie = '" + numSalarie + "' OR idA ='" + idA + "' AND code = '" + code + "' OR idA ='" + idA + "' AND numero = '" + numero+"';";
			 Statement stmt = getCon().createStatement();
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
