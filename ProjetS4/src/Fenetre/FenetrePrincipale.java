package Fenetre;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import EcouteurEvenement.AjouterEnleverRessourceListener;
import EcouteurEvenement.NouveauListener;
import Model.Entreprise;
import Panel.*;


/**
 * Fenetre principal qui contient les trois panels principaux:
 * - le panel principal
 * - le panel ressource 
 * - le panel projet
 * 
 * il contient egalement la barre du menu
 * 
 * @author damien planchamp
 *
 */
public class FenetrePrincipale extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	private Entreprise entreprise;
	public static final int NouveauProjet = 0, NouvelleRessource = 1, NouvelleActivite = 2,
			AjouterRessource = 3, EnleverRessource = 4;

	public FenetrePrincipale(Entreprise entreprise) {	
		this.entreprise = entreprise;
		this.setTitle("ProjetS4");
		this.setSize(LARGEUR,HAUTEUR);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setBackground(PanelPrincipal.BLANC);
		setLayout(new BorderLayout());
		PanelPrincipal pp = new PanelPrincipal(entreprise);	
		
		
		this.add(pp, BorderLayout.CENTER);
		creationBarreMenu();
		
		this.addWindowListener(new FermerFenetre(this));
		this.setVisible(true);	
		pp.setVisible(true);
	}
	
	private void creationBarreMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuFichier = new JMenu("Fichier");
		JMenu menuEditer = new JMenu("Editer");
		JMenu menuPropos = new JMenu("A propos");

		JMenuItem itemNouveauProjet = new JMenuItem("Nouveau projet", KeyEvent.VK_P);
	    KeyStroke ctrlNouveauProjet = KeyStroke.getKeyStroke("control P");
	    itemNouveauProjet.setAccelerator(ctrlNouveauProjet);
	    itemNouveauProjet.addActionListener(new NouveauListener(entreprise, NouveauProjet));

		JMenuItem itemNouvelleRessource = new JMenuItem("Nouvelle ressource", KeyEvent.VK_R);
	    KeyStroke ctrlNouvelleRessource = KeyStroke.getKeyStroke("control R");
	    itemNouvelleRessource.setAccelerator(ctrlNouvelleRessource);
	    itemNouvelleRessource.addActionListener(new NouveauListener(entreprise, NouvelleRessource));

		JMenuItem itemQuitter = new JMenuItem("Quitter", KeyEvent.VK_Q);
	    KeyStroke ctrlQuitter = KeyStroke.getKeyStroke("control Q");
	    itemQuitter.setAccelerator(ctrlQuitter);
	    itemQuitter.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent event){
	          System.exit(0);
	        }
	      });		
		
		JMenuItem itemAjout = new JMenuItem("Ajouter ressource", KeyEvent.VK_A);
	    KeyStroke ctrlAjout = KeyStroke.getKeyStroke("control A");
	    itemAjout.setAccelerator(ctrlAjout);
	    itemAjout.addActionListener(new AjouterEnleverRessourceListener(entreprise, AjouterRessource));

	    JMenuItem itemEnlever = new JMenuItem("Enlever ressource", KeyEvent.VK_E);
	    KeyStroke ctrlEnlever = KeyStroke.getKeyStroke("control E");
	    itemEnlever.setAccelerator(ctrlEnlever);
	    itemEnlever.addActionListener(new AjouterEnleverRessourceListener(entreprise, EnleverRessource));

	    JMenuItem itemNouvelleActivite = new JMenuItem("Ajouter Activite", KeyEvent.VK_Y);
	    KeyStroke ctrlNouvelleActivite = KeyStroke.getKeyStroke("control Y");
	    itemNouvelleActivite.setAccelerator(ctrlNouvelleActivite);
	    itemNouvelleActivite.addActionListener(new NouveauListener(entreprise, NouvelleActivite));

		JMenuItem itemModifier = new JMenuItem("Modifier");
		JMenuItem itemSupprimer = new JMenuItem("Supprimer Projet");

		JMenuItem itemPropos = new JMenuItem("Projet");

		
		
		menuFichier.add(itemNouveauProjet);
		menuFichier.add(itemNouvelleRessource);
		menuFichier.add(itemQuitter);
		
		menuEditer.add(itemAjout);
		menuEditer.add(itemEnlever);
		menuEditer.add(itemNouvelleActivite);
		menuEditer.add(itemModifier);
		menuEditer.add(itemSupprimer);
		
		menuPropos.add(itemPropos);
		
		menuBar.add(menuFichier);
		menuBar.add(menuEditer);
		menuBar.add(menuPropos);
		this.setJMenuBar(menuBar);	
	}

}
