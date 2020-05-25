package DebugFenetre;

import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import GestionTicket.Ticket;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;
import Ressource.Calculateur;
import Ressource.Competence;
import Ressource.Domaine;
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

public class ActionDebugAjout implements ActionListener{
	private Window w;
	private TextField numero;
	private TextField nom;
	private TextField prenom;
	private TextField place;
	private Choice role;
	private TextField motDePasse;
	private int typeVerif;
	private Entreprise entreprise;
	
	
	private TextField sujet;
	private TextField message;
	private Choice action; 
	private Choice envoyeur; 
	private Choice receveur;
	
	
	private Choice competence;
	private Choice personne; 
	private Choice niveau; 
	
	private TextField typeMatos;
	private Choice numSalle;

	
	private TextField jour;
	private TextField mois;
	private TextField annee;
	private TextField priorite;
	private Choice chefChoix;
	private TextField couleur;

	private TextField titre;
	private TextField charge;
	private Choice projetChoix;
	private TextField ordre;

	private Choice activiteChoix;
	private Choice ressourceRelationChoix;
	
	private Choice tagChoix;

	
	
	
	private TextField capacite;
	
	public static int SALLE = 0, DOMAINE = 1,PERSONNE = 2, TICKET = 3, COMPETENCE = 4, CALCULATEUR = 5, MATERIEL = 6, PROJET = 7,
			ACTIVITE = 8, PARTICIPESALARIE = 9, PARTICIPESALLE = 10, PARTICIPECALCUL = 11, LISTEDOMAINE = 12 ;

	
	public ActionDebugAjout (Window w,TextField nom,int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.nom = nom;
		this.typeVerif = typeVerif;
	}
	
	public ActionDebugAjout (Window w,TextField nom,TextField prenom,Choice role,TextField motDePasse, int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.nom = nom;
		this.prenom = prenom;
		this.role = role;
		this.motDePasse = motDePasse;
		this.typeVerif = typeVerif;
	}
	
	public ActionDebugAjout (Window w,TextField sujet,TextField message,Choice action,Choice envoyeur,Choice receveur,int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.sujet = sujet;
		this.message = message;
		this.action = action;		
		this.envoyeur = envoyeur;
		this.receveur = receveur;
		this.typeVerif = typeVerif;

	}
	
	public ActionDebugAjout (Window w,Choice competence,Choice personne,Choice niveau,int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.competence = competence;
		this.personne = personne;
		this.niveau = niveau;
		this.typeVerif = typeVerif;

	}
	
	public ActionDebugAjout (Window w,TextField texteUn,TextField texteDeux,int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.typeVerif = typeVerif;
		if (typeVerif == CALCULATEUR) {
			this.nom = texteUn;
			this.capacite = texteDeux;
		}
		else if (typeVerif == SALLE) {
			this.nom = texteUn;
			this.place = texteDeux;
		}
			
		
		

	}
	public ActionDebugAjout (Window w,TextField texteUn,Choice choix,int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.typeVerif = typeVerif;
		 if (typeVerif == MATERIEL) {
				 this.typeMatos = texteUn;
				 this.numSalle= choix;		
				 }
		
		

	}
	
	

	public ActionDebugAjout(Window w, TextField nom2, TextField priorite, TextField jour, TextField mois,
			TextField annee, TextField couleur, Choice chefChoix, int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.typeVerif = typeVerif;
		if (typeVerif == PROJET) {
			this.jour = jour;
			this.mois = mois;
			this.annee = annee;
			this.nom = nom2;
			this.priorite = priorite;
			this.chefChoix = chefChoix;
			this.couleur = couleur;
		}
		

	}
	
