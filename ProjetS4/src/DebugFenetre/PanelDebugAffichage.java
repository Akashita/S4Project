package DebugFenetre;

import java.awt.FlowLayout;
import java.awt.Window;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Entreprise;
import Ressource.Personne;
import SQL.JavaSQLPersonne;

public class PanelDebugAffichage extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	public static final int PERSONNE = 0;
	private int type;
	public PanelDebugAffichage(Entreprise entreprise, int type) {
		this.entreprise = entreprise;
		this.type = type;
		setLayout(new FlowLayout());
		if (type == 0) {
			ArrayList<Personne> personneTab = new ArrayList<Personne>();
			JavaSQLPersonne sqlPersonne = new JavaSQLPersonne();
			try {
				personneTab = sqlPersonne.affiche();
				for (int i = 0; i < personneTab.size(); i++) {
					JLabel personne = new JLabel(personneTab.get(i).toString());
					this.add(personne);

			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
