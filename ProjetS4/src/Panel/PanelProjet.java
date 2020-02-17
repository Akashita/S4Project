package Panel;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisProjetListener;
import Model.Entreprise;
import Model.Projet;

public class PanelProjet extends JPanel{
	private ArrayList<Projet> listeProjet = new ArrayList<Projet>();
	private ArrayList<JLabel> listeLabel = new ArrayList<JLabel>();
	
	Entreprise entreprise;
	
	public void nouveauProjet(Entreprise entreprise, String nom) {
		this.entreprise = entreprise;
		this.entreprise.creerProjet(nom);
		ajoutProjet(entreprise.getDernierProjet());
	}

	public void ajoutProjet(Projet projet) {
		listeProjet.add(projet);
		creerLabel(projet.getNom());
		this.revalidate();
	}
	
	private void creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 30));
		label.addMouseListener(new SourisProjetListener(this, label));
		this.add(label);
		listeLabel.add(label);
		selectionnerProjet (label);

	}
	
	public void selectionnerProjet (JLabel label) {
		deselectionnerLabel();
		label.setForeground(new Color(255,255,255));
		label.setOpaque(true);
		label.setBackground(Color.BLUE);
		String nom = label.getText();
		entreprise.selectionnerProjet(nom);
	}
	
	private void deselectionnerLabel() {
		entreprise.deselectionnerProjet();
		for(int i=0; i<listeLabel.size();i++) {
			JLabel label = listeLabel.get(i);
			label.setForeground(new Color(0,0,0));
			label.setOpaque(true);
			label.setBackground(Color.WHITE);
		}
	}

}
