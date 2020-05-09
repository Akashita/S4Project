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
	public static final int PERSONNE = 0, SALLE = 1;

	public PanelDebugActionBarre(Entreprise entreprise,Window w,int type) {
		this.entreprise = entreprise;
		this.w= w;
		this.type = type;
		setLayout(new FlowLayout());


		if (type == this.PERSONNE) {
			
		
		Button affiche = new Button("Affiche Personne");
		Button ajout = new Button("Ajout Personne");
		Button supprime = new Button("Suppression Personne");


		affiche.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PERSONNE));
		//ajout.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PERSONNE));
		//supprime.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PERSONNE));


		
		this.add(affiche);		
		this.add(ajout);
		this.add(supprime);

		}
		
		else if (type == this.SALLE) {
			Button affiche = new Button("Affiche Salle");
			Button ajout = new Button("Ajout Salle");
			Button supprime = new Button("Suppression Salle");


			affiche.addActionListener(new NavigationListener(w,entreprise,NavigationListener.SALLE));
			ajout.addActionListener(new NavigationListener(w,entreprise,NavigationListener.SALLEAJOUT));
			//supprime.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PERSONNE));


			
			this.add(affiche);		
			this.add(ajout);
			this.add(supprime);

		}
		
		this.setVisible(true);	
	}
}
