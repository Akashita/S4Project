package SQL;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public final class JavaSQLConge extends JavaSQL{
	
	public static void connection() {
		JavaSQL.connection();
	}
	
	
	
	public static void affiche() throws SQLException{
		String sql = "SELECT * FROM Conge;";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("idC = " + res.getString("idC") + ", date = " + res.getString("date") + ", numSalarie = " + res.getString("numSalarie"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	public static void insertion(LocalDate date, int numSalarie) throws SQLException{
		Date date1 = Date.valueOf(date);
		String sql = "INSERT INTO Conge(idC, date, numsalarie) VALUE(NULL, '" +date1+"' , '"+numSalarie+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	public static void supprime(int idC) throws SQLException{
		try{
			 String sql = "DELETE FROM Conge WHERE idC =" + idC;
			 Statement stmt = getCon().createStatement();
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void modifie(int idC, LocalDate date, int numSalarie) throws SQLException{
		Date date1 = Date.valueOf(date);
		try{
			 Statement stmt = getCon().createStatement();
			 String sql = "UPDATE Conge SET date = '"+date1+"', numSalarie = '"+numSalarie+"' WHERE idC = '"+idC+"';";
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	

}
