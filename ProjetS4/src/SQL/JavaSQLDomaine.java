package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JavaSQLDomaine extends JavaSQL{

	private String tag;

	public JavaSQLDomaine (String tag) {
		super();
		this.tag = tag;
	}

	public JavaSQLDomaine () {
		super();
	}

	public void connection() {
		super.connection();
	}



	public ArrayList<String> affiche() throws SQLException{
		String sql = "SELECT * FROM Domaine;";
		ArrayList<String> tagtab = new ArrayList<String>();
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 tagtab.add(res.getString("tag"));
						 System.out.println("tag = " + res.getString("tag"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return tagtab;

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
