package Model;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

public final class Temps {
	
	public static int getSemaine() {
		LocalDate date = LocalDate.now();
		TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		return date.get(woy);
	}
	
	
	//Risque de poser probl�me lors du changement d'ann�e
	public static LocalDate[] getJourSemaine(int annee, int semaine) {
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
	}
	
	
}
