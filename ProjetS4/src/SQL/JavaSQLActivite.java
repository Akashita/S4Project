package SQL;

import java.awt.Color;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import Model.Activite;

public final class JavaSQLActivite extends JavaSQL{
//	int idA;
//	private String titre;
//	private LocalDate debut;
//	private float charge;
//	private int ordre;
//	private int couleur;
//	private int idP;
//	private ArrayList<String> listeDom;
//
//	public JavaSQLActivite (int idA, String titre, LocalDate debut, float charge, int ordre, int couleur, int idP, ArrayList<String> listeDom) {
//
//		super();
//		this.idA = idA;
//		this.titre = titre;
//		this.debut = debut;
//		this.charge = charge;
//		this.ordre = ordre;
//		this.couleur = couleur;
//		this.idP= idP;
//		this.listeDom = listeDom;
//	}
//	public JavaSQLActivite () {
//
//		super();
//	}


	public static void connection() {
		JavaSQL.connection();
	}

	
	public static ArrayList<Activite> affiche() throws SQLException{
		ArrayList<Activite> acttab = new ArrayList<Activite>();
		ArrayList<String> listeDom = new ArrayList<String>();
		String sql = "SELECT * FROM Activite;";
			try{
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
						 acttab.add(new Activite(res.getInt("idA"), res.getString("titre"), res.getFloat("charge"), debut, new Color(res.getInt("couleur")), res.getInt("ordre"),listeDom));

					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return acttab;

	}

	public static void insertion(String titre, LocalDate debut, float charge, int ordre, int couleur, int idP, ArrayList<String> listeDom) throws SQLException{
		Date debut1 = Date.valueOf(debut);
		String sql = "INSERT INTO Activite(idA, titre, debut, charge, ordre, couleur, idP) VALUE(NULL, '" + titre + "' ,  '"+debut1+"' ,'"+charge+"' , '"+ordre+"' , '"+couleur+"' , '"+idP+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
				 String sql2 = "SELECT MAX(idA) FROM Activite;";
				 Statement stmt2 = getCon().createStatement();
				 if (listeDom != null) {
				 try (ResultSet res2 = stmt2.executeQuery(sql2)){
					 for(int i = 0; i<listeDom.size(); i++) {
						 JavaSQLListeDomaine.insertion(listeDom.get(i),res2.getInt("idA"));
					 }
				 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	 public static void supprime(int idA) throws SQLException{
			try{
				 String sql = "DELETE FROM ListeDomaine WHERE idA =" + idA;
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 sql = "DELETE FROM ParticipeSalarie WHERE idA =" + idA;
				 stmt.executeUpdate(sql);
				 sql = "DELETE FROM ParticipeCalcul WHERE idA =" + idA;
				 stmt.executeUpdate(sql);
				 sql = "DELETE FROM ParticipeSalle WHERE idA =" + idA;
				 stmt.executeUpdate(sql);
				 sql = "DELETE FROM Creneaux WHERE idA =" + idA;
				 stmt.executeUpdate(sql);
				 sql = "DELETE FROM Activite WHERE idA =" + idA;
				 stmt.executeUpdate(sql);
			} catch(SQLException e){
				e.printStackTrace();
			}
	 }
	 
	 public static void modifie(int idA, String titre, LocalDate debut, float charge, int ordre, int couleur, int idP, ArrayList<String> listeDom) throws SQLException{
			try{
				 Statement stmt = getCon().createStatement();
				 Date debut1 = Date.valueOf(debut);
				 String sql = "UPDATE Activite SET titre= '" + titre + "' ,debut  = '" + debut1 + "' ,charge = '" + charge + "' ,ordre = '" + ordre + "' ,couleur = '" + couleur + "' ,idP = '" + idP + "' WHERE idA= '"+ idA+"';";
				 stmt.executeUpdate(sql);
				 for (int i=0; i<listeDom.size(); i++){
					 sql  = "UPDATE ListeDomaine SET tag = '" + listeDom.get(i) + "' WHERE idA = '"+ idA+"';";
					 stmt.executeUpdate(sql);
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
		}

	public static String toString(String titre, LocalDate debut, float charge, int ordre, int couleur, int idP) {
		return "nom : " + titre +debut+charge+ordre+couleur+idP;
	}

}
