package Panel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import EcouteurEvenement.SourisProjetListener;
import Model.Entreprise;
import Model.Projet;
import Ressource.Personne;

public class PanelProjet extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private JTextField rechercher;
	private Font font = new Font("Arial", Font.PLAIN, 30);
	
	public PanelProjet(Entreprise entreprise) {
		this.entreprise = entreprise;
		this.setBackground(PanelPrincipal.BLEU2);
		this.setLayout(new BorderLayout());
		this.add(afficherProjet(), BorderLayout.WEST);
		this.add(afficherRechercher(), BorderLayout.EAST);
		this.setPreferredSize(new Dimension(this.getWidth(),100));

	}
		
	
	public JPanel afficherProjet() {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLEU2);
		ArrayList<Projet> listeProjet = entreprise.getProjetUser(entreprise.getUser());
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		if (listeProjet.size()>0) { //on vérifie qu'il a des projets dans la liste
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
	
	
	private JPanel afficherRechercher() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,3));
		panel.setBackground(PanelPrincipal.BLEU2);
    	rechercher = new JTextField("rechercher                             ");
    	rechercher.getFont().deriveFont(Font.ITALIC);
    	rechercher.setForeground(PanelPrincipal.GRIS2);
    	rechercher.addMouseListener(new MouseListener() {           
			@Override
			public void mouseClicked(MouseEvent e) {
    	        JTextField texteField = ((JTextField)e.getSource());
    	        texteField.setText("");
    	        texteField.getFont().deriveFont(Font.PLAIN);
    	        texteField.setForeground(PanelPrincipal.NOIR);
    	        texteField.removeMouseListener(this);
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
    	});
    	panel.add(new JLabel(" "));
    	panel.add(rechercher);
    	panel.add(new JLabel(" "));
		return panel;
	}	
	
	
}
