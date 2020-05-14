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

import GestionTicket.Ticket;
import Model.Entreprise;
import Ressource.Competence;
import Ressource.Domaine;
import Ressource.Personne;
import Ressource.Salle;
import SQL.JavaSQLDomaine;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLSalle;

public class PanelDebugAjout extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	public static final int PERSONNE = 0, SALLE = 1, DOMAINE = 2, TICKET = 3, COMPETENCE = 4, CALCULATEUR = 5, MATERIEL = 6, PROJET = 7;
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
		else if (type == SALLE) {
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
		else if (type == DOMAINE) {
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
		
		else if (type == TICKET) {
			this.setLayout(new GridLayout(6,2));
			
			Label sujetLabel = new Label("sujet : ");
			TextField sujet = new TextField(20);
			this.add(sujetLabel);
			this.add(sujet);
			
			Label messageLabel = new Label("message : ");
			TextField message= new TextField(20);
			this.add(messageLabel);
			this.add(message);
			
			Label actionLabel = new Label("action : ");
			final Choice action= new Choice();
			action.addItem("libere");
			action.addItem("transfert");
			action.addItem("message");


			
			
			this.add(actionLabel);
			this.add(action);
			
			Label PersonneLabel = new Label("Envoyeur : ");
			final Choice  PersonneChoix = new Choice();
			
			ArrayList<Personne> personneTab = new ArrayList<Personne>();
			try {
				personneTab = JavaSQLPersonne.affiche();

				for (int i = 0; i < personneTab.size(); i++) {
					PersonneChoix.addItem(personneTab.get(i).toString());
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

									
			this.add(PersonneLabel);
			this.add(PersonneChoix);

			Label Personne2Label = new Label("Receveur : ");
			final Choice  Personne2Choix = new Choice();
			
			for (int i = 0; i < personneTab.size(); i++) {
				Personne2Choix.addItem(personneTab.get(i).toString());
		}
			this.add(Personne2Label);
			this.add(Personne2Choix);


			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugAjout(w,sujet,message,action,PersonneChoix, Personne2Choix,ActionDebugAjout.TICKET,entreprise));
			this.add(okLabel);
			this.add(ok);

			
		}
		else if (type == COMPETENCE) {
			this.setLayout(new GridLayout(4,2));
			
			Label competenceLabel = new Label("competence : ");
			Choice competenceChoix = new Choice();
			
			try {
				ArrayList<String> domaineTab = JavaSQLDomaine.affiche();

				for (int i = 0; i < domaineTab.size(); i++) {
					competenceChoix.addItem(domaineTab.get(i).toString());
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.add(competenceLabel);
			this.add(competenceChoix);
			
			Label niveauLabel = new Label("niveau : ");
			Choice niveauChoix= new Choice();
			
			niveauChoix.addItem("debutant");
			niveauChoix.addItem("confirme");
			niveauChoix.addItem("expert");
			
			this.add(niveauLabel);
			this.add(niveauChoix);
			
			
			Label PersonneLabel = new Label("personne : ");
			final Choice  PersonneChoix = new Choice();
			
			ArrayList<Personne> personneTab = new ArrayList<Personne>();
			try {
				personneTab = JavaSQLPersonne.affiche();

				for (int i = 0; i < personneTab.size(); i++) {
					PersonneChoix.addItem(personneTab.get(i).creeAffiche());
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

									
			this.add(PersonneLabel);
			this.add(PersonneChoix);

			


			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugAjout(w,competenceChoix,niveauChoix,PersonneChoix, ActionDebugAjout.COMPETENCE,entreprise));
			this.add(okLabel);
			this.add(ok);

			
		}
		
		else if (type == CALCULATEUR) {
			this.setLayout(new GridLayout(3,2));
			Label nomLabel = new Label("nom : ");
			TextField nom = new TextField(20);
			this.add(nomLabel);
			this.add(nom);
			
			Label capaciteLabel = new Label("capacite : ");
			TextField capacite = new TextField(20);
			this.add(capaciteLabel);
			this.add(capacite);
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugAjout(w,nom,capacite,ActionDebugAjout.CALCULATEUR,entreprise));
			this.add(okLabel);
			this.add(ok);

			
		}
		
		else if (type == MATERIEL) {
			this.setLayout(new GridLayout(3,2));
			Label typeLabel = new Label("type : ");
			TextField typeMatos = new TextField(20);
			this.add(typeLabel);
			this.add(typeMatos);
			
			Label salleLabel = new Label("salle : ");
			final Choice  salleChoix = new Choice();
			
			ArrayList<Salle> salleTab = new ArrayList<Salle>();
			try {
				salleTab = JavaSQLSalle.affiche();

				for (int i = 0; i < salleTab.size(); i++) {
					salleChoix.addItem(salleTab.get(i).creeAffiche());
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

									
			this.add(salleLabel);
			this.add(salleChoix);
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugAjout(w,typeMatos,salleChoix,ActionDebugAjout.MATERIEL,entreprise));
			this.add(okLabel);
			this.add(ok);

			
		}
		
		else if (type == PROJET) {
			this.setLayout(new GridLayout(8,2));
			
			Label nomLabel = new Label("nom : ");
			TextField nom = new TextField(20);
			
			this.add(nomLabel);
			this.add(nom);
			
			
			Label prioriteLabel = new Label("priorite : ");
			TextField priorite= new TextField(20);
			
			this.add(prioriteLabel);
			this.add(priorite);
			
			Label jourLabel = new Label("jour deadLine : ");
			TextField jour = new TextField(20);
			
			this.add(jourLabel);
			this.add(jour);
			
			Label moisLabel = new Label("mois deadLine : ");
			TextField mois= new TextField(20);
			
			this.add(moisLabel);
			this.add(mois);
			
			Label anneeLabel = new Label("annee deadLine : ");
			TextField annee= new TextField(20);
			
			this.add(anneeLabel);
			this.add(annee);
			
			Label couleurLabel = new Label("couleur  : ");
			TextField couleur= new TextField(20);
			
			this.add(couleurLabel);
			this.add(couleur);
			
			
			Label chefLabel = new Label("chef de projet : ");
			final Choice  chefChoix = new Choice();
			
			ArrayList<Personne> chefTab = new ArrayList<Personne>();
			try {
				chefTab = JavaSQLPersonne.affiche();

				for (int i = 0; i < chefTab.size(); i++) {
					chefChoix.addItem(chefTab.get(i).creeAffiche());
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

									
			this.add(chefLabel);
			this.add(chefChoix);
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugAjout(w,nom,priorite,jour,mois,annee,couleur,chefChoix,ActionDebugAjout.PROJET,entreprise));
			this.add(okLabel);
			this.add(ok);

			
		}
	}
	
}