package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaSQLParticipe extends JavaSQL{
	private int numSalarie;
	private int code;
	private int numero;
	private int idA;
	
	public JavaSQLParticipe (int numSalarie, int code, int numero, int idA) {
		super();
		this.numero= numero;
		this.numSalarie = numSalarie;
		this.code = code;
		this.idA = idA;
	}
	
	
	public void connection() {
		super.connection();
	}
	
	
	
	public void affiche() throws SQLException{
		String sql = "SELECT * FROM Participe;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("numSalarie = " + res.getString("numSalarie") + ", code = " + res.getString("code") + ", numero = " + res.getString("numero") + ", idA = " + res.getString("idA"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	public void insertion() throws SQLException{
		String sql = "INSERT INTO Participes(numSalarie, code, numero, idA) VALUE('" + this.numSalarie+ "' ,  '"+this.code+"' ,  '"+this.numero+"' ,  '"+this.idA+"');";
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
		return "nom : " + this.numSalarie+this.code+this.numero+this.idA; 
	}

}
