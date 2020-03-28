package Fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import EcouteurEvenement.SourisSemaineListener;
import Model.CreneauHoraire;
import Model.Entreprise;
import Model.Temps;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;

public class FenetreInfoRessource extends JDialog{
	private Entreprise entreprise;
	private Ressource ressource;
	private CreneauHoraire [][] tableauCreneau;
	private int semaineSelectionner;
	
	private JSlider slide = new JSlider();
	
	private JPanel panelSemaine = new JPanel();
	private JPanel panelListeSemaine = new JPanel();
	private JPanel panelCompletJourDeLasemaine = new JPanel();
	private JPanel panelJourDeLasemaine = new JPanel();
	private JPanel panelCompletSemaineCreneau = new JPanel();
	private JPanel panelSemaineCreneau = new JPanel();

	public FenetreInfoRessource(Entreprise entreprise, Ressource ressource) {
		super(entreprise.getFenetrePrincipale(), "Information de la ressource");
		this.entreprise = entreprise;
		this.ressource = ressource;
		this.setSize(800,770);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetre(this));
	    this.setResizable(false);
	    creationInterface();
		this.setVisible(true);

	}

	private void creationInterface() {
		this.setLayout(new BorderLayout());
		this.add(afficheInfoRessource(), BorderLayout.NORTH);
		this.add(afficherEmploiDuTemps(), BorderLayout.CENTER);
		this.revalidate();

	}
	
	private JPanel afficheInfoRessource() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Information de la ressource: "));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);	
		if(ressource.getType() == Ressource.PERSONNE) {
			panel.add(creerLabel("Nom: " + ((Personne) ressource).getPrenom() + " " + ressource.getNom()));
			panel.add(creerLabel("Compétence: "));			
	
		}
		if(ressource.getType() == Ressource.SALLE) {
			panel.add(creerLabel("Nom: " + ressource.getNom()));
			panel.add(creerLabel("Capacité: " + ((Salle)ressource).getCapacite()));			
		}
		if(ressource.getType() == Ressource.CALCULATEUR) {
			panel.add(creerLabel("Nom: " + ressource.getNom()));	
		}
		return panel;
	}

	
	private JLabel creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		//label.addMouseListener(new SourisRessourceListener(this, label));
		return label;
	}
	
	//============ Affichage de l'emploi du temps ========///
	
	private JPanel afficherEmploiDuTemps() {
		semaineSelectionner = Temps.getSemaine();
		tableauCreneau = ressource.getSemaineEDT(Temps.getAnnee(), Temps.getSemaine());
		JPanel panel = new JPanel();		
		panel.setLayout(new BorderLayout());
		
		panel.add(panelCompletJourDeLasemaine, BorderLayout.NORTH);
		panelCompletJourDeLasemaine.setLayout(new BorderLayout());
		afficheJourDeLaSemaine();
		
		panel.add(afficheHeure(tableauCreneau[0].length), BorderLayout.WEST);
		
		afficherListeSemaine();
		
		panel.add(panelCompletSemaineCreneau, BorderLayout.CENTER);
		panelCompletSemaineCreneau.setLayout(new BorderLayout());
		afficheCreneauDeLaSemaine();
		
		return panel;
	}
	

	private void afficheJourDeLaSemaine() {
		panelCompletJourDeLasemaine.remove(panelJourDeLasemaine);
		panelJourDeLasemaine = new JPanel();
		int nbJour = tableauCreneau.length;
		panelJourDeLasemaine.setLayout(new GridLayout(nbJour,0));
		LocalDate[] jours = Temps.getJourSemaine(Temps.getAnnee(), semaineSelectionner);
		panelJourDeLasemaine.setLayout(new GridLayout(2,nbJour));
		for (int i=0; i<nbJour; i++) {
			LocalDate jourActuel = jours[i];
			String jour = jourActuel.getDayOfWeek().toString();			
			panelJourDeLasemaine.add(creerLabelInterface(jour));
		}
		for (int i=0; i<nbJour; i++) {
			LocalDate jourActuel = jours[i];
			String date = jourActuel.getDayOfMonth() + "/" + jourActuel.getMonthValue() + "/" + jourActuel.getYear();			
			panelJourDeLasemaine.add(creerLabelInterface(date));
		}
		panelCompletJourDeLasemaine.add(panelJourDeLasemaine,BorderLayout.CENTER);
		this.revalidate();
	}

	
	private JPanel afficheHeure(int nbHeure) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(nbHeure+2,0));
		for (int i=0; i<=nbHeure; i++) {
			panel.add(creerLabelInterface(Integer.toString(i+8)+"h"));
		}
		return panel;
	}

	
	private void afficherListeSemaine() {
		configureSlider();
		this.add(panelSemaine, BorderLayout.SOUTH);
		panelSemaine.setLayout(new BoxLayout(panelSemaine, BoxLayout.Y_AXIS));
		panelSemaine.add(slide);
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
		panelSemaine.remove(panelListeSemaine);
		panelListeSemaine = new JPanel();
		int nbSemaine = 10;
		int ecart = slide.getValue();
		panelListeSemaine.setLayout(new GridLayout(0, nbSemaine));
		for (int i=1; i<=nbSemaine; i++) {
			panelListeSemaine.add(creerLabelInteractif(Integer.toString(i+ecart)));
		}
		panelSemaine.add(panelListeSemaine);
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
		int nbHeure = tableauCreneau.length;
		panel.setLayout(new GridLayout(nbHeure+1,0));
		for (int i=0; i<nbHeure; i++) {
			if (i == 5) {
				panel.add(creerLabelCreneau(creneau));
			}
			else {
				CreneauHoraire creneau = tableauCreneau[i];
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
		return label;
	}
	
	

	private JLabel creerLabelInterface(String texte) {
		JLabel label = new JLabel(texte);
		label.setBackground(Color.GRAY);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return label;
	}

	public int getIdRessource() {
		return ressource.getId();
	}
}
