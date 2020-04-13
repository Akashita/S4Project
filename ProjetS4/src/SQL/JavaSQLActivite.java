package SQL;

import java.awt.Color;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import Model.Activite;

public class JavaSQLActivite extends JavaSQL{
	private String titre;
	private LocalDate debut;
	private Double charge;
	private int ordre;
	private int couleur;
	private int idC;
	private int idP;

	public JavaSQLActivite (String titre, LocalDate debut, Double charge, int ordre, int couleur, int idC, int idP) {

		super();
		this.titre = titre;
		this.debut = debut;
		this.charge = charge;
		this.ordre = ordre;
		this.couleur = couleur;
		this.idC= idC;
		this.idP= idP;
	}
	public JavaSQLActivite () {

		super();
	}


	public void connection() {
		super.connection();
	}

	public void creation() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS Activite(idA INT PRIMARY KEY AUTO_INCREMENT, titre VARCHAR(30), debut DATE, charge DECIMAL(4,2), ordre INT, couleur INT, indC INT, idP INT, "
				+ "CONTRAINT fk_Activite_idC FOREIGN KEY(idC) REFERENCES Creneaux(idC),"
				+ "CONTRAINT fk_Activite_idP FOREIGN KEY(idP) REFERENCES Projet(idP));";
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

	@SuppressWarnings("deprecation")
	public ArrayList<Activite> affiche() throws SQLException{
		ArrayList<Activite> acttab = new ArrayList<Activite>();
		String sql = "SELECT * FROM Activite;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 Date debut = res.getDate("debut");
						 acttab.add(new Activite(res.getInt("idA"), res.getString("titre"), res.getDouble("charge"), LocalDate.of(debut.getYear(), debut.getMonth(), debut.getDay()), new Color(res.getInt("couleur")), null/*new Projet(....) avec idP*/, res.getInt("ordre")));
						 System.out.println("idA = " + res.getString("idA") + ", titre = " + res.getString("titre") + ", debut = " + res.getString("debut") + ", charge = " + res.getString("charge")
						 + ", ordre = " + res.getString("ordre")+ ", couleur = " + res.getString("couleur") + ", idC = " + res.getString("idC") + ", nom = " + res.getString("nom"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return acttab;

	}

	public void insertion() throws SQLException{
		String sql = "INSERT INTO Activite(idA, titre, debut, charge, ordre, couleur, idC, nom) VALUE(NULL, '" + this.titre + "' ,  '"+this.debut+"' ,'"+this.charge+"' , '"+this.ordre+"' , '"+this.couleur+"' , '"+this.idC+"' , '"+this.idP+"');";
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
		return "nom : " + this.titre +this.debut+this.charge+this.ordre+this.couleur+this.idC+this.idP;
	}

}
