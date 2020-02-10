import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Panel;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class FenetrePrincipale extends JFrame{
	public static final int HAUTEUR = 1000,
			LARGEUR = 1500;
	
	public FenetrePrincipale(Entreprise entreprise) {
		this.setTitle("ProjetS4");
		setSize(LARGEUR,HAUTEUR);
		setLayout(new BorderLayout());
		PanelPrincipal pp = new PanelPrincipal(entreprise);		
		Panel panelGauche = new Panel(new FlowLayout());
		Panel panelBas = new Panel(new FlowLayout());
		
		Button nouvelleRessource = new Button("nouvelle une ressource");
		panelGauche.add(nouvelleRessource);
		nouvelleRessource.addActionListener(new NouvelleRessourceListener(entreprise));

		Button ajouterRessource = new Button("ajouter une ressource");
		panelBas.add(ajouterRessource);
		ajouterRessource.addActionListener(new AjouterRessourceListener(entreprise));

		Button nouveauProjet = new Button("nouveau projet");
		panelBas.add(nouveauProjet);
		nouveauProjet.addActionListener(new NouveauProjetListener(entreprise));
		
		this.add(panelGauche,BorderLayout.WEST);
		this.add(panelBas,BorderLayout.SOUTH);
		this.add(pp, BorderLayout.CENTER);

		this.addWindowListener(new FermerFenetre(this));
		setVisible(true);		
	}
}
