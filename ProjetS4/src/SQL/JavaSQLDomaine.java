package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaSQLDomaine extends JavaSQL{

	private String tag;

	public JavaSQLDomaine (String tag) {
		super();
		this.tag = tag;
	}


	public void connection() {
		super.connection();
	}

	public void creation() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS Domaine(tag VARCHAR(30) PRIMARY KEY);";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("creation fait");
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}

	public void affiche() throws SQLException{
		String sql = "SELECT * FROM Domaine;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("tag = " + res.getString("tag"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}

	public void insertion() throws SQLException{
		String sql = "INSERT INTO Domaine(tag) VALUE('" + this.tag + "');";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	}

	public String toString() {
		return "nom : " + this.tag; 
	}

}
