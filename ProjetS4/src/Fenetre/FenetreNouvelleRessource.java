package Fenetre;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import Model.Entreprise;
import Panel.PanelRessource;
import Ressource.Personne;
import Ressource.Ressource;

public class FenetreNouvelleRessource extends JDialog{
	private Entreprise entreprise;
	private JPanel panelPrincipal = new JPanel();
    private JComboBox<String> comboBoxType;
	private JPanel panelSecondaire = new JPanel();
	
	private JTextField nom, prenom, capacite;
	private JRadioButton tranche1, tranche2, tranche3;

	private final String NOM = "nom", PRENOM = "prenom", CAPACITE = "capacité";
	
	private final int tailleLargeurPersonne = 360,
			tailleHauteurPersonne = 220,
			tailleLargeurSalle = 360,		
			tailleHauteurSalle = 220,
			tailleLargeurCalculateur = 360,
			tailleHauteurCalculateur = 180;

	public FenetreNouvelleRessource(Entreprise entreprise) {
		this.entreprise = entreprise;
		this.setSize(300,100);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetre(this));
		this.setVisible(true);
		creationInterface();
		//creationRessource(choixType());
	}
	
	private void creationInterface() {
		panelPrincipal.setBackground(Color.WHITE);
		panelSecondaire.setBackground(Color.WHITE);
		this.add(panelPrincipal);
		panelPrincipal.add(choixType());
		comboBoxType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String type = (String)comboBoxType.getSelectedItem();
				changeTailleFenetre(type);
				panelSecondaire.removeAll();
				panelSecondaire.add(creationRessource(type));
				panelPrincipal.add(panelSecondaire);
				panelPrincipal.revalidate();
			}
		});
	}
	
	private void changeTailleFenetre(String type) {
		if (type == Ressource.PERSONNE) {
			this.setSize(tailleLargeurPersonne, tailleHauteurPersonne);
		}
		if (type == Ressource.SALLE) {
			this.setSize(tailleLargeurSalle, tailleHauteurSalle);
		}
		if (type == Ressource.CALCULATEUR) {
			this.setSize(tailleLargeurCalculateur, tailleHauteurCalculateur);
		}
	}

	private JPanel choixType() {
	    String[] type = {"Choisissez le type de la ressource", Ressource.PERSONNE, Ressource.SALLE, Ressource.CALCULATEUR};
	    comboBoxType = new JComboBox<String>(type);
	    JPanel panel = new JPanel();
	    panel.setBackground(Color.white);
	    panel.setBorder(BorderFactory.createTitledBorder("Type de la ressource a creer"));
	    panel.add(comboBoxType);
	    return panel;
	}
	
	private JPanel creationRessource(String type) {
	    JPanel panelPrincipal= new JPanel();
	    panelPrincipal.setBackground(Color.white);
	    panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

	    JPanel panel1 = new JPanel();
	    panel1.setBackground(Color.white);
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
	    panel1.setBorder(BorderFactory.createTitledBorder(type + " a creer"));
		if (type == Ressource.PERSONNE) {
			panel1.add(creationPersonne());
		}
		if (type == Ressource.SALLE) {
			panel1.add(creationSalle());
		}
		if (type == Ressource.CALCULATEUR) {
			panel1.add(creationCalculateur());
		}
		panelPrincipal.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.white);
		panel2.setLayout(new GridLayout(1, 1));
		panel2.add(creerBouttonNouv(type));
		panel2.add(creerBouttonAnnuler());
		panelPrincipal.add(panel2);
		return panelPrincipal;
	}

	private JPanel creationPersonne() {
	    JPanel panel = new JPanel();
	    panel.setBackground(Color.WHITE);
	    panel.setLayout(new GridLayout(2, 0));
	    panel.add(creerJTextField(this.PRENOM));
	    panel.add(creerJTextField(this.NOM));
	    return panel;
	}
	
	private JPanel creationSalle() {
	    JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBackground(Color.WHITE);
	    panel.add(creerJTextField(this.NOM));
	    panel.add(creerJTextField(this.CAPACITE));
	    return panel;
	}

	private JPanel creationCalculateur() {
	    JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBackground(Color.WHITE);
	    panel.add(creerJTextField(this.NOM));
	    return panel;
	}

	private JPanel creerJTextField(String type) {
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(1, 1));
	    panel.add(new JLabel("Veillez saisir son " + type + ": "));
	    if (type == this.PRENOM) {
	    	prenom = new JTextField();
		    panel.add(prenom);	    	
	    }
	    if (type == this.NOM) {
	    	nom = new JTextField();
		    panel.add(nom);	    	
	    }
	    if (type == this.CAPACITE) {
	    	capacite = new JTextField();
		    panel.add(capacite);	    	
	    }
		return panel;
	}

	private JButton creerBouttonNouv(String type) {
		JButton bouton = new JButton("Creer");
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
			        ajouterRessourceAEntreprise(type);	 
	        }
	    });			
	    return bouton;
	}
	
	private JButton creerBouttonAnnuler() {
		JButton bouton = new JButton("Annuler");
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	dispose();
	        }
	    });			
	    return bouton;
	}
	
	private void ajouterRessourceAEntreprise(String type) {
		if (!nom.getText().isEmpty()) {
			if (type == Ressource.PERSONNE) {
				if (!prenom.getText().isEmpty()) {
					entreprise.nouvPersonne(nom.getText(), prenom.getText());	 
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire son prenom", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			if (type == Ressource.SALLE) {
				if (estUnEntier(capacite.getText())) {
					entreprise.nouvSalle(nom.getText(), Integer.parseInt(capacite.getText()));
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire un nombre", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			if (type == Ressource.CALCULATEUR) {
				entreprise.nouvCalculateur(nom.getText());
			}	
		    dispose();	        			        					
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veillez ecrire son nom", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}

	private boolean estUnEntier(String chaine) {
		try {
			Integer.parseInt(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
}
