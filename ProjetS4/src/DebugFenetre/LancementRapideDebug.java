package DebugFenetre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import Connexion.FenetreConnexion;
import Model.Entreprise;
import Model.Temps;
import Ressource.Personne;
import SQL.JavaSQL;
import SQL.JavaSQLActivite;
import SQL.JavaSQLConge;
import SQL.JavaSQLDebug;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLRecherche;
import SQL.JavaSQLReunion;

public class LancementRapideDebug {
	public static void main (String[] args) {

		JavaSQL.connection();
//		try {
////			System.out.println(JavaSQLRecherche.recupereChefDeProjetParIdPersonne(1));
////			JavaSQLDebug.affiche();
////			System.out.println(JavaSQLPersonne.affiche());
////			System.out.println(JavaSQLActivite.affiche());
////			LocalDate date = Temps.creerLaDate(24, 5, 2020);
////			JavaSQLReunion.insertion(5, date, "test", 9);
////			JavaSQLReunion.affiche();
////			JavaSQLConge.affiche();
////			JavaSQLDebug.drop();
////			JavaSQL.creation();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		new Entreprise("debugBDD");

	}
}
