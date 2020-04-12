package Fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JPanel;

import Model.Entreprise;
import Panel.PanelPrincipal;

public class FenetreModal extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
    protected JPanel panelInterface;
	
    protected Color couleurFond = PanelPrincipal.BLEU3;


	public FenetreModal(Entreprise entreprise, String titre, int largeur, int hauteur) {
		super(entreprise.getFenetrePrincipale(), titre, true);
		this.setSize(largeur, hauteur);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetre(this));
		this.setLayout(new BorderLayout());
		
	}
	
		
}
