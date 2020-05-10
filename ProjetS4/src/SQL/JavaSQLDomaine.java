package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public final class JavaSQLDomaine extends JavaSQL{

//	private String tag;
//
//	public JavaSQLDomaine (String tag) {
//		super();
//		this.tag = tag;
//	}
//
//	public JavaSQLDomaine () {
//		super();
//	}

	public static void connection() {
		JavaSQL.connection();
	}



	public static ArrayList<String> affiche() throws SQLException{
		String sql = "SELECT * FROM Domaine;";
		ArrayList<String> tagtab = new ArrayList<String>();
			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 tagtab.add(res.getString("tag"));
						 System.out.println("tag = " + res.getString("tag"));
					 }
				 }
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return tagtab;

	}

	public static void insertion(String tag) throws SQLException{
		String sql = "INSERT INTO Domaine(tag) VALUE('" + tag + "');";
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
	
	public static void supprime(String tag) throws SQLException{
		try{
			 connection();
			 Statement stmt = getCon().createStatement();
			 String sql = "DELETE FROM Competence WHERE tag = '" + tag + "'";
			 stmt.executeUpdate(sql);					 
			 sql = "DELETE FROM ListeDomaine WHERE tag = '" + tag + "'";
			 stmt.executeUpdate(sql);			 
			 sql = "DELETE FROM Domaine WHERE tag = '" + tag + "'";
			 stmt.executeUpdate(sql);
			 con.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static String toString(String tag) {
		return "nom : " + tag; 
	}

}
