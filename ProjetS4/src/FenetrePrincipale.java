import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Panel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

public class FenetrePrincipale extends JFrame{
	public static final int HAUTEUR = 400,
			LARGEUR = 500;
	
	public FenetrePrincipale(Entreprise entreprise) {
		this.setTitle("ProjetS4");
		setSize(LARGEUR,HAUTEUR);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		PanelPrincipal pp = new PanelPrincipal(entreprise);		
		this.add(pp, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		JMenu menuNouveau = new JMenu("Nouveau");
		JMenu menuAjout = new JMenu("Ajouter");
		
		JMenuItem  itemNouvelleRessource = new JMenuItem("Nouvelle Ressource");
		itemNouvelleRessource.addActionListener(new NouvelleRessourceListener(entreprise));
		JMenuItem itemNouveauProjet = new JMenuItem("Nouveau Projet");
		itemNouveauProjet.addActionListener(new NouveauProjetListener(entreprise));

		JMenuItem  itemAjoutPersonne = new JMenuItem(Ressource.PERSONNE);
		itemAjoutPersonne.addActionListener(new AjouterRessourceListener(entreprise, Ressource.PERSONNE));
		JMenuItem itemAjoutSalle = new JMenuItem(Ressource.SALLE);
		itemAjoutSalle.addActionListener(new AjouterRessourceListener(entreprise, Ressource.SALLE));
		JMenuItem itemAjoutCalculateur = new JMenuItem(Ressource.CALCULATEUR);
		itemAjoutCalculateur.addActionListener(new AjouterRessourceListener(entreprise, Ressource.CALCULATEUR));

		
		
		menuNouveau.add(itemNouvelleRessource);
		menuNouveau.add(itemNouveauProjet);
		menuAjout.add(itemAjoutPersonne);
		menuAjout.add(itemAjoutSalle);
		menuAjout.add(itemAjoutCalculateur);
		menuFichier.add(menuNouveau);
		menuFichier.add(menuAjout);
		menuBar.add(menuFichier);
		setJMenuBar(menuBar);
		
		//JMenu test2 = new JMenu("Edition");
		this.addWindowListener(new FermerFenetre(this));
		setVisible(true);		
	}
}
