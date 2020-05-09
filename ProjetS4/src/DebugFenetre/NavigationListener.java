package DebugFenetre;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.Entreprise;



public class NavigationListener  implements ActionListener{
	private Window w;
	private int direction;
	private Entreprise entreprise;
	public static int PERSONNE = 0, MENU = 1 ;
	public NavigationListener(Window w,Entreprise entreprise, int direction) {
		this.w = w;
		this.direction = direction;
		this.entreprise = entreprise;

	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (direction == 0){
			new FenetreDebugPersonne(entreprise);
		
		}
		else if (direction == 1) {
			new FenetreDebugBDD(entreprise);
		}
		w.dispose();

	}
}
