package SQL;
import java.sql.*;


public class JavaSQL {
	protected Connection con;
	protected ResultSet res;
	
	public JavaSQL () {
		this.con = null; //varriable de conn�ction
	    this.res = null;//varriable de resultat de demande
	}
	
	//fonction pour arr�ter et afficher un message en cas d'erreur
	public static void arret(String message) {
	      System.err.println(message);
	      System.exit(99);
	   }
	
	
	public void connection(){
	System.out.println("connection au pilote");
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		arret("disfonctionnement du pilote");
	}
	System.out.println("connection � la base de donn�e");
	try {
		String URLdb = "jdbc:mysql://localhost:3306/l2_gr_6";
       this.con = DriverManager.getConnection(URLdb,"l2_gr_6","DMDRjUP2");
		//-------------------------------------------base tmp----------------------------------------
//		String URLdb = "jdbc:mysql://localhost";
//	       this.con = DriverManager.getConnection(URLdb,"phpmyadmin","elrouliobdd");
   } catch (SQLException e) {
       arret("Connection � la base de donn�es impossible");
   }

	}
	
	public Connection getCon(){
		return this.con;
	}
	
	public ResultSet getRes(){
		return this.res;
	}
}
