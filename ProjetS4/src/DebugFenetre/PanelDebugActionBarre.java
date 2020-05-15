package DebugFenetre;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JPanel;

import Model.Entreprise;

public class PanelDebugActionBarre extends JPanel{
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private Window w;
	private int type;
	public static final int PERSONNE = 0, SALLE = 1,DOMAINE = 2, TICKET = 3, COMPETENCE = 4, CALCULATEUR = 5, MATERIEL = 6, PROJET = 7,ACTIVITE = 8;

	public PanelDebugActionBarre(Entreprise entreprise,Window w,int type) {
		this.entreprise = entreprise;
		this.w= w;
		this.type = type;
		setLayout(new FlowLayout());


		if (type == PERSONNE) {
			
		
		Button affiche = new Button("Affiche Personne");
		Button ajout = new Button("Ajout Personne");
		Button supprime = new Button("Suppression Personne");


		affiche.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PERSONNE));
		ajout.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PERSONNEAJOUT));
		supprime.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PERSONNESUPPRIME));


		
		this.add(affiche);		
		this.add(ajout);
		this.add(supprime);

		}
		
		else if (type == SALLE) {
			Button affiche = new Button("Affiche Salle");
			Button ajout = new Button("Ajout Salle");
			Button supprime = new Button("Suppression Salle");


			affiche.addActionListener(new NavigationListener(w,entreprise,NavigationListener.SALLE));
			ajout.addActionListener(new NavigationListener(w,entreprise,NavigationListener.SALLEAJOUT));
			supprime.addActionListener(new NavigationListener(w,entreprise,NavigationListener.SALLESUPPRIME));


			
			this.add(affiche);		
			this.add(ajout);
			this.add(supprime);

		}
		
		else if (type == this.DOMAINE) {
			Button affiche = new Button("Affiche Domaine");
			Button ajout = new Button("Ajout Domaine");
			Button supprime = new Button("Suppression Domaine");


			affiche.addActionListener(new NavigationListener(w,entreprise,NavigationListener.DOMAINE));
			ajout.addActionListener(new NavigationListener(w,entreprise,NavigationListener.DOMAINEAJOUT));
			supprime.addActionListener(new NavigationListener(w,entreprise,NavigationListener.DOMAINESUPPRIME));


			
			this.add(affiche);		
			this.add(ajout);
			this.add(supprime);

		}
		
		else if (type == TICKET) {
			Button affiche = new Button("Affiche Ticket");
			Button ajout = new Button("Ajout Ticket");
			Button supprime = new Button("Suppression Ticket");


			affiche.addActionListener(new NavigationListener(w,entreprise,NavigationListener.TICKET));
			ajout.addActionListener(new NavigationListener(w,entreprise,NavigationListener.TICKETAJOUT));
			supprime.addActionListener(new NavigationListener(w,entreprise,NavigationListener.TICKETSUPPRIME));


			
			this.add(affiche);		
			this.add(ajout);
			this.add(supprime);

		}
		
		
		else if (type == COMPETENCE) {
			Button affiche = new Button("Affiche Competence");
			Button ajout = new Button("Ajout Competence");
			Button supprime = new Button("Suppression Competence");


			affiche.addActionListener(new NavigationListener(w,entreprise,NavigationListener.COMPETENCE));
			ajout.addActionListener(new NavigationListener(w,entreprise,NavigationListener.COMPETENCEAJOUT));
			supprime.addActionListener(new NavigationListener(w,entreprise,NavigationListener.COMPETENCESUPPRIME));


			
			this.add(affiche);		
			this.add(ajout);
			this.add(supprime);

		}
		else if (type == CALCULATEUR) {
			Button affiche = new Button("Affiche calculateur");
			Button ajout = new Button("Ajout calculateur");
			Button supprime = new Button("Suppression calculateur");


			affiche.addActionListener(new NavigationListener(w,entreprise,NavigationListener.CALCULATEUR));
			ajout.addActionListener(new NavigationListener(w,entreprise,NavigationListener.CALCULATEURAJOUT));
			supprime.addActionListener(new NavigationListener(w,entreprise,NavigationListener.CALCULATEURSUPPRIME));


			
			this.add(affiche);		
			this.add(ajout);
			this.add(supprime);

		}
		
		else if (type == MATERIEL) {
			Button affiche = new Button("Affiche materiel");
			Button ajout = new Button("Ajout materiel");
			Button supprime = new Button("Suppression materiel");


			affiche.addActionListener(new NavigationListener(w,entreprise,NavigationListener.MATERIEL));
			ajout.addActionListener(new NavigationListener(w,entreprise,NavigationListener.MATERIELAJOUT));
			supprime.addActionListener(new NavigationListener(w,entreprise,NavigationListener.MATERIELSUPPRIME));


			
			this.add(affiche);		
			this.add(ajout);
			this.add(supprime);

		}
		
		else if (type == PROJET) {
			Button affiche = new Button("Affiche projet");
			Button ajout = new Button("Ajout projet");
			Button supprime = new Button("Suppression projet");


			affiche.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PROJET));
			ajout.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PROJETAJOUT));
			supprime.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PROJETSUPPRIME));


			
			this.add(affiche);		
			this.add(ajout);
			this.add(supprime);

		}
		
		else if (type == ACTIVITE) {
			Button affiche = new Button("Affiche activite");
			Button ajout = new Button("Ajout activite");
			Button supprime = new Button("Suppression activite");


			affiche.addActionListener(new NavigationListener(w,entreprise,NavigationListener.ACTIVITE));
			ajout.addActionListener(new NavigationListener(w,entreprise,NavigationListener.ACTIVITEAJOUT));
			supprime.addActionListener(new NavigationListener(w,entreprise,NavigationListener.ACTIVITESUPPRIME));


			
			this.add(affiche);		
			this.add(ajout);
			this.add(supprime);

		}
		this.setVisible(true);	
	}
}
