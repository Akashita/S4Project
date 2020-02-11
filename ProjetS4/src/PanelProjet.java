import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelProjet extends JPanel{
	private ArrayList<Projet> listeProjet = new ArrayList<Projet>();
	private ArrayList<JLabel> listeLabel = new ArrayList<JLabel>();
	
	public PanelProjet() {
	}
	
	public void ajoutProjet(Projet projet) {
		creerLabel(projet.getNom());
		listeProjet.add(projet);
		this.revalidate();
	}
	
	private void creerLabel(String nom) {
		JLabel label = new JLabel(nom);
		label.addMouseListener(new SourisProjetListener(this, label));
		selectionnerLabel (label);
		this.add(label);
		listeLabel.add(label);
	}
	
	public void selectionnerLabel (JLabel label) {
		int indice;
		for(int i=0; i<listeLabel.size();i++) {
			deselectionnerLabel(listeLabel.get(i));
			if (listeLabel.get(i) ==  label) {
				indice = i;
			}
		}
		label.setForeground(new Color(255,255,255));
		label.setOpaque(true);
		label.setBackground(Color.BLUE);
		
	 }
	
	private void deselectionnerLabel(JLabel label) {
		label.setForeground(new Color(0,0,0));
		label.setOpaque(true);
		label.setBackground(Color.WHITE);
	}
}
