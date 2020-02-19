package Panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisProjetListener;
import Model.Entreprise;
import Model.Projet;

public class PanelProjet extends JPanel{
	Entreprise entreprise;
	
	public PanelProjet(Entreprise entreprise) {
		this.entreprise = entreprise;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createTitledBorder("liste des projets"));

	}
	
	public void afficherProjet() {
		ArrayList<Projet> listeProjet = entreprise.getListeProjet();
		String nomProjetSelectionner = entreprise.getProjetSelectionner().getNom();
		this.removeAll();
		for (int i=0; i<listeProjet.size(); i++) {
			Projet projet = listeProjet.get(i);
			boolean selectionner = false;
			if(projet.getNom() == nomProjetSelectionner) {
				selectionner = true;
			}
			this.add(creerLabel(projet.getNom(), selectionner));
			this.add(Box.createRigidArea(new Dimension(10,0)));
		}
	}
	
	private JLabel creerLabel(String nom, boolean selectionner) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 30));
		label.addMouseListener(new SourisProjetListener(this, label));
		if(selectionner) {
			label.setOpaque(true);
			label.setBackground(Color.BLUE);
		}
		return label;
	}

	public void nouveauProjet(String nom) {
		entreprise.creerProjet(nom);
	}
	
	public void selectionnerProjet(JLabel label) {
		entreprise.deselectionnerProjet();
		entreprise.selectionnerProjet(label.getText());
	} 
}
