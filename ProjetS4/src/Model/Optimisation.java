package Model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import GestionTicket.Ticket;
import SQL.JavaSQLProjet;
import SQL.JavaSQLRecherche;
import SQL.JavaSQLTicket;

public class Optimisation {

	
	
	public static float optimisePriorite(float priorite, LocalDate deadline ) {
		float prioriteCour = priorite;
		int i = 0;
		Boolean difference = false;
		try {
			ArrayList<Projet> liste = JavaSQLRecherche.recupereListeProjetTrieParPriorite();
			while (i < liste.size() && difference == false) {
				Projet projetListe = liste.get(i);
				if (prioriteCour == projetListe.getPriorite()) {
					if (Temps.dateUnEstSuperieurDateDeux(deadline, projetListe.getDeadline())) {
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
	
	public static void envoiMessageSiDepasseDeadLine(Projet projet) {
		LocalDate fin = Entreprise.getLocalDateFinDunProjet(projet);
		if (Temps.dateUnEstSuperieurDateDeux(fin, projet.getDeadline())) {
			try {
				JavaSQLTicket.insertion(Ticket.MESSAGE, "deadLine dépassé", "Vous dépassez la deadLine", 1, projet.getChefDeProjet().getId(), null, null, null, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		}
	}
	
	
	
	
}
