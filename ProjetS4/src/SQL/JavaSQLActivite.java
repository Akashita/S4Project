package SQL;

import java.awt.Color;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import Model.Activite;

public class JavaSQLActivite extends JavaSQL{
	int idA;
	private String titre;
	private LocalDate debut;
	private Double charge;
	private int ordre;
	private int couleur;
	private int idP;
	private ArrayList<String> listeDom;

	public JavaSQLActivite (int idA, String titre, LocalDate debut, Double charge, int ordre, int couleur, int idP, ArrayList<String> listeDom) {

		super();
		this.idA = idA;
		this.titre = titre;
		this.debut = debut;
		this.charge = charge;
		this.ordre = ordre;
		this.couleur = couleur;
		this.idP= idP;
		this.listeDom = listeDom;
	}
	public JavaSQLActivite () {

		super();
	}


	public void connection() {
		super.connection();
	}

	
	public ArrayList<Activite> affiche() throws SQLException{
		ArrayList<Activite> acttab = new ArrayList<Activite>();
		ArrayList<String> listeDom = new ArrayList<String>();
		String sql = "SELECT * FROM Activite;";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 LocalDate debut = res.getDate("debut").toLocalDate();
						 String sql2 = "SELECT tag FROM ListeDomaine WHERE idA = " + res.getInt("idA") + ";";
						 Statement stmt2 = getCon().createStatement();
						 try (ResultSet res2 = stmt2.executeQuery(sql2)){
							 while(res2.next()) {
								 listeDom.add(res2.getString("tag"));
							 }
						 }
						 acttab.add(new Activite(res.getInt("idA"), res.getString("titre"), res.getDouble("charge"), debut, new Color(res.getInt("couleur")), res.getInt("ordre"),listeDom));
						 System.out.println("idA = " + res.getString("idA") + ", titre = " + res.getString("titre") + ", debut = " + res.getString("debut") + ", charge = " + res.getString("charge")
						 + ", ordre = " + res.getString("ordre")+ ", couleur = " + res.getString("couleur") +  ", nom = " + res.getString("nom"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return acttab;

	}

	public void insertion() throws SQLException{
		Date debut = Date.valueOf(this.debut);
		String sql = "INSERT INTO Activite(idA, titre, debut, charge, ordre, couleur, idP) VALUE(NULL, '" + this.titre + "' ,  '"+debut+"' ,'"+this.charge+"' , '"+this.ordre+"' , '"+this.couleur+"' , '"+this.idP+"');";
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
				 String sql2 = "SELECT MAX(idA) FROM Activite;";
				 Statement stmt2 = getCon().createStatement();
				 try (ResultSet res2 = stmt2.executeQuery(sql2)){
					 for(int i = 0; i<listeDom.size(); i++) {
						 JavaSQLListeDomaine ins = new JavaSQLListeDomaine(listeDom.get(i),res2.getInt("idA"));
						 ins.insertion();
					 }
				 }
				 
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	 public void supprime() throws SQLException{
			try{
				 this.connection();
				 String sql = "DELETE FROM ListeDomaine WHERE idA =" + this.idA;
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 sql = "DELETE FROM Participe WHERE idA =" + this.idA;
				 stmt.executeUpdate(sql);
				 sql = "DELETE FROM Creneaux WHERE idA =" + this.idA;
				 stmt.executeUpdate(sql);
				 sql = "DELETE FROM Activite WHERE idA =" + this.idA;
				 stmt.executeUpdate(sql);
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	 }

	public String toString() {
		return "nom : " + this.titre +this.debut+this.charge+this.ordre+this.couleur+this.idP;
	}

}
