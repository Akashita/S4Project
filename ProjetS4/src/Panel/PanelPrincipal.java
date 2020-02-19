package Panel;
import java.awt.*;
import java.util.*;
import javax.swing.*;

import Model.Entreprise;
import Model.Projet;
import Ressource.Calculateur;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;

public class PanelPrincipal extends JPanel implements Observer{

	private Entreprise entreprise;
	private PanelRessource panelRessource;
	private PanelProjet panelProjet;
	
	public PanelPrincipal (Entreprise entreprise,
			PanelRessource panelRessource, PanelProjet panelProjet) {
		this.entreprise = entreprise;
		this.panelRessource = panelRessource;
		this.panelProjet = panelProjet;
        this.setLayout(new BorderLayout());
		this.setSize(this.getWidth(), this.getHeight());
		entreprise.addObserver(this);
	}
		
	private void afficheRessourceProjet() {
		Projet projet = entreprise.getProjetSelectionner();
		JPanel panelProjet = entreprise.getPanelDuProjet();
		if (projet != null) {
			ArrayList<Ressource> listeRessource = projet.getListe();
	
			panelProjet.removeAll();
			panelProjet.setSize(this.getWidth(), this.getHeight());
			//panelProjet.setLayout(new BorderLayout());
			panelProjet.setLayout(new GridLayout(0, 3));

			panelProjet.add(creerPanelRessource(listeRessource, Ressource.PERSONNE));
			panelProjet.add(creerPanelRessource(listeRessource, Ressource.SALLE));
			panelProjet.add(creerPanelRessource(listeRessource, Ressource.CALCULATEUR));
			
			this.add(panelProjet, BorderLayout.CENTER);
			this.revalidate();
		}
	}
	
	
	private JPanel creerPanelRessource(ArrayList<Ressource> listeRessource, String type) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		for (int i=0; i<listeRessource.size(); i++) {
			if (listeRessource.get(i).getType() == type) {
				panel.add(Box.createRigidArea(new Dimension(0,20)));
				panel.add(creerJLabelRessource(listeRessource.get(i)), "Center");
			}
		}
		return panel;
	}
	
	private JLabel creerJLabelRessource(Ressource ressource) {
		JLabel label = null;
		String texte = null;
		
		if (ressource.getType() == Ressource.PERSONNE) {
			Personne personne = (Personne) ressource;
			texte = personne.getRole()+ " " + personne.getPrenom()+ " " +personne.getNom();
		}
		if (ressource.getType() == Ressource.SALLE) {
			texte = "Salle: " + ressource.getNom();
		}
		if (ressource.getType() == Ressource.CALCULATEUR) {
			texte = "Calculateur: " + ressource.getNom();
		}
		
		label = new JLabel(texte);
		label.setFont(new Font("Arial", Font.BOLD, 20));
		return label;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		this.afficheRessourceProjet();
		panelRessource.afficherRessource();
		panelProjet.afficherProjet();
		this.repaint();	
	}


}
