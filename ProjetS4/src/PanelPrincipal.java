import java.awt.*;
import java.util.*;
import javax.swing.*;

public class PanelPrincipal extends JPanel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	
	public PanelPrincipal (Entreprise entreprise) {
		this.entreprise = entreprise;
        this.setLayout(new BorderLayout());
		this.setSize(this.getWidth(), this.getHeight());
		entreprise.addObserver(this);
	}
		
	private void afficheRessourceProjet() {
		Projet projet = entreprise.getProjetSelectionner();
		JPanel panelProjet = entreprise.getPanelDuProjet();
		if (projet != null) {
			ArrayList<Ressource> listeRessource = projet.getListe();
			ArrayList<Personne> listePersonne = new ArrayList<Personne>();
			ArrayList<Salle> listeSalle = new ArrayList<Salle>();
			ArrayList<Calculateur> listeCalculateur = new ArrayList<Calculateur>();
			
			for (int i=0; i<listeRessource.size(); i++) {
				Ressource ressource = listeRessource.get(i);
				if (ressource.getType() == Ressource.PERSONNE) {
					listePersonne.add((Personne) ressource);
				}
				if (ressource.getType() == Ressource.SALLE) {
					listeSalle.add((Salle) ressource);
				}
				if (ressource.getType() == Ressource.CALCULATEUR) {
					listeCalculateur.add((Calculateur) ressource);
				}
			}
			panelProjet.removeAll();
			panelProjet.setSize(this.getWidth(), this.getHeight());
			panelProjet.setLayout(new BorderLayout());
			panelProjet.add(affichePersonne(listePersonne), BorderLayout.WEST);
			panelProjet.add(afficheSalle(listeSalle), BorderLayout.CENTER);
			panelProjet.add(afficheCalculateur(listeCalculateur), BorderLayout.EAST);
			this.add(panelProjet, BorderLayout.CENTER);
			this.revalidate();
		}
	}
	
	
	
	
	private JPanel affichePersonne(ArrayList<Personne> listePersonne) {
		JPanel panel = new JPanel();
		for (int i=0; i<listePersonne.size(); i++) {
			Personne personne = listePersonne.get(i);
			String texte = personne.getRole()+ " " + personne.getPrenom()+personne.getNom();
			panel.add(new JLabel(texte));
		}
		return panel;
	}

	private JPanel afficheSalle(ArrayList<Salle> listeSalle) {
		JPanel panel = new JPanel();
		for (int i=0; i<listeSalle.size(); i++) {
			Salle salle = listeSalle.get(i);
			String texte = "Salle :" + salle.getNom();
			panel.add(new JLabel(texte));
		}	
		return panel;
	}

	private JPanel afficheCalculateur(ArrayList<Calculateur> listeCalculateur) {
		JPanel panel = new JPanel();
		for (int i=0; i<listeCalculateur.size(); i++) {
			Calculateur calculateur = listeCalculateur.get(i);
			String texte = "Calculateur: " + calculateur.getNom();
			panel.add(new JLabel(texte));
		}
		return panel;
	}

	
	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		this.afficheRessourceProjet();
		this.repaint();	
	}


}
