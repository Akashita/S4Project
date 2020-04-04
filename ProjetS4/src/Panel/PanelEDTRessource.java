package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import EcouteurEvenement.SourisSemaineListener;
import Model.CreneauHoraire;
import Model.Temps;
import Ressource.Ressource;

public class PanelEDTRessource extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ressource ressource;
	private CreneauHoraire [][] tableauCreneau;
	private int semaineSelectionner;
	
	private JSlider slide = new JSlider();
	
	private JPanel panelCompletListeSemaine = new JPanel();
	private JPanel panelListeSemaine = new JPanel();
	
	private JPanel panelCompletJourDeLasemaine = new JPanel();
	private JPanel panelJourDeLasemaine = new JPanel();
	
	private JPanel panelCompletSemaineCreneau = new JPanel();
	private JPanel panelSemaineCreneau = new JPanel();


	public PanelEDTRessource(Ressource resssource) {
		this.ressource = resssource;
		this.setLayout(new BorderLayout());
		this.add(afficherEmploiDuTemps(), BorderLayout.CENTER);
	}


	private JPanel afficherEmploiDuTemps() {
	semaineSelectionner = Temps.getSemaine();
	tableauCreneau = ressource.getSemaineEDT(Temps.getAnnee(), Temps.getSemaine());
	JPanel panelPrincipal = new JPanel();		
	panelPrincipal.setLayout(new BorderLayout());
	
	
	panelPrincipal.add(afficheHeure(), BorderLayout.WEST);
	
	JPanel panel = new JPanel();		
	panel.setLayout(new BorderLayout());
	
	panelPrincipal.add(panelCompletJourDeLasemaine, BorderLayout.NORTH);
	panelCompletJourDeLasemaine.setLayout(new BorderLayout());
	afficheJourDeLaSemaine();
	
	panelPrincipal.add(panelCompletSemaineCreneau, BorderLayout.CENTER);
	panelCompletSemaineCreneau.setLayout(new BorderLayout());
	afficheCreneauDeLaSemaine();
	
	panelPrincipal.add(panelCompletListeSemaine, BorderLayout.SOUTH);
	afficherListeSemaine();
	
	//panelPrincipal.add(panel);
	return panelPrincipal;
	}
	
	
	private void afficheJourDeLaSemaine() {
		panelCompletJourDeLasemaine.remove(panelJourDeLasemaine);
		panelJourDeLasemaine = new JPanel();
		int nbJour = tableauCreneau.length;
		panelJourDeLasemaine.setLayout(new GridLayout(0,nbJour));
		LocalDate[] jours = Temps.getJourSemaine(Temps.getAnnee(), semaineSelectionner);
		for (int i=0; i<nbJour; i++) {
			LocalDate jourActuel = jours[i];
			String jour = jourActuel.getDayOfWeek().toString();	
			String date = jourActuel.getDayOfMonth() + "/" + jourActuel.getMonthValue() + "/" + jourActuel.getYear();			
			panelJourDeLasemaine.add(creerLabelInterface(jour+" "+date, false));
		}
		panelCompletJourDeLasemaine.add(panelJourDeLasemaine,BorderLayout.CENTER);
		this.revalidate();
	}
	
	
	private JPanel afficheHeure() {
		JPanel panel = new JPanel();
		int nbHeure = (tableauCreneau[0].length+1);
		panel.setLayout(new GridLayout(nbHeure,0));
		for (int i=0; i<nbHeure; i++) {
			panel.add(creerLabelInterface(Integer.toString(i+8)+"h", true));
		}
		return panel;
	}
	
	
	private void afficherListeSemaine() {
		configureSlider();
		panelCompletListeSemaine.setLayout(new BoxLayout(panelCompletListeSemaine, BoxLayout.Y_AXIS));
		panelCompletListeSemaine.add(slide);
		changeSemaine();
	}
	
	private void configureSlider() {
		slide.setMinimum(0);
		slide.setMaximum(42);
	    slide.setPaintTicks(false);
	    slide.setPaintLabels(false);
	    int defaultPos = semaineSelectionner;
	    if(defaultPos <= 10) { defaultPos = 10;}
	    if(defaultPos >= 42) { defaultPos = 42;}
	    slide.setValue(defaultPos-5);
	    slide.addChangeListener(new ChangeListener(){
	        public void stateChanged(ChangeEvent event){
	        	changeSemaine();
	        	afficheJourDeLaSemaine();
	        }
	      });  
	}
	
	private void changeSemaine() {
		panelCompletListeSemaine.remove(panelListeSemaine);
		panelListeSemaine = new JPanel();
		int nbSemaine = 10;
		int ecart = slide.getValue();
		panelListeSemaine.setLayout(new GridLayout(0, nbSemaine));
		for (int i=1; i<=nbSemaine; i++) {
			panelListeSemaine.add(creerLabelInteractif(Integer.toString(i+ecart)));
		}
		panelCompletListeSemaine.add(panelListeSemaine);
		this.revalidate();
	}
	
	private JLabel creerLabelInteractif(String texte) {
		JLabel label = new JLabel(texte);
		label.addMouseListener(new SourisSemaineListener(this, label));
		if(semaineSelectionner == Integer.parseInt(texte)) {
			label.setOpaque(true);
			label.setBackground(Color.YELLOW);
		}
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
	
	public void selectionnerProjet(JLabel label) {
		semaineSelectionner = Integer.parseInt(label.getText());
		changeSemaine();
		afficheJourDeLaSemaine();
		tableauCreneau = ressource.getSemaineEDT(Temps.getAnnee(), semaineSelectionner);
		afficheCreneauDeLaSemaine();
	} 
	
	private void afficheCreneauDeLaSemaine() {
		panelCompletSemaineCreneau.remove(panelCompletSemaineCreneau);
		panelSemaineCreneau = new JPanel();
		int nbJour = tableauCreneau.length;
		panelSemaineCreneau.setLayout(new GridLayout(0, nbJour));
		for (int i=0; i<nbJour; i++) {
			panelSemaineCreneau.add(afficheJour(tableauCreneau[i]));
		}
		panelCompletSemaineCreneau.add(panelSemaineCreneau, BorderLayout.CENTER);
		this.revalidate();
	}
	
	private JPanel afficheJour(CreneauHoraire [] tableauCreneau) {
		JPanel panel = new JPanel();
		int nbHeure = tableauCreneau.length+1;
		panel.setLayout(new GridLayout(nbHeure,0));
		for (int i=0; i<nbHeure; i++) {
			if (i == 4) {
				panel.add(new JLabel("Pause déjeuner"));
			}
			else {
				int index = i;
				if (i > 4) {
					index --;
				}
				CreneauHoraire creneau = tableauCreneau[index];
				panel.add(creerLabelCreneau(creneau));				
			}
		}
		return panel;
	}
	
	
	private JLabel creerLabelCreneau(CreneauHoraire creneau) {
		JLabel label = new JLabel();
		if (creneau != null) {
			label.setText(creneau.getTitre());
			label.setOpaque(true);
			label.setBackground(Color.GREEN);			
		}
		else {
			label.setText("---");
			label.setOpaque(true);
			label.setBackground(Color.WHITE);			
		}
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return label;
	}
	
	
	
	private JPanel creerLabelInterface(String texte, boolean heure) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel label = new JLabel(texte);
		if (heure) {
			panel.add(label, BorderLayout.NORTH);
		}
		else {
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(label, BorderLayout.CENTER);
		}
		return panel ;
	}
	
	public int getIdRessource() {
		return ressource.getId();
	}
}
