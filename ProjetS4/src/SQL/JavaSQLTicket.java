package SQL;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import GestionTicket.Ticket;
import Ressource.Personne;

public class JavaSQLTicket extends JavaSQL{
	
	
	public static void connection() {
		JavaSQL.connection();
	}
	
	
	
	public static ArrayList<Ticket> affiche() throws SQLException{
		String sql = "SELECT * FROM Ticket;";
		ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();

			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 try (ResultSet res = stmt.executeQuery(sql)){
					 while(res.next()) {
						 LocalDate dateTicket = res.getDate("dateTicket").toLocalDate();

						 System.out.println("idT = " + res.getString("idT") + ", sujet = " + res.getString("sujet") + ", message = " + res.getString("message") + ", modif = " + res.getString("modif")+", statut = " +res.getInt("statut"));
						 ticketTab.add(new Ticket(res.getInt("idT"), res.getString("sujet"), res.getString("message"), res.getString("modif"),dateTicket ,res.getInt("statut") , res.getInt("numSalarieEnv"), res.getInt("numSalarieERec")));
					 }
				 }
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
			return ticketTab;
	}
	
	public static void insertion(String sujet ,String message ,String modif , LocalDate dateTicket, int statut, int numSalarieEnv ,int numSalarieRec) throws SQLException{
		Date dateTicketModif = Date.valueOf(dateTicket);

		String sql = "INSERT INTO Ticket(sujet, message, modif,dateTicket, statut, numSalarieEnv, numSalarieRec) VALUE('" + sujet+ "' ,  '"+message+"' ,  '"+modif+"' , '"+dateTicketModif+"', '"+statut+"', '"+numSalarieEnv+"' ,  '"+numSalarieRec+"');";
			try{
				 connection();
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 System.out.println("insertion fait");
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	 public static void supprime(int idT) throws SQLException{
			try{
				 connection();
				 String sql = "DELETE FROM Ticket WHERE idT =" + idT;
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	 }
	
	public static String toString(String sujet ,String message ,String modif ,LocalDate dateTicket, int statut,int numSalarieEnv ,int numSalarieRec) {
		return "nom : " + sujet+message+modif+dateTicket+statut+numSalarieEnv+numSalarieRec; 
	}

}
