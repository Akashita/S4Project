package DebugFenetre;

import java.awt.Choice;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import GestionTicket.Ticket;
import Model.Entreprise;
import Ressource.Competence;
import Ressource.Personne;
import SQL.JavaSQLCompetence;
import SQL.JavaSQLDomaine;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLSalle;
import SQL.JavaSQLTicket;

public class ActionDebugSupprime implements ActionListener{
	private Window w;
	private TextField numero;
	
	
	private Choice ticketASupprime;
	
	private Choice competenceASupprime;
	private String tag;

	
	
	private int typeVerif;
	private Entreprise entreprise;
	public static int SALLE = 0, DOMAINE = 1,PERSONNE = 2, TICKET = 3, COMPETENCE = 4  ;

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
	}

}