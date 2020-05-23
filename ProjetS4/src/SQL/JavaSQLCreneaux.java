package SQL;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public final class JavaSQLCreneaux extends JavaSQL{
//	private String titre;
//	private int couleur;
//	private int debut;
//	private int fin;
//	private int numSalarie;
//	private int code;
//	private int numero;
//	private int idA;
//	
//	public JavaSQLCreneaux (String titre, int couleur, int debut, int fin, int numSalarie, int code, int numero, int idA) {
//		super();
//		this.titre = titre;
//		this.couleur= couleur;
//		this.debut = debut;
//		this.fin= fin;
//		this.numSalarie= numSalarie;
//		this.code= code;
//		this.numero= numero;
//		this.idA = idA;
//	}
	
	
	public static void connection() {
		JavaSQL.connection();
	}
	
	
	
	public static void affiche() throws SQLException{
		String sql = "SELECT * FROM Creneaux;";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 System.out.println("idC = " + res.getString("idC") + ", titre = " + res.getString("titre") + ", couleur = " + res.getString("couleur") + ", debut = " + res.getString("debut") 
						 + ", date = " + res.getString("date") + ", position = " + res.getString("position")+ ", type = " + res.getString("type")+ ", numSalarie = " + res.getString("numSalarie")+ res.getString("idA"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	public static void insertion(String titre, int couleur, int debut, LocalDate date, int position, int type, int numSalarie, int idA) throws SQLException{
		Date date1 = Date.valueOf(date);
		String sql = "INSERT INTO Creneaux(idC, titre, couleur, debut, fin, numsalarie, idA) VALUE(NULL, '" + titre + "' ,  '"+couleur+"' ,'"+debut+"' ,'"+date1+"' , '"+position+ "' , '"+type+
				"' , '"+numSalarie+"' , '"+idA+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	public static void supprime(int idC) throws SQLException{
		try{
			 String sql = "DELETE FROM Creneaux WHERE idC =" + idC;
			 Statement stmt = getCon().createStatement();
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void modifie(int idC, String titre, int couleur, int debut, LocalDate date, int position, int type, int numSalarie, int idA) throws SQLException{
		Date date1 = Date.valueOf(date);
		try{
			 Statement stmt = getCon().createStatement();
			 String sql = "UPDATE Creneaux SET titre= '" + titre+ "' ,couleur  = '" + couleur + "' ,debut  = '" + debut + "' ,date  = '" + date1 + "' ,position  = '" + position +  "' ,type  = '" + type + "' ,numSalarie  = '" + numSalarie +"' ,idA  = '" + idA +"';";
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	

}
