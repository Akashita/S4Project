package Fenetre;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import EcouteurEvenement.AjouterRessourceListener;
import EcouteurEvenement.EnleverRessourceListener;
import EcouteurEvenement.NouveauProjetListener;
import EcouteurEvenement.NouvelleRessourceListener;
import Model.Entreprise;
import Panel.PanelPrincipal;
import Panel.PanelProjet;
import Ressource.Ressource;

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
		
		
		JMenuItem itemNouvelleRessource = new JMenuItem("Nouvelle Ressource", KeyEvent.VK_R);
	    KeyStroke ctrlNouvelleRessource = KeyStroke.getKeyStroke("control R");
	    itemNouvelleRessource.setAccelerator(ctrlNouvelleRessource);
		itemNouvelleRessource.addActionListener(new NouvelleRessourceListener(entreprise));
		
		JMenuItem itemNouveauProjet = new JMenuItem("Nouveau Projet", KeyEvent.VK_P);
	    KeyStroke ctrlNouvelleProjet = KeyStroke.getKeyStroke("control P");
	    itemNouveauProjet.setAccelerator(ctrlNouvelleProjet);
		itemNouveauProjet.addActionListener(new NouveauProjetListener(entreprise, projetPanel));

		JMenuItem itemQuitter = new JMenuItem("Quitter", KeyEvent.VK_Q);
	    KeyStroke ctrlQuitter = KeyStroke.getKeyStroke("control Q");
	    itemQuitter.setAccelerator(ctrlQuitter);
	    itemQuitter.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent event){
	          System.exit(0);
	        }
	      });		
		
		JMenuItem JMenuItem = new JMenuItem("Ajouter", KeyEvent.VK_A);
	    KeyStroke ctrlAjoutPersonne = KeyStroke.getKeyStroke("control A");
	    JMenuItem.setAccelerator(ctrlAjoutPersonne);
	    JMenuItem.addActionListener(new AjouterRessourceListener(entreprise));

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
		
		
		menuFichier.add(menuNouveau);
		menuFichier.add(itemQuitter);
		
		menuEditer.add(JMenuItem);
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
