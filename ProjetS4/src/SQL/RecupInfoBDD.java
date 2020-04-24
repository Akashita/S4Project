package SQL;

import java.sql.SQLException;
import java.util.ArrayList;

import Model.Entreprise;
import Model.Projet;
import Ressource.Calculateur;
import Ressource.Personne;
import Ressource.Salle;

public final class RecupInfoBDD {
	static JavaSQLDomaine domaine = new JavaSQLDomaine();
	static JavaSQLPersonne personne = new JavaSQLPersonne();
	static JavaSQLCalculateur calculateur = new JavaSQLCalculateur();
	static JavaSQLSalle salle = new JavaSQLSalle();
	static JavaSQLActivite activite = new JavaSQLActivite();
	static JavaSQLProjet projet = new JavaSQLProjet();

	public static void recupBDDDomaine(Entreprise e) throws SQLException {
		ArrayList<String> dom = new ArrayList<String>();
		dom = domaine.affiche();
		for (int i = 0; i < dom.size(); i++) {
			e.nouvDomaine(dom.get(i));
		}
	}

	public static void recupBDDRessource(Entreprise e) throws SQLException {
		ArrayList<Personne> pers = new ArrayList<Personne>();
		pers = personne.affiche();
		ArrayList<Calculateur> cal = new ArrayList<Calculateur>();
		cal = calculateur.affiche();
		ArrayList<Salle> sal= new ArrayList<Salle>();
		sal = salle.affiche();
		for (int i = 0; i < pers.size(); i++) {
			e.nouvPersonne(pers.get(i));
		}
		for (int i = 0; i < cal.size(); i++) {
			e.nouvCalculateur(cal.get(i));
		}
		for (int i = 0; i < sal.size(); i++) {
			e.nouvSalle(sal.get(i));
		}
		
	}
	
	public static void recupBDDActivite(Projet p) throws SQLException {		
		p.setListeActivite(JavaSQLActivite.affiche());
	}
	
	public static void recupBDDProjet(Entreprise e) throws SQLException {
		ArrayList<Projet> prog = new ArrayList<Projet>();
		prog = JavaSQLProjet.affiche();
		for (int i = 0; i < prog.size(); i++) {
			e.nouvProjet(prog.get(i));
		}
		e.selectionnerProjet(prog.get(0));
	}
}
