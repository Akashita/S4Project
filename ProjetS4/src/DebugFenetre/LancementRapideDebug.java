package DebugFenetre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Connexion.FenetreConnexion;
import Model.Entreprise;
import Ressource.Personne;
import SQL.JavaSQL;
import SQL.JavaSQLDebug;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLRecherche;

public class LancementRapideDebug {
	public static void main (String[] args) {

		JavaSQL.connection();
		//try {
//			System.out.println(JavaSQLRecherche.recupereChefDeProjetParIdPersonne(1));
//			JavaSQLDebug.affiche();
//			System.out.println(JavaSQLPersonne.affiche());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		new Entreprise("debugBDD");

}
}
