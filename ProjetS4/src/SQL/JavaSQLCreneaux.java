package SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaSQLCreneaux extends JavaSQL{
	private String titre;
	private String couleur;
	private int debut;
	private int fin;
	private int numSalarie;
	private int code;
	private int numero;
	private int idA;
	
	public JavaSQLCreneaux (String titre, String couleur, int debut, int fin, int numSalarie, int code, int numero, int idA) {
		super();
		this.titre = titre;
		this.couleur= couleur;
		this.debut = debut;
		this.fin= fin;
		this.numSalarie= numSalarie;
		this.code= code;
		this.numero= numero;
		this.idA = idA;
	}
	
	
	public void connection() {
		super.connection();
	}
	
	public void creation() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS Creneaux(idC INT PRIMARY KEY AUTO_INCREMENT, titre VARCHAR(30), couleur VARCHAR(30), debut INT, fin INT, numSalarie INT, code INT, numero INT, idA INT,"
				+ "CONTRAINT fk_Creneaux_numSalarie FOREIGN KEY(numSalarie) REFERENCES Personne(numSalarie),"
				+ "CONTRAINT fk_Creneaux_code FOREIGN KEY(code) REFERENCES Calculateur(code),"
				+ "CONTRAINT fk_Creneaux_numero FOREIGN KEY(numero) REFERENCES Salle(numero)"
				+ "CONTRAINT fk_Creneaux_idA FOREIGN KEY(idA) REFERENCES Activite(idA));";
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
		String sql = "SELECT * FROM Creneaux;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("idC = " + res.getString("idC") + ", titre = " + res.getString("titre") + ", couleur = " + res.getString("couleur") + ", debut = " + res.getString("debut") 
						 + ", fin = " + res.getString("fin")+ ", numSalarie = " + res.getString("numSalarie") + ", code = " + res.getString("code") + ", numero = " + res.getString("numero") + res.getString("idA"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	public void insertion() throws SQLException{
		String sql = "INSERT INTO Creneaux(idC, titre, couleur, debut, fin, numsalarie, code, numero, idA) VALUE(NULL, '" + this.titre + "' ,  '"+this.couleur+"' ,'"+this.debut+"' , '"+this.fin+
				"' , '"+this.numSalarie+"' , '"+this.code+"' , '"+this.numero+"' , '"+this.idA+"');";
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
		return "nom : " + this.titre +this.couleur+this.debut+this.fin+this.numSalarie+this.code+this.numero+this.idA; 
	}

}
