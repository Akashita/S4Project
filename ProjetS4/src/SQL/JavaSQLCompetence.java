package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Ressource.Competence;
import Ressource.Personne;

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

	

	public static ArrayList<Competence> affiche() throws SQLException{
		String sql = "SELECT * FROM Competence;";
		ArrayList<Competence> competenceTab= new ArrayList<Competence>();

			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 competenceTab.add(new Competence(res.getInt("numSalarie"), res.getString("tag"), res.getInt("niveau")));

						 //System.out.println("numSalarie = " + res.getString("numSalarie") + ", tag = " + res.getString("tag") + ", niveau = " + res.getString("niveau"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return competenceTab;

	}

	public static void insertion(int numSalarie, String tag, int niveau) throws SQLException{
		String sql = "INSERT INTO Competence(numSalarie, tag, niveau) VALUE('" + numSalarie+ "' ,  '"+tag+"' ,  '"+niveau+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	public static void supprime(int numSalarie, String tag) throws SQLException{
		try{
			 String sql = "DELETE FROM Competence WHERE numSalarie ='" + numSalarie + "' AND tag = '" + tag+"';";
			 Statement stmt = getCon().createStatement();
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static String toString(int numSalarie, String tag, int niveau) {
		return "nom : " + numSalarie+tag+niveau; 
	}

}
