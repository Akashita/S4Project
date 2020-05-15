package SQL;
import java.sql.*;


public class JavaSQL {
	
	protected static Connection con;
	protected static ResultSet res;



	public JavaSQL () {

	}

	//fonction pour arr�ter et afficher un message en cas d'erreur
	public static void arret(String message) {
	      System.err.println(message);
	      System.exit(99);
	   }


	public static void connection(){
		System.out.println("connection au pilote");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			arret("disfonctionnement du pilote");
		}
		System.out.println("connection � la base de donn�e");
		try {
			String URLdb = "jdbc:mysql://localhost:3306/l2_gr_6";
	       con = DriverManager.getConnection(URLdb,"l2_gr_6","DMDRjUP2");
	   } catch (SQLException e) {
	       arret("Connection � la base de donn�es impossible");
	   }

	}

	public static Connection getCon(){
		return con;
	}
	
	public static void finCo() {
			try {
				con.close();
				System.out.println("Connection fini");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public ResultSet getRes(){
		return res;
	}
	
	public static void creation() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS Domaine(tag VARCHAR(30) PRIMARY KEY);";
			try{
				 connection();
				 
				 Statement stmt = getCon().createStatement();
				 stmt.executeUpdate(sql);
				 
				 sql = "CREATE TABLE IF NOT EXISTS Personne(numSalarie INT PRIMARY KEY AUTO_INCREMENT,nom VARCHAR(30),prenom VARCHAR(30),role VARCHAR(30),motDePasse VARCHAR(30));";
				 stmt.executeUpdate(sql);
				 System.out.println("Table Personne faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS Ticket(idT INT PRIMARY KEY AUTO_INCREMENT,sujet VARCHAR(50),message VARCHAR(100),modif VARCHAR(100),dateTicket DATE,statut INT,numSalarieEnv INT,numSalarieRec INT,"
				 		+ "CONSTRAINT fk_Ticket_numSalarieEnv FOREIGN KEY(numSalarieEnv) REFERENCES Personne(numSalarie),"
				 		+ "CONSTRAINT fk_Ticket_numSalarieRec FOREIGN KEY(numSalarieRec) REFERENCES Personne(numSalarie));";
				 stmt.executeUpdate(sql);
				 System.out.println("Table Ticket faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS Competence(numSalarie INT, tag VARCHAR(30), niveau INT NOT NULL,"
							+ "CONSTRAINT fk_Competence_numSalarie FOREIGN KEY(numSalarie) REFERENCES Personne(numSalarie),"
							+ "CONSTRAINT fk_Competence_tag FOREIGN KEY(tag) REFERENCES Domaine(tag));";
				 stmt.executeUpdate(sql);
				 System.out.println("Table Competence faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS Calculateur(code INT PRIMARY KEY AUTO_INCREMENT, nom VARCHAR(30), capacite INT NOT NULL);";
				 stmt.executeUpdate(sql);
				 System.out.println("Table Calculateur faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS Salle(numero INT PRIMARY KEY AUTO_INCREMENT, nom VARCHAR(30), place INT NOT NULL);";
				 stmt.executeUpdate(sql);
				 System.out.println("Table Salle faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS Materiel(numSerie INT PRIMARY KEY AUTO_INCREMENT, type VARCHAR(30), numero INT, "
							+ "CONSTRAINT fk_Materiel_numero FOREIGN KEY(numero) REFERENCES Salle(numero) );";
				 stmt.executeUpdate(sql);
				 System.out.println("Table Materiel faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS Projet(idP INT PRIMARY KEY AUTO_INCREMENT, nom VARCHAR(30), priorite INT, deadline DATE, couleur INT, numSalarie INT,"
							+ "CONSTRAINT fk_Projet_numSalarie FOREIGN KEY(numSalarie) REFERENCES Personne(numSalarie) );";
				 stmt.executeUpdate(sql);
				 System.out.println("Table Projet faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS Activite(idA INT PRIMARY KEY AUTO_INCREMENT, titre VARCHAR(30), debut DATE, charge FLOAT, ordre INT, couleur INT, idP INT, "
							+ "CONSTRAINT fk_Activite_idP FOREIGN KEY(idP) REFERENCES Projet(idP));";
				 stmt.executeUpdate(sql);
				 System.out.println("Table Activite faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS Creneaux(idC INT PRIMARY KEY AUTO_INCREMENT, titre VARCHAR(30), couleur INT, debut INT, fin INT, numSalarie INT, code INT, numero INT, idA INT,"
							+ "CONSTRAINT fk_Creneaux_numSalarie FOREIGN KEY(numSalarie) REFERENCES Personne(numSalarie),"
							+ "CONSTRAINT fk_Creneaux_code FOREIGN KEY(code) REFERENCES Calculateur(code),"
							+ "CONSTRAINT fk_Creneaux_numero FOREIGN KEY(numero) REFERENCES Salle(numero),"
							+ "CONSTRAINT fk_Creneaux_idA FOREIGN KEY(idA) REFERENCES Activite(idA));";
				 stmt.executeUpdate(sql);
				 System.out.println("Table Creneaux faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS ParticipeSalarie(numSalarie INT, idA INT,"
							+ "CONSTRAINT fk_ParticipeSalarie_numSalarie FOREIGN KEY(numSalarie) REFERENCES Personne(numSalarie),"
							+ "CONSTRAINT fk_ParticipeSalarie_idA FOREIGN KEY(idA) REFERENCES Activite(idA));";
				 stmt.executeUpdate(sql);
				 System.out.println("Table ParticipeSalarie faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS ParticipeCalcul(code INT, idA INT,"
							+ "CONSTRAINT fk_ParticipeCalcul_code FOREIGN KEY(code) REFERENCES Calculateur(code),"
							+ "CONSTRAINT fk_ParticipeCalcul_idA FOREIGN KEY(idA) REFERENCES Activite(idA));";
				 stmt.executeUpdate(sql);
				 System.out.println("Table ParticipeCalcul faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS ParticipeSalle(numero INT, idA INT,"
							+ "CONSTRAINT fk_ParticipeSalle_numero FOREIGN KEY(numero) REFERENCES Salle(numero),"
							+ "CONSTRAINT fk_ParticipeSalle_idA FOREIGN KEY(idA) REFERENCES Activite(idA));";
				 stmt.executeUpdate(sql);
				 System.out.println("Table ParticipeSalle faite");
				 
				 sql = "CREATE TABLE IF NOT EXISTS ListeDomaine(idA INT, tag VARCHAR(30),"
							+ "CONSTRAINT fk_ListeDomaine_idA FOREIGN KEY(idA) REFERENCES Activite(idA),"
							+ "CONSTRAINT fk_ListeDomaine_tag FOREIGN KEY(tag) REFERENCES Domaine(tag));";
				 stmt.executeUpdate(sql);
				 System.out.println("Table ListeDomaine faite");
				 
				 con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}

	}
	
	
}
