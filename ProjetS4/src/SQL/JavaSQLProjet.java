package SQL;

import java.awt.Color;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import Model.Activite;
import Model.Projet;
import Ressource.Competence;
import Ressource.Personne;

public final class JavaSQLProjet extends JavaSQL{
//	private int idP;
//	private String nom;
//	private int priorite;
//	private LocalDate deadline;
//	private int couleur;
//	private int numSalarie;
//	
//	public JavaSQLProjet (int idP,String nom, int priorite, LocalDate deadline, int couleur, int numSalarie) {
//		super();
//		this.idP = idP;
//		this.nom = nom;
//		this.priorite = priorite;
//		this.deadline = deadline;
//		this.couleur = couleur;
//		this.numSalarie = numSalarie;
//	}
//	
//	public JavaSQLProjet () {
//		super();
//	}
	
	
	public static void connection() {
		JavaSQL.connection();
	}
	
	
	
	public static ArrayList<Projet> affiche() throws SQLException{
		ArrayList<Projet> protab = new ArrayList<Projet>();
		ArrayList<String> listeDom = new ArrayList<String>();
		String sql = "SELECT * FROM Projet;";
		Personne personne;
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 String sql2 = "SELECT * FROM Personne WHERE numSalarie = " + res.getString("numSalarie") + ";";
						 Statement stmt2 = getCon().createStatement();
						 try (ResultSet res2 = stmt2.executeQuery(sql2)){
							 ArrayList<Competence> tagtab = new ArrayList<Competence>();
							 String sqltag = "SELECT * FROM Competence WHERE numSalarie = " + res.getString("numSalarie") + ";";
							 Statement stmt3 = getCon().createStatement();
							 try (ResultSet res3 = stmt3.executeQuery(sqltag)){
								 while(res3.next()) {
									 tagtab.add(new Competence(res3.getString("tag"), res3.getInt("niveau")));
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
											 String sql6 = "SELECT tag FROM ListeDomaine WHERE idA = " + res5.getInt("idA") + ";";
											 Statement stmt6 = getCon().createStatement();
											 try (ResultSet res6 = stmt6.executeQuery(sql6)){
												 while(res6.next()) {
													 listeDom.add(res6.getString("tag"));
												 }
											 }
											 LocalDate debut = res5.getDate("debut").toLocalDate();
											 acttab.add(new Activite(res5.getInt("idA"), res5.getString("titre"), res5.getFloat("charge"), debut, 
													 new Color(res5.getInt("couleur")), res5.getInt("ordre"),listeDom));
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
			} catch(SQLException e){
				e.printStackTrace();
			}
			return protab;

	}
	
	public static void insertion(String nom,float priorite,LocalDate deadline,int couleur, int numSalarie) throws SQLException{
		Date tadat = Date.valueOf(deadline);
		String sql = "INSERT INTO Projet(idP, nom, priorite, deadline, couleur, numSalarie) VALUE(NULL,'" + nom + "' ,  '"+priorite+"' ,'"+tadat+"' , '"+couleur+"' , '"+numSalarie+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	public static void supprime(int idP) throws SQLException{
		String sql = "SELECT idA FROM Activite WHERE idP =" + idP;
		try{
			 Statement stmt = getCon().createStatement();
			 try (ResultSet res = stmt.executeQuery(sql)){
				 while(res.next()) {
					 sql = "DELETE FROM ListeDomaine WHERE idA =" + res.getInt("idA");
					 stmt.executeUpdate(sql);
					 sql = "DELETE FROM ParticipeSalarie WHERE idA =" + res.getInt("idA");
					 stmt.executeUpdate(sql);
					 sql = "DELETE FROM ParticipeCalcul WHERE idA =" + res.getInt("idA");
					 stmt.executeUpdate(sql);
					 sql = "DELETE FROM ParticipeSalle WHERE idA =" + res.getInt("idA");
					 stmt.executeUpdate(sql);
					 sql = "DELETE FROM Creneaux WHERE idA =" + res.getInt("idA");
					 stmt.executeUpdate(sql);
					 sql = "DELETE FROM Activite WHERE idA =" + res.getInt("idA");
					 stmt.executeUpdate(sql);					 
				 }
				 sql = "DELETE FROM Projet WHERE idP = " + idP;
				 stmt.executeUpdate(sql);
			 }
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void modifie(int idP, String nom,float priorite,LocalDate deadline,int couleur, int numSalarie) throws SQLException{
		try{
			 Statement stmt = getCon().createStatement();
			 Date date1 = Date.valueOf(deadline);
			 String sql = "UPDATE Projet SET nom = '" + nom + "' ,priorite  = '" + priorite + "' ,deadline = '" + date1 + "' ,couleur = '" + couleur + "' ,numSalarie = '" + numSalarie + "' WHERE idP = '"+ idP+"';";
			 stmt.executeUpdate(sql);
			 
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static String toString(String nom,float priorite,LocalDate deadline,int couleur, int numSalarie) {
		return "nom : " + nom +priorite+deadline+couleur+numSalarie; 
	}

}
