package DebugFenetre;

import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import Ressource.Salle;
import SQL.JavaSQLActivite;
import SQL.JavaSQLCalculateur;
import SQL.JavaSQLCompetence;
import SQL.JavaSQLDomaine;
import SQL.JavaSQLListeDomaine;
import SQL.JavaSQLMateriel;
import SQL.JavaSQLParticipe;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLProjet;
import SQL.JavaSQLSalle;
import SQL.JavaSQLTicket;

public class PanelDebugAffichage extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	public static final int PERSONNE = 0, SALLE = 1, DOMAINE = 2, TICKET = 3, COMPETENCE = 4, CALCULATEUR = 5, MATERIEL = 6,PROJET = 7, ACTIVITE = 8, PARTICIPESALARIE = 9,
			PARTICIPESALLE = 10, PARTICIPECALCUL = 11, LISTEDOMAINE = 12;
	private int type;
	public PanelDebugAffichage(Entreprise entreprise, int type) {
		this.entreprise = entreprise;
		this.type = type;
		
		if (type == PERSONNE) {
			ArrayList<Personne> personneTab = new ArrayList<Personne>();
			try {
				personneTab = JavaSQLPersonne.affiche();
				setLayout(new GridLayout(personneTab.size(),1));

				for (int i = 0; i < personneTab.size(); i++) {
					JLabel personne = new JLabel(personneTab.get(i).creeAffiche());
					this.add(personne);

			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if (type == SALLE) {
			ArrayList<Salle> personneSalle = new ArrayList<Salle>();
			try {
				personneSalle = JavaSQLSalle.affiche();
				setLayout(new GridLayout(personneSalle.size(),1));

				for (int i = 0; i < personneSalle.size(); i++) {
					JLabel salle = new JLabel(personneSalle.get(i).toString());
					this.add(salle);

			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if (type == DOMAINE) {
			ArrayList<String> tagtab = new ArrayList<String>();
			try {
				tagtab = JavaSQLDomaine.affiche();
				setLayout(new GridLayout(tagtab.size(),1));

				for (int i = 0; i < tagtab.size(); i++) {
					JLabel tag = new JLabel(tagtab.get(i));
					this.add(tag);

			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (type == TICKET) {
            ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();
            try {
                ticketTab = JavaSQLTicket.affiche();
                setLayout(new GridLayout(ticketTab.size(),1));

                for (int i = 0; i < ticketTab.size(); i++) {
                    JLabel ticket = new JLabel(ticketTab.get(i).toString());
                    this.add(ticket);

            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		else if (type == COMPETENCE) {
            ArrayList<Competence> competenceTab = new ArrayList<Competence>();
            try {
            	competenceTab = JavaSQLCompetence.affiche();
                setLayout(new GridLayout(competenceTab.size(),1));

                for (int i = 0; i < competenceTab.size(); i++) {
                    JLabel competence = new JLabel(competenceTab.get(i).creeAffiche());
                    this.add(competence);

            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		else if (type == CALCULATEUR) {
            ArrayList<Calculateur> calculateurTab = new ArrayList<Calculateur>();
            try {
            	calculateurTab = JavaSQLCalculateur.affiche();
                setLayout(new GridLayout(calculateurTab.size(),1));

                for (int i = 0; i < calculateurTab.size(); i++) {
                    JLabel calculateur = new JLabel(calculateurTab.get(i).creeAffiche());
                    this.add(calculateur);

            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		else if (type == MATERIEL) {
            ArrayList<Materiel> materielTab = new ArrayList<Materiel>();
            try {
            	materielTab = JavaSQLMateriel.affiche();
                setLayout(new GridLayout(materielTab.size(),1));

                for (int i = 0; i < materielTab.size(); i++) {
                    JLabel materiel = new JLabel(materielTab.get(i).creeAffiche());
                    this.add(materiel);

            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		else if (type == PROJET) {
            ArrayList<Projet> projetTab = new ArrayList<Projet>();
            try {
            	projetTab = JavaSQLProjet.affiche();
                setLayout(new GridLayout(projetTab.size(),1));

                for (int i = 0; i < projetTab.size(); i++) {
                    JLabel projetLabel = new JLabel(projetTab.get(i).creeAffiche());
                    this.add(projetLabel);

            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		else if (type == ACTIVITE) {
            ArrayList<Activite> activiteTab = new ArrayList<Activite>();
            try {
            	activiteTab = JavaSQLActivite.affiche();
                setLayout(new GridLayout(activiteTab.size(),1));

                for (int i = 0; i < activiteTab.size(); i++) {
                    JLabel projetLabel = new JLabel(activiteTab.get(i).creeAffiche());
                    this.add(projetLabel);

            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		else if (type == PARTICIPESALARIE) {
            ArrayList<int[]> idParticipe = new ArrayList<int[]>();
            try {
            	idParticipe = JavaSQLParticipe.afficheParticipeSalarie();
                setLayout(new GridLayout(idParticipe.size(),2));

                for (int i = 0; i < idParticipe.size(); i++) {
                    JLabel idLabel = new JLabel("" + idParticipe.get(i)[0]);
                    this.add(idLabel);

                    JLabel projetLabel = new JLabel("" + idParticipe.get(i)[1]);
                    this.add(projetLabel);

            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		else if (type == PARTICIPESALLE) {
            ArrayList<int[]> idParticipe = new ArrayList<int[]>();
            try {
            	idParticipe = JavaSQLParticipe.afficheParticipeSalle();
                setLayout(new GridLayout(idParticipe.size(),2));

                for (int i = 0; i < idParticipe.size(); i++) {
                	  JLabel idLabel = new JLabel("" + idParticipe.get(i)[0]);
                      this.add(idLabel);

                      JLabel projetLabel = new JLabel("" + idParticipe.get(i)[1]);
                      this.add(projetLabel);


            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

		
		else if (type == PARTICIPECALCUL) {
            ArrayList<int[]> idParticipe = new ArrayList<int[]>();
            try {
            	idParticipe = JavaSQLParticipe.afficheParticipeCalcul();
                setLayout(new GridLayout(idParticipe.size(),2));

                for (int i = 0; i < idParticipe.size(); i++) {
                	  JLabel idLabel = new JLabel("" + idParticipe.get(i)[0]);
                      this.add(idLabel);

                      JLabel projetLabel = new JLabel("" + idParticipe.get(i)[1]);
                      this.add(projetLabel);


            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		else if (type == LISTEDOMAINE) {
			ArrayList<String[]> liste = new ArrayList<String[]>();
			try {
            	liste = JavaSQLListeDomaine.recupere();
                setLayout(new GridLayout(liste.size(),1));

                for (int i = 0; i < liste.size(); i++) {

                      JLabel projetLabel = new JLabel(" activité : " + liste.get(i)[0] + "tag : " + liste.get(i)[1]);
                      this.add(projetLabel);


            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

		}

    }

}