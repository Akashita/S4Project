import java.awt.*;
import java.awt.event.KeyEvent;

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
		
		JMenuItem itemNouvelleRessource = new JMenuItem("Nouvelle Ressource", KeyEvent.VK_R);
	    KeyStroke ctrlNouvelleRessource = KeyStroke.getKeyStroke("control R");
	    itemNouvelleRessource.setAccelerator(ctrlNouvelleRessource);
		itemNouvelleRessource.addActionListener(new NouvelleRessourceListener(entreprise));
		
		JMenuItem itemNouveauProjet = new JMenuItem("Nouveau Projet", KeyEvent.VK_P);
	    KeyStroke ctrlNouvelleProjet = KeyStroke.getKeyStroke("control P");
	    itemNouveauProjet.setAccelerator(ctrlNouvelleProjet);
		itemNouveauProjet.addActionListener(new NouveauProjetListener(entreprise, projetPanel));

		JMenuItem itemAjoutPersonne = new JMenuItem(Ressource.PERSONNE, KeyEvent.VK_A);
	    KeyStroke ctrlAjoutPersonne = KeyStroke.getKeyStroke("control A");
	    itemAjoutPersonne.setAccelerator(ctrlAjoutPersonne);
		itemAjoutPersonne.addActionListener(new AjouterRessourceListener(entreprise, Ressource.PERSONNE));
		
		JMenuItem itemAjoutSalle = new JMenuItem(Ressource.SALLE, KeyEvent.VK_S);
	    KeyStroke ctrlAjoutSalle = KeyStroke.getKeyStroke("control S");
	    itemAjoutSalle.setAccelerator(ctrlAjoutSalle);
		itemAjoutSalle.addActionListener(new AjouterRessourceListener(entreprise, Ressource.SALLE));
		
		JMenuItem itemAjoutCalculateur = new JMenuItem(Ressource.CALCULATEUR, KeyEvent.VK_D);
	    KeyStroke ctrlAjoutCalculateur = KeyStroke.getKeyStroke("control D");
	    itemAjoutCalculateur.setAccelerator(ctrlAjoutCalculateur);
		itemAjoutCalculateur.addActionListener(new AjouterRessourceListener(entreprise, Ressource.CALCULATEUR));

		JMenuItem itemEnleverRessource = new JMenuItem("Enlever ressource", KeyEvent.VK_E);
	    KeyStroke ctrlEnleverRessource = KeyStroke.getKeyStroke("control E");
	    itemEnleverRessource.setAccelerator(ctrlEnleverRessource);
		itemEnleverRessource.addActionListener(new EnleverRessourceListener(entreprise));

		JMenuItem itemModifier = new JMenuItem("Modifier");
		JMenuItem itemSupprimer = new JMenuItem("Supprimer Projet");

		JMenuItem itemPropos = new JMenuItem("Projet");

		//itemNouvelleRessource.setMnemonic(KeyEvent.VK_N);
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
