package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JavaSQLDebug extends JavaSQL{
	
	public JavaSQLDebug () {
		super();
	}
	

	public static void connection() {
		JavaSQL.connection();
	}
	
	public static void drop() throws SQLException{
		String sql = "DROP TABLE IF EXISTS Competence";
		try{

			 Statement stmt = getCon().createStatement();
			 stmt.executeUpdate(sql);
			 System.out.println("drop Competence fait");
			 
			 sql = "DROP TABLE IF EXISTS ParticipeSalarie";
			 stmt.executeUpdate(sql);
			 System.out.println("drop ParticipeSalarie fait");

			 sql = "DROP TABLE IF EXISTS ParticipeCalcul";
			 stmt.executeUpdate(sql);
			 System.out.println("drop ParticipeCalcul fait");

			 sql = "DROP TABLE IF EXISTS ParticipeSalle";
			 stmt.executeUpdate(sql);
			 System.out.println("drop ParticipeSalle fait");
			 
			 sql = "DROP TABLE IF EXISTS ListeDomaine";
			 stmt.executeUpdate(sql);
			 System.out.println("drop ListeDomaine fait");
			 
			 sql = "DROP TABLE IF EXISTS Creneaux";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Creneaux fait");
			 
			 sql = "DROP TABLE IF EXISTS Activite";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Activite fait");
			 
			 sql = "DROP TABLE IF EXISTS Projet";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Projet fait");
			 
			 sql = "DROP TABLE IF EXISTS Materiel";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Materiel fait");
			 
			 sql = "DROP TABLE IF EXISTS Salle";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Salle fait");
			 
			 sql = "DROP TABLE IF EXISTS Calculateur";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Calculateur fait");
			 
			 sql = "DROP TABLE IF EXISTS Ticket";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Ticket fait");
			 
			 sql = "DROP TABLE IF EXISTS Personne";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Personne fait");
			 
			 sql = "DROP TABLE IF EXISTS Domaine";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Domaine fait");
			 
			 con.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void affiche() throws SQLException{
		String sql = "SELECT TABLE_NAME\r\n" +
				"FROM   INFORMATION_SCHEMA.TABLES\r\n" +
				"WHERE Table_Type='BASE TABLE'";
			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println(res.getString(1));
					 }
				 }
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}

}
