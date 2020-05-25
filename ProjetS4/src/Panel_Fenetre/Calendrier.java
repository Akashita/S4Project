package Panel_Fenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JComboBox;

import Model.Temps;

/**
 * Permet de gerer l'affichage des date lorsque qu'on doit en creer une ou en modifier une
 * @author Damien
 *
 */
public class Calendrier {

	private PanelFenetre pfp, pfe;

	
	private JComboBox<String> cba,cbm,cbj,cbh = null;
	
	private String [] jours,
	mois = {"Janvier", "Février", "Mars", "Avril",
			"Mai", "Juin", "Juillet", "Aout",
			"Septembre", "Octobre", "Novembre", "Décembre"},
	heure = {"8h", "9h", "10h", "11h", "13h", "14", "15", "16h", "17h"},
	annees = new String [Temps.nbAnnnee];
	
	
	public Calendrier(PanelFenetre pfp ,PanelFenetre pfe, LocalDate date) {
		this.pfp = pfp;
		this.pfe = pfe;
		initialiseComboBoxAnnee();
		initialiseComboBoxMois();
		initialiseComboBoxJour();
		initialiseComboBoxHeure();
	}
	
	private void initialiseComboBoxAnnee() {
		int anneeActuel = Temps.getAnnee();
		 for (int i=0; i<annees.length; i++) {
			 annees[i] = Integer.toString(anneeActuel+i);
		 }
		 cba = new JComboBox<String>(annees);
		 cba.setSelectedIndex(0);
		 cba.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					metAJourCalendrier();
				}
			});	  
	}
	
	private void initialiseComboBoxMois() {
		cbm = new JComboBox<String>(mois);
		cbm.setSelectedIndex(Temps.getIndexMois()-1);
		cbm.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				metAJourCalendrier();
			}
		});	
	}
	
	private void initialiseComboBoxJour() {
		int annee = Integer.parseInt((String) cba.getSelectedItem());
		int mois = cbm.getSelectedIndex()+1;
		int nbJour = Temps.getJourMois(annee, mois);
	    jours = new String [nbJour];
		for (int i=0; i<nbJour; i++) {
			jours[i] = Integer.toString(i+1);
		}
		cbj = new JComboBox<String>(jours);
		cbj.setSelectedIndex(Temps.getIndexJour()-1);
	}

	private void adapteComboBoxJour() {
		int index = cbj.getSelectedIndex();
		int annee = Integer.parseInt((String) cba.getSelectedItem());
		int mois = cbm.getSelectedIndex()+1;
		int nbJour = Temps.getJourMois(annee, mois);
	    jours = new String [nbJour];
		for (int i=0; i<nbJour; i++) {
			jours[i] = Integer.toString(i+1);
		}
		cbj = new JComboBox<String>(jours);
		if (index >= jours.length) {
			index = jours.length-1;
		}
		cbj.setSelectedIndex(index);
	}
	
	private void initialiseComboBoxHeure() {
		cbh = new JComboBox<String>(heure);
		cbh.setSelectedIndex(0);
	}

	public void metAJourCalendrier() {
		adapteComboBoxJour();
		pfp.maj(pfe);
	}
	
	
	public JComboBox<String> getComboBoxAnnee(){
		return cba;
	}
	public JComboBox<String> getComboBoxMois(){
		return cbm;
	}
	public JComboBox<String> getComboBoxJour(){
		return cbj;
	}
	public JComboBox<String> getComboBoxHeure(){
		return cbh;
	}
	
	public LocalDate getDate() {
		int jour = Integer.parseInt((String) cbj.getSelectedItem());
		int mois = cbm.getSelectedIndex()+1;
		int annee = Integer.parseInt((String) cba.getSelectedItem());

		 return Temps.creerLaDate(jour, mois, annee);
	}

	public int getTemps() {
		String h = (String) cbh.getSelectedItem();
        String[] regex = h.split("h", 2); 
        int heure = Integer.parseInt(regex[0]);
		 return heure;
	}

	
	
	
	
	
}