	public ActionDebugAjout(Window w, TextField nom2, TextField priorite, TextField jour, TextField mois,
			TextField annee, TextField couleur, Choice chefChoix,TextField ordre, int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.typeVerif = typeVerif;
		if (typeVerif == ACTIVITE) {
			this.jour = jour;
			this.mois = mois;
			this.annee = annee;
			this.titre = nom2;
			this.charge = priorite;
			this.couleur = couleur;
			this.projetChoix = chefChoix;
			this.ordre = ordre;


		}
	}
		public ActionDebugAjout (Window w,Choice choix1 ,Choice choix2,int typeVerif, Entreprise entreprise) {
			this.w = w;
			this.typeVerif = typeVerif;
			 if (typeVerif == PARTICIPESALARIE) {
					 this.activiteChoix = choix1;
					 this.ressourceRelationChoix= choix2;		
					 }
			 else if (typeVerif == PARTICIPESALLE) {
				 this.activiteChoix = choix1;
				 this.ressourceRelationChoix= choix2;		
				 }
			 else if (typeVerif == PARTICIPECALCUL) {
				 this.activiteChoix = choix1;
				 this.ressourceRelationChoix= choix2;		
				 }
			 else if (typeVerif == LISTEDOMAINE) {
				 this.activiteChoix = choix1;
				 this.tagChoix= choix2;		
				 }
			

		}

	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (typeVerif == SALLE) {
			try {

				JavaSQLSalle.insertion( nom.getText(), Integer.parseInt(place.getText()));
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
		else if (typeVerif == DOMAINE) {
		try {

			JavaSQLDomaine.insertion( nom.getText());
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
		else if (typeVerif == PERSONNE) {
			String roleString = role.getItem(role.getSelectedIndex());
			ArrayList<String> tag = new ArrayList<String>();
			ArrayList<Integer> niveau = new ArrayList<Integer>();
			try {

				JavaSQLPersonne.insertion( nom.getText(),prenom.getText(), roleString,motDePasse.getText(),tag,niveau);
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
			else if (typeVerif == TICKET) {
				ArrayList<Personne> personneTab = new ArrayList<Personne>();
				int envoyeurId = -1;
				int receveurId = -1;

				try {
					personneTab = JavaSQLPersonne.affiche();

					for (int i = 0; i < personneTab.size(); i++) {
						if (envoyeur.getItem(envoyeur.getSelectedIndex()).equals(personneTab.get(i).toString())) {
							envoyeurId = personneTab.get(i).getId();

						}
						if (receveur.getItem(receveur.getSelectedIndex()).equals(personneTab.get(i).toString())) {
							receveurId = personneTab.get(i).getId();

						}				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int actionNb;
				
				if (action.getItem(action.getSelectedIndex()) == "transfert") {
					actionNb = Ticket.TRANSFERT;
				}
				else if (action.getItem(action.getSelectedIndex()) == "libere") {
					actionNb = Ticket.LIBERE;
				}
				else {
					actionNb = Ticket.MESSAGE;

				}
				try {
					JavaSQLTicket.insertion(actionNb, sujet.getText(), message.getText(), envoyeurId, receveurId, null, null, null, null );

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
				String tag = "erreur";
				try {
					ArrayList<String> domaineTab =JavaSQLDomaine.affiche();

					for (int i = 0; i < domaineTab.size(); i++) {
						if (competence.getItem(competence.getSelectedIndex()).equals(domaineTab.get(i))) {
							tag = domaineTab.get(i);

						}
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				ArrayList<Personne> personneTab = new ArrayList<Personne>();
				int personneId = -1;

				try {
					personneTab = JavaSQLPersonne.affiche();

					for (int i = 0; i < personneTab.size(); i++) {
						if (personne.getItem(personne.getSelectedIndex()).equals(personneTab.get(i).creeAffiche())) {
							personneId = personneTab.get(i).getId();

						}
									}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int niveauNb;
				
				if (niveau.getItem(niveau.getSelectedIndex()) == "confirme") {
					niveauNb = Competence.CONFIRME;
				}
				else if (niveau.getItem(niveau.getSelectedIndex()) == "expert") {
					niveauNb = Competence.EXPERT;
				}
				else {
					niveauNb = Competence.DEBUTANT;

				}
				try {
					JavaSQLCompetence.insertion(niveauNb,tag ,personneId);

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
				try {

					JavaSQLCalculateur.insertion( nom.getText(), Integer.parseInt(capacite.getText()));
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
				int salleId = -1;
				ArrayList<Salle> salleTab = new ArrayList<Salle>();
				try {
					salleTab = JavaSQLSalle.affiche();

					for (int i = 0; i < salleTab.size(); i++) {
						if (numSalle.getItem(numSalle.getSelectedIndex()).equals(salleTab.get(i).creeAffiche())) {
							salleId = salleTab.get(i).getId();

						}
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {

					JavaSQLMateriel.insertion( typeMatos.getText(),salleId);
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
				LocalDate deadLine = LocalDate.of( Integer.parseInt(annee.getText()), Integer.parseInt(mois.getText()), Integer.parseInt(jour.getText()));
				int chefId = -1;
				ArrayList<Personne> personneTab = new ArrayList<Personne>();
				try {
					personneTab = JavaSQLPersonne.affiche();

					for (int i = 0; i < personneTab.size(); i++) {
						if (chefChoix.getItem(chefChoix.getSelectedIndex()).equals(personneTab.get(i).creeAffiche())) {
							chefId = personneTab.get(i).getId();

						}
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					JavaSQLProjet.insertion(nom.getText(), Integer.parseInt(priorite.getText()), deadLine, Integer.parseInt(couleur.getText()), chefId);
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
				LocalDate debut = LocalDate.of( Integer.parseInt(annee.getText()), Integer.parseInt(mois.getText()), Integer.parseInt(jour.getText()));
				int projetId = -1;
				ArrayList<Projet> projetTab = new ArrayList<Projet>();
				try {
					projetTab = JavaSQLProjet.affiche();

					for (int i = 0; i < projetTab.size(); i++) {
						if (projetChoix.getItem(projetChoix.getSelectedIndex()).equals(projetTab.get(i).creeAffiche())) {
							projetId = projetTab.get(i).getId();

						}
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					JavaSQLActivite.insertion(titre.getText(),debut, Float.parseFloat(charge.getText()), Integer.parseInt(ordre.getText()),Integer.parseInt(couleur.getText()), projetId,null);
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
		
		
			else if (typeVerif == PARTICIPESALARIE) {
				int numSalarie = -1;
				int idA = -1;
				ArrayList<Activite> activiteTab = new ArrayList<Activite>();
				try {
					activiteTab = JavaSQLActivite.affiche();

					for (int i = 0; i < activiteTab.size(); i++) {
						if (activiteChoix.getItem(activiteChoix.getSelectedIndex()).equals(activiteTab.get(i).creeAffiche())) {
							idA = activiteTab.get(i).getId();

						}
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<Personne> personneTab = new ArrayList<Personne>();

				try {
					personneTab = JavaSQLPersonne.affiche();

					for (int i = 0; i < personneTab.size(); i++) {
						if (ressourceRelationChoix.getItem(ressourceRelationChoix.getSelectedIndex()).equals(personneTab.get(i).creeAffiche())) {
							numSalarie = personneTab.get(i).getId();

						}
									}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					JavaSQLParticipe.insertionSalarie(numSalarie, idA);
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
		
		
			else if (typeVerif == PARTICIPECALCUL) {
				int code = -1;
				int idA = -1;
				ArrayList<Activite> activiteTab = new ArrayList<Activite>();
				try {
					activiteTab = JavaSQLActivite.affiche();

					for (int i = 0; i < activiteTab.size(); i++) {
						if (activiteChoix.getItem(activiteChoix.getSelectedIndex()).equals(activiteTab.get(i).creeAffiche())) {
							idA = activiteTab.get(i).getId();

						}
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<Calculateur> calculateurTab = new ArrayList<Calculateur>();

				try {
					calculateurTab = JavaSQLCalculateur.affiche();

					for (int i = 0; i < calculateurTab.size(); i++) {
						if (ressourceRelationChoix.getItem(ressourceRelationChoix.getSelectedIndex()).equals(calculateurTab.get(i).creeAffiche())) {
							code = calculateurTab.get(i).getId();

						}
									}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					JavaSQLParticipe.insertionCalcul(code, idA);
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
		
		
			else if (typeVerif == PARTICIPESALLE) {
				int numero = -1;
				int idA = -1;
				ArrayList<Activite> activiteTab = new ArrayList<Activite>();
				try {
					activiteTab = JavaSQLActivite.affiche();

					for (int i = 0; i < activiteTab.size(); i++) {
						if (activiteChoix.getItem(activiteChoix.getSelectedIndex()).equals(activiteTab.get(i).creeAffiche())) {
							idA = activiteTab.get(i).getId();

						}
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<Salle> salleTab = new ArrayList<Salle>();

				try {
					salleTab = JavaSQLSalle.affiche();

					for (int i = 0; i < salleTab.size(); i++) {
						if (ressourceRelationChoix.getItem(ressourceRelationChoix.getSelectedIndex()).equals(salleTab.get(i).creeAffiche())) {
							numero = salleTab.get(i).getId();

						}
									}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					JavaSQLParticipe.insertionSalle(numero, idA);
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
		
			else if (typeVerif == LISTEDOMAINE) {
				String tag = "123";
				int idA = -1;
				ArrayList<Activite> activiteTab = new ArrayList<Activite>();
				try {
					activiteTab = JavaSQLActivite.affiche();

					for (int i = 0; i < activiteTab.size(); i++) {
						if (activiteChoix.getItem(activiteChoix.getSelectedIndex()).equals(activiteTab.get(i).creeAffiche())) {
							idA = activiteTab.get(i).getId();

						}
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ArrayList<String> tagTab = new ArrayList<String>();
				try {
					tagTab = JavaSQLDomaine.affiche();

					for (int i = 0; i < tagTab.size(); i++) {
						if (tagChoix.getItem(tagChoix.getSelectedIndex()).equals(tagTab.get(i))) {
							tag = tagTab.get(i);
				}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					JavaSQLListeDomaine.insertion(tag, idA);
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
