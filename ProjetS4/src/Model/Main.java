package Model;

import java.sql.*;
import java.util.ArrayList;

import SQL.*;

public class Main {
	public static void main (String[] args) {
		Entreprise e = new Entreprise();
		//e.creerProjet("projet 1", 1);
		//e.creerActivite(e.getProjetSelectionner(), "activité 1", 11, 0, Temps.getAujourdhui());
		//e.creerActivite(e.getProjetSelectionner(), "activité 2", 50, 1, Temps.getAujourdhui());
		ArrayList<String> testP = new ArrayList<String>();
		testP.add("sql");
		ArrayList<Integer> testN = new ArrayList<Integer>();
		testN.add(1);
		JavaSQLPersonne testSqlPersonne = new JavaSQLPersonne("Geyer","Jules","Chef","putheu", testP, testN);
		JavaSQLDomaine testSqlDomaine = new JavaSQLDomaine("sqli");
		JavaSQLCompetence testSqlCompetence = new JavaSQLCompetence(10,"xjbv",5);
		
//		try {
////			testSqlDomaine.creation();
////			testSqlDomaine.insertion();
////			
////			testSqlDomaine.affiche();
//			
////			testSqlCompetence.creation();
//			testSqlPersonne.creation();
////			
////			testSqlPersonne.insertion();
////			testSqlPersonne.drop();
//			testSqlPersonne.affiche();
//
//		}catch(SQLException f){
//			f.printStackTrace();
//		}
	}
}
