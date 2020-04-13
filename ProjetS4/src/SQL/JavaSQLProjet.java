package SQL;

import java.awt.Color;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

import Model.Activite;
import Model.Projet;
import Ressource.Personne;

public class JavaSQLProjet extends JavaSQL{
	private String nom;
	private int priorite;
	private LocalDate deadline;
	private int couleur;
	private int numSalarie;
	
	public JavaSQLProjet (String nom, int priorite, LocalDate deadline, int couleur, int numSalarie) {
		super();
		this.nom = nom;
		this.priorite = priorite;
		this.deadline = deadline;
		this.couleur = couleur;
		this.numSalarie = numSalarie;
	}
	
	public JavaSQLProjet () {
		super();
	}
	
	
	public void connection() {
		super.connection();
	}
	
	
	
	public ArrayList<Projet> affiche() throws SQLException{
		ArrayList<Projet> protab = new ArrayList<Projet>();
		String sql = "SELECT * FROM Projet;";
		Personne personne;
			try{
				 this.connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 String sql2 = "SELECT * FROM Personne WHERE numSalarie = " + res.getString("numSalarie") + ";";
						 Statement stmt2 = getCon().createStatement();
						 try (ResultSet res2 = stmt2.executeQuery(sql2)){
							 Hashtable<String, String> tagtab = new Hashtable<String, String>();
							 String sqltag = "SELECT * FROM Competence WHERE numSalarie = " + res.getString("numSalarie") + ";";
							 Statement stmt3 = getCon().createStatement();
							 try (ResultSet res3 = stmt3.executeQuery(sqltag)){
								 while(res3.next()) {
									 tagtab.put(res3.getString("tag"), res3.getString("niveau"));
								 }
							 }
							 res2.next();
							 personne  = new Personne(res2.getString("nom"), res2.getString("prenom"), res2.getString("role"), res2.getInt("numSalarie"), res2.getString("motDePasse"), tagtab);
						 }
						 
						 
						 ArrayList<Activite> acttab = new ArrayList<Activite>();
						 String sql5 = "SELECT * FROM Activite WHERE idP = "  + res.getInt("idP") +";";
								try{
									 Statement stmt5 = getCon().createStatement();
									 try (ResultSet res5 = stmt5.executeQuery(sql5)){
										 while(res5.next()) {
											 LocalDate debut = res5.getDate("debut").toLocalDate();
											 acttab.add(new Activite(res5.getInt("idA"), res5.getString("titre"), res5.getDouble("charge"), debut, 
													 new Color(res5.getInt("couleur")), res5.getInt("ordre")));
										 }
									 }
								} catch(SQLException e){
									e.printStackTrace();
								}
						 LocalDate deadl = res.getDate("deadline").toLocalDate();
						 protab.add(new Projet(acttab,personne, res.getString("nom"), res.getFloat("priorite"), deadl, res.getInt("idP"), 
								 new Color(res.getInt("couleur"))));
						 
						 System.out.println("idP = " + res.getString("idP") +"nom = " + res.getString("nom") + ", priorite = " + res.getString("priorite") + ", deadline = " + res.getString("deadline") +
								 ", couleur = " + res.getString("couleur") + ", numSalarie = " + res.getString("numSalarie"));
					 }
				 }
				 this.con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return protab;

	}
	
	public void insertion() throws SQLException{
		Date tadat = Date.valueOf(this.deadline);
		String sql = "INSERT INTO Projet(idP, nom, priorite, deadline, couleur, numSalarie) VALUE(NULL,'" + this.nom + "' ,  '"+this.priorite+"' ,'"+tadat+"' , '"+this.couleur+"' , '"+this.numSalarie+"');";
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
		return "nom : " + this.nom +this.priorite+this.deadline+this.couleur+this.numSalarie; 
	}

}
