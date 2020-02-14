package Fenetre;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import Model.Entreprise;
import Model.Projet;
import Ressource.Ressource;

public class FenetreEnleverRessource extends JFrame{
	Entreprise entreprise;
	
	public FenetreEnleverRessource(Entreprise entreprise) {
		this.entreprise= entreprise;
		this.setTitle("Enlever ressource");
		this.setSize(300,200);
		this.setVisible(true);
		enleverRessource();
	}

	private void enleverRessource() {
		ArrayList<Ressource> listeRessource = entreprise.getProjetSelectionner().getListe();
		String tab[] = new String[listeRessource.size()];
		for (int i=0; i<tab.length; i++) {
			tab[i] = listeRessource.get(i).getNom();
		}
		enleverRessource(tab, listeRessource);			
	}

	private void enleverRessource(String[] tab, ArrayList<Ressource> listeRessource) {
		Projet projet = entreprise.getProjetSelectionner();
		JButton bouton = new JButton("Enlever");
		bouton.setBounds(100, 20, 100, 10);
		final JComboBox jcb = new JComboBox(tab);
		this.add(bouton);
		this.add(jcb);
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	    	    int index = jcb.getSelectedIndex();
	    	    entreprise.enleverRessourceProjet(index);
	        }
	    });
	}	
}
