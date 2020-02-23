package Panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisEmploieDuTempsListener;
import EcouteurEvenement.SourisProjetListener;
import Model.Entreprise;
import Model.Projet;
import Ressource.Plage;
import Ressource.Ressource;

public class PanelEmploiDuTemps extends JFrame{
	private Ressource ressource;
	private final int nbHeure = 24, nbJour = 7;
	
	public PanelEmploiDuTemps(Ressource ressource) {
		this.ressource = ressource;
		this.setSize(600, 550);
		this.add(afficherEmploiDuTemps());
	}

	
	public JPanel afficherEmploiDuTemps() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(nbHeure, nbJour)); //on represente 24h et une semaine
		
		ArrayList<Plage>listePlage = ressource.getPlages();
		panel.add(afficheSemaine(listePlage));
		
		return panel;
	}
	
	private JPanel afficheSemaine(ArrayList<Plage>listePlage) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		for (int i=0; i<nbJour; i++) {
			panel.add(afficheJour(listePlage, "esfsese"));
		}
		
		return panel;
	}
	

	private JPanel afficheJour(ArrayList<Plage>listePlage, String nom) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		for (int i=0; i<nbHeure; i++) {
			if(listePlage.get(i).getDebut().getHour() == i) {
				panel.add(creerLabel(nom, Color.CYAN));
			}
			else {
				panel.add(creerLabel("       ", Color.WHITE));
			}
		}
		return panel;
	}
	
	private JLabel creerLabel(String texte, Color couleur) {
		JLabel label = new JLabel();
		label.setText(texte);
		label.setBackground(couleur);
		label.addMouseListener(new SourisEmploieDuTempsListener(this, label));
		return label;
	}
}
