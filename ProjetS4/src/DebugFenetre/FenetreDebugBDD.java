package DebugFenetre;

import java.awt.Button;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import Fenetre.FermerFenetre;
import Model.Entreprise;

public class FenetreDebugBDD extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	
	public FenetreDebugBDD(Entreprise entreprise) {	
		this.entreprise = entreprise;
		this.setTitle("DebugBDD");
		this.setSize(LARGEUR,HAUTEUR);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.addWindowListener(new FermerFenetre(this));

		
		PanelDebugMenuBarre panel = new PanelDebugMenuBarre(entreprise, this);
		this.add(panel);

		
		
		this.setVisible(true);	
		setLayout(new FlowLayout());
}
}