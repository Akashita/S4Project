package DebugFenetre;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Window;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Entreprise;
import Ressource.Personne;
import SQL.JavaSQLPersonne;

public class PanelDebugAjout extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	public static final int PERSONNE = 0, SALLE = 1, DOMAINE = 2;
	private int type;
	private Window w;
	public PanelDebugAjout(Entreprise entreprise,Window w, int type) {
		this.w = w;
		this.entreprise = entreprise;
		this.type = type;
		if (type == PERSONNE) {
			this.setLayout(new GridLayout(5,2));
			Label nomLabel = new Label("nom : ");
			TextField nom = new TextField(20);
			this.add(nomLabel);
			this.add(nom);
			
			Label prenomLabel = new Label("prenom : ");
			TextField prenom = new TextField(20);
			this.add(prenomLabel);
			this.add(prenom);
			
			
			Label roleLabel = new Label("Role : ");
			final Choice  roleChoix = new Choice();
			
			
			roleChoix.addItem(Personne.ADMINISTRATEUR);
			roleChoix.addItem(Personne.CHEFDEPROJET);
			roleChoix.addItem(Personne.COLLABORATEUR);
			roleChoix.addItem(Personne.DEBUG);

			
			roleChoix.select(Personne.COLLABORATEUR);
						
			this.add(roleLabel);
			this.add(roleChoix);

			
			Label motDePasseLabel = new Label("mot de passe  : ");
			TextField motDePasse= new TextField(20);
			this.add(motDePasseLabel);
			this.add(motDePasse);
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugAjout(w,nom,prenom,roleChoix,motDePasse,ActionDebugAjout.PERSONNE,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
		else if (type == this.SALLE) {
			this.setLayout(new GridLayout(4,2));
			Label numeroLabel = new Label("numero : ");
			TextField numero = new TextField(20);
			this.add(numeroLabel);
			this.add(numero);
			
			Label nomLabel = new Label("nom : ");
			TextField nom = new TextField(20);
			this.add(nomLabel);
			this.add(nom);
			
			Label placeLabel = new Label("place : ");
			TextField place = new TextField(20);
			this.add(placeLabel);
			this.add(place);
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugAjout(w,numero,nom,place,ActionDebugAjout.SALLE,entreprise));
			this.add(okLabel);
			this.add(ok);

			
		}
		else if (type == this.DOMAINE) {
			this.setLayout(new GridLayout(2,2));
			Label nomLabel = new Label("nom : ");
			TextField nom = new TextField(20);
			this.add(nomLabel);
			this.add(nom);
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugAjout(w,nom,ActionDebugAjout.DOMAINE,entreprise));
			this.add(okLabel);
			this.add(ok);

			
		}
	}
	
}