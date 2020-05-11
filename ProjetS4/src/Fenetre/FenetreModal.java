package Fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JPanel;

import Model.Entreprise;
import Panel.PanelPrincipal;
import Panel_Fenetre.PanelAjouterRessource;
import Panel_Fenetre.PanelEnleverRessource;
import Panel_Fenetre.PanelModifierActivite;
import Panel_Fenetre.PanelModifierProjet;
import Panel_Fenetre.PanelDomaine;
import Panel_Fenetre.PanelNouveauProjet;
import Panel_Fenetre.PanelNouveauTicket;
import Panel_Fenetre.PanelNouvelleActivite;
import Panel_Fenetre.PanelNouvelleRessource;

public class FenetreModal extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Entreprise entreprise;
	private JPanel panelInterface;
	private String titre;
    protected Color couleurFond = PanelPrincipal.BLEU2;
    protected int largeur, hauteur;


	public FenetreModal(Entreprise entreprise, int choix) {
		super(entreprise.getFenetrePrincipale(), true);
		this.entreprise = entreprise;
		
		getPanelFenetre(choix);
		
		this.setTitle(titre);
		this.setSize(largeur, hauteur);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetre(this));
		this.setBackground(couleurFond);
		this.setLayout(new BorderLayout());
		panelInterface.setBackground(couleurFond);
		this.add(panelInterface, BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);

	}
	
	private void getPanelFenetre(int choix) {
		largeur = 400;
		hauteur = 350;
		
		switch (choix) {
		case FenetrePrincipale.NouveauProjet:
			titre = "Creation projet";
			panelInterface = new PanelNouveauProjet(entreprise, this);
			break;
		case FenetrePrincipale.NouvelleActivite:
			titre = "Creation activite";
			panelInterface = new PanelNouvelleActivite(entreprise, this);
			break;
		case FenetrePrincipale.NouvelleRessource:
			titre = "Creation ressource";
			panelInterface = new PanelNouvelleRessource(entreprise, this);
			break;
		case FenetrePrincipale.NouveauDomaine:
			titre = "Creation domaine";
			panelInterface = new PanelDomaine(entreprise, this);
			largeur = 400;
			hauteur = 300;
			break;
		case FenetrePrincipale.NouveauTicket:
			titre = "Creation ticket";
			largeur = 400;
			hauteur = 600;
			panelInterface = new PanelNouveauTicket(entreprise, this);
			break;
		
		
		case FenetrePrincipale.ModifierProjet:
			titre = "Modifier projet";
			panelInterface = new PanelModifierProjet(entreprise, this);
			break;
		case FenetrePrincipale.ModifierActivite:
			titre = "Modifier activit�";
			panelInterface = new PanelModifierActivite(entreprise, this);
			break;
			
		case FenetrePrincipale.AjouterRessource:
			titre = "Ajouter ressource";
			panelInterface = new PanelAjouterRessource(entreprise, this);
			break;
		case FenetrePrincipale.EnleverRessource:
			titre = "Enlever ressource";
			panelInterface = new PanelEnleverRessource(entreprise, this);
			break;

		case FenetrePrincipale.InformationCompte:
			titre = "Ajouter ressource";
			panelInterface = new PanelAjouterRessource(entreprise, this);
			break;
		case FenetrePrincipale.Deconnexion:
			titre = "Enlever ressource";
			panelInterface = new PanelEnleverRessource(entreprise, this);
			break;

		}
	}
}
