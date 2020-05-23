package Model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JOptionPane;

public final class Temps {
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			ATTRIBUTS
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public final static int nbAnnnee = 4;
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//	ATTENTION : CONSTRUCTEUR IMPLICITES (class static)
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//			METHODES
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
	
	//--------------------------------------------------------------------------------->>>>> Getteurs simples
	
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
	
	
	public static int getNbSemaine(int annee) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, annee);
		return cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
	}
	

	public static LocalDate[] getJourSemaine() {
		Calendar cal = Calendar.getInstance();
		return getJourSemaine(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR));
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
	
	//--------------------------------------------------------------------------------->>>>> Getteurs complexes

	
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


	public static LocalDate creerLaDate(int jour, int mois, int annee) {
		LocalDate debut = null;
		if (Temps.jourValide (jour, mois, annee)) {
			debut = LocalDate.of(annee, mois, jour);
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Date invalide", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
		
		return debut;
	}

	
	public static int nombreDeMoisEntreDeuxDates(LocalDate debut, LocalDate fin) {
		int nb = 0;
		Period diff = Period.between(debut.withDayOfMonth(1),fin.withDayOfMonth(1));
		nb = diff.getMonths();
		return nb;
	}

	
	public static boolean jourValide(int jour, int mois, int annee){
		try {
			LocalDate.of(annee, mois, jour);
		}catch(DateTimeException e) {
			return false;
		}
		return true;
	}

	
	public static boolean dateUnEstSuperieurDateDeux(LocalDate d1, LocalDate d2) {
		return d1.isAfter(d2);
	}
	
	
	public static ArrayList<Integer> getNumSemainesEntreDates(LocalDate debut, LocalDate fin){		
		TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		LocalDate semaineCourante = debut;
		int i = 0;
		
		while((semaineCourante.getYear() < fin.getYear()) || (semaineCourante.get(woy) < fin.get(woy))) {
			res.add(i, semaineCourante.get(woy));
			semaineCourante = semaineCourante.plusWeeks(1);
			i++;
		}
		res.add(i, semaineCourante.get(woy));

		return res;
		
	}
	
}