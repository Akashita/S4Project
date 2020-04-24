package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JavaSQLCompetence extends JavaSQL{
//	private int numSalarie;
//	private String tag;
//	private int niveau;
//
//	public JavaSQLCompetence (int numSalarie, String tag, int niveau) {
//		super();
//		this.tag= tag;
//		this.numSalarie = numSalarie;
//		this.niveau = niveau;
//	}


	public static void connection() {
		JavaSQL.connection();
	}

	

	public static void affiche() throws SQLException{
		String sql = "SELECT * FROM Competence;";
			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("numSalarie = " + res.getString("numSalarie") + ", tag = " + res.getString("tag") + ", niveau = " + res.getString("niveau"));
					 }
				 }
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}

	public static void insertion(int numSalarie, String tag, int niveau) throws SQLException{
		String sql = "INSERT INTO Competence(numSalarie, tag, niveau) VALUE('" + numSalarie+ "' ,  '"+tag+"' ,  '"+niveau+"');";
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

	public static String toString(int numSalarie, String tag, int niveau) {
		return "nom : " + numSalarie+tag+niveau; 
	}

}
