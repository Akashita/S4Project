import java.awt.*;
import javax.swing.*;

public class FenetrePrincipale extends JFrame{
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	
	public FenetrePrincipale(Entreprise entreprise) {	
		this.entreprise = entreprise;
		this.setTitle("ProjetS4");
		this.setSize(LARGEUR,HAUTEUR);
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
		JMenu menuEditer = new JMenu("Editer");
		JMenu menuPropos = new JMenu("A propos");

		JMenu menuNouveau = new JMenu("Nouveau");
		JMenu menuAjout = new JMenu("Ajouter");
		
		JMenuItem itemNouvelleRessource = new JMenuItem("Nouvelle Ressource");
		itemNouvelleRessource.addActionListener(new NouvelleRessourceListener(entreprise));
		JMenuItem itemNouveauProjet = new JMenuItem("Nouveau Projet");
		itemNouveauProjet.addActionListener(new NouveauProjetListener(entreprise, projetPanel));

		JMenuItem itemAjoutPersonne = new JMenuItem(Ressource.PERSONNE);
		itemAjoutPersonne.addActionListener(new AjouterRessourceListener(entreprise, Ressource.PERSONNE));
		JMenuItem itemAjoutSalle = new JMenuItem(Ressource.SALLE);
		itemAjoutSalle.addActionListener(new AjouterRessourceListener(entreprise, Ressource.SALLE));
		JMenuItem itemAjoutCalculateur = new JMenuItem(Ressource.CALCULATEUR);
		itemAjoutCalculateur.addActionListener(new AjouterRessourceListener(entreprise, Ressource.CALCULATEUR));

		JMenuItem itemEnleverRessource = new JMenuItem("Enlever ressource");
		itemEnleverRessource.addActionListener(new EnleverRessourceListener(entreprise));

		JMenuItem itemModifier = new JMenuItem("Modifier");
		JMenuItem itemSupprimer = new JMenuItem("Supprimer Projet");

		JMenuItem itemPropos = new JMenuItem("Projet");

		itemNouvelleRessource.setMnemonic('N');
		menuNouveau.add(itemNouvelleRessource);
		menuNouveau.add(itemNouveauProjet);
		
		menuAjout.add(itemAjoutPersonne);
		menuAjout.add(itemAjoutSalle);
		menuAjout.add(itemAjoutCalculateur);
		
		menuFichier.add(menuNouveau);
		
		menuEditer.add(menuAjout);
		menuEditer.add(itemEnleverRessource);
		menuEditer.add(itemModifier);
		menuEditer.add(itemSupprimer);
		
		menuPropos.add(itemPropos);
		
		menuBar.add(menuFichier);
		menuBar.add(menuEditer);
		menuBar.add(menuPropos);
		setJMenuBar(menuBar);	
	}
	
}
