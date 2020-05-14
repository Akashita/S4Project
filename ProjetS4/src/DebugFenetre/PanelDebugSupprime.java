package DebugFenetre;

import java.awt.Button;
import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Window;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JPanel;

import GestionTicket.Ticket;
import Model.Entreprise;
import Ressource.Calculateur;
import Ressource.Competence;
import Ressource.Personne;
import SQL.JavaSQLCalculateur;
import SQL.JavaSQLCompetence;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLTicket;

public class PanelDebugSupprime extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	public static final int PERSONNE = 0, SALLE = 1, DOMAINE = 2, TICKET = 3, COMPETENCE = 4, CALCULATEUR = 5;
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
		else if (type == TICKET) {
			this.setLayout(new GridLayout(2,2));
			
			Label ticketLabel = new Label("id : ");
			final Choice  ticketChoix = new Choice();
			
			ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();
			try {
				ticketTab = JavaSQLTicket.affiche();

				for (int i = 0; i < ticketTab.size(); i++) {
					ticketChoix.addItem(ticketTab.get(i).toString());
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

									
			this.add(ticketLabel);
			this.add(ticketChoix);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,ticketChoix,ActionDebugSupprime.TICKET,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
		
		else if (type == COMPETENCE) {
			this.setLayout(new GridLayout(2,2));
			
			Label competenceLabel = new Label("id : ");
			final Choice  competenceChoix = new Choice();
			
			ArrayList<Competence> competenceTab = new ArrayList<Competence>();
			try {
				competenceTab = JavaSQLCompetence.affiche();

				for (int i = 0; i < competenceTab.size(); i++) {
					competenceChoix.addItem(competenceTab.get(i).creeAffiche());
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

									
			this.add(competenceLabel);
			this.add(competenceChoix);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,competenceChoix,ActionDebugSupprime.COMPETENCE,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
		
		else if (type == CALCULATEUR) {
			this.setLayout(new GridLayout(2,2));
			
			Label calculateurLabel = new Label("calculateur a supprime : ");
			final Choice  calculateurChoix = new Choice();
			
			ArrayList<Calculateur> calculateurTab = new ArrayList<Calculateur>();
			try {
				calculateurTab = JavaSQLCalculateur.affiche();

				for (int i = 0; i < calculateurTab.size(); i++) {
					calculateurChoix.addItem(calculateurTab.get(i).creeAffiche());
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

									
			this.add(calculateurLabel);
			this.add(calculateurChoix);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,calculateurChoix,ActionDebugSupprime.CALCULATEUR,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
	}
	
}