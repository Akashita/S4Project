package Fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import Model.Entreprise;
import Model.Projet;
import Ressource.*;

public class FenetreAjouterRessource extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private Entreprise entreprise;
    private JComboBox<String> comboBoxType;
    private JComboBox<String> comboBoxRessource;
    private ArrayList<Ressource> listeRessource;

	private JPanel panelPrincipal = new JPanel();
	private JPanel panelSecondaire = new JPanel();
	
	private JTextField jourHomme;

    private JComboBox<String> comboBoxPourcent;
    
	private final String JOURHOMME = "jourHomme", POURCENT = "pourcent";
	
	private final int tailleLargeurDefaut = 280,
			tailleHauteurDefaut = 100,
			tailleLargeurRessource = 500,
			tailleHauteurRessource = 200;



	public FenetreAjouterRessource(Entreprise entreprise) {
		this.entreprise = entreprise;
		if (entreprise.getProjetSelectionner() != null) {
			this.setTitle("Ajout ressource");
			this.setSize(tailleLargeurDefaut,tailleHauteurDefaut);
			this.setLocationRelativeTo(null);
			this.addWindowListener(new FermerFenetre(this));
			this.setVisible(true);
			
			JRootPane rootPane = this.getRootPane();
			rootPane.getInputMap().put(
					KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
					"close");
			rootPane.getActionMap().put("close", new AbstractAction() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					//this.setVisible(false);
					dispose();
				}
			});


			creationInterface();			
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Aucun projet selectionné", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	private void creationInterface() {
		panelPrincipal.setBackground(Color.WHITE);
		panelSecondaire.setBackground(Color.WHITE);
		this.add(panelPrincipal);
	    //panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.add(choixType());
		comboBoxType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String type = (String)comboBoxType.getSelectedItem();
				changeTailleFenetre("ressource");
				panelSecondaire.removeAll();
				panelSecondaire.add(ajoutRessourceInterface(type));
				if (listeRessource.size() > 0) {
					panelSecondaire.add(creerBoutton());
				}
				panelPrincipal.add(panelSecondaire);
				panelPrincipal.revalidate();
				
			}
		});
	}	 
	
	//adapte la taille de la fenetre en fonction du type de la ressource
	private void changeTailleFenetre(String type) {
		if (type == "defaut") {
			this.setSize(tailleLargeurDefaut, tailleHauteurDefaut);
		}
		else {
			this.setSize(tailleLargeurRessource, tailleHauteurRessource);
		}
	}

	//creer le bouton pour ajouter la ressource selectionner
	private JButton creerBoutton() {
		JButton bouton = new JButton("Ajouter");
		panelPrincipal.add(bouton);
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	ajouterRessource();
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
	
	//creer l'interface des informations à saisir 
	private JPanel ajoutRessourceInterface(String type) {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBackground(Color.white);
	    panel.setBorder(BorderFactory.createTitledBorder(type + " a ajouter"));
	    panel.add(creerComboBox(type));
	    return panel;
	}
	
	//retourne un panel avec une combobox et un label
	private JPanel creerComboBox(String type) {
		JPanel panel = new JPanel();
		if (type == this.POURCENT) {
		    panel.add(new JLabel("Veillez indiquer son pourcentage: "));
		    String tabPourcent[] = {"25","50", "75", "100"};
			comboBoxPourcent = new JComboBox<String>(tabPourcent);
			panel.add(comboBoxPourcent);
		}
		else {
			ArrayList<String> listeNom = new ArrayList<String>();
			listeRessource = entreprise.getListeRessourceType(type);
		    panel.add(new JLabel("Veillez indiquer le type de la ressource: "));
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
			panel.add(comboBoxRessource);
		}
		return panel;
	}
	
	
	private String[] convertirArrayEnTab(ArrayList<String> listeNom) {
		String[] tab = new String[listeNom.size()];
		for (int i=0; i<tab.length; i++) {
			tab[i] = listeNom.get(i);
		}
		return tab;
	}	

	private void ajouterRessource() {
		int index = comboBoxRessource.getSelectedIndex();
	    Ressource ressource = getRessource(index);
		entreprise.ajouterRessourceActivite(ressource);
	    dispose();			
	}
	
	private Ressource getRessource(int index) {
		return listeRessource.get(index);
	}

}
