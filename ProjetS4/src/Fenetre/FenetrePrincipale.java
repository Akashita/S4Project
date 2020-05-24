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

import EcouteurEvenement.MenuBarListener;
import Model.Entreprise;
import Panel.*;


/**
 * Fenetre principal qui contient le panel principal
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
	public static final int NouveauProjet = 0, NouvelleRessource = 1, NouvelleActivite = 2, NouveauDomaine = 3,
			AjouterRessource = 4, EnleverRessource = 5,
			ModifierProjet = 6, ModifierActivite = 7, ModifierRessource = 8,
			InformationCompte = 9, Deconnexion = 10,
			NouveauTicket = 11, 
			NouveauConge = 12, NouvelleReunion = 13;
	
	public FenetrePrincipale(Entreprise entreprise) {	
		this.entreprise = entreprise;
		this.setTitle("ProjetS4");
		this.setSize(LARGEUR,HAUTEUR);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setBackground(PanelPrincipal.BLANC);
		this.setLayout(new BorderLayout());
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
		JMenu menuModifier = new JMenu("Modifier");
		JMenu menuPropos = new JMenu("à propos");
		JMenu menuCompte = new JMenu(entreprise.getUser().getPrenomNom());
		

		//------------------------------------------------------------------->>> menu fichier
		JMenuItem itemNouveauProjet = new JMenuItem("Nouveau projet", KeyEvent.VK_P);
	    KeyStroke ctrlNouveauProjet = KeyStroke.getKeyStroke("control P");
	    itemNouveauProjet.setAccelerator(ctrlNouveauProjet);
	    itemNouveauProjet.addActionListener(new MenuBarListener(entreprise, NouveauProjet));

		JMenuItem itemNouvelleRessource = new JMenuItem("Nouvelle ressource", KeyEvent.VK_R);
	    KeyStroke ctrlNouvelleRessource = KeyStroke.getKeyStroke("control R");
	    itemNouvelleRessource.setAccelerator(ctrlNouvelleRessource);
	    itemNouvelleRessource.addActionListener(new MenuBarListener(entreprise, NouvelleRessource));

		JMenuItem itemNouvelleDomaine = new JMenuItem("Nouveau domaine", KeyEvent.VK_D);
	    KeyStroke ctrlNouvelleDomaine = KeyStroke.getKeyStroke("control D");
	    itemNouvelleDomaine.setAccelerator(ctrlNouvelleDomaine);
	    itemNouvelleDomaine.addActionListener(new MenuBarListener(entreprise, NouveauDomaine));

		JMenuItem itemQuitter = new JMenuItem("Quitter", KeyEvent.VK_Q);
	    KeyStroke ctrlQuitter = KeyStroke.getKeyStroke("control Q");
	    itemQuitter.setAccelerator(ctrlQuitter);
	    itemQuitter.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent event){
	          System.exit(0);
	        }
	      });		
		
		//------------------------------------------------------------------->>> menu editer
		JMenuItem itemAjout = new JMenuItem("Ajouter ressource", KeyEvent.VK_A);
	    KeyStroke ctrlAjout = KeyStroke.getKeyStroke("control A");
	    itemAjout.setAccelerator(ctrlAjout);
	    itemAjout.addActionListener(new MenuBarListener(entreprise, AjouterRessource));

	    JMenuItem itemEnlever = new JMenuItem("Enlever ressource", KeyEvent.VK_E);
	    KeyStroke ctrlEnlever = KeyStroke.getKeyStroke("control E");
	    itemEnlever.setAccelerator(ctrlEnlever);
	    itemEnlever.addActionListener(new MenuBarListener(entreprise, EnleverRessource));

	    JMenuItem itemNouvelleActivite = new JMenuItem("Ajouter Activite", KeyEvent.VK_Y);
	    KeyStroke ctrlNouvelleActivite = KeyStroke.getKeyStroke("control Y");
	    itemNouvelleActivite.setAccelerator(ctrlNouvelleActivite);
	    itemNouvelleActivite.addActionListener(new MenuBarListener(entreprise, NouvelleActivite));
	    
		//----------------------------------------->>> sous menu Modifier
	    JMenuItem itemModifierProjet = new JMenuItem("Modifier Projet", KeyEvent.VK_O);
	    KeyStroke ctrlModifierProjet = KeyStroke.getKeyStroke("control O");
	    itemModifierProjet.setAccelerator(ctrlModifierProjet);
	    itemModifierProjet.addActionListener(new MenuBarListener(entreprise, ModifierProjet));

	    JMenuItem itemModifierActivite = new JMenuItem("Modifier Activite", KeyEvent.VK_X);
	    KeyStroke ctrlModifierActivite = KeyStroke.getKeyStroke("control X");
	    itemModifierActivite.setAccelerator(ctrlModifierActivite);
	    itemModifierActivite.addActionListener(new MenuBarListener(entreprise, ModifierActivite));
	    

		//------------------------------------------------------------------->>> menu propos
		JMenuItem itemPropos = new JMenuItem("Projet");

		//------------------------------------------------------------------->>> menu compte
		JMenuItem itemInfoCompte = new JMenuItem("Information Compte", KeyEvent.VK_I);
	    KeyStroke ctrlInfoCompte = KeyStroke.getKeyStroke("control I");
	    itemInfoCompte.setAccelerator(ctrlInfoCompte);
	    itemInfoCompte.addActionListener(new MenuBarListener(entreprise, InformationCompte));

	    JMenuItem itemDeconnexion = new JMenuItem("Déconnexion", KeyEvent.VK_D);
	    KeyStroke ctrlDeconnexion = KeyStroke.getKeyStroke("control D");
	    itemDeconnexion.setAccelerator(ctrlDeconnexion);
	    itemDeconnexion.addActionListener(new MenuBarListener(entreprise, Deconnexion));


	    
	    
	    
		menuFichier.add(itemNouveauProjet);
		menuFichier.add(itemNouvelleRessource);
		menuFichier.add(itemNouvelleDomaine);
		menuFichier.add(itemQuitter);
		
		menuEditer.add(itemAjout);
		menuEditer.add(itemEnlever);
		menuEditer.add(itemNouvelleActivite);
		
		menuModifier.add(itemModifierProjet);
		menuModifier.add(itemModifierActivite);	
		menuEditer.add(menuModifier);
		
		menuPropos.add(itemPropos);
		
		menuCompte.add(itemInfoCompte);
		menuCompte.add(itemDeconnexion);
		
		menuBar.add(menuFichier);
		menuBar.add(menuEditer);
		menuBar.add(menuPropos);
		menuBar.add(menuCompte);
		this.setJMenuBar(menuBar);	
	}

}
