package Panel_Fenetre;

import java.awt.Checkbox;
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
import Model.Activite;
import Model.Entreprise;
import Model.Projet;
import Model.Temps;
import Panel.PanelPrincipal;
import Ressource.Competence;
import Ressource.Domaine;
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

    String [] niveau = {"niveau", "Debutant", "Confirmé", "Expert"};
    protected JComboBox<String> comboBoxNiveau1, comboBoxNiveau2, comboBoxNiveau3;		
    protected JComboBox<String> comboBoxDomaine1, comboBoxDomaine2, comboBoxDomaine3;		

	protected Entreprise entreprise;
    protected FenetreModal fm;
    protected Color couleurFond = PanelPrincipal.BLEU3;
    protected Projet projet;
    protected Activite activite;

    String [] jours;
    String[] mois = {"Janvier", "Février", "Mars", "Avril",
    		"Mai", "Juin", "Juillet", "Aout",
    		"Septembre", "Octobre", "Novembre", "Décembre"};
    String[] annees = new String [Temps.nbAnnnee];
    protected JComboBox<String> comboBoxAnnee, comboBoxMois, comboBoxJour;	
    
    protected Checkbox checkBoxestAdmin = new Checkbox("administrateur", false);

    
	public PanelFenetre(Entreprise entreprise, FenetreModal fm) {
		this.entreprise = entreprise;
		this.fm = fm;
	}
	
	protected void creerInterface() {}
	
	protected void initialiseNiveau() {
		comboBoxNiveau1 = new JComboBox<String>(niveau);	
		comboBoxNiveau2 = new JComboBox<String>(niveau);	
		comboBoxNiveau3 = new JComboBox<String>(niveau);	
	}
	
	protected void initialiseDomaine() {
		Domaine domaine = entreprise.getDomaine();
		String [] liste = new String [domaine.getListeDomaine().size()+1];
		liste[0] = "Choissiez une compétence";
		for (int i=0; i<liste.length-1; i++) {
			liste[i+1] = domaine.getListeDomaine().get(i);
		}
		comboBoxDomaine1 = new JComboBox<String>(liste);
		comboBoxDomaine2 = new JComboBox<String>(liste);
		comboBoxDomaine3 = new JComboBox<String>(liste);
	}
	
	//----------------------------------------------------->>>> Gesion jour/mois/annee
	
	protected void initialseJMA(LocalDate date, PanelFenetre pf) {
		int jour = date.getDayOfMonth()-1;
		int mois = date.getMonthValue()-1;
		int annee = date.getYear()-Temps.getAnnee();
		
		initialiseComboBoxAnnee(pf);
		initialiseComboBoxMois(pf);
		initialiseComboBoxJour();
		
		comboBoxJour.setSelectedIndex(jour);
		comboBoxMois.setSelectedIndex(mois);
		comboBoxAnnee.setSelectedIndex(annee);
	}

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
	
	protected void initialiseComboBoxJour() {
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

	protected void adapteComboBoxJour() {
		int index = comboBoxJour.getSelectedIndex();
		int annee = Integer.parseInt((String) comboBoxAnnee.getSelectedItem());
		int mois = comboBoxMois.getSelectedIndex()+1;
		int nbJour = Temps.getJourMois(annee, mois);
	    jours = new String [nbJour];
		for (int i=0; i<nbJour; i++) {
			jours[i] = Integer.toString(i+1);
		}
		comboBoxJour = new JComboBox<String>(jours);
		if (index >= jours.length) {
			index = jours.length-1;
		}
		comboBoxJour.setSelectedIndex(index);
	}

	public void metAJourCalendrier(PanelFenetre pf) {
		adapteComboBoxJour();
		pf.removeAll();
		pf.creerInterface();
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


	//-----------------------------------------
	
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

	
	
	
	
	protected void initialiseComboBoxRessource(ArrayList<Ressource> listeRessource) {
		Ressource [] tabRes = new Ressource[listeRessource.size()];
		for (int i=0; i<tabRes.length; i++) {
			tabRes[i] = listeRessource.get(i);
		}	
		comboBoxRessource = new JComboBox<Ressource>(tabRes);
	}

	protected void adapteComboBoxRessource(Ressource ressource) {
		comboBoxRessource.setSelectedItem(ressource);
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

	protected ArrayList<Competence> convertToCompetence(){
		ArrayList<Competence> liste = new ArrayList<Competence>();
		for (int i=1; i<4; i++) {
			Competence competence = creerCompetence(i);
			if (competence != null) {
				liste.add(competence);
			}
		}
		return liste;
	}
	private Competence creerCompetence(int choix) {
		Competence competence = null;
		switch (choix) {
		case 1:
			if (comboBoxDomaine1.getSelectedIndex()>0 && comboBoxNiveau1.getSelectedIndex()>0) {
				competence = new Competence((String) comboBoxDomaine1.getSelectedItem(), comboBoxNiveau1.getSelectedIndex());
			}		
			break;
		case 2:
			if (comboBoxDomaine2.getSelectedIndex()>0 && comboBoxNiveau2.getSelectedIndex()>0) {
				competence = new Competence((String) comboBoxDomaine2.getSelectedItem(), comboBoxNiveau2.getSelectedIndex());
			}		
			break;
		case 3:
			if (comboBoxDomaine3.getSelectedIndex()>0 && comboBoxNiveau3.getSelectedIndex()>0) {
				competence = new Competence((String) comboBoxDomaine3.getSelectedItem(), comboBoxNiveau3.getSelectedIndex());
			}		
			break;

		default:
			break;
		}
		return competence;
	}
}
