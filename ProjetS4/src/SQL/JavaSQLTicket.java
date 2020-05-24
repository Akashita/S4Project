package SQL;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import GestionTicket.Ticket;
import Model.Activite;
import Model.Temps;
import Ressource.Personne;
import Ressource.Ressource;

public class JavaSQLTicket extends JavaSQL{
	
	
	public static void connection() {
		JavaSQL.connection();
	}
	
	
	
	public static ArrayList<Ticket> affiche() throws SQLException{
		String sql = "SELECT * FROM Ticket;";
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
	
	public static void insertion(int action,String sujet ,String message  ,  int numSalarieEnv ,int numSalarieRec,Ressource r, Activite activiteArrive,Activite activiteDepart,Ticket ticketTransfert) throws SQLException{
		Ticket ticketCour = new Ticket(0, action, sujet, message, Temps.getAujourdhui(), 0, numSalarieEnv, numSalarieRec, r, activiteArrive, activiteDepart, ticketTransfert);
		String modif = ticketCour.getModif();
		String sql = "INSERT INTO Ticket(sujet, message, modif,dateTicket, statut, numSalarieEnv, numSalarieRec) VALUE('" + sujet+ "' ,  '"+message+"' ,  '"+modif+"' , '"+Temps.getAujourdhui()+"', '"+ Ticket.NONVU +"', '"+numSalarieEnv+"' ,  '"+numSalarieRec+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
				 
				 String sql2 = "SELECT MAX(idT) FROM Ticket;";
				 Statement stmt2 = getCon().createStatement();
					if (action == Ticket.TRANSFERT) {
						ArrayList<Activite> liste = new ArrayList<Activite>();
						switch (r.getType()) {
						case Ressource.PERSONNE:
							liste = JavaSQLRecherche.recupereActiviteParIdPersonne(r.getId());
							break;
						case Ressource.SALLE:
							liste = JavaSQLRecherche.recupereActiviteParIdPersonne(r.getId());
							break;
						case Ressource.CALCULATEUR:
							liste = JavaSQLRecherche.recupereActiviteParIdPersonne(r.getId());
							break;
						default:
							break;
						}
						try (ResultSet res2 = stmt2.executeQuery(sql2)){
							 res2.next();
							 for(int i = 0; i<liste.size(); i++) {
								 int receveur = (JavaSQLRecherche.recupereChefDeProjetParIdActivite(liste.get(i).getId())).getId();
								 if (numSalarieEnv != receveur) {
									JavaSQLTicket.insertion(Ticket.LIBERE, sujet, message, numSalarieEnv, receveur, r,null,liste.get(i),JavaSQLRecherche.recupereTicketParId(res2.getInt(1)));
								 }
							 }
						 }
						
					}

			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	 public static void supprime(int idT) throws SQLException{
			try{
				 String sql = "DELETE FROM Ticket WHERE idT =" + idT;
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
			} catch(SQLException e){
				e.printStackTrace();
			}
	 }
	 
	 
	 public static void modifieStatut(Ticket ticket, int statut) throws SQLException {
		 if (ticket.getStatut() == Ticket.NONVU && statut == Ticket.VU) {
			 try{
				 Statement stmt = getCon().createStatement();
				 String sql = "UPDATE Ticket SET statut= '" + statut + "' WHERE idT= '"+ ticket.getId()+"';";
				 stmt.executeUpdate(sql);
			} catch(SQLException e){
				e.printStackTrace();
			}			 
		 }
		 if (ticket.getStatut() == Ticket.VU) {			 
			if( statut == Ticket.ACCEPTEE || statut == Ticket.REFUSE) {
				 try{
					 Statement stmt = getCon().createStatement();
					 String sql = "UPDATE Ticket SET statut= '" + statut + "' WHERE idT= '"+ ticket.getId()+"';";
					 stmt.executeUpdate(sql);
				} catch(SQLException e){
					e.printStackTrace();
				}		 				
			}
		 }
	}
	 
	public static String toString(String sujet ,String message ,String modif ,LocalDate dateTicket, int statut,int numSalarieEnv ,int numSalarieRec) {
		return "nom : " + sujet+message+modif+dateTicket+statut+numSalarieEnv+numSalarieRec; 
	}

}
