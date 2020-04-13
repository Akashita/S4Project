package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import SQL.*;

public class Main {
	public static void main (String[] args) {
		Entreprise e = new Entreprise();
		//e.creerProjet("projet 1", 1);
		//e.creerActivite(e.getProjetSelectionner(), "activité 1", 11, 0, Temps.getAujourdhui());
		//e.creerActivite(e.getProjetSelectionner(), "activité 2", 50, 1, Temps.getAujourdhui());
		ArrayList<String> testP = new ArrayList<String>();
		testP.add("php");
		ArrayList<Integer> testN = new ArrayList<Integer>();
		testN.add(1);
		JavaSQLPersonne testSqlPersonne = new JavaSQLPersonne("Geyer","Jules","Chef","putheu", testP, testN);
		JavaSQLDomaine testSqlDomaine = new JavaSQLDomaine("php");
		JavaSQLCompetence testSqlCompetence = new JavaSQLCompetence(10,"xjbv",5);
		JavaSQLDebug test = new JavaSQLDebug();
		JavaSQL java = new JavaSQL();
		LocalDate  date = LocalDate.of(2000, 05, 28);
		JavaSQLProjet testprojet = new JavaSQLProjet("projet test", 5, date, 1, 1);
		
		
		try {
//			testSqlDomaine.insertion();
//
//			testSqlPersonne.insertion();
//			
//			testprojet.insertion();
//			
			RecupInfoBDD.recupBDDProjet(e);
			RecupInfoBDD.recupBDDRessource(e);
//			test.affiche();
//			test.drop();
//			java.creation();
//			test.affiche();
			

		}catch(SQLException f){
			f.printStackTrace();
		}
	}
}
