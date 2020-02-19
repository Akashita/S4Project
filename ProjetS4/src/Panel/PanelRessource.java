package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisRessourceListener;
import Model.Entreprise;
import Ressource.Personne;
import Ressource.Ressource;

public class PanelRessource extends JPanel{
	Entreprise entreprise;
	
	public PanelRessource(Entreprise entreprise) {
		this.entreprise= entreprise;
		this.setLayout(new GridLayout(3,0));
		afficherRessource();
	}

	public void afficherRessource() {
		this.removeAll();
		this.add(creerPanelRessource(Ressource.PERSONNE));
		this.add(creerPanelRessource(Ressource.SALLE));
		this.add(creerPanelRessource(Ressource.CALCULATEUR));
		this.revalidate();
	}
	
	private JPanel creerPanelRessource(String type) {
		JPanel panel = new JPanel();
		panel.add(creerLabel("                                            "));
	    panel.setBackground(Color.white);
	    panel.setBorder(BorderFactory.createTitledBorder("liste de " + type));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		ArrayList<Ressource> listeRessource = entreprise.getListeRessourceType(type);
		for (int i=0; i<listeRessource.size(); i++) {
			Ressource ressource = listeRessource.get(i);
			String nom;
			if (ressource.getType() == Ressource.PERSONNE) {
				nom = (((Personne) ressource).getPrenom()) + ressource.getNom();
			}
			else {
				nom = ressource.getNom();
			}
			this.add(creerLabel(nom));
		}
		return panel;
	}
	
	private JLabel creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 10));
		label.addMouseListener(new SourisRessourceListener(this, label));
		return label;
	}
	
}
