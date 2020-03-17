package Fenetre;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import Model.Entreprise;
import Ressource.Personne;
import Ressource.Ressource;

/**
 * Cette fenetre laisse le choix à l'utilisateur pour le choix de 
 * la ressource à enlever du projet selectionné
 * @author damien planchamp
 *
 */
public class FenetreEnleverRessource extends JDialog{
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private JPanel panelPrincipal = new JPanel();
    private JComboBox<String> comboBoxRessource;
	ArrayList<Ressource> listeRessource;

    /*public FenetreEnleverRessource(Entreprise entreprise) {
		this.entreprise= entreprise;
		if (entreprise.getProjetSelectionner() != null) { // on vérifie qu'un projet est selectionné
			listeRessource = entreprise.getProjetSelectionner().getListe();
			if (listeRessource.size()>0) { // on vérifie que des ressources sont présentes dans le projet
				this.setTitle("Enlever ressource");
				this.setSize(300,200);
				this.setLocationRelativeTo(null);
				this.addWindowListener(new FermerFenetre(this));
				this.setVisible(true);
				JRootPane rootPane = this.getRootPane(); //le rootPane permet la supression de la fenetre avec la touche "echap"
				rootPane.getInputMap().put(
						KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
						"close");
				rootPane.getActionMap().put("close", new AbstractAction() {
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent e) {
						//this.setVisible(false);
						dispose();
					}
				});
				creerInterface();				
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Aucune ressource presente", "Erreur", JOptionPane.ERROR_MESSAGE);							
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Aucun projet selectionner", "Erreur", JOptionPane.ERROR_MESSAGE);							
		}		
	}*/

    /**
     * creer l'interface
     */
	private void creerInterface() {
		panelPrincipal.setBackground(Color.white);
		panelPrincipal.setLayout(new GridLayout(2, 0));
	    panelPrincipal.setBorder(BorderFactory.createTitledBorder("Enlever ressource du Projet"));
		panelPrincipal.add(creerComboBox());
		if(listeRessource.size()>0) {
			panelPrincipal.add(creerBoutton());
		}
		this.add(panelPrincipal);		
	}
	
	
	// creer le bouton pour enlever la ressource selectionné
	private JButton creerBoutton() {
		JButton bouton = new JButton("Enlever");
		panelPrincipal.add(bouton);
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	    	    int index = comboBoxRessource.getSelectedIndex();
	    	    //entreprise.enleverRessourceProjet(listeRessource.get(index).getId());
	    	    dispose();
	        }
	    });			
	    return bouton;
	}
	
	private JComboBox<String> creerComboBox(){
		ArrayList<String> listeNom = new ArrayList<String>();

		for (int i=0; i<listeRessource.size(); i++) {
			Ressource ressource = listeRessource.get(i); 
			if (ressource.getType() == Ressource.PERSONNE) {
				listeNom.add("Personne: " + ((Personne)ressource).getPrenom() + " " + ressource.getNom());
			}
			if (ressource.getType() == Ressource.SALLE) {
				listeNom.add("Salle: "+ ressource.getNom());
			}
			if (ressource.getType() == Ressource.CALCULATEUR) {
				listeNom.add("Calculateur: " + ressource.getNom());
			}
		}
		comboBoxRessource = new JComboBox<String>(convertirArrayEnTab(listeNom));
		return comboBoxRessource;
		
	}
	
	 // convertit une arraylist en tableau necessaire pour la creation d'une combobox
	private String[] convertirArrayEnTab(ArrayList<String> listeNom) {
		String[] tab = new String[listeNom.size()];
		for (int i=0; i<tab.length; i++) {
			tab[i] = listeNom.get(i);
		}
		return tab;
	}	

}
