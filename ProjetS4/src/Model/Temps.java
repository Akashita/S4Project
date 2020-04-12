package Model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

public final class Temps {
	
		
	public static LocalDate getAujourdhui() {
		return LocalDate.now();
	}
	
	public static int getAnnee() {
		return getAujourdhui().getYear();
	}
	
	
	public static int getSemaine() {
		TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		return getAujourdhui().get(woy);
	}
	
	
	//TODO Risque de poser probl�me lors du changement d'ann�e
	public static LocalDate[] getJourSemaine(int annee, int semaine) {
		LocalDate[] tab = new LocalDate[5];
		
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setWeekDate(annee, semaine, Calendar.MONDAY);
		int numJour = cal.get(Calendar.DAY_OF_YEAR); 
		
		LocalDate init = LocalDate.ofYearDay(annee, numJour);
		
		for (int i = 0; i < 5; i++) {
			init = init.plus(1, ChronoUnit.DAYS);
			tab[i] = init;
		}
		return tab;
		
		
		/**
		LocalDate[] tab = new LocalDate[5];
		
		//On r�cup�re le num�ro du premier jour (initialis� au lundi) de la semaine pass�e en param�tre
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setWeekDate(annee, semaine, Calendar.MONDAY);
		int numJour = cal.get(Calendar.DAY_OF_YEAR); 
		
		//On remplit le tableau avec les 5 jours ouvrables de la semaine (lundi -> vendredi)
		// en incr�mentant le num�ro du jour 
		for (int i = 0; i < 5; i++) {
			tab[i] = LocalDate.ofYearDay(annee, numJour+i);
		}
		
		return tab;	
		**/
	}
	
	public static LocalDate[] getJourSemaine() {
		LocalDate[] tab = new LocalDate[5];
		LocalDate date = LocalDate.now();
		//On recuere le numero du premier jour (initialise au lundi) de la semaine passee en parametre
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setWeekDate(date.getYear(), date.getMonthValue(), Calendar.MONDAY);
		int numJour = cal.get(Calendar.DAY_OF_YEAR); 
		
		//On remplit le tableau avec les 5 jours ouvrables de la semaine (lundi -> vendredi)
		// en incrementant le numero du jour 
		for (int i = 0; i < 5; i++) {
			tab[i] = LocalDate.ofYearDay(date.getYear(), numJour+i);
		}
		
		return tab;	
	}
	
	public static String getLocalDateString(LocalDate date) {
		String res = "";
		switch (date.getDayOfWeek()) {
		case MONDAY:
			res += "Lundi";
			break;
		case TUESDAY:
			res += "Mardi";
			break;
		case WEDNESDAY:
			res += "Mercredi";
			break;
		case THURSDAY:
			res += "Jeudi";
			break;
		case FRIDAY:
			res += "Vendredi";
			break;
		case SATURDAY:
			res += "Samedi";
			break;
		case SUNDAY:
			res += "Dimanche";
			break;

		default:
			res += "Jour inconnu";
			break;
		}
		res += " ";
		res += date.getDayOfMonth() + " ";
		res += date.getMonth() + " ";
		res += date.getYear();
		
		return res;
	}


	
	public static boolean jourValide(int jour, int mois, int annee){
		try {
			LocalDate.of(annee, mois, jour);
		}catch(DateTimeException e) {
			return false;
		}
		return true;
	}

	public static int getJourMois(int annee, int mois) {
		YearMonth anneeMois = YearMonth.of(annee, mois);
		int nbJour = anneeMois.lengthOfMonth(); //28  		
		return nbJour;
	}

	public static int getIndexMois() {
		return getAujourdhui().getMonthValue();
	}

	public static int getIndexJour() {
		return getAujourdhui().getDayOfMonth();
	}

}