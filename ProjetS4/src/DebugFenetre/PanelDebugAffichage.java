package DebugFenetre;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GestionTicket.Ticket;
import Model.Entreprise;
import Ressource.Personne;
import Ressource.Salle;
import SQL.JavaSQLDomaine;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLSalle;
import SQL.JavaSQLTicket;

public class PanelDebugAffichage extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	public static final int PERSONNE = 0, SALLE = 1, DOMAINE = 2, TICKET = 3;
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
					JLabel personne = new JLabel(personneTab.get(i).toString());
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
		
		
	}
	
}
