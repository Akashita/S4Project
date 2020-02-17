package Fenetre;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.Entreprise;
import Model.Projet;
import Ressource.Personne;
import Ressource.Ressource;

public class FenetreEnleverRessource extends JDialog{
	private Entreprise entreprise;
	private JPanel panelPrincipal = new JPanel();
    private JComboBox<String> comboBoxRessource;
	ArrayList<Ressource> listeRessource;

    public FenetreEnleverRessource(Entreprise entreprise) {
		this.entreprise= entreprise;
		if (entreprise.getProjetSelectionner() != null) {
			this.setTitle("Enlever ressource");
			this.setSize(300,200);
			this.setLocationRelativeTo(null);
			this.addWindowListener(new FermerFenetre(this));
			this.setVisible(true);
			creerInterface();
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Aucun projet selectioner", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}		
	}

	private void creerInterface() {
		panelPrincipal.setBackground(Color.white);
		panelPrincipal.setLayout(new GridLayout(2, 0));
	    panelPrincipal.setBorder(BorderFactory.createTitledBorder("Enlever ressource du Projet"));
		panelPrincipal.add(creerComboBox());
		panelPrincipal.add(creerBoutton());
		this.add(panelPrincipal);		
	}
	
	private JButton creerBoutton() {
		JButton bouton = new JButton("Enlever");
		panelPrincipal.add(bouton);
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	    	    int index = comboBoxRessource.getSelectedIndex();
	    	    entreprise.enleverRessourceProjet(listeRessource.get(index).getId());
	    	    dispose();
	        }
	    });			
	    return bouton;
	}

	
	private JComboBox<String> creerComboBox(){
		ArrayList<String> listeNom = new ArrayList<String>();
		listeRessource = entreprise.getProjetSelectionner().getListe();

		for (int i=0; i<listeRessource.size(); i++) {
			Ressource ressource = listeRessource.get(i); 
			if (ressource.getType() == Ressource.PERSONNE) {
				listeNom.add(((Personne)ressource).getPrenom() + " " + ressource.getNom());
			}
			if (ressource.getType() == Ressource.SALLE) {
				listeNom.add(ressource.getNom());
			}
			if (ressource.getType() == Ressource.CALCULATEUR) {
				listeNom.add(ressource.getNom());
			}
		}
		comboBoxRessource = new JComboBox<String>(convertirArrayEnTab(listeNom));
		return comboBoxRessource;
		
	}
		
	private String[] convertirArrayEnTab(ArrayList<String> listeNom) {
		String[] tab = new String[listeNom.size()];
		for (int i=0; i<tab.length; i++) {
			tab[i] = listeNom.get(i);
		}
		return tab;
	}	

}
