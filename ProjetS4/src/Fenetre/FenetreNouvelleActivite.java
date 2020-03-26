package Fenetre;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;

import javax.swing.*;

import Model.Entreprise;
import Model.Projet;

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
	private Projet projet;
	private JPanel panelPrincipal = new JPanel();	
	
	private JTextField titre, charge, ordre;
	private JComboBox<String> jour, mois, annee;

	
	public FenetreNouvelleActivite(Entreprise entreprise) {
		this.entreprise = entreprise;
		projet = entreprise.getProjetSelectionner();
		if (entreprise.getProjetSelectionner() != null) {
			this.setSize(700,300);
			this.setLocationRelativeTo(null);
			this.addWindowListener(new FermerFenetre(this));
			this.setVisible(true);
			creationInterface();			
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Aucun projet selectionné", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	/**
	 * creation de l'interface 
	 */
	private void creationInterface() {

		panelPrincipal.removeAll();
		panelPrincipal.setBackground(Color.WHITE);
		
		panelPrincipal.add(infoAEcrire());
		panelPrincipal.add(panelDebut());
		
		
		/*panelPrincipal.add(creerBouttonAnnuler());
		panelPrincipal.add(creerBouttonAjout());*/
		panelPrincipal.add(ajoutBouton());
		
		
		this.add(panelPrincipal);
		this.revalidate();
	}

	private JPanel infoAEcrire() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	    panel.setBorder(BorderFactory.createTitledBorder("Rentrez les informations de l'activité"));
		panel.add(creerJTextField("titre"));
		panel.add(creerJTextField("charge"));
		panel.add(creerJTextField("ordre"));

		return panel;
	}  
    
	private JPanel creerJTextField(String type) {
		/*
		 * retourne un panel avec le champs de saisie et un label
		 */
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(1, 1));
	    if (type == "titre") {
		    panel.add(new JLabel("Veillez indiquez le nom de l'activité : "));
	    	titre = new JTextField();
		    panel.add(titre);	    	
	    }
	    if (type == "charge") {
		    panel.add(new JLabel("Veillez indiquez la charge de travail en jour/homme : "));
	    	charge = new JTextField();
		    panel.add(charge);	    	
	    }
	    if (type == "ordre") {
		    panel.add(new JLabel("Veillez indiquez l'ordre (A étant le premier ordre): "));
	    	ordre = new JTextField();
		    panel.add(ordre);	    	
	    }
		return panel;
	}

	private JPanel panelDebut() {
		JPanel panel = new JPanel();
	    panel.setBorder(BorderFactory.createTitledBorder("Indiquez quand commencera l'activité"));

	    String[] jours = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"
	    		, "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
	    		, "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
	    String[] mois = {"Janvier", "Février", "Mars", "Avril",
	    		"Mai", "Juin", "Juillet", "Aout",
	    		"Septembre", "Octobre", "Novembre", "Décembre"};
	    String[] annees = {"2020", "2021", "2022"};
	    
	    jour = new JComboBox<String>(jours);
	    this.mois = new JComboBox<String>(mois);
	    annee = new JComboBox<String>(annees);

	    panel.add(jour);
	    panel.add(this.mois);
	    panel.add(annee);
	    
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
	        	creertActivite();	 
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
	private void creertActivite() {
		if (!titre.getText().isEmpty()) {
			if (!charge.getText().isEmpty()) {
				if (!ordre.getText().isEmpty()) {
					if (estUnEntier(charge.getText())) {
						LocalDate debut = creerLaDate();
						entreprise.creerActivite(projet, 
								titre.getText(), Integer.parseInt(charge.getText()), ordre.getText(), debut);
						dispose();
					}
					else {
				    	JOptionPane.showMessageDialog(null, "Veillez ecrire un nombre pour charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
					}
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire son ordre", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Veillez ecrire sa charge", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veillez ecrire son titre", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	private LocalDate creerLaDate() {
		int jour = Integer.parseInt((String) this.jour.getSelectedItem());
		int mois = this.mois.getSelectedIndex()+1;
		int annee = Integer.parseInt((String) this.annee.getSelectedItem());
		LocalDate debut = null;
		if (jourValide (jour, mois, annee)) {
			debut = LocalDate.of(annee, mois, jour);
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Date invalide", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
		
		return debut;
	}
	
	private boolean jourValide(int jour, int mois, int annee){
		try {
			LocalDate.of(annee, mois, jour);
		}catch(DateTimeException e) {
			return false;
		}
		return true;
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
