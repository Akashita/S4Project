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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
	private static String[] listeType = {"Personne", "Salle", "Calculateur"};
    protected int typeChoisi = Ressource.PERSONNE;

    protected JTextField textFieldNom = new JTextField(),
    		textFieldPrenom = new JTextField(),
    	    textFieldMdp = new JTextField(),
    	    textFieldPriorite = new JTextField(),
    	    textFieldCharge = new JTextField(),
    	    textFieldCapacite = new JTextField(),
    	    textFieldLogin = new JTextField();
    
    	

    protected JComboBox<String> comboBoxType = new JComboBox<String>(listeType);		
    protected JComboBox<Ressource> comboBoxRessource;		

    private String [] niveau = {"niveau", "Debutant", "Confirme", "Expert"};
    protected JComboBox<String> comboBoxNiveau, comboBoxDomaine;		
    protected ArrayList<Competence> listeCompetenceChoisie;
    protected ArrayList<String> listeDomaineChoisi;
    
	protected Entreprise entreprise;
    protected FenetreModal fm;
    protected Color couleurFond = PanelPrincipal.BLEU2;
    protected Projet projet;
    protected Activite activite;
    protected int actionChoisie;
    
    protected JComboBox<String> comboBoxActionTicket;	
    protected String [] actionTicket = {"Envoyer un message", "Liberer une ressource", "Transferer une ressource"};
    protected Checkbox checkBoxestAdmin = new Checkbox("administrateur", false);
    protected JButton boutonAjoutCompetence, boutonAjoutDomaine;
    
    protected ArrayList<String> listeDomaine;
    protected JList<String> jListDomaine;
    protected JScrollPane scrollPaneJListDomaine;
    protected JButton boutonSupprimerDomaine;
    
    protected JComboBox<Projet> comboBoxProjet;
    
    protected JButton boutonAfficherDateDebut, boutonAfficherDateFin, boutonAjoutDate;
    protected boolean afficherDateDebut = false, afficherDateFin = false;
    protected LocalDate dateDebut, dateFin;
    
    protected boolean alreadyPressed = false;
    
    protected JTextArea textArea;
    
    protected Calendrier calendrier1, calendrier2;
    
	public PanelFenetre(Entreprise entreprise, FenetreModal fm) {
		this.entreprise = entreprise;
		this.fm = fm;
		this.setBackground(couleurFond);
	}
	
	protected void creerInterface() {}
	
	
	//-------------------------------------------->>>>> gere la liste de domaine 
	protected void initialiseDomaine (PanelFenetre pf) {
		listeDomaine = entreprise.getListeDomaineEntreprise();
		boutonAjoutDomaine = new JButton("Ajouter");
		boutonAjoutDomaine.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	ajoutDomaine(pf);
	        }
	    });			
		/*boutonSupprimerDomaine = new JButton("Supprimer");
		boutonSupprimerDomaine.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	supprimerDomaine(pf);
	        }
	    });	*/		
	}

	protected Component afficheListeDomaine(FenetreModal fm, PanelFenetre pf) {
		listeDomaine = entreprise.getListeDomaineEntreprise();
		String [] domaine = new String [listeDomaine.size()];
		Collections.sort(listeDomaine); //trie dans l'odre alphabetique
		for (int i=0; i<listeDomaine.size(); i++) {
			domaine[i] = listeDomaine.get(i);
		}
		JList<String> jlt = new JList<String>(domaine);
		jlt.setBackground(couleurFond);
		jlt.setFont(new Font("Arial", Font.BOLD, 15));
		jlt.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent evt) {
		        if (evt.getButton() == MouseEvent.BUTTON3) { //clic droit
		        	jlt.setSelectedIndex(jlt.locationToIndex(evt.getPoint()));
		        	jlt.setComponentPopupMenu(popupMenuDomaine(pf, jlt.getSelectedValue()));
		        	;
		        }
		    }
		});

		scrollPaneJListDomaine = new JScrollPane(jlt);
		scrollPaneJListDomaine.setViewportView(jlt);
		scrollPaneJListDomaine.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		/*JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new BorderLayout());
		panel.add(scrollPaneJListDomaine, BorderLayout.CENTER);*/
		return scrollPaneJListDomaine;
	}

	public JPopupMenu popupMenuDomaine(PanelFenetre pf, String domaine) {
		JPopupMenu m = new JPopupMenu("Supprimer");  
		JMenuItem supp = new JMenuItem("Supprimer");
		supp.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {              
            	supprimerDomaine(pf, domaine);
            	}  
           });  

		m.add(supp);
		return m;
		//m.show(fm, x, y);
		//fm.add(m);
	}
	
	protected void ajoutDomaine (PanelFenetre pf) {
		if (!textFieldNom.getText().isEmpty()) {
			String domaine = textFieldNom.getText().toLowerCase();
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
		    	JOptionPane.showMessageDialog(null, "Ce domaine existe déjà ", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}

	}
	
	protected void supprimerDomaine(PanelFenetre pf, String domaine) {
		//String domaine = jListDomaine.getSelectedValue();
		if (!entreprise.PersonneOuActiviteACeDomaine(domaine)) {
			entreprise.supprimerDomaine(domaine);
			maj(pf);			
		}
		else {
	    	JOptionPane.showMessageDialog(null,"Des personnes ont ce domaine (liste de ces personnes pas encore implemente)", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}			
		/*if (jListDomaine.getModel().getSize() > 0) {
			int index = jListDomaine.getSelectedIndex();
			if (index != -1) {
				String domaine = listeDomaine.get(index);
				if (entreprise.PersonneOuActiviteACeDomaine(domaine)) {
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
		}*/
	}
	
	
	//-------------------------------------------->>>>> Competence pour Ressource
	protected void initialiseCompetence (PanelFenetre pf) {
		listeCompetenceChoisie = new ArrayList<Competence>();
		listeDomaine = entreprise.getListeDomaineEntreprise();

		String [] liste = new String [listeDomaine.size()+1];
		liste[0] = "Competence";
		for (int i=0; i<liste.length-1; i++) {
			liste[i+1] = listeDomaine.get(i);
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
					maj(pf);
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

		listeDomaine = entreprise.getListeDomaineEntreprise();
		String [] liste = new String [listeDomaine.size()+1];
		liste[0] = "Competence";
		for (int i=0; i<liste.length-1; i++) {
			liste[i+1] = listeDomaine.get(i);
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
	
	protected void initialiseCalendrier(LocalDate date, PanelFenetre pf) {
		calendrier1 = new Calendrier(this, pf, date);
	}

	protected JPanel panelCalendrier(Calendrier c) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(couleurFond);
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 3;
		gc.weighty = 0;
		
		gc.gridx = 0;
		panel.add(c.getComboBoxJour(), gc);			
		gc.gridx = 1;
		panel.add(c.getComboBoxMois(), gc);			
		gc.gridx = 2;
		panel.add(c.getComboBoxAnnee(), gc);			

		return panel;
	}

	//----------------------------------------------------->>>> Gesion ticket

	protected void initialiseTicket(PanelFenetre pf) {
		//afficherDateDebut =  afficherDateFin = false;
		comboBoxProjet = new JComboBox<Projet>();
		comboBoxActionTicket = new JComboBox<String>(actionTicket);
		comboBoxActionTicket.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				nouvelleAction(pf);
			}
		});	    
		calendrier1 = new Calendrier(this, pf, Temps.getAujourdhui());
		calendrier2 = new Calendrier(this, pf, Temps.getAujourdhui());
		//initialiseBoutonDate(pf);
		initialiseTextFieldLogin(pf);
		textArea = new JTextArea();
	}
	
	protected void actualiseTicket(PanelFenetre pf) {
		afficherDateDebut =  afficherDateFin = false;
		initialiseTextFieldLogin(pf);		
	}

	
	
	protected void initialiseTextFieldLogin(PanelFenetre pf) {
    	textFieldLogin = new JTextField("nom#id");
    	textFieldLogin.getFont().deriveFont(Font.ITALIC);
    	textFieldLogin.setForeground(PanelPrincipal.GRIS2);
    	textFieldLogin.addMouseListener(new MouseListener() {           
			@Override
			public void mouseClicked(MouseEvent e) {
    	        JTextField texteField = ((JTextField)e.getSource());
    	        texteField.setText("");
    	        texteField.getFont().deriveFont(Font.PLAIN);
    	        texteField.setForeground(PanelPrincipal.NOIR);
    	        texteField.removeMouseListener(this);
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
    	});
    	textFieldLogin.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {alreadyPressed = false;}
			@Override
			public void keyPressed(KeyEvent e) {
				if (!alreadyPressed) {
					alreadyPressed = true;
					if (textFieldCapacite.getText().indexOf(entreprise.SEPARATEUR)!=-1) {
						majComboBoxProjet(pf);
					}
				}
			}
		});
	}
	
	protected void majComboBoxProjet(PanelFenetre pf) {
		String login = textFieldCapacite.getText();
		int type = comboBoxType.getSelectedIndex();
		ArrayList<Projet> l = entreprise.getListeProjetdeRessourceParLogin(type, login);
		
		comboBoxProjet = new JComboBox<Projet>((Projet[]) l.toArray());			
		maj(pf);
	}
	
	protected void nouvelleAction(PanelFenetre pf) {
		actionChoisie = comboBoxActionTicket.getSelectedIndex();
		actualiseTicket(this);
		maj(pf);
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
		typeChoisi = comboBoxType.getSelectedIndex();
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
	
	public void maj (PanelFenetre pf) {
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

	
	protected String dateToString(LocalDate date) {
		return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();			
	}
}