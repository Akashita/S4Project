package Fenetre;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import EcouteurEvenement.AjouterRessourceListener;
import EcouteurEvenement.EnleverRessourceListener;
import EcouteurEvenement.NouveauListener;
import EcouteurEvenement.nouvelleActiviteListener;
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
	private JTabbedPane onglet = new JTabbedPane();

	public FenetrePrincipale(Entreprise entreprise) {	
		this.entreprise = entreprise;
		this.setTitle("ProjetS4");
		this.setSize(LARGEUR,HAUTEUR);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		PanelProjet panelProjet = new PanelProjet(entreprise);
		PanelRessource panelRessource = new PanelRessource(entreprise);
		PanelPrincipal pp = new PanelPrincipal(entreprise, 
				panelRessource, panelProjet, this);	
		
		
		//this.add(pp, BorderLayout.CENTER);
		this.add(panelRessource, BorderLayout.WEST);
		//this.add(panelProjet, BorderLayout.SOUTH);
		this.add(onglet, BorderLayout.CENTER);

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

		JMenuItem itemNouveau = new JMenuItem("Nouveau", KeyEvent.VK_N);
	    KeyStroke ctrlNouveau = KeyStroke.getKeyStroke("control N");
	    itemNouveau.setAccelerator(ctrlNouveau);
	    itemNouveau.addActionListener(new NouveauListener(entreprise));

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
	    itemAjout.addActionListener(new AjouterRessourceListener(entreprise));

	    JMenuItem itemEnlever = new JMenuItem("Enlever ressource", KeyEvent.VK_E);
	    KeyStroke ctrlEnlever = KeyStroke.getKeyStroke("control E");
	    itemEnlever.setAccelerator(ctrlEnlever);
	    itemEnlever.addActionListener(new EnleverRessourceListener(entreprise));

	    JMenuItem itemNouvelleActivite = new JMenuItem("Ajoutre Activite", KeyEvent.VK_Y);
	    KeyStroke ctrlNouvelleActivite = KeyStroke.getKeyStroke("control Y");
	    itemNouvelleActivite.setAccelerator(ctrlNouvelleActivite);
	    itemNouvelleActivite.addActionListener(new nouvelleActiviteListener(entreprise));

		JMenuItem itemModifier = new JMenuItem("Modifier");
		JMenuItem itemSupprimer = new JMenuItem("Supprimer Projet");

		JMenuItem itemPropos = new JMenuItem("Projet");

		
		
		menuFichier.add(itemNouveau);
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

	public void ajouterProjet(PanelInfoProjet pip) {
		onglet.add(pip.getProjetNom(), pip);
	}
	
	public PanelInfoProjet getPanelInfoProjet() {
		if (onglet.getSelectedComponent()!=null) {
			return (PanelInfoProjet) onglet.getSelectedComponent();
		}
		else {
			return null;
		}
	}
}
