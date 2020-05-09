package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import SQL.*;

public class Main {
	public static void main (String[] args) {
		Entreprise e = new Entreprise();
		

		ArrayList<String> testP = new ArrayList<String>();
		testP.add("php");
		ArrayList<Integer> testN = new ArrayList<Integer>();
		testN.add(1);
//		JavaSQLPersonne testSqlPersonne = new JavaSQLPersonne(1,"Geyer","Jules","Chef","putheu", testP, testN);
//		JavaSQLDomaine testSqlDomaine = new JavaSQLDomaine("php");
//		JavaSQLCompetence testSqlCompetence = new JavaSQLCompetence(10,"xjbv",5);
		JavaSQLDebug test = new JavaSQLDebug();
		JavaSQL java = new JavaSQL();
		LocalDate  date = LocalDate.of(2000, 05, 28);
		LocalDate  dateact = LocalDate.of(2020, 04, 14);
//		JavaSQLProjet testprojet = new JavaSQLProjet(1,"projet test", 5, date, 1, 1);
//		JavaSQLActivite testact = new JavaSQLActivite (1,"Activit������ 1", dateact, 2.5, 1, 2, 1, testP);
		
		try {
//			testSqlDomaine.insertion();
//			JavaSQLDomaine.insertion("php");
//			JavaSQLPersonne.insertion("Geyer","Jules","Chef","putheu", testP, testN);
//			JavaSQLProjet.insertion("test1",1,date,1, 1);
//			testprojet.insertion();
//			testact.insertion();
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
