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
import Panel_Fenetre.PanelNouveauProjet;
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
    protected Color couleurFond = PanelPrincipal.BLEU3;


	public FenetreModal(Entreprise entreprise, int choix) {
		super(entreprise.getFenetrePrincipale(), true);
		this.entreprise = entreprise;
		
		getPanelFenetre(choix);
		
		this.setTitle(titre);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetre(this));
		this.setLayout(new BorderLayout());
		
		this.setLayout(new BorderLayout());
		this.add(panelInterface, BorderLayout.CENTER);
		this.setResizable(false);
		this.setVisible(true);

	}
	
	private void getPanelFenetre(int choix) {
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
			
		case FenetrePrincipale.ModifierProjet:
			titre = "Modifier projet";
			panelInterface = new PanelModifierProjet(entreprise, this);
			break;
		case FenetrePrincipale.ModifierActivite:
			titre = "Modifier activité";
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

		}
	}
}
