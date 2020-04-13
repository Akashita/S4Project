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
	
	
	public static LocalDate[] getJourSemaine(int annee, int semaine) {
		
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int premierJour = cal.getMinimalDaysInFirstWeek();

		
		if(semaine == 1 && premierJour > Calendar.MONDAY) {
			cal.set(Calendar.YEAR, annee-1);
			cal.set(Calendar.WEEK_OF_YEAR, cal.getActualMaximum(Calendar.WEEK_OF_YEAR) + 1);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		} else {
			cal.setWeekDate(annee, semaine, Calendar.MONDAY);
		}
		
		
		int numJour = cal.get(Calendar.DAY_OF_YEAR); 
		int numAnne = cal.get(Calendar.YEAR);
		
		LocalDate init = LocalDate.ofYearDay(numAnne, numJour);
		LocalDate[] tab = new LocalDate[5];
		
		for (int i = 0; i < 5; i++) {
			tab[i] = init;
			init = init.plus(1, ChronoUnit.DAYS);
		}

		return tab;
		
	}
	
	
	public static int getNbSemaine(int annee) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, annee);
		return cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
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