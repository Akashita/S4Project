package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaSQLDebug extends JavaSQL{
	
	public JavaSQLDebug () {
		super();
	}
	

	public void connection() {
		super.connection();
	}
	
	public void drop() throws SQLException{
		String sql = "DROP TABLE IF EXISTS Competence";
		try{
			 this.connection();
			 Statement stmt = getCon().createStatement();
			 stmt.executeUpdate(sql);
			 System.out.println("drop Competence fait");
			 
			 sql = "DROP TABLE IF EXISTS Participe";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Participe fait");
			 
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
			 
			 sql = "DROP TABLE IF EXISTS Personne";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Personne fait");
			 
			 sql = "DROP TABLE IF EXISTS Domaine";
			 stmt.executeUpdate(sql);
			 System.out.println("drop Domaine fait");
			 
			 this.con.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void affiche() throws SQLException{
		String sql = "SELECT TABLE_NAME\r\n" +
				"FROM   INFORMATION_SCHEMA.TABLES\r\n" +
				"WHERE Table_Type='BASE TABLE'";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println(res.getString(1));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}

}
