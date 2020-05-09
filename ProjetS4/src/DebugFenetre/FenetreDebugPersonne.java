package DebugFenetre;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Fenetre.FermerFenetre;
import Model.Entreprise;
import Ressource.Personne;
import SQL.JavaSQLPersonne;

public class FenetreDebugPersonne  extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	
	public FenetreDebugPersonne(Entreprise entreprise){	
		this.entreprise = entreprise;
		
		this.setTitle("gestionBDD personne");
		this.setSize(LARGEUR,HAUTEUR);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.addWindowListener(new FermerFenetre(this));
		setLayout(new FlowLayout());

		PanelDebugMenuBarre panelBarre = new PanelDebugMenuBarre(entreprise, this);
		this.add(panelBarre);
		
		PanelDebugAffichage panelAffichage = new PanelDebugAffichage(entreprise,PanelDebugAffichage.PERSONNE);
		this.add(panelAffichage);

		
		TextField nom = new TextField(20);
		this.add(nom);
		this.setVisible(true);	
}
}