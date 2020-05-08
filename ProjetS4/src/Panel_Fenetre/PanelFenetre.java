package Panel_Fenetre;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

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
    	    textFieldMdp = new JTextField(),
    	    textFieldPriorite = new JTextField(),
    	    textFieldCharge = new JTextField(),
    	    textFieldCapacite = new JTextField();
    
    	

    protected JComboBox<String> comboBoxType = new JComboBox<String>(listeType);		
    protected JComboBox<Ressource> comboBoxRessource;		

    String [] niveau = {"niveau", "Debutant", "Confirme", "Expert"};
    protected JComboBox<String> comboBoxNiveau, comboBoxDomaine;		
    protected ArrayList<Competence> listeCompetenceChoisie;
    protected ArrayList<String> listeDomaineChoisi;
    
	protected Entreprise entreprise;
    protected FenetreModal fm;
    protected Color couleurFond = PanelPrincipal.BLEU3;
    protected Projet projet;
    protected Activite activite;

    String [] jours;
    String[] mois = {"Janvier", "Fevrier", "Mars", "Avril",
    		"Mai", "Juin", "Juillet", "Aout",
    		"Septembre", "Octobre", "Novembre", "Decembre"};
    String[] annees = new String [Temps.nbAnnnee];
    protected JComboBox<String> comboBoxAnnee, comboBoxMois, comboBoxJour;	
    
    protected Checkbox checkBoxestAdmin = new Checkbox("administrateur", false);
    protected JButton boutonAjoutCompetence, boutonAjoutDomaine;
    
    protected ArrayList<String> listeDomaine;
    protected JList<String> jListDomaine;
    protected JScrollPane scrollPaneJListDomaine;
    protected JButton boutonSupprimerDomaine;
    
	public PanelFenetre(Entreprise entreprise, FenetreModal fm) {
		this.entreprise = entreprise;
		this.fm = fm;
	}
	
	protected void creerInterface() {}
	
	
	//-------------------------------------------->>>>> gere la liste de domaine 
	protected void initialiseDomaine (PanelFenetre pf) {
		listeDomaine = entreprise.getDomaine().getListeDomaine();
		boutonAjoutDomaine = new JButton("Ajouter");
		boutonAjoutDomaine.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	ajoutDomaine(pf);
	        }
	    });			
		boutonSupprimerDomaine = new JButton("Supprimer");
		boutonSupprimerDomaine.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	supprimerDomaine(pf);
	        }
	    });			
	}

	protected Component afficheListeDomaine() {
		listeDomaine = entreprise.getDomaine().getListeDomaine();
		String [] domaine = new String [listeDomaine.size()];
		Collections.sort(listeDomaine); //trie dans l'odre alphabetique
		for (int i=0; i<listeDomaine.size(); i++) {
			domaine[i] = listeDomaine.get(i);
		}
		jListDomaine = new JList<String>(domaine);
		jListDomaine.setBackground(couleurFond);
		scrollPaneJListDomaine = new JScrollPane(jListDomaine);
		scrollPaneJListDomaine.setViewportView(jListDomaine);
		scrollPaneJListDomaine.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(scrollPaneJListDomaine, BorderLayout.CENTER);
		return panel;
	}

	protected void ajoutDomaine (PanelFenetre pf) {
		if (!textFieldNom.getText().isEmpty()) {
			String domaine = textFieldNom.getText().toUpperCase();
			boolean estPresent = false;
			for (int i=0; i<listeDomaine.size(); i++) {
				if (domaine.equals(listeDomaine.get(i))) {
					estPresent = true;
				}
			}
			if (!estPresent) {
				entreprise.ajoutDomaine(domaine);
				textFieldNom = new JTextField();
				maj(pf);
				}
			else {
		    	JOptionPane.showMessageDialog(null, "Ce domaine existe deja ", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}

	}
	
	protected void supprimerDomaine(PanelFenetre pf) {
		if (jListDomaine.getModel().getSize() > 0) {
			int index = jListDomaine.getSelectedIndex();
			if (index != -1) {
				String domaine = listeDomaine.get(index);
				if (entreprise.aucuneRessourceACeDomaine(domaine)) {
					entreprise.supprimerDomaine(domaine);
					maj(pf);			
				}
				else {
			    	JOptionPane.showMessageDialog(null,"Des personnes ont ce domaine (liste de ces personnes pas encore implemente)", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null,"Il y a aucun domaine dans l'entreprise", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	
	//-------------------------------------------->>>>> Competence pour Ressource
	protected void initialiseCompetence (PanelFenetre pf) {
		listeCompetenceChoisie = new ArrayList<Competence>();

		Domaine domaine = entreprise.getDomaine();
		String [] liste = new String [domaine.getListeDomaine().size()+1];
		liste[0] = "Competence";
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
					if (competence.getNom().equals(listeCompetenceChoisie.get(i).getNom())) {
						estPresent = true;
					}
				}
				if (!estPresent) {
					listeCompetenceChoisie.add(competence);
				}	
				else {
			    	JOptionPane.showMessageDialog(null, "Vous l'avez deja  choisie", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Choissisez un niveau", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Choissisez une competence", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	protected JPanel afficherListeCompetenceChoisie() {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(creerTexte("Competences choisies: "));
		for (int i=0; i<listeCompetenceChoisie.size(); i++) {
			panel.add(creerTexte(listeCompetenceChoisie.get(i).getNom()));
		}
		return panel;
	}
	
	
	//------------------------------------------------------->>>>>> Domaine pour activite
	

	protected void initialiseDomaineActivite (PanelFenetre pf) {
		listeDomaineChoisi = new ArrayList<String>();

		Domaine domaine = entreprise.getDomaine();
		String [] liste = new String [domaine.getListeDomaine().size()+1];
		liste[0] = "Competence";
		for (int i=0; i<liste.length-1; i++) {
			liste[i+1] = domaine.getListeDomaine().get(i);
		}
		comboBoxDomaine = new JComboBox<String>(liste);


		boutonAjoutCompetence = new JButton("Ajouter");
		boutonAjoutCompetence.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	ajoutDomaineChoisi(pf);
	        }
	    });			
	}
		
	public void ajoutDomaineChoisi(PanelFenetre pf) {
		if (comboBoxDomaine.getSelectedIndex()>0) {
			boolean estPresent = false;
			String domaine = (String) comboBoxDomaine.getSelectedItem();
			for (int i=0; i<listeDomaineChoisi.size(); i++) {
				if (domaine.equals(listeDomaineChoisi.get(i))) {
					estPresent = true;
				}
			}
			if (!estPresent) {
				listeDomaineChoisi.add(domaine);
				maj(pf);
			}	
			else {
			    JOptionPane.showMessageDialog(null, "Vous l'avez deja  choisie", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Choissisez une competence", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	protected JPanel afficherListeDomaineChoisi() {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(creerTexte("Competences choisies: "));
		for (int i=0; i<listeDomaineChoisi.size(); i++) {
			panel.add(creerTexte(listeDomaineChoisi.get(i)));
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
		maj(pf);
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
		maj(pf);
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
	
//----------------------------------------------------------------------------->>>>> Bouton	
	
	protected JButton creerBoutonAnnuler() {
		JButton bouton = new JButton("Annuler");
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	fm.dispose();
	        }
	    });			
	    return bouton;
	}

	protected JButton creerBoutonFin(PanelFenetre pf, String titre) {
		JButton bouton = new JButton(titre);
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	pf.actionFin();
	        }
	    });			
	    return bouton;
	}

	protected void actionFin() {}

	public void creerRessource() {}

	
	protected JButton creerBoutonSupprimer(PanelFenetre pf) {
		JButton bouton = new JButton("Supprimer");
	    bouton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	pf.supprimer();
	        }
	    });			
	    return bouton;
	}
	
	protected void supprimer() {}

//-----------------------------------------------------------------------
	
	private void maj (PanelFenetre pf) {
		pf.removeAll();
		pf.creerInterface();
		pf.revalidate();
		pf.repaint();		
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