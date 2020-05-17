package SQL;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import GestionTicket.Ticket;
import Model.Activite;
import Model.Projet;
import Ressource.Calculateur;
import Ressource.Competence;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;

public final class JavaSQLRecherche extends JavaSQL{
	
	
	
	public static ArrayList<Projet> recupereProjetUser(int idUser) throws SQLException{
		ArrayList<Projet> protab = new ArrayList<Projet>();
		ArrayList<String> listeDom = new ArrayList<String>();
		String sql = "SELECT * FROM Projet WHERE numSalarie = '" + idUser + "';";
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
	
	public static ArrayList<Ticket> recupereTicketEnvUser(int idUser) throws SQLException{
		String sql = "SELECT * FROM Ticket WHERE numSalarieEnv = '" + idUser + "';";
		ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();

			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 LocalDate dateTicket = res.getDate("dateTicket").toLocalDate();
						
							 ticketTab.add(new Ticket(res.getInt("idT"), res.getString("sujet"), res.getString("message"), res.getString("modif"),dateTicket ,res.getInt("statut") , res.getInt("numSalarieEnv"), res.getInt("numSalarieRec") ));
 
						 
					}	
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return ticketTab;
	}
	
	public static ArrayList<Ticket> recupereTicketRecUser(int idUser) throws SQLException{
		String sql = "SELECT * FROM Ticket WHERE numSalarieRec = '" + idUser + "';";
		ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();

			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 LocalDate dateTicket = res.getDate("dateTicket").toLocalDate();
						
							 ticketTab.add(new Ticket(res.getInt("idT"), res.getString("sujet"), res.getString("message"), res.getString("modif"),dateTicket ,res.getInt("statut") , res.getInt("numSalarieEnv"), res.getInt("numSalarieRec") ));
 
						 
					}	
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return ticketTab;
	}
	
	public static Ressource recuperPersonneParId(int idUser) throws SQLException{
		String sql = "SELECT * FROM Personne WHERE numSalarie = '" + idUser + "';";
		Ressource personne = null;
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 ArrayList<Competence> tagtab = new ArrayList<Competence>();
						 String sqltag = "SELECT * FROM Competence WHERE numSalarie = " + res.getString("numSalarie") + ";";
						 Statement stmt2 = getCon().createStatement();
						 try (ResultSet res2 = stmt2.executeQuery(sqltag)){
							 while(res2.next()) {
								 tagtab.add(new Competence(res2.getString("tag"), res2.getInt("niveau")));
							 }
						 }
						 personne = (new Personne(res.getString("nom"), res.getString("prenom"), res.getString("role"), res.getInt("numSalarie"), res.getString("motDePasse"), tagtab));
						 
						 
						
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return personne;

	}
	public static ArrayList<Ressource> recuperePersonne() throws SQLException{
		String sql = "SELECT * FROM Personne;";
		ArrayList<Ressource> personnetab = new ArrayList<Ressource>();

			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 ArrayList<Competence> tagtab = new ArrayList<Competence>();
						 String sqltag = "SELECT * FROM Competence WHERE numSalarie = " + res.getString("numSalarie") + ";";
						 Statement stmt2 = getCon().createStatement();
						 try (ResultSet res2 = stmt2.executeQuery(sqltag)){
							 while(res2.next()) {
								 tagtab.add(new Competence(res2.getString("tag"), res2.getInt("niveau")));
							 }
						 }
						 personnetab.add(new Personne(res.getString("nom"), res.getString("prenom"), res.getString("role"), res.getInt("numSalarie"), res.getString("motDePasse"), tagtab));
						 
						 
						
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return personnetab;

	}
	
	public static Ressource recuperSalleParId(int idSalle) throws SQLException{
		Ressource salle = null;
			String sql = "SELECT * FROM Salle WHERE numero = '" + idSalle + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 salle = (new Salle(res.getInt("numero"),res.getString("nom"),res.getInt("place")));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return salle;

	}
	
	public static ArrayList<Ressource> recupereSalle() throws SQLException{
		ArrayList<Ressource> salletab = new ArrayList<Ressource>();
		String sql = "SELECT * FROM Salle;";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 salletab.add(new Salle(res.getInt("numero"),res.getString("nom"),res.getInt("place")));
						 System.out.println("numero = " + res.getString("numero") + ", nom = " + res.getString("nom") + ", place = " + res.getString("place"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return salletab;

	}
	
	public static Ressource recuperCalculateurParId(int idCalculateur	) throws SQLException{
		String sql = "SELECT * FROM Calculateur WHERE code = '" + idCalculateur + "';";
		Ressource calcul = null;

			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						calcul = new Calculateur(res.getInt("code"),res.getString("nom"),res.getInt("capacite"));
						 System.out.println("code = " + res.getString("code") + ", nom= " + res.getString("nom") + ", capacite= " + res.getString("capacite"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return calcul;

	}
	public static ArrayList<Ressource> recupereCalculateur() throws SQLException{
		String sql = "SELECT * FROM Calculateur;";
		ArrayList<Ressource> calcultab = new ArrayList<Ressource>();
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 calcultab.add(new Calculateur(res.getInt("code"),res.getString("nom"),res.getInt("capacite")));
						 System.out.println("code = " + res.getString("code") + ", nom= " + res.getString("nom") + ", capacite= " + res.getString("capacite"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return calcultab;

	}
	

	
}
