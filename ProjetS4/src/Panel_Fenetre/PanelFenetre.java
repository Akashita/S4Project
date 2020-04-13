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

import javax.swing.BoxLayout;
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
    protected JComboBox<String> comboBoxNiveau, comboBoxDomaine;		
    protected ArrayList<Competence> listeCompetenceChoisie;
    
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
    protected JButton boutonAjoutCompetence;
    
	public PanelFenetre(Entreprise entreprise, FenetreModal fm) {
		this.entreprise = entreprise;
		this.fm = fm;
	}
	
	protected void creerInterface() {}
	
	//-------------------------------------------->>>>> Competence
	protected void initialiseCompetence (PanelFenetre pf) {
		listeCompetenceChoisie = new ArrayList<Competence>();

		Domaine domaine = entreprise.getDomaine();
		String [] liste = new String [domaine.getListeDomaine().size()+1];
		liste[0] = "Compétence";
		for (int i=0; i<liste.length-1; i++) {
			liste[i+1] = domaine.getListeDomaine().get(i);
		}
		comboBoxDomaine = new JComboBox<String>(liste);

		comboBoxNiveau = new JComboBox<String>(niveau);	

		boutonAjoutCompetence = new JButton("Ajouter");
		boutonAjoutCompetence.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	ajoutCompetenceChoisie(pf);
	        }
	    });			
	}
		
	public void ajoutCompetenceChoisie(PanelFenetre pf) {
		if (comboBoxDomaine.getSelectedIndex()>0) {
			if (comboBoxNiveau.getSelectedIndex()>0) {
				boolean estPresent = false;
				Competence competence = new Competence((String) comboBoxDomaine.getSelectedItem(), comboBoxNiveau.getSelectedIndex());
				for (int i=0; i<listeCompetenceChoisie.size(); i++) {
					if (competence.getNom() == listeCompetenceChoisie.get(i).getNom()) {
						estPresent = true;
					}
				}
				if (!estPresent) {
					listeCompetenceChoisie.add(competence);
					pf.removeAll();
					pf.creerInterface();
					pf.revalidate();
					pf.repaint();		
				}	
				else {
			    	JOptionPane.showMessageDialog(null, "Vous l'avez déjà choisie", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Choissisez un niveau", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Choissisez une compétence", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	protected JPanel afficherListeCompetenceChoisie() {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(creerTexte("Compétences choisies: "));
		for (int i=0; i<listeCompetenceChoisie.size(); i++) {
			panel.add(creerTexte(listeCompetenceChoisie.get(i).getNom()));
		}
		return panel;
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

}