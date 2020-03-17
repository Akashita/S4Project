package Fenetre;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import Model.Entreprise;
import Ressource.Ressource;

/**
 * Cette fenetre affiche à l'utilisateur les données à rentrer pour
 * creer une nouvelle ressource (Personne, Salle, Calculateur)
 * 
 * @author damien planchamp
 *
 */
public class FenetreNouvelleActivite extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private JPanel panelPrincipal = new JPanel();
    private JComboBox<String> comboBoxType;
	private JPanel panelSecondaire = new JPanel();
	
	
	private JTextField titre, charge, ordre;
	
	
	public FenetreNouvelleActivite(Entreprise entreprise) {
		this.entreprise = entreprise;
		this.setSize(300,100);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetre(this));
		this.setVisible(true);
		creationInterface();
	}
	
	/**
	 * creation de l'interface 
	 */
	private void creationInterface() {
		/*
		 * le panel principal gere le type de la ressource
		 * le panel secondaire gere les informations de saisie pour la ressource 
		 */
		panelPrincipal.removeAll();
	    panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.setBackground(Color.WHITE);
		panelPrincipal.add(creationInterface());
		
		panelSecondaire.setBackground(Color.WHITE);
		this.add(panelPrincipal);
		comboBoxType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String type = (String)comboBoxType.getSelectedItem();
				panelSecondaire.removeAll();
				panelSecondaire.add(creationActiviteInterface());
				panelPrincipal.add(panelSecondaire);
				panelPrincipal.revalidate();
			}
		});
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
		/*
		 * retourne un panel avec le champs de saisie et un label
		 */
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

	/**
	 * 
	 * @param type
	 * @return le bouton qui permet l'action pour creer la nouvelle ressource
	 */
	private JButton creerBouttonNouv(String type) {
		JButton bouton = new JButton("Creer");
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
			        ajouterRessourceAEntreprise(type);	 
	        }
	    });			
	    return bouton;
	}
	
	/**
	 * 
	 * @return le bouton qui permet l'action pour fermer la fenetre
	 */
	private JButton creerBouttonAnnuler() {
		JButton bouton = new JButton("Annuler");
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	dispose();
	        }
	    });			
	    return bouton;
	}
	
	/**
	 * verifie les informations saisie et ajoute la ressource au model (Entreprise)
	 */
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
