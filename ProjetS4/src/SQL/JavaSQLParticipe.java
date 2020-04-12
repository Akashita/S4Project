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
	
	public void creation() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS Participe(numSalarie INT, code INT, numero INT, idA INT,"
				+ "CONSTRAINT fk_Participe_numSalarie FOREIGN KEY(numSalarie) REFERENCES Personne3(numSalarie),"
				+ "CONSTRAINT fk_Participe_code FOREIGN KEY(code) REFERENCES Calculateur(code),"
				+ "CONSTRAINT fk_Participe_numero FOREIGN KEY(numero) REFERENCES Salle(numero),"
				+ "CONSTRAINT fk_Participe_idA FOREIGN KEY(idA) REFERENCES Activite(idA));";
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
