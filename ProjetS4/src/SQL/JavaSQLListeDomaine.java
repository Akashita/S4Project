package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("idA = " + res.getString("idA") + ", tag = " + res.getString("tag"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	public static  ArrayList<String[]> recupere() throws SQLException{
		
		ArrayList<String[]> liste = new ArrayList<String[]>();
		String sql = "SELECT * FROM ListeDomaine;";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 String [] tab= {res.getString("idA"), res.getString("tag") };
						 liste.add( tab);
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return liste;

	}

	public static void insertion(String tag, int idA) throws SQLException{
		String sql = "INSERT INTO ListeDomaine(idA, tag) VALUE('" + idA+ "' ,  '"+tag+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	public static void supprime(int idA, String tag) throws SQLException{
		try{
			 String sql = "DELETE FROM ListeDomaine WHERE idA ='" + idA + "' AND tag ='" + tag + "';" ;
			 Statement stmt = getCon().createStatement();
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static String toString(int idA, String tag) {
		return "nom : " + idA+tag;
	}


}
