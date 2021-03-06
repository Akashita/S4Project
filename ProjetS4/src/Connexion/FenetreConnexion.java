package Connexion;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import Fenetre.FermerFenetre;
import Panel.PanelPrincipal;
import SQL.JavaSQL;


/**
 * Fenetre ou l'utilisateur va se connecter � son compte
 * @author Damien
 *
 */
public class FenetreConnexion extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FenetreConnexion() {
		JavaSQL.connection();

		this.setTitle("Connexion");
		this.setSize(350,400);
		this.setLocationRelativeTo(null);
		this.setBackground(PanelPrincipal.BLEU2);
		this.setLayout(new BorderLayout());
		
		this.add(new PanelConnexion(this), BorderLayout.CENTER);
		this.addWindowListener(new FermerFenetre(this));
		this.setVisible(true);	
	}
}
