package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JavaSQLListeDomaine extends JavaSQL{
//	private String tag;
//	private int idA;
//
//	public JavaSQLListeDomaine (String tag, int idA) {
//
//		super();
//		this.tag= tag;
//		this.idA = idA;
//	}


	public static void connection() {
		JavaSQL.connection();
	}

	
	public static void affiche() throws SQLException{
		String sql = "SELECT * FROM ListeDomaine;";
			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("idA = " + res.getString("idA") + ", tag = " + res.getString("tag"));
					 }
				 }
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}

	public static void insertion(String tag, int idA) throws SQLException{
		String sql = "INSERT INTO Activite(idA, tag) VALUE('" + idA+ "' ,  '"+tag+"');";
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

	public static String toString(int idA, String tag) {
		return "nom : " + idA+tag;
	}


}
