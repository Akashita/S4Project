package SQL;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class JavaSQLReunion extends JavaSQL{
	
	public static void connection() {
		JavaSQL.connection();
	}
	
	
	
	public static void affiche() throws SQLException{
		String sql = "SELECT * FROM Reunion;";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("idR = " + res.getString("idR") + ", date = " + res.getString("date") + ", debut = " + res.getString("debut") + ", titre = " + res.getString("titre") + ", idA = " + res.getString("idA"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	public static void insertion(int debut, LocalDate date, String titre, int idA) throws SQLException{
		Date date1 = Date.valueOf(date);
		String sql = "INSERT INTO Reunion(idR, debut, date, titre, idA) VALUE(NULL, '" +debut+"' , '"+date1+"' , '"+titre+"' , '" +idA+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	public static void supprime(int idR) throws SQLException{
		try{
			 String sql = "DELETE FROM Reunion WHERE idR =" + idR;
			 Statement stmt = getCon().createStatement();
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void modifie(int idR, int debut, LocalDate date, String titre, int idA) throws SQLException{
		Date date1 = Date.valueOf(date);
		try{
			 Statement stmt = getCon().createStatement();
			 String sql = "UPDATE Reunion SET date = '"+date1+"', debut = '"+debut+"', titre = '" + titre+"' WHERE idR = '"+idR+"';";
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}

}
