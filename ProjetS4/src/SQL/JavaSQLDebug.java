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
			 sql = "DROP TABLE IF EXISTS Competance";
			 stmt.executeUpdate(sql);
			 sql = "DROP TABLE IF EXISTS Participation";
			 stmt.executeUpdate(sql);
			 sql = "DROP TABLE IF EXISTS Activite";
			 stmt.executeUpdate(sql);
			 sql = "DROP TABLE IF EXISTS Materiel";
			 stmt.executeUpdate(sql);
			 sql = "DROP TABLE IF EXISTS Projet";
			 stmt.executeUpdate(sql);
			 sql = "DROP TABLE IF EXISTS Creneaux";
			 stmt.executeUpdate(sql);
			 sql = "DROP TABLE IF EXISTS Calculateur";
			 stmt.executeUpdate(sql);
			 sql = "DROP TABLE IF EXISTS Domaine";
			 stmt.executeUpdate(sql);
			 sql = "DROP TABLE IF EXISTS Personne";
			 stmt.executeUpdate(sql);
			 sql = "DROP TABLE IF EXISTS Personne2";
			 stmt.executeUpdate(sql);
			 sql = "DROP TABLE IF EXISTS Personne";
			 stmt.executeUpdate(sql);
			 sql = "DROP TABLE IF EXISTS Salle";
			 stmt.executeUpdate(sql);
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
