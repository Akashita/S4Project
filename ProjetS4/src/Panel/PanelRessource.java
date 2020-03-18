package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import EcouteurEvenement.SourisRessourceListener;
import Fenetre.FenetrePrincipale;
import Model.Entreprise;
import Ressource.Personne;
import Ressource.Ressource;

public class PanelRessource extends JPanel{
	Entreprise entreprise;
	
	public PanelRessource(Entreprise entreprise) {
		this.entreprise= entreprise;
		//this.add(creerTextField(), BorderLayout.SOUTH);
	}

	public void afficherRessource() {
		this.setLayout(new BorderLayout());
		this.add(creerPanel(), BorderLayout.CENTER);
		this.revalidate();
	}
	
	private JPanel creerPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,0));
		panel.add(creerPanelRessource(Ressource.PERSONNE));
		panel.add(creerPanelRessource(Ressource.SALLE));
		panel.add(creerPanelRessource(Ressource.CALCULATEUR));
		return panel;
	}
		
	private JPanel creerPanelRessource(String type) {
		ArrayList<Ressource> listeRessource = entreprise.getListeRessourceType(type);
		JPanel panel = new JPanel();
		if (listeRessource.size() > 0) {
			panel.add(creerLabel("                                    "));
		    panel.setBackground(Color.white);
		    panel.setBorder(BorderFactory.createTitledBorder("liste de " + type));
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			for (int i=0; i<listeRessource.size(); i++) {
				Ressource ressource = listeRessource.get(i);
				String nom;
				if (ressource.getType() == Ressource.PERSONNE) {
					nom = (((Personne) ressource).getPrenom()) + " " + ressource.getNom();
				}
				else {
					nom = ressource.getNom();
				}
				panel.add(creerLabel(nom));
			}
		}
		return panel;			
	}
	
	private JLabel creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		//label.addMouseListener(new SourisRessourceListener(this, label));
		return label;
	}
	
	private JPanel creerTextField() {
		JPanel panel = new JPanel();
	    panel.setBackground(Color.white);
		panel.add(new JLabel("Recherche"));
		JTextField jtf = new JTextField();
		panel.add(jtf);
		jtf.setText("Rechercher...");
		panel.setBounds(0, this.getHeight(), 10, 10);
		return panel;
	}
}
