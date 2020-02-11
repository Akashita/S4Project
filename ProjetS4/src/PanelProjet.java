import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelProjet extends JPanel{
	private ArrayList<Projet> listeProjet = new ArrayList<Projet>();
	private ArrayList<JLabel> listeLabel = new ArrayList<JLabel>();
	
	Entreprise entreprise;
	
	public void nouveauProjet(Entreprise entreprise, String nom) {
		this.entreprise = entreprise;
		this.entreprise.creerProjet(nom);
		ajoutProjet(entreprise.getDernierProjet());
	}

	public void ajoutProjet(Projet projet) {
		listeProjet.add(projet);
		listeProjet.get(0).deselectionner();

		creerLabel(projet.getNom());

		this.revalidate();
	}
	
	private void creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		label.addMouseListener(new SourisProjetListener(this, label));
		this.add(label);
		listeLabel.add(label);
		selectionnerLabel (label);

	}
	
	public void selectionnerLabel (JLabel label) {
		int indice = 0;
		for(int i=0; i<listeLabel.size();i++) {
			deselectionnerLabel(listeLabel.get(i), listeProjet.get(i));
			if (listeLabel.get(i) ==  label) {
				indice = i;
			}
			entreprise.update();
		}
		label.setForeground(new Color(255,255,255));
		label.setOpaque(true);
		label.setBackground(Color.BLUE);
		listeProjet.get(indice).selectionner();
	}
	
	private void deselectionnerLabel(JLabel label, Projet projet) {
		label.setForeground(new Color(0,0,0));
		label.setOpaque(true);
		label.setBackground(Color.WHITE);
		projet.deselectionner();
	}

}
