import java.awt.*;
import javax.swing.*;

public class FenetrePrincipale extends JFrame{
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	
	public FenetrePrincipale(Entreprise entreprise) {
		
		this.entreprise = entreprise;
		this.setTitle("ProjetS4");
		setSize(LARGEUR,HAUTEUR);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		PanelProjet projetPanel = new PanelProjet();
		PanelPrincipal pp = new PanelPrincipal(entreprise);	
		this.add(pp, BorderLayout.CENTER);
		this.add(projetPanel, BorderLayout.SOUTH);

		creationBarreMenu(projetPanel);
		
		this.addWindowListener(new FermerFenetre(this));
		this.setVisible(true);	
		pp.setVisible(true);
	}
	
	private void creationBarreMenu(PanelProjet projetPanel) {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		JMenu menuNouveau = new JMenu("Nouveau");
		JMenu menuAjout = new JMenu("Ajouter");
		
		JMenuItem  itemNouvelleRessource = new JMenuItem("Nouvelle Ressource");
		itemNouvelleRessource.addActionListener(new NouvelleRessourceListener(entreprise));
		JMenuItem itemNouveauProjet = new JMenuItem("Nouveau Projet");
		itemNouveauProjet.addActionListener(new NouveauProjetListener(entreprise, projetPanel));

		JMenuItem  itemAjoutPersonne = new JMenuItem(Ressource.PERSONNE);
		itemAjoutPersonne.addActionListener(new AjouterRessourceListener(entreprise, Ressource.PERSONNE));
		JMenuItem itemAjoutSalle = new JMenuItem(Ressource.SALLE);
		itemAjoutSalle.addActionListener(new AjouterRessourceListener(entreprise, Ressource.SALLE));
		JMenuItem itemAjoutCalculateur = new JMenuItem(Ressource.CALCULATEUR);
		itemAjoutCalculateur.addActionListener(new AjouterRessourceListener(entreprise, Ressource.CALCULATEUR));
				
		menuNouveau.add(itemNouvelleRessource);
		menuNouveau.add(itemNouveauProjet);
		menuAjout.add(itemAjoutPersonne);
		menuAjout.add(itemAjoutSalle);
		menuAjout.add(itemAjoutCalculateur);
		menuFichier.add(menuNouveau);
		menuFichier.add(menuAjout);
		menuBar.add(menuFichier);
		setJMenuBar(menuBar);		
	}
	
}
