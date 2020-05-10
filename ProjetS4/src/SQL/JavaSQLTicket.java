package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaSQLTicket extends JavaSQL{
	
	
	public static void connection() {
		JavaSQL.connection();
	}
	
	
	
	public static void affiche() throws SQLException{
		String sql = "SELECT * FROM Ticket;";
			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("idT = " + res.getString("idT") + ", sujet = " + res.getString("sujet") + ", message = " + res.getString("message") + ", modif = " + res.getString("modif"));
					 }
				 }
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	public static void insertion(String sujet ,String message ,String modif ,int numSalarieEnv ,int numSalarieRec) throws SQLException{
		String sql = "INSERT INTO Ticket(sujet, message, modif, numSalarieEnv, numSalarieRec) VALUE('" + sujet+ "' ,  '"+message+"' ,  '"+modif+"' ,  '"+numSalarieEnv+"' ,  '"+numSalarieRec+"');";
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
	
	 public static void supprime(int idT) throws SQLException{
			try{
				 connection();
				 String sql = "DELETE FROM Ticket WHERE idT =" + idT;
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	 }
	
	public static String toString(String sujet ,String message ,String modif ,int numSalarieEnv ,int numSalarieRec) {
		return "nom : " + sujet+message+modif+numSalarieEnv+numSalarieRec; 
	}

}
