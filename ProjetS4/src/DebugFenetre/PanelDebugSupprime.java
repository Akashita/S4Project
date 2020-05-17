package DebugFenetre;

import java.awt.Button;
import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Window;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GestionTicket.Ticket;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;
import Ressource.Calculateur;
import Ressource.Competence;
import Ressource.Materiel;
import Ressource.Personne;
import SQL.JavaSQLActivite;
import SQL.JavaSQLCalculateur;
import SQL.JavaSQLCompetence;
import SQL.JavaSQLDomaine;
import SQL.JavaSQLListeDomaine;
import SQL.JavaSQLMateriel;
import SQL.JavaSQLParticipe;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLProjet;
import SQL.JavaSQLTicket;

public class PanelDebugSupprime extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	public static final int PERSONNE = 0, SALLE = 1, DOMAINE = 2, TICKET = 3, COMPETENCE = 4, CALCULATEUR = 5, MATERIEL = 6, PROJET = 7, ACTIVITE = 8,
			PARTICIPESALARIE = 9, PARTICIPESALLE = 10, PARTICIPECALCUL = 11, LISTEDOMAINE = 12;
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
		else if (type == DOMAINE) {
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
		
		else if (type == MATERIEL) {
			this.setLayout(new GridLayout(2,2));
			
			Label materielLabel = new Label("materiel a supprime : ");
			final Choice  materielChoix = new Choice();
			
			ArrayList<Materiel> materielTab = new ArrayList<Materiel>();
			try {
				materielTab = JavaSQLMateriel.affiche();

				for (int i = 0; i < materielTab.size(); i++) {
					materielChoix.addItem(materielTab.get(i).creeAffiche());
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

									
			this.add(materielLabel);
			this.add(materielChoix);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,materielChoix,ActionDebugSupprime.MATERIEL,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
		
		
		else if (type == PROJET) {
			this.setLayout(new GridLayout(2,2));
			
			Label projetLabel = new Label("projet a supprime : ");
			final Choice  projetChoix = new Choice();
			
			ArrayList<Projet> projetTab = new ArrayList<Projet>();
			try {
				projetTab = JavaSQLProjet.affiche();

				for (int i = 0; i < projetTab.size(); i++) {
					projetChoix.addItem(projetTab.get(i).creeAffiche());
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

									
			this.add(projetLabel);
			this.add(projetChoix);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,projetChoix,ActionDebugSupprime.PROJET,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
		
		else if (type == ACTIVITE) {
			this.setLayout(new GridLayout(2,2));
			
			Label activiteLabel = new Label("activite a supprime : ");
			final Choice  activiteChoix = new Choice();
			
			ArrayList<Activite> activiteTab = new ArrayList<Activite>();
			try {
				activiteTab = JavaSQLActivite.affiche();

				for (int i = 0; i < activiteTab.size(); i++) {
					activiteChoix.addItem(activiteTab.get(i).creeAffiche());
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

									
			this.add(activiteLabel);
			this.add(activiteChoix);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,activiteChoix,ActionDebugSupprime.ACTIVITE,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
		
		else if (type == PARTICIPESALARIE) {
			this.setLayout(new GridLayout(2,2));
			
			Label idLabel = new Label("participeSalarie a supprime : ");
			final Choice  idChoix = new Choice();
			
			ArrayList<int[]> idParticipe = new ArrayList<int[]>();
            try {
            	idParticipe = JavaSQLParticipe.afficheParticipeSalarie();

                for (int i = 0; i < idParticipe.size(); i++) {
                	idChoix.add("" + idParticipe.get(i)[0] + idParticipe.get(i)[1]);
                	  


            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			

									
			this.add(idLabel);
			this.add(idChoix);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,idChoix,ActionDebugSupprime.ACTIVITE,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
		
		else if (type == PARTICIPESALLE) {
			this.setLayout(new GridLayout(2,2));
			
			Label idLabel = new Label("participeSalle a supprime : ");
			final Choice  idChoix = new Choice();
			
			ArrayList<int[]> idParticipe = new ArrayList<int[]>();
            try {
            	idParticipe = JavaSQLParticipe.afficheParticipeSalle();

                for (int i = 0; i < idParticipe.size(); i++) {
                	idChoix.add("" + idParticipe.get(i)[0] + idParticipe.get(i)[1]);

            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			

									
			this.add(idLabel);
			this.add(idChoix);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,idChoix,ActionDebugSupprime.ACTIVITE,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
		
		else if (type == PARTICIPECALCUL) {
			this.setLayout(new GridLayout(2,2));
			
			Label idLabel = new Label("participeCalcul a supprime : ");
			final Choice  idChoix = new Choice();
			
			ArrayList<int[]> idParticipe = new ArrayList<int[]>();
            try {
            	idParticipe = JavaSQLParticipe.afficheParticipeCalcul();

                for (int i = 0; i < idParticipe.size(); i++) {
                	idChoix.add("" + idParticipe.get(i)[0] + idParticipe.get(i)[1]);

            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			

									
			this.add(idLabel);
			this.add(idChoix);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,idChoix,ActionDebugSupprime.ACTIVITE,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
		
		else if (type == LISTEDOMAINE) {
			this.setLayout(new GridLayout(2,2));
			

			Label tagLabel = new Label("tag a supprimer: ");
			final Choice  tagChoix = new Choice();
			
			ArrayList<String[]> tagTab = new ArrayList<String[]>();
			try {
				tagTab = JavaSQLListeDomaine.recupere();

				for (int i = 0; i < tagTab.size(); i++) {
					tagChoix.addItem("" + tagTab.get(i)[0] + tagTab.get(i)[1]);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

									
			this.add(tagLabel);
			this.add(tagChoix);
			
			
			Label okLabel = new Label("ok : ");
			Button ok = new Button("ok");
			ok.addActionListener(new ActionDebugSupprime(w,tagChoix,ActionDebugSupprime.LISTEDOMAINE,entreprise));
			this.add(okLabel);
			this.add(ok);
			
		}
	}
	
}