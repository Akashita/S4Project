package DebugFenetre;

import java.awt.Choice;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Entreprise;
import Ressource.Salle;
import SQL.JavaSQLDomaine;
import SQL.JavaSQLPersonne;
import SQL.JavaSQLSalle;
import SQL.JavaSQLTicket;

public class ActionDebugAjout implements ActionListener{
	private Window w;
	private TextField numero;
	private TextField nom;
	private TextField prenom;
	private TextField place;
	private String role;
	private TextField motDePasse;
	private int typeVerif;
	private Entreprise entreprise;
	
	
	private TextField sujet;
	private TextField message;
	private TextField modif;
	private int envoyeur; 
	private int receveur;
	
	public static int SALLE = 0, DOMAINE = 1,PERSONNE = 2, TICKET = 3 ;

	public ActionDebugAjout (Window w,TextField numero,TextField nom,TextField place, int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.numero = numero;
		this.nom = nom;
		this.place = place;
		this.typeVerif = typeVerif;
	}
	public ActionDebugAjout (Window w,TextField nom,int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.nom = nom;
		this.typeVerif = typeVerif;
	}
	
	public ActionDebugAjout (Window w,TextField nom,TextField prenom,Choice role,TextField motDePasse, int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.nom = nom;
		this.prenom = prenom;
		this.role = role.getItem(role.getSelectedIndex());
		this.motDePasse = motDePasse;
		this.typeVerif = typeVerif;
	}
	
	public ActionDebugAjout (Window w,TextField sujet,TextField message,TextField modif,int envoyeur,int receveur,int typeVerif, Entreprise entreprise) {
		this.w = w;
		this.sujet = sujet;
		this.message = message;
		this.modif= modif;
		this.envoyeur = envoyeur;
		this.receveur = receveur;
		this.typeVerif = typeVerif;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (typeVerif == SALLE) {
			try {

				JavaSQLSalle.insertion(Integer.parseInt(numero.getText()), nom.getText(), Integer.parseInt(place.getText()));
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
			ArrayList<String> tag = new ArrayList<String>();
			ArrayList<Integer> niveau = new ArrayList<Integer>();
			try {

				JavaSQLPersonne.insertion( nom.getText(),prenom.getText(), role,motDePasse.getText(),tag,niveau);
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
			
				try {

					JavaSQLTicket.insertion( sujet.getText(),message.getText(), modif.getText(),envoyeur,receveur);
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
}

}
