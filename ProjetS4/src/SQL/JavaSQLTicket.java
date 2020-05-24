package SQL;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import GestionTicket.Ticket;
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
	
	public static void insertion(int action,String sujet ,String message  ,  int numSalarieEnv ,int numSalarieRec,Ressource r) throws SQLException{
		Ticket ticketCour = new Ticket(0, action, sujet, message, Temps.getAujourdhui(), 0, numSalarieEnv, numSalarieRec, r);
		String modif = ticketCour.getModif();
		String sql = "INSERT INTO Ticket(sujet, message, modif,dateTicket, statut, numSalarieEnv, numSalarieRec) VALUE('" + sujet+ "' ,  '"+message+"' ,  '"+modif+"' , '"+Temps.getAujourdhui()+"', '"+ Ticket.NONVU +"', '"+numSalarieEnv+"' ,  '"+numSalarieRec+"');";
			try{
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
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
	 
	 
	 public static void modifieStatut(int idT, int statut) throws SQLException {
		 try{
			 Statement stmt = getCon().createStatement();
			 String sql = "UPDATE Ticket SET statut= '" + statut + "' WHERE idT= '"+ idT+"';";
			 stmt.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	public static String toString(String sujet ,String message ,String modif ,LocalDate dateTicket, int statut,int numSalarieEnv ,int numSalarieRec) {
		return "nom : " + sujet+message+modif+dateTicket+statut+numSalarieEnv+numSalarieRec; 
	}

}
