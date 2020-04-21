package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaSQLListeDomaine extends JavaSQL{
	private String tag;
	private int idA;

	public JavaSQLListeDomaine (String tag, int idA) {

		super();
		this.tag= tag;
		this.idA = idA;
	}


	public void connection() {
		super.connection();
	}

	
	public void affiche() throws SQLException{
		String sql = "SELECT * FROM ListeDomaine;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("idA = " + res.getString("idA") + ", tag = " + res.getString("tag"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}

	public void insertion() throws SQLException{
		String sql = "INSERT INTO Activite(idA, tag) VALUE('" + this.idA+ "' ,  '"+this.tag+"');";
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
		return "nom : " + this.idA+this.tag;
	}


}
