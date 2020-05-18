package DebugFenetre;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JPanel;

import Model.Entreprise;

public class PanelDebugMenuBarre extends JPanel{
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private Window w;
	public PanelDebugMenuBarre(Entreprise entreprise,Window w) {
		this.entreprise = entreprise;
		this.w= w;
		setLayout(new FlowLayout());


		
		Button menu = new Button("menu");
		Button personne = new Button("personne");
		Button salle = new Button("salle");
		Button domaine = new Button("domaine");
		Button ticket = new Button("ticket");
		Button competence = new Button("competence");
		Button calculateur = new Button("calculateur");
		Button materiel = new Button("materiel");
		Button projet = new Button("projet");
		Button activite = new Button("activite");
		Button participeSalarie = new Button("participeSalarie");
		Button participeCalcul = new Button("participeCalcul");
		Button ParticipeSalle = new Button("ParticipeSalle");
		Button listeDomaine = new Button("listeDomaine");


		menu.addActionListener(new NavigationListener(w,entreprise,NavigationListener.MENU));
		personne.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PERSONNE));
		salle.addActionListener(new NavigationListener(w,entreprise,NavigationListener.SALLE));
		domaine.addActionListener(new NavigationListener(w,entreprise,NavigationListener.DOMAINE));
		ticket.addActionListener(new NavigationListener(w,entreprise,NavigationListener.TICKET));
		competence.addActionListener(new NavigationListener(w,entreprise,NavigationListener.COMPETENCE));
		calculateur.addActionListener(new NavigationListener(w,entreprise,NavigationListener.CALCULATEUR));
		materiel.addActionListener(new NavigationListener(w,entreprise,NavigationListener.MATERIEL));
		projet.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PROJET));
		activite.addActionListener(new NavigationListener(w,entreprise,NavigationListener.ACTIVITE));
		participeSalarie.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PARTICIPESALARIE));
		participeCalcul.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PARTICIPECALCUL));
		ParticipeSalle.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PARTICIPESALLE));
		listeDomaine.addActionListener(new NavigationListener(w,entreprise,NavigationListener.LISTEDOMAINE));

		
		this.add(menu);		
		this.add(personne);
		this.add(salle);
		this.add(domaine);
		this.add(ticket);
		this.add(competence);
		this.add(calculateur);
		this.add(materiel);
		this.add(projet);
		this.add(activite);
		this.add(participeSalarie);
		this.add(participeCalcul);
		this.add(ParticipeSalle);
		this.add(listeDomaine);


		
		
		this.setVisible(true);	
	}
}
