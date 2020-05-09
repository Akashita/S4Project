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
	public static final int PERSONNE = 0, SALLE = 1;
	private int type;
	private Window w;
	public PanelDebugSupprime(Entreprise entreprise,Window w, int type) {
		this.w = w;
		this.entreprise = entreprise;
		this.type = type;
		if (type == 0) {

			
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
	}
	
}