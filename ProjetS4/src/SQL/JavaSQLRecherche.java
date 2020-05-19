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
///////////////////////////////////////////////////////////////////////////////////ACTIVITE////////////////////////////////////////////////////////////////////////////	
	public static ArrayList<Activite> recupereActivite() throws SQLException{
		ArrayList<Activite> actTab = new ArrayList<Activite>();
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
						 actTab.add(new Activite(res.getInt("idA"), res.getString("titre"), res.getFloat("charge"), debut, new Color(res.getInt("couleur")), res.getInt("ordre"),listeDom));

					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return actTab;

	}
	public static Activite recupereActiviteParId(int idA) throws SQLException{
		ArrayList<String> listeDom = new ArrayList<String>();
		Activite activite = null;
		String sql = "SELECT * FROM Activite WHERE idA = '"+ idA + "';";
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
						 	activite = new Activite(res.getInt("idA"), res.getString("titre"), res.getFloat("charge"), debut, new Color(res.getInt("couleur")), res.getInt("ordre"),listeDom);

					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return activite;

	}
	
	
	
	
	
	public static  ArrayList<Activite> recupereActiviteParIdPersonne(int numSalarie) throws SQLException{
		
		ArrayList<Activite> liste = new ArrayList<Activite>();
		String sql = "SELECT idA FROM ParticipeSalarie WHERE numSalarie = '"+ numSalarie + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 Activite activite= recupereActiviteParId(res.getInt("idA"));
						 liste.add( activite);
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return liste;

	}
	
	
	public static  ArrayList<Integer> recupereIdActiviteParIdPersonne(int numSalarie) throws SQLException{
		ArrayList<Integer> liste = new ArrayList<Integer>();
		String sql = "SELECT idA FROM ParticipeSalarie WHERE numSalarie = '"+ numSalarie + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 liste.add(res.getInt("idA"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return liste;

	}
	
	public static  ArrayList<Activite> recupereActiviteParIdSalle(int numero) throws SQLException{
		
		ArrayList<Activite> liste = new ArrayList<Activite>();
		String sql = "SELECT idA FROM ParticipeSalle WHERE numero = '"+ numero + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 Activite activite= recupereActiviteParId(res.getInt("idA"));
						 liste.add( activite);
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return liste;

	}
	
	public static  ArrayList<Integer> recupereIdActiviteParIdSalle(int numero) throws SQLException{
		ArrayList<Integer> liste = new ArrayList<Integer>();
		String sql = "SELECT idA FROM ParticipeSalle WHERE numero = '"+ numero + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 liste.add(res.getInt("idA"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return liste;

	}
	
	public static  ArrayList<Activite> recupereActiviteParIdCalculateur(int code) throws SQLException{
		
		ArrayList<Activite> liste = new ArrayList<Activite>();
		String sql = "SELECT idA FROM ParticipeCalcul WHERE code = '"+ code + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 Activite activite= recupereActiviteParId(res.getInt("idA"));
						 liste.add( activite);
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return liste;

	}


	public static  ArrayList<Integer>  recupereIdActiviteParIdCalculateur(int code) throws SQLException{
		ArrayList<Integer> liste = new ArrayList<Integer>();
		String sql = "SELECT idA FROM ParticipeCalcul WHERE code = '"+ code + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 liste.add(res.getInt("idA"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return liste;

	}
///////////////////////////////////////////////////////////////////////////////////PresenceDansActivite////////////////////////////////////////////////////////////////////////////	
	
	
	
	public static Boolean presenceActivitePersonne(Personne salarie, int idA) throws SQLException{
		Boolean presence = false;
		String sql = "SELECT idA FROM ParticipeSalarie WHERE numero = '"+ salarie.getId() + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next() || presence == false) {
						 if (res.getInt("idA") == idA) {
						 presence = true;	
						 }
						 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return presence;
	}
	
	public static Boolean presenceDansUneActivitePersonne(int numSalarie) throws SQLException{
		Boolean presence = false;
		String sql = "SELECT * FROM ParticipeSalarie WHERE numSalarie = '"+ numSalarie + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 if (res.getInt("numSalarie") == numSalarie) {
						 presence = true;	
						 }
						 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return presence;
	}
	
	public static Boolean presenceActiviteSalle(Salle salle, int idA) throws SQLException{
		Boolean presence = false;
		String sql = "SELECT idA FROM ParticipeSalle WHERE numero = '"+ salle.getId() + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next() || presence == false) {
						 if (res.getInt("idA") == idA) {
						 presence = true;	
						 }
						 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return presence;
	}
	
	public static Boolean presenceDansUneActiviteSalle(int numero) throws SQLException{
		Boolean presence = false;
		String sql = "SELECT * FROM ParticipeSalle WHERE numero = '"+ numero + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next() ) {
						 if (res.getInt("numero") == numero) {
						 presence = true;	
						 }
						 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return presence;
	}
	
	public static Boolean presenceActiviteCalculateur(Calculateur calculateur, int idA) throws SQLException {
		Boolean presence = false;
		String sql = "SELECT idA FROM ParticipeCalcul WHERE numero = '"+ calculateur.getId() + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next() || presence == false) {
						 if (res.getInt("idA") == idA) {
						 presence = true;	
						 }
						 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return presence;
	}
	
	public static Boolean presenceDansUneActiviteCalculateur(int code) throws SQLException{
		Boolean presence = false;
		String sql = "SELECT * FROM ParticipeCalcul WHERE code = '"+ code + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next() ) {
						 if (res.getInt("code") == code) {
						 presence = true;	
						 }
						 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return presence;
	}
	
	
	
	
	
	
	
	
	
///////////////////////////////////////////////////////////////////////////////////PROJET////////////////////////////////////////////////////////////////////////////	
	public static ArrayList<Projet> recupereProjetParIdChef(int idUser) throws SQLException{
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
						 
						
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return protab;

	}
	
	
	public static Projet recupereProjetParId(int idP) throws SQLException{
		Projet projet = null;
		ArrayList<String> listeDom = new ArrayList<String>();
		String sql = "SELECT * FROM Projet WHERE idP = '" + idP + "';";
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
						 projet = new Projet(acttab,personne, res.getString("nom"), res.getFloat("priorite"), deadl, res.getInt("idP"), 
								 new Color(res.getInt("couleur")));
						 
						 
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return projet;

	}
	
	public static Projet recupereProjetParIdActivite(int idA) throws SQLException{
		Projet projet = null;
		int idProjet = -1;
		String sql = "SELECT idP FROM Activite WHERE idA = '"  + idA +"';";
		Statement stmt = getCon().createStatement();
		 try (ResultSet res = stmt.executeQuery(sql)){
			 while(res.next()) {
				 idProjet = res.getInt("idP");
			 }
		 }
		 projet =  recupereProjetParId(idProjet);
		
		return projet;
	}
	
	public static ArrayList<Projet> recupereProjetParIdSalle(int numero) throws SQLException{
		ArrayList<Projet> proTab = new ArrayList<Projet>();
		int idProjet = -1;
		ArrayList<Integer> idActiviteTab = recupereIdActiviteParIdSalle(numero);
		for (int i = 0; i < idActiviteTab.size(); i++) {
			
		
		String sql = "SELECT idP FROM Activite WHERE idA = '"  + idActiviteTab.get(i) +"';";
		Statement stmt = getCon().createStatement();
		 try (ResultSet res = stmt.executeQuery(sql)){
			 while(res.next()) {
				 idProjet = res.getInt("idP");
			 }
		 }
		 Projet projCour = recupereProjetParId(idProjet);
		 if (!(proTab.contains(projCour))){
		 proTab.add(projCour);
		 }
		 }
		return proTab;
	}
	
	public static ArrayList<Projet> recupereProjetParIdPersonne(int numSalarie) throws SQLException{
		ArrayList<Projet> proTab = new ArrayList<Projet>();
		int idProjet = -1;
		ArrayList<Integer> idActiviteTab = recupereIdActiviteParIdPersonne(numSalarie);
		for (int i = 0; i < idActiviteTab.size(); i++) {
			
		
			String sql = "SELECT idP FROM Activite WHERE idA = '"  + idActiviteTab.get(i) +"';";
			Statement stmt = getCon().createStatement();
			 try (ResultSet res = stmt.executeQuery(sql)){
				 while(res.next()) {
					 idProjet = res.getInt("idP");
				 }
			 }
			 Projet projCour = recupereProjetParId(idProjet);
			 if (!(proTab.contains(projCour))){
			 proTab.add(projCour);
			 }
		}
		ArrayList<Projet> projetChef = new ArrayList<Projet>();
		projetChef = recupereProjetParIdChef(numSalarie);
		for (int i = 0; i < projetChef.size(); i++) {
		Projet projCour = projetChef.get(i);
		 if (!(proTab.contains(projCour))){
		 proTab.add(projCour);
		 }
		}
		
		return proTab;
	}
	
	
	public static ArrayList<Projet> recupereProjetParIdCalculateur(int code) throws SQLException{
		
		ArrayList<Projet> proTab = new ArrayList<Projet>();
		int idProjet = -1;
		ArrayList<Integer> idActiviteTab = recupereIdActiviteParIdCalculateur(code);
		for (int i = 0; i < idActiviteTab.size(); i++) {
			
		
		String sql = "SELECT idP FROM Activite WHERE idA = '"  + idActiviteTab.get(i) +"';";
		Statement stmt = getCon().createStatement();
		 try (ResultSet res = stmt.executeQuery(sql)){
			 while(res.next()) {
				 idProjet = res.getInt("idP");
			 }
		 }
		 Projet projCour = recupereProjetParId(idProjet);
		 if (!(proTab.contains(projCour))){
		 proTab.add(projCour);
		 }
		 }
		return proTab;
	}
	
///////////////////////////////////////////////////////////////////////////////////PresenceDansProjet////////////////////////////////////////////////////////////////////////////	

	public static Boolean presenceProjetParIdActivite(int idA, int idP) throws SQLException{
		Boolean presence = false;
		int idProjet;
		String sql = "SELECT idP FROM Activite WHERE idA = '"  + idA +"';";
		Statement stmt = getCon().createStatement();
		 try (ResultSet res = stmt.executeQuery(sql)){
			 while(res.next()) {
				 idProjet = res.getInt("idP");
				 if  (idP == idProjet) {
						presence = true;
					}
			 }
		 }
		return presence;
	}
	
	
	public static Boolean presenceProjetParIdPersonne(int numSalarie, int idP) throws SQLException{
		Boolean presence = false;
		int i = 0;
		ArrayList<Activite> idA = recupereActiviteParIdPersonne(numSalarie);
		while (i < idA.size() && presence == false ) {
			presence = presenceProjetParIdActivite(idA.get(i).getId(),idP);
			i++;
		}
		return presence;
	}
	
	public static Boolean presenceProjetParIdSalle(int numero, int idP) throws SQLException{
		Boolean presence = false;
		int i = 0;
		ArrayList<Activite> idA = recupereActiviteParIdSalle(numero);
		while (i < idA.size() && presence == false ) {
			presence = presenceProjetParIdActivite(idA.get(i).getId(),idP);
			i++;
		}
		return presence;
	}
	
	public static Boolean presenceProjetParIdCalculateur(int code, int idP) throws SQLException{
		Boolean presence = false;
		int i = 0;
		ArrayList<Activite> idA = recupereActiviteParIdCalculateur(code);
		while (i < idA.size() && presence == false ) {
			presence = presenceProjetParIdActivite(idA.get(i).getId(),idP);
			i++;
		}
		return presence;
	}
	
	
	
///////////////////////////////////////////////////////////////////////////////////TICKET////////////////////////////////////////////////////////////////////////////	

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
	
	
	
///////////////////////////////////////////////////////////////////////////////////PERSONNE////////////////////////////////////////////////////////////////////////////	

	public static Ressource recuperePersonneParId(int numSalarie) throws SQLException{
		String sql = "SELECT * FROM Personne WHERE numSalarie = '" + numSalarie + "';";
		Ressource personne = null;
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 ArrayList<Competence> tagtab = new ArrayList<Competence>();
						 String sqltag = "SELECT * FROM Competence WHERE numSalarie = '" + numSalarie + "';";
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
	
	public static  ArrayList<Ressource> recuperePersonneDansActiviteParId(int idA) throws SQLException{
		
		ArrayList<Ressource> liste = new ArrayList<Ressource>();
		String sql = "SELECT * FROM ParticipeSalarie WHERE idA = '"+ idA + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 Ressource ressource= recupereSalleParId(res.getInt("numSalarie"));
						 liste.add( ressource);
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return liste;

	}
	
	public static  Ressource recupereChefDeProjetParIdProjet(int idP) throws SQLException{
		Ressource chef = null;
		String sql = "SELECT numSalarie FROM Projet WHERE idP = '"+ idP + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 chef= recuperePersonneParId(res.getInt("numSalarie"));
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return chef;

	}
	
	public static  Ressource recupereChefDeProjetParIdActivite(int idA) throws SQLException{
		Ressource chef = null;
		Projet projet = recupereProjetParIdActivite(idA);
		chef = recupereChefDeProjetParIdProjet(projet.getId()); 
		return chef;

	}
	
	public static  ArrayList<Ressource> recupereChefDeProjetParIdPersonne(int numSalarie) throws SQLException{
		ArrayList<Ressource> chefListe = new ArrayList<Ressource>();
		ArrayList<Activite> activiteDeLaPersonne = new ArrayList<Activite>();
		activiteDeLaPersonne = recupereActiviteParIdPersonne(numSalarie);
		for (int i = 0; i < activiteDeLaPersonne.size(); i++) {
			Ressource chefCourant = recupereChefDeProjetParIdActivite(activiteDeLaPersonne.get(i).getId());
			 if (!(chefListe.contains(chefCourant))){

			chefListe.add(chefCourant);
			 }
		}
			 ArrayList<Projet> projetTab = recupereProjetParIdChef(numSalarie);
			 for (int i = 0; i < projetTab.size(); i++) {
					Ressource chefCourant = projetTab.get(i).getChefDeProjet();
					 if (!(chefListe.contains(chefCourant))){

					chefListe.add(chefCourant);
					 }
			 
			 } 
		
		return chefListe;
	}
	
	public static  ArrayList<Ressource> recupereChefDeProjetParIdSalle(int numero) throws SQLException{
		ArrayList<Ressource> chefListe = new ArrayList<Ressource>();
		ArrayList<Activite> activiteDeLaSalle = new ArrayList<Activite>();
		activiteDeLaSalle = recupereActiviteParIdSalle(numero);
		for (int i = 0; i < activiteDeLaSalle.size(); i++) {
			Ressource chefCourant = recupereChefDeProjetParIdActivite(activiteDeLaSalle.get(i).getId());
			 if (!(chefListe.contains(chefCourant))){

			chefListe.add(chefCourant);
			 }
		}
		return chefListe;
	}
	
	public static  ArrayList<Ressource> recupereChefDeProjetParIdCalculateur(int code) throws SQLException{
		ArrayList<Ressource> chefListe = new ArrayList<Ressource>();
		ArrayList<Activite> activiteDuCalculateur = new ArrayList<Activite>();
		activiteDuCalculateur = recupereActiviteParIdCalculateur(code);
		for (int i = 0; i < activiteDuCalculateur.size(); i++) {
			Ressource chefCourant = recupereChefDeProjetParIdActivite(activiteDuCalculateur.get(i).getId());
			 if (!(chefListe.contains(chefCourant))){
			chefListe.add(chefCourant);
			 }
		}
		return chefListe;
	}
/////////////////////////////////////////////////////////////////////////////////////PRESENCE//////////////////////////////////////////////////////////////////////////	
	public static Boolean presenceDansUnProjetPersonne(int numSalarie) throws SQLException {
		Boolean b = false;
		ArrayList<Projet> projetRessource;
		try {
			projetRessource = recupereProjetParIdPersonne(numSalarie);if (projetRessource == null) {
				b = true;
			}
			else if (projetRessource.isEmpty()) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return b;
	}

///////////////////////////////////////////////////////////////////////////////////SALLE////////////////////////////////////////////////////////////////////////////	

	
	public static Ressource recupereSalleParId(int idSalle) throws SQLException{
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
	
	public static  ArrayList<Ressource> recupereSalleDansActiviteParId(int idA) throws SQLException{
		
		ArrayList<Ressource> liste = new ArrayList<Ressource>();
		String sql = "SELECT * FROM ParticipeSalle WHERE idA = '"+ idA + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 Ressource ressource= recupereSalleParId(res.getInt("numero"));
						 liste.add( ressource);
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return liste;

	}
/////////////////////////////////////////////////////////////////////////////////////PRESENCE//////////////////////////////////////////////////////////////////////////	
	public static Boolean presenceDansUnProjetSalle(int numero) throws SQLException {
		Boolean b = false;
		ArrayList<Projet> projetRessource;
		try {
			projetRessource = recupereProjetParIdSalle(numero);
			if (projetRessource == null) {
				b = true;
			}
			else if (projetRessource.isEmpty()) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return b;
	}

///////////////////////////////////////////////////////////////////////////////////CALCULATEUR////////////////////////////////////////////////////////////////////////////	

	public static Ressource recupereCalculateurParId(int idCalculateur	) throws SQLException{
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
	
	public static  ArrayList<Ressource> recupereCalculateurDansActiviteParId(int idA) throws SQLException{
		
		ArrayList<Ressource> liste = new ArrayList<Ressource>();
		String sql = "SELECT * FROM ParticipeCalcul WHERE idA = '"+ idA + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 Ressource ressource= recupereSalleParId(res.getInt("code"));
						 liste.add( ressource);
					 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return liste;

	}
	
/////////////////////////////////////////////////////////////////////////////////////PRESENCE//////////////////////////////////////////////////////////////////////////	
public static Boolean presenceDansUnProjetCalculateur(int code) throws SQLException {
	Boolean b = false;
	ArrayList<Projet> projetRessource;
	try {
		projetRessource = recupereProjetParIdCalculateur(code);
		if (projetRessource == null) {
			b = true;
		}
		else if (projetRessource.isEmpty()) {
			b = true;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return b;
}

///////////////////////////////////////////////////////////////////////////////////LISTEDOMAINE////////////////////////////////////////////////////////////////////////////	

	
	
	public static  Boolean presenceDomaineDansListeDomaine(String domaine) throws SQLException{
		Boolean presence = false;
		String sql = "SELECT * FROM ListeDomaine WHERE tag = '"+ domaine + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next() && presence == false) {
						 presence = true;
						 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return presence;

	}
///////////////////////////////////////////////////////////////////////////////////COMPETENCE////////////////////////////////////////////////////////////////////////////	
	public static  Boolean presenceDomaineDansCompetence(String domaine) throws SQLException{
		Boolean presence = false;
		String sql = "SELECT * FROM Competence WHERE tag = '"+ domaine + "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next() && presence == false) {
						 presence = true;
						 
						 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return presence;

	}
	public static  Boolean presenceCompetence(String domaine, int numSalarie) throws SQLException{
		Boolean presence = false;
		String sql = "SELECT * FROM Competence WHERE tag = '"+ domaine + "' AND numSalarie = '" + numSalarie +  "';";
			try{
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next() && presence == false) {
						 presence = true;
						 
						 }
				 }
			} catch(SQLException e){
				e.printStackTrace();
			}
			return presence;

	}
	
	
	
	
	
}
