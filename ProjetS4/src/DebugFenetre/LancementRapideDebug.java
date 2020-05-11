package DebugFenetre;

import java.sql.SQLException;

import Connexion.FenetreConnexion;
import Model.Entreprise;
import Ressource.Personne;
import SQL.JavaSQLDebug;
import SQL.JavaSQLPersonne;

public class LancementRapideDebug {
	public static void main (String[] args) {

		new Entreprise("debugBDD");
	}
}
