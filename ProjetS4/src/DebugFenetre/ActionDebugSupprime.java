package DebugFenetre;

import java.awt.Choice;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

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
import SQL.JavaSQLSalle;
import SQL.JavaSQLTicket;

public class ActionDebugSupprime implements ActionListener{
	private Window w;
	private TextField numero;
	
	
	private Choice ticketASupprime;
	
	private Choice competenceASupprime;
	private String tag;

	private Choice calculateurASupprime;
	
	private Choice materielASupprime;
	
	private Choice projetASupprime;
	
	private Choice activiteASupprime;
	
	private Choice idParticipeASupprime;
	
	private Choice listeDomaineASupprime;





	
	private int typeVerif;
	private Entreprise entreprise;
	public static int SALLE = 0, DOMAINE = 1,PERSONNE = 2, TICKET = 3, COMPETENCE = 4, CALCULATEUR = 5, MATERIEL = 6, PROJET = 7, ACTIVITE = 8, 
			PARTICIPESALARIE = 9, PARTICIPESALLE = 10, PARTICIPECALCUL = 11, LISTEDOMAINE = 12  ;

	public ActionDebugSupprime (Window w,TextField numero, int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.numero = numero;
		this.typeVerif = typeVerif;
	}
	
	public ActionDebugSupprime (Window w,Choice choix, int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.typeVerif = typeVerif;
		if (typeVerif == TICKET) {
		this.ticketASupprime = choix;
		}
		
		else if (typeVerif == COMPETENCE) {
			this.competenceASupprime = choix;
		}
		
		else if (typeVerif == CALCULATEUR) {
			this.calculateurASupprime = choix;
		}
		else if (typeVerif == MATERIEL) {
			this.materielASupprime = choix;
		}
		else if (typeVerif == PROJET) {
			this.projetASupprime = choix;
		}
		else if (typeVerif == ACTIVITE) {
			this.activiteASupprime = choix;
		}
		else if (typeVerif == PARTICIPESALARIE) {
			this.idParticipeASupprime = choix;
		}
		else if (typeVerif == PARTICIPESALLE) {
			this.idParticipeASupprime = choix;
		}
		else if (typeVerif == PARTICIPECALCUL) {
			this.idParticipeASupprime = choix;
		}
		else if (typeVerif == LISTEDOMAINE) {
			this.listeDomaineASupprime = choix;
		}
		}
	
	
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (typeVerif == SALLE) {
			try {

				JavaSQLSalle.supprime(Integer.parseInt(numero.getText()));
				new FenetreDebugSalle(entreprise,FenetreDebugSalle.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (typeVerif == DOMAINE) {
			String tag = numero.getText();
			try {

				JavaSQLDomaine.supprime(tag);
				new FenetreDebugDomaine(entreprise,FenetreDebugDomaine.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (typeVerif == PERSONNE) {
			int id = Integer.parseInt(numero.getText());
			try {

				JavaSQLPersonne.supprime(id);
				new FenetreDebugPersonne(entreprise,FenetreDebugPersonne.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (typeVerif == TICKET) {
			
			ArrayList<Ticket> ticketTab = new ArrayList<Ticket>();
			int ticketId = -1;

			try {
				ticketTab = JavaSQLTicket.affiche();

				for (int i = 0; i < ticketTab.size(); i++) {
					if (this.ticketASupprime.getItem(ticketASupprime.getSelectedIndex()).equals(ticketTab.get(i).toString())) {
						ticketId = ticketTab.get(i).getId();
					}
								}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {

				JavaSQLTicket.supprime(ticketId);
				new FenetreDebugTicket(entreprise,FenetreDebugTicket.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		else if (typeVerif == COMPETENCE) {
			
			ArrayList<Competence> competenceTab = new ArrayList<Competence>();
			int competenceId = -1;

			try {
				competenceTab = JavaSQLCompetence.affiche();

				for (int i = 0; i < competenceTab.size(); i++) {
					if (this.competenceASupprime.getItem(competenceASupprime.getSelectedIndex()).equals(competenceTab.get(i).creeAffiche())) {
						competenceId = competenceTab.get(i).getNumSalarie();
						tag = competenceTab.get(i).getNom();
					}
								}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {

				JavaSQLCompetence.supprime(competenceId,tag);
				new FenetreDebugCompetence(entreprise,FenetreDebugCompetence.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
else if (typeVerif == CALCULATEUR) {
			
			ArrayList<Calculateur> calculateurTab = new ArrayList<Calculateur>();
			int calculateurId = -1;

			try {
				calculateurTab = JavaSQLCalculateur.affiche();

				for (int i = 0; i < calculateurTab.size(); i++) {
					if (this.calculateurASupprime.getItem(calculateurASupprime.getSelectedIndex()).equals(calculateurTab.get(i).creeAffiche())) {
						calculateurId = calculateurTab.get(i).getId();
					}
								}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {

				JavaSQLCalculateur.supprime(calculateurId);
				new FenetreDebugCalculateur(entreprise,FenetreDebugCalculateur.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	else if (typeVerif == MATERIEL) {
	
		ArrayList<Materiel> materielTab = new ArrayList<Materiel>();
		int materielId = -1;
	
		try {
			materielTab = JavaSQLMateriel.affiche();
	
			for (int i = 0; i < materielTab.size(); i++) {
				if (this.materielASupprime.getItem(materielASupprime.getSelectedIndex()).equals(materielTab.get(i).creeAffiche())) {
					materielId = materielTab.get(i).getId();
				}
							}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
	
			JavaSQLMateriel.supprime(materielId);
			new FenetreDebugMateriel(entreprise,FenetreDebugMateriel.AFFICHE);
			w.dispose();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		else if (typeVerif == PROJET) {
			
			ArrayList<Projet> projetTab = new ArrayList<Projet>();
			int projetId = -1;
		
			try {
				projetTab = JavaSQLProjet.affiche();
		
				for (int i = 0; i < projetTab.size(); i++) {
					if (this.projetASupprime.getItem(projetASupprime.getSelectedIndex()).equals(projetTab.get(i).creeAffiche())) {
						projetId = projetTab.get(i).getId();
					}
								}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
		
				JavaSQLProjet.supprime(projetId);
				new FenetreDebugProjet(entreprise,FenetreDebugProjet.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (typeVerif == ACTIVITE) {
			
			ArrayList<Activite> activiteTab = new ArrayList<Activite>();
			int activiteId = -1;
		
			try {
				activiteTab = JavaSQLActivite.affiche();
		
				for (int i = 0; i < activiteTab.size(); i++) {
					if (this.activiteASupprime.getItem(activiteASupprime.getSelectedIndex()).equals(activiteTab.get(i).creeAffiche())) {
						activiteId = activiteTab.get(i).getId();
					}
								}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
		
				JavaSQLActivite.supprime(activiteId);
				new FenetreDebugActivite(entreprise,FenetreDebugActivite.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (typeVerif == PARTICIPESALARIE) {
			int idActivite = -1;
			int idSalarie = -1;
			ArrayList<int[]> idParticipe = new ArrayList<int[]>();
            try {
            	idParticipe = JavaSQLParticipe.afficheParticipeSalarie();

                for (int i = 0; i < idParticipe.size(); i++) {
					if (this.idParticipeASupprime.getItem(idParticipeASupprime.getSelectedIndex()).equals("" + idParticipe.get(i)[0] + idParticipe.get(i)[1])) {
						idSalarie = idParticipe.get(i)[0];
						idActivite = idParticipe.get(i)[1];

					}
            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			
			
			try {
					JavaSQLParticipe.supprimeParticipeSalarie(idSalarie, idActivite);
				new FenetreDebugParticipeSalarie(entreprise,FenetreDebugParticipeSalarie.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (typeVerif == PARTICIPESALLE) {
			int idActivite = -1;
			int idSalle = -1;
			ArrayList<int[]> idParticipe = new ArrayList<int[]>();
            try {
            	idParticipe = JavaSQLParticipe.afficheParticipeSalle();

                for (int i = 0; i < idParticipe.size(); i++) {
					if (this.idParticipeASupprime.getItem(idParticipeASupprime.getSelectedIndex()).equals("" + idParticipe.get(i)[0] + idParticipe.get(i)[1])) {
						idSalle = idParticipe.get(i)[0];
						idActivite = idParticipe.get(i)[1];

					}
            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			
			
			try {
					JavaSQLParticipe.supprimeParticipeSalle(idSalle, idActivite);
				new FenetreDebugParticipeSalle(entreprise,FenetreDebugParticipeSalle.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		else if (typeVerif == PARTICIPECALCUL) {
			int idActivite = -1;
			int idCalcul = -1;
			ArrayList<int[]> idParticipe = new ArrayList<int[]>();
            try {
            	idParticipe = JavaSQLParticipe.afficheParticipeSalle();

                for (int i = 0; i < idParticipe.size(); i++) {
					if (this.idParticipeASupprime.getItem(idParticipeASupprime.getSelectedIndex()).equals("" + idParticipe.get(i)[0] + idParticipe.get(i)[1])) {
						idCalcul = idParticipe.get(i)[0];
						idActivite = idParticipe.get(i)[1];

					}
            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			
			
			try {
					JavaSQLParticipe.supprimeParticipeCalcul(idCalcul, idActivite);
				new FenetreDebugParticipeCalcul(entreprise,FenetreDebugParticipeCalcul.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		else if (typeVerif == LISTEDOMAINE) {
			int idActivite = -1;
			String tag= "123";
			ArrayList<String[]> tagTab = new ArrayList<String[]>();
			try {
				tagTab = JavaSQLListeDomaine.recupere();

				for (int i = 0; i < tagTab.size(); i++) {
					if (this.listeDomaineASupprime.getItem(listeDomaineASupprime.getSelectedIndex()).equals("" + tagTab.get(i)[0] + tagTab.get(i)[1])) {
						idActivite = Integer.parseInt(tagTab.get(i)[0]);
						tag= tagTab.get(i)[1];
					
					}
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				JavaSQLListeDomaine.supprime(idActivite, tag);
				new FenetreDebugListeDomaine(entreprise,FenetreDebugListeDomaine.AFFICHE);
				w.dispose();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}