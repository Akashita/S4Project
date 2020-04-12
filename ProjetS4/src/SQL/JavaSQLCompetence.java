package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaSQLCompetence extends JavaSQL{
	private int numSalarie;
	private String tag;
	private int niveau;

	public JavaSQLCompetence (int numSalarie, String tag, int niveau) {
		super();
		this.tag= tag;
		this.numSalarie = numSalarie;
		this.niveau = niveau;
	}


	public void connection() {
		super.connection();
	}

	public void creation() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS Competence(numSalarie INT, tag VARCHAR(30), niveau INT NOT NULL,"
				+ "CONSTRAINT fk_Competence_numSalarie FOREIGN KEY(numSalarie) REFERENCES Personne3(numSalarie),"
				+ "CONSTRAINT fk_Competence_tag FOREIGN KEY(tag) REFERENCES Domaine(tag));";
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
		String sql = "SELECT * FROM Competence;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("numSalarie = " + res.getString("numSalarie") + ", tag = " + res.getString("tag") + ", niveau = " + res.getString("niveau"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}

	public void insertion() throws SQLException{
		String sql = "INSERT INTO Competence(numSalarie, tag, niveau) VALUE('" + this.numSalarie+ "' ,  '"+this.tag+"' ,  '"+this.niveau+"');";
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
		return "nom : " + this.numSalarie+this.tag+this.niveau; 
	}

}
