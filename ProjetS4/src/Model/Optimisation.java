package Model;

import java.sql.SQLException;
import java.util.ArrayList;

import SQL.JavaSQLProjet;
import SQL.JavaSQLRecherche;

public class Optimisation {

	
	
	public static float optimisePriorite(Projet projetCour) {
		float prioriteCour = projetCour.getPriorite();
		int i = 0;
		Boolean difference = false;
		try {
			ArrayList<Projet> liste = JavaSQLRecherche.recupereListeProjetTrieParPriorite();
			while (i < liste.size() && difference == false) {
				Projet projetListe = liste.get(i);
				if (prioriteCour == projetListe.getPriorite()) {
					if (Temps.dateUnEstSuperieurDateDeux(projetCour.getDeadline(), projetListe.getDeadline())) {
						prioriteCour = (float)(prioriteCour + 0.1);
					}
					else {
						JavaSQLProjet.modifiePriorite(projetListe.getId(), (float)(projetListe.getPriorite() + 0.1));
					}
				}
				else if (prioriteCour < projetListe.getPriorite()) {
					difference = true;
				}
				
				
				i++;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prioriteCour;
	}
}
