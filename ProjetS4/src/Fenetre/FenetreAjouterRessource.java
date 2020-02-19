package Fenetre;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import Model.Entreprise;
import Ressource.*;

public class FenetreAjouterRessource extends JDialog{
	private Entreprise entreprise;
    private JComboBox<String> comboBoxType;
    private JComboBox<String> comboBoxRessource;
	ArrayList<Ressource> listeRessource;

	private JPanel panelPrincipal = new JPanel();
	private JPanel panelSecondaire = new JPanel();

	public FenetreAjouterRessource(Entreprise entreprise) {
		this.entreprise = entreprise;
		if (entreprise.getProjetSelectionner() != null) {
			this.setTitle("Ajout ressource");
			this.setSize(270,220);
			this.setLocationRelativeTo(null);
			this.addWindowListener(new FermerFenetre(this));
			this.setVisible(true);
			JRootPane rootPane = this.getRootPane();
			rootPane.getInputMap().put(
					KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
					"close");
			rootPane.getActionMap().put("close", new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					//this.setVisible(false);
					dispose();
				}
			});


			creationInterface();			
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Aucun projet selectionner", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	private void creationInterface() {
		this.add(panelPrincipal);
		panelPrincipal.setLayout(new GridLayout(2, 0));
		panelSecondaire.setLayout(new GridLayout(2, 0));
		panelPrincipal.add(choixType());
		comboBoxType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String type = (String)comboBoxType.getSelectedItem();
				panelSecondaire.removeAll();
				panelSecondaire.add(ajoutRessource(type));
				if (listeRessource.size() > 0) {
					panelSecondaire.add(creerBoutton());
				}
				panelPrincipal.add(panelSecondaire);
				panelPrincipal.revalidate();
			}
		});
	}

	private JButton creerBoutton() {
		JButton bouton = new JButton("Ajouter");
		panelPrincipal.add(bouton);
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	    	    int index = comboBoxRessource.getSelectedIndex();
	    	    entreprise.ajouterRessourceProjet(listeRessource.get(index).getId(), entreprise.getProjetSelectionner().getNom());
	    	    dispose();
	        }
	    });			
	    return bouton;
	}
		
	private JPanel choixType() {
	    String[] type = {"Choisissez le type de la ressource", Ressource.PERSONNE, Ressource.SALLE, Ressource.CALCULATEUR};
	    comboBoxType = new JComboBox<String>(type);
	    JPanel panel = new JPanel();
	    panel.setBackground(Color.white);
	    panel.setBorder(BorderFactory.createTitledBorder("Type de la ressource a ajouter"));
	    panel.add(comboBoxType);
	    return panel;
	}
	
	private JPanel ajoutRessource(String type) {
	    JPanel panel = new JPanel();
	    panel.setBackground(Color.white);
	    panel.setBorder(BorderFactory.createTitledBorder(type + " a ajouter"));
	    panel.add(creerComboBox(type));
	    return panel;
	}

	private JComboBox<String> creerComboBox(String type) {
	ArrayList<String> listeNom = new ArrayList<String>();
	listeRessource = entreprise.getListeRessourceType(type);	
	for (int i=0; i<listeRessource.size(); i++) {
		Ressource ressource = listeRessource.get(i); 
		if (ressource.getType() == Ressource.PERSONNE && type == Ressource.PERSONNE) {
			listeNom.add(((Personne)ressource).getPrenom() + " " + ressource.getNom());
		}
		if (ressource.getType() == Ressource.SALLE && type == Ressource.SALLE) {
			listeNom.add(ressource.getNom());
		}
		if (ressource.getType() == Ressource.CALCULATEUR && type == Ressource.CALCULATEUR) {
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
