package Panel_Fenetre;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Fenetre.FenetreModal;
import Model.Entreprise;
import Model.Temps;
import Panel.PanelPrincipal;
import Ressource.Ressource;

public class PanelFenetre extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String[] listeType = {Ressource.PERSONNE, Ressource.SALLE, Ressource.CALCULATEUR};
    protected String typeChoisi = listeType[0];

    protected JTextField textFieldNom = new JTextField(),
    		textFieldPrenom = new JTextField(),
    	    textFieldPriorite = new JTextField(),
    	    textFieldCharge = new JTextField(),
    	    textFieldCapacite = new JTextField();

    protected JComboBox<String> comboBoxType = new JComboBox<String>(listeType);		
    protected JComboBox<Ressource> comboBoxRessource;		

	protected Entreprise entreprise;
    protected FenetreModal fm;
    protected Color couleurFond = PanelPrincipal.BLEU3;

    String [] jours;
    String[] mois = {"Janvier", "Février", "Mars", "Avril",
    		"Mai", "Juin", "Juillet", "Aout",
    		"Septembre", "Octobre", "Novembre", "Décembre"};
    String[] annees = new String [20];
    protected JComboBox<String> comboBoxAnnee, comboBoxMois, comboBoxJour;		

    
	public PanelFenetre(Entreprise entreprise, FenetreModal fm) {
		this.entreprise = entreprise;
		this.fm = fm;
	}
	
	protected void creerInterface() {}
	
	protected void initialiseComboBoxAnnee(PanelFenetre pf) {
		int anneeActuel = Temps.getAnnee();
		 for (int i=0; i<annees.length; i++) {
			 annees[i] = Integer.toString(anneeActuel+i);
		 }
		 comboBoxAnnee = new JComboBox<String>(annees);
		 comboBoxAnnee.setSelectedIndex(0);
		 comboBoxAnnee.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					metAJourCalendrier(pf);
				}
			});	    	
	}
	
	protected void initialiseComboBoxMois(PanelFenetre pf) {
		comboBoxMois = new JComboBox<String>(mois);
		comboBoxMois.setSelectedIndex(Temps.getIndexMois()-1);
		comboBoxMois.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				metAJourCalendrier(pf);
			}
		});	    	
	}

	
	
	protected void adapteComboBoxJour() {
		int annee = Integer.parseInt((String) comboBoxAnnee.getSelectedItem());
		int mois = comboBoxMois.getSelectedIndex()+1;
		int nbJour = Temps.getJourMois(annee, mois);
	    jours = new String [nbJour];
		for (int i=0; i<nbJour; i++) {
			jours[i] = Integer.toString(i+1);
		}
		comboBoxJour = new JComboBox<String>(jours);
		comboBoxJour.setSelectedIndex(Temps.getIndexJour()-1);
	}
	
	public void metAJourCalendrier(PanelFenetre pf) {
		adapteComboBoxJour();
		pf.removeAll();
		pf.creerInterface();
		pf.revalidate();
		pf.repaint();		
	}

	
	protected void initialiseComboBoxType(PanelFenetre pf) {
		comboBoxType.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				nouveauChoix(pf);
			}
		});	    	
	}
	
	protected void nouveauChoix(PanelFenetre pf) {
		typeChoisi = (String) comboBoxType.getSelectedItem();
		pf.removeAll();
		creerInterface();
		pf.revalidate();
		pf.repaint();
	}

	protected JPanel calendrier() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(couleurFond);
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 3;
		gc.weighty = 0;
		
		gc.gridx = 0;
		panel.add(comboBoxJour, gc);			
		gc.gridx = 1;
		panel.add(comboBoxMois, gc);			
		gc.gridx = 2;
		panel.add(comboBoxAnnee, gc);			

		return panel;
	}

	protected void initialiseComboBoxRessource(ArrayList<Ressource> listeRessource) {
		Ressource [] tabRes = new Ressource[listeRessource.size()];
		for (int i=0; i<tabRes.length; i++) {
			tabRes[i] = listeRessource.get(i);
		}	
		comboBoxRessource = new JComboBox<Ressource>(tabRes);
	}



	protected JLabel creerTitre(String titre) {
		JLabel label = new JLabel(titre);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		return label;
	}
	
	protected JLabel creerTexte(String titre) {
		JLabel label = new JLabel(titre);
		label.setFont(new Font("Arial", Font.PLAIN, 15));
		label.setBackground(Color.BLUE);
		return label;
	}
	
	public JButton creerBoutonAnnuler() {
		JButton bouton = new JButton("Annuler");
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	fm.dispose();
	        }
	    });			
	    return bouton;
	}

	public JButton creerBoutonFin(PanelFenetre pf, String titre) {
		JButton bouton = new JButton(titre);
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	pf.actionFin();
	        }
	    });			
	    return bouton;
	}

	protected void actionFin() {}

	public void creerRessource() {
	}

	
	protected boolean estUnEntier(String chaine) {
		try {
			Integer.parseInt(chaine);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}

	protected LocalDate creerLaDate() {
		int jour = Integer.parseInt((String) comboBoxJour.getSelectedItem());
		int mois = comboBoxMois.getSelectedIndex()+1;
		int annee = Integer.parseInt((String) comboBoxAnnee.getSelectedItem());
		LocalDate debut = null;
		if (Temps.jourValide (jour, mois, annee)) {
			debut = LocalDate.of(annee, mois, jour);
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Date invalide", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
		
		return debut;
	}

}
