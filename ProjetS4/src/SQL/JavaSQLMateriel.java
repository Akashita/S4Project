package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaSQLMateriel extends JavaSQL{

	private int numSerie;
	private String type;
	private int numero;
	
	public JavaSQLMateriel (int numSerie, String type,int numero ) {
		super();
		this.numSerie = numSerie;
		this.type= type;
		this.numero= numero;
	}
	
	
	public void connection() {
		super.connection();
	}
	
	
	
	public void affiche() throws SQLException{
		String sql = "SELECT * FROM Materiel;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("numSerie= " + res.getString("numSerie") + ", type= " + res.getString("type") + ", numero= " + res.getString("numero"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	public void insertion() throws SQLException{
		String sql = "INSERT INTO Materiel(numSerie, type, numero) VALUE('" + this.numSerie+ "' ,  '"+this.type+"' ,  '"+this.numero+"');";
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
		return "nom : " + this.numSerie+this.type+this.numero; 
	}

}
