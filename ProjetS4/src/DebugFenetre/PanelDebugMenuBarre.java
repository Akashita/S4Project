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



		

		menu.addActionListener(new NavigationListener(w,entreprise,NavigationListener.MENU));
		personne.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PERSONNE));
		salle.addActionListener(new NavigationListener(w,entreprise,NavigationListener.SALLE));
		domaine.addActionListener(new NavigationListener(w,entreprise,NavigationListener.DOMAINE));
		ticket.addActionListener(new NavigationListener(w,entreprise,NavigationListener.TICKET));




		
		this.add(menu);		
		this.add(personne);
		this.add(salle);
		this.add(domaine);
		this.add(ticket);



		
		
		this.setVisible(true);	
	}
}
