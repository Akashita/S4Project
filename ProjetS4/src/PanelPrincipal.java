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
		this.setSize(this.getWidth(), this.getHeight());
		entreprise.addObserver(this);

	}
	
	/*public void paint(Graphics g){
		entreprise.dessineToi(g);
		
		//this.add(entreprise.ajoutProjet(this));
		
	}*/
	
	private void afficheRessourceProjet() {
		Projet projet = entreprise.getProjetSelectionner();
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
			affichePersonne(listePersonne);
			afficheSalle(listeSalle);
			afficheCalculateur(listeCalculateur);			
		}
	}
	
	
	
	
	private void affichePersonne(ArrayList<Personne> listePersonne) {
		for (int i=0; i<listePersonne.size(); i++) {
			JPanel panel = new JPanel();
			Personne personne = listePersonne.get(i);
			JLabel label = new JLabel(personne.getRole()+ " " + personne.getPrenom()+personne.getNom());
			panel.add(label);
			panel.setBounds(200, 200, 100, 100);
			this.add(panel);
			this.revalidate();
		}
	}

	private void afficheSalle(ArrayList<Salle> listeSalle) {
		for (int i=0; i<listeSalle.size(); i++) {
			JPanel panel = new JPanel();
			Salle salle = listeSalle.get(i);
			JLabel label = new JLabel("Salle :" + salle.getNom());
			panel.add(label);
			panel.setBounds(200, 200, 100, 100);
			this.add(panel);
			this.revalidate();
		}		
	}

	private void afficheCalculateur(ArrayList<Calculateur> listeCalculateur) {
		for (int i=0; i<listeCalculateur.size(); i++) {
			JPanel panel = new JPanel();
			Calculateur calculateur = listeCalculateur.get(i);
			JLabel label = new JLabel("Calculateur: " + calculateur.getNom());
			panel.add(label);
			panel.setBounds(200, 200, 100, 100);
			this.add(panel);
			this.revalidate();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		this.repaint();	
		this.afficheRessourceProjet();
	}


}
