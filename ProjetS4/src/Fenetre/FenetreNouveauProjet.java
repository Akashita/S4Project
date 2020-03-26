package Fenetre;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.Entreprise;
import Panel.PanelProjet;

/**
 * Cette fenetre permet la creation d'un projet
 * 
 * @author damien planchamp
 *
 */
public class FenetreNouveauProjet extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private JPanel panelPrincipal = new JPanel();
	private JTextField nom, priorite;

	
	public FenetreNouveauProjet(Entreprise entreprise, PanelProjet pp) {		
		this.entreprise = entreprise;
		this.setSize(480,150);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new FermerFenetre(this));
		this.setVisible(true);
		creationInterface();			
	}
	
	/**
	 * creation de l'interface 
	 */
	private void creationInterface() {

		panelPrincipal.removeAll();
		panelPrincipal.setBackground(Color.WHITE);
		
		panelPrincipal.add(infoAEcrire());
		panelPrincipal.add(ajoutBouton());
		
		
		this.add(panelPrincipal);
		this.revalidate();
	}

	private JPanel infoAEcrire() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	    panel.setBorder(BorderFactory.createTitledBorder("Rentrez les informations de l'activité"));
		panel.add(creerJTextField("nom"));
		panel.add(creerJTextField("priorite"));

		return panel;
	}  
    
	private JPanel creerJTextField(String type) {
		/*
		 * retourne un panel avec le champs de saisie et un label
		 */
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(1, 1));
	    if (type == "nom") {
		    panel.add(new JLabel("Veillez indiquer le nom du projet : "));
	    	nom = new JTextField();
		    panel.add(nom);	    	
	    }
	    if (type == "priorite") {
		    panel.add(new JLabel("Veillez indiquez la priorité du projet: "));
		    priorite = new JTextField();
		    panel.add(priorite);	    	
	    }
		return panel;
	}
	
	
	private JPanel ajoutBouton() {
		JPanel panel = new JPanel();
		panel.setSize(this.getWidth(),10);
		panel.add(creerBouttonAnnuler());
		panel.add(creerBouttonAjout());
		return panel;
	}
	
	/**
	 * 
	 * @param type
	 * @return le bouton qui permet l'action pour creer la nouvelle activité
	 */
	private JButton creerBouttonAjout() {
		JButton bouton = new JButton("Creer");
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	creerProjet();	 
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
	 * verifie les informations saisie et ajoute le projet au model (Entreprise)
	 */
	private void creerProjet() {
		if (!nom.getText().isEmpty()) {
			if (!priorite.getText().isEmpty()) {
				if (estUnEntier(priorite.getText())) {
					entreprise.creerProjet(nom.getText(), Integer.parseInt(priorite.getText()));
					dispose();
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire un nombre pour charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veillez ecrire sa priorité", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
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
	}}
