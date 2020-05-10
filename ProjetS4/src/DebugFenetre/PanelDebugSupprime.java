package DebugFenetre;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Window;

import javax.swing.JPanel;

import Model.Entreprise;

public class PanelDebugSupprime extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	public static final int PERSONNE = 0, SALLE = 1, DOMAINE = 2;
	private int type;
	private Window w;
	public PanelDebugSupprime(Entreprise entreprise,Window w, int type) {
		this.w = w;
		this.entreprise = entreprise;
		this.type = type;
		if (type == PERSONNE) {
			this.setLayout(new GridLayout(2,2));
			Label numeroLabel = new Label("numero de la personne a supprimer : ");
			TextField numero = new TextField(20);
			this.add(numeroLabel);
			this.add(numero);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,numero,ActionDebugSupprime.PERSONNE,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
		else if (type == this.SALLE) {
			this.setLayout(new GridLayout(2,2));
			Label numeroLabel = new Label("numero de la salle a supprimer : ");
			TextField numero = new TextField(20);
			this.add(numeroLabel);
			this.add(numero);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,numero,ActionDebugSupprime.SALLE,entreprise));
			this.add(okLabel);
			this.add(ok);

			
		}
		else if (type == this.DOMAINE) {
			this.setLayout(new GridLayout(2,2));
			Label nomLabel = new Label("nom du domaine a supprimer : ");
			TextField nom= new TextField(20);
			this.add(nomLabel);
			this.add(nom);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,nom,ActionDebugSupprime.DOMAINE,entreprise));
			this.add(okLabel);
			this.add(ok);

			
		}
	}
	
}