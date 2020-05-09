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

		menu.addActionListener(new NavigationListener(w,entreprise,NavigationListener.MENU));
		personne.addActionListener(new NavigationListener(w,entreprise,NavigationListener.PERSONNE));


		
		this.add(menu);		
		this.add(personne);
		
		
		this.setVisible(true);	
	}
}
