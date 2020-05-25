package Panel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisProjetListener;
import Model.Entreprise;
import Model.Projet;


/**
 * Affiche en haut une barre contenenant tout les projets concerner par l'user
 * Un admin peut voir tout les projets
 * Un Colaborateur peut voir tout ses projets (ceux qu'il gere et ceux dans lequel il travaille)
 * @author Damien
 *
 */
public class PanelProjet extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private Font font = new Font("Arial", Font.PLAIN, 30);
	
	public PanelProjet(Entreprise entreprise) {
		this.entreprise = entreprise;
		this.setBackground(PanelPrincipal.BLEU2);
		this.setLayout(new BorderLayout());
		this.add(afficherProjet(), BorderLayout.WEST);
		this.setPreferredSize(new Dimension(this.getWidth(),100));

	}
		
	
	public JPanel afficherProjet() {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLEU2);
		ArrayList<Projet> listeProjet = new ArrayList<Projet>();
		if (entreprise.getUser().estAdmin()) {
			listeProjet = entreprise.getListeProjetDeEntreprise();
		}
		else {
			listeProjet = entreprise.getListeProjetDeUser(entreprise.getUser().getId());
		}
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		if (listeProjet.size()>0) {
			for (int i=0; i<listeProjet.size(); i++) {
				Projet projet = listeProjet.get(i);
				boolean selectionner = false;
				if (entreprise.getProjetSelectionner() != null) {
					if(projet.getId() == entreprise.getProjetSelectionner().getId()) {
						selectionner = true;
					}				
				}
				panel.add(creerLabel(projet, selectionner));
			}			
		}
		return panel;
	}
	
	private JPanel creerLabel(Projet projet, boolean selectionner) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(150,this.getHeight()));

		panel.setLayout(new BorderLayout());
		JLabel label = new JLabel(projet.getNom());
		label.setFont(font);
	    label.setHorizontalAlignment(JLabel.CENTER);

		label.setOpaque(true);
		if(selectionner) {
			label.setForeground(PanelPrincipal.BLEU1);
			label.setBackground(PanelPrincipal.BLANC);
			panel.setBackground(PanelPrincipal.BLANC);
		}
		else {
			label.setForeground(PanelPrincipal.NOIR);
			label.setBackground(PanelPrincipal.BLEU2);
			panel.setBackground(PanelPrincipal.BLEU2);
		}
		label.addMouseListener(new SourisProjetListener(entreprise, projet));
		panel.add(label, BorderLayout.CENTER);
		return panel;
	}
	
	
}
