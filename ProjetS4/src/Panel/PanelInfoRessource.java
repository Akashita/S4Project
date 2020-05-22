package Panel;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Fenetre.FenetreInfoRessource;
import Fenetre.FenetreModal;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;
import Panel_Fenetre.PanelFenetre;
import Ressource.Calculateur;
import Ressource.Competence;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;



/**
 * Affiche les information de la ressource en fonction de l'user pour la fenetre affiche info ressource
 * (seul les admins peuvent voir les mdp)
 * @author Damien
 *
 */
public class PanelInfoRessource extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FenetreInfoRessource fir;
	private Entreprise entreprise;
	private Ressource ressource;
	private Color couleurFond;
	private boolean modeModification;
	private JButton boutonModifier, boutonTerminer, boutonSupprimer, boutonAnnuler, boutonAjoutCompetence;
	private Checkbox checkBoxestAdmin;
	private String [] niveau = {"niveau", "Debutant", "Confirme", "Expert"};
	private JComboBox<String> comboBoxNiveau, comboBoxDomaine;		
    private Insets insets = new Insets(2, 5, 2, 0);
	private ArrayList<Competence> competences;

	private JTextField textFieldNom = new JTextField(),
    		textFieldPrenom = new JTextField(),
    	    textFieldMdp = new JTextField(),
    	    textFieldCapacite = new JTextField();

	
	public PanelInfoRessource (FenetreInfoRessource fir, Entreprise entreprise, Ressource ressource) {
		this.fir = fir;
		this.entreprise = entreprise;
		this.ressource = ressource;
		couleurFond = PanelPrincipal.BLEU2;
		this.setBackground(couleurFond);
		modeModification = false;
		initialiseBouton();
		creerInterface();
	}
	
	private void initialiseBouton() {
		boutonModifier = new JButton("Modifier");
		boutonModifier.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	actionBoutonModifier();
	        }
	    });			
		
		boutonTerminer = new JButton("Terminer");
		boutonTerminer.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	actionBoutonTerminer();
	        }
	    });			
		
		boutonAnnuler = new JButton("Annuler");
		boutonAnnuler.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	actionBoutonAnnuler();
	        }
	    });	
		
		boutonSupprimer = new JButton("Supprimer");
		boutonSupprimer.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	actionBoutonSupprimer();
	        }
	    });			
		
	}
	
	public void creerInterface() {
		this.setBackground(couleurFond);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = insets;

		if (ressource.getType() == Ressource.PERSONNE) {
			competences = ((Personne) ressource).getListeDeCompetence();
		}
		if (modeModification) {
			infoModifier(gc);
		}
		else {
			info(gc);
		}
		
		if (ressource.getType() == Ressource.PERSONNE) {
			colChefDeProjet(gc);
			if (modeModification) {
				colCompetenceModifie(gc);
			}
			else {
				colCompetence(gc);
			}
		}
		colActivite(gc);
	}
	
	private void info(GridBagConstraints gc) {
		Personne user = entreprise.getUser();
		
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		String nom;
		String prenom = null;
		String role = "Collaborateur";
		String capacite = "0";
		String login = null;
		String mdp = null;
		if (ressource.getType() == Ressource.PERSONNE) {
			Personne p = ((Personne) ressource);
			if (p.estAdmin()) {
				role = "Administrateur";
			}
			prenom = ((Personne) ressource).getPrenom();
			
			login = p.getLogin();
			mdp = p.getMdp();
			
		}
		
		nom = ressource.getNom();
		
		if (ressource.getType() == Ressource.SALLE) {
			capacite = Integer.toString(((Salle) ressource).getCapacite());
		}

		this.add(labelInfo("Nom : " + nom), gc);
		
		if (ressource.getType() == Ressource.PERSONNE) {
			gc.gridy ++;
			this.add(labelInfo("Prenom : " + prenom), gc);

			gc.gridy ++;
			this.add(labelInfo("Role : " + role), gc);		
			
			gc.gridy ++;
			this.add(labelInfo("Login : " + login), gc);

			if (ressource.getType() == Ressource.PERSONNE) {
				Personne p = ((Personne) ressource);
				if (user.estAdmin() || user.getId() == p.getId()) { //seul un admin peut voir les mdp et les users peuvent voir leur mdp
					gc.gridy ++;
					this.add(labelInfo("Mot de passe : " + mdp), gc);								
				}
			}
		}
		
		if (ressource.getType() == Ressource.SALLE) {
			gc.gridy ++;
			this.add(labelInfo("Capacité : " + capacite), gc);			
		}
		
		gc.gridy ++;
		this.add(labelInfo("Type : " + getStringByIntOfType(ressource.getType())), gc);
		
		if (user.estAdmin()) {
			gc.gridy ++;
			this.add(panelBouton(), gc);					
		}
	}
	
	private void infoModifier(GridBagConstraints gc) {
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.ipady = gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		String nom;
		String prenom = null;
		String capacite = "0";
		String login = null;
		String mdp = null;
		if (ressource.getType() == Ressource.PERSONNE) {
			Personne p = ((Personne) ressource);
			prenom = ((Personne) ressource).getPrenom();
			login = p.getLogin();
			mdp = p.getMdp();
			textFieldPrenom.setText(prenom);
			textFieldMdp.setText(mdp);

		}
		
		nom = ressource.getNom();
		textFieldNom.setText(nom);
		if (ressource.getType() == Ressource.SALLE) {
			capacite = Integer.toString(((Salle) ressource).getCapacite());
			textFieldCapacite.setText(capacite);

		}

		
		if (ressource.getType() == Ressource.PERSONNE) {
			this.add(panelJtextfield("Prenom : ",textFieldPrenom), gc);
			gc.gridy ++;
			this.add(panelJtextfield("Nom : ",textFieldNom), gc);

			gc.gridy ++;
			checkBoxestAdmin = new Checkbox("administrateur", ((Personne) ressource).estAdmin());
			this.add(checkBoxestAdmin, gc);		
			
			gc.gridy ++;
			this.add(labelInfo("Login : " + login), gc);

			gc.gridy ++;
			this.add(panelJtextfield("Mot de passe : ",textFieldMdp), gc);
		}
		
		if (ressource.getType() == Ressource.SALLE || ressource.getType() == Ressource.CALCULATEUR) {
			this.add(panelJtextfield("Nom : ",textFieldNom), gc);
			if (ressource.getType() == Ressource.SALLE) {
				gc.gridy ++;
				this.add(panelJtextfield("Capacite : ",textFieldCapacite), gc);			
			}			
		}


		gc.gridy ++;
		this.add(labelInfo("Type : " + getStringByIntOfType(ressource.getType())), gc);
		
		gc.gridy ++;
		this.add(labelInfo("Type : " + getStringByIntOfType(ressource.getType())), gc);
		
		gc.gridy ++;
		this.add(panelBouton(), gc);		
	}
	
	private void colChefDeProjet(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.CENTER;
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.gridx ++;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		this.add(labelTitreColonne("Chef des projets"), gc);

		ArrayList<Projet> projets = entreprise.getListeProjetparIdChef(ressource.getId());
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy++;
		gc.gridheight = GridBagConstraints.REMAINDER;
		this.add(creerScrollPane(listProjet(projets)), gc);

	}
	
	private void colCompetence(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.gridx ++;
		gc.gridy = 0;
		
		this.add(labelTitreColonne("COMPETENCES"),gc);

		gc.fill = GridBagConstraints.BOTH;
		gc.gridy++;
		gc.gridheight = GridBagConstraints.REMAINDER;
		this.add(creerScrollPane(listCompetence(competences)), gc);
	}

	private void colCompetenceModifie(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.gridx ++;
		gc.gridy = 0;
		
		this.add(labelTitreColonne("COMPETENCES"),gc);

		gc.fill = GridBagConstraints.BOTH;
		gc.gridy++;
		gc.gridheight = GridBagConstraints.RELATIVE;
		this.add(afficheListeDomaine(), gc);

		gc.ipady = gc.anchor = GridBagConstraints.SOUTH;
		gc.gridy = 20;
		gc.gridheight = 1;
		this.add(actionModificationCompetence(),gc);
	}

	private JPanel actionModificationCompetence() {
		JPanel p = new JPanel();
		p.setBackground(couleurFond);
		p.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		ArrayList<String> listeDomaine = entreprise.getListeDomaineEntreprise();

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
	        	ajoutCompetenceChoisie();
	        }
	    });			
		
		p.add(comboBoxDomaine, gc);
		gc.gridx ++;
		p.add(comboBoxNiveau, gc);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridx = 0;
		gc.gridy ++;
		p.add(boutonAjoutCompetence, gc);
		return p;
	}
	
	private void ajoutCompetenceChoisie() {
		if (comboBoxDomaine.getSelectedIndex()>0) {
			if (comboBoxNiveau.getSelectedIndex()>0) {
				boolean estPresent = false;
				Competence competence = new Competence((String) comboBoxDomaine.getSelectedItem(), comboBoxNiveau.getSelectedIndex());
				ArrayList<Competence> lc = ((Personne) ressource).getListeDeCompetence();
				for (int i=0; i<lc.size(); i++) {
					if (competence.getNom().equals(lc.get(i).getNom())) {
						estPresent = true;
					}
				}
				if (!estPresent) {
					
					lc.add(competence);
					entreprise.modifPersonne((Personne) ressource, ressource.getNom(), ((Personne) ressource).getPrenom(),
							((Personne) ressource).getRole(), ((Personne) ressource).getMdp(), lc);
					maj();
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
	
	
	private void colActivite(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.gridx ++;
		gc.gridy = 0;
		
		this.add(labelTitreColonne("ACTIVITÉS"),gc);

		ArrayList<Activite> listeAct = entreprise.getListeActivitetdeRessourceParId(ressource.getType(), ressource.getId());
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy++;
		gc.gridheight = GridBagConstraints.REMAINDER;
		ArrayList<String> lAetP = new ArrayList<String>();
		for (int i=0; i<listeAct.size(); i++) {
			Activite a = listeAct.get(i);
			String projet = entreprise.getProjetDeActiviteParId(a.getId()).getNom();
			lAetP.add(a.getTitre()+" - "+projet);
			
		}
		this.add(creerScrollPane(listString(lAetP)), gc);
	}
	
	private JPanel labelInfo(String nom) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
	    String[] regex = nom.split(":", 2); 

		JLabel labelTitre = new JLabel(regex[0]+":");
		labelTitre.setFont(new Font("Arial", Font.BOLD, 15));
		labelTitre.setHorizontalAlignment(JLabel.CENTER);
		panel.add(labelTitre);
			
		JLabel labelResultat = new JLabel(regex[1]);
		labelResultat.setFont(new Font("Arial", Font.PLAIN, 15));
		labelResultat.setForeground(PanelPrincipal.GRIS2);
		labelResultat.getFont().deriveFont(Font.ITALIC);
		labelResultat.setHorizontalAlignment(JLabel.CENTER);
		panel.add(labelResultat);

		return panel;
	}
	
	private JLabel labelTitreColonne(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		return label;
	}
			
	private JLabel labelInfoColonne(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.BOLD, 14));
		return label;
	}

	

	private JPanel panelJtextfield(String nom, JTextField tf) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		gc.weightx = 3;
		gc.weighty = 1;

		gc.ipadx = gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.CENTER;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		panel.add(labelInfoColonne(nom), gc);
	
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 2;
		gc.gridx = 1;
		panel.add(tf, gc);
		return panel;
	}
	
	private JPanel panelBouton() {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.CENTER;


		gc.weightx = 2;
		gc.weighty = 1;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;

		if (modeModification) {
			panel.add(boutonAnnuler, gc);
			gc.gridx ++;
			panel.add(boutonTerminer, gc);
		}
		else {
			panel.add(boutonSupprimer, gc);
			gc.gridx ++;
			panel.add(boutonModifier, gc);

		}			
		return panel;
	}
	
	
	@SuppressWarnings("unchecked")
	private JList<Projet> listProjet(ArrayList<Projet> l){
		Projet [] tr = new Projet [l.size()];
		for (int i=0; i<tr.length; i++) {
			tr[i] = l.get(i);
		}
		JList<Projet> jlt = new JList<Projet>(tr);
		return liste(jlt);
	}

	@SuppressWarnings("unchecked")
	private JList<Competence> listCompetence(ArrayList<Competence> l){
		Competence [] tr = new Competence [l.size()];
		for (int i=0; i<tr.length; i++) {
			tr[i] = l.get(i);
		}
		JList<Competence> jlt = new JList<Competence>(tr);
		return liste(jlt);
	}

	@SuppressWarnings("unchecked")
	private JList<Activite> listActivite(ArrayList<Activite> l){
		Activite [] tr = new Activite [l.size()];
		for (int i=0; i<tr.length; i++) {
			tr[i] = l.get(i);
		}
		JList<Activite> jlt = new JList<Activite>(tr);
		return liste(jlt);
	}
	
	@SuppressWarnings("unchecked")
	private JList<String> listString(ArrayList<String> l){
		String [] tr = new String [l.size()];
		for (int i=0; i<tr.length; i++) {
			tr[i] = l.get(i);
		}
		JList<String> jlt = new JList<String>(tr);
		return liste(jlt);
	}

	@SuppressWarnings({ "rawtypes" })
	private JList liste(JList liste) {
		JList jlt = liste;
		jlt.setBackground(couleurFond);		
		jlt.setFont(new Font("Arial", Font.BOLD, 15));
		//jlt.setForeground(PanelPrincipal.BLANC);		
		return jlt;
	}
	
	private JScrollPane creerScrollPane(JList l) {
		JScrollPane scrollPane = new JScrollPane(l);
		scrollPane.setViewportView(l);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPane;
	}

	
	private Component afficheListeDomaine() {
		Competence [] tc = new Competence [competences.size()];
		for (int i=0; i<competences.size(); i++) {
			tc[i] = competences.get(i);
		}
		JList<Competence> jlt = new JList<Competence>(tc);
		jlt.setBackground(couleurFond);
		jlt.setFont(new Font("Arial", Font.BOLD, 15));
		jlt.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent evt) {
		        if (evt.getButton() == MouseEvent.BUTTON3) { //clic droit
		        	jlt.setSelectedIndex(jlt.locationToIndex(evt.getPoint()));
		        	jlt.setComponentPopupMenu(popupMenuDomaine(jlt.getSelectedValue()));
		        	;
		        }
		    }
		});

		JScrollPane scrollPaneJListDomaine = new JScrollPane(jlt);
		scrollPaneJListDomaine.setViewportView(jlt);
		scrollPaneJListDomaine.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPaneJListDomaine;
	}

	public JPopupMenu popupMenuDomaine(Competence c) {
		JPopupMenu m = new JPopupMenu("Supprimer");  
		JMenuItem supp = new JMenuItem("Supprimer");
		supp.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {              
            	supprimerCompetence(c);
            	}  
           });  

		m.add(supp);
		return m;
	}

	protected void supprimerCompetence(Competence c) {
		competences.remove(c);
		entreprise.supprimeCompetence(ressource.getId(),c.getNom());
		maj();			
	}

	
	
	
	
	
	//------------------------------------------------------------------------------->>>>> Action des boutons
	private void actionBoutonModifier() {
		modeModification = true;
		maj();
	}

	private void actionBoutonTerminer() {
		modeModification = false;
		
		if (!textFieldNom.getText().isEmpty()) {
			String nom = textFieldNom.getText();
			if (ressource.getType() == Ressource.PERSONNE) {
				if (!textFieldPrenom.getText().isEmpty()) {
					String role = "";
					if (checkBoxestAdmin.getState()) {
						role = Personne.ADMINISTRATEUR;
					}
					else {
						role = Personne.COLLABORATEUR;
					}
					String prenom = textFieldPrenom.getText();
					String mdp =  textFieldMdp.getText();
					ArrayList<Competence> listeComp = ((Personne) ressource).getListeDeCompetence();
					entreprise.modifPersonne((Personne) ressource, nom, prenom, role, mdp, listeComp);
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire son prenom", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			if (ressource.getType() == Ressource.SALLE) {
				if (estUnEntier(textFieldCapacite.getText())) {
					int capacite = Integer.parseInt(textFieldCapacite.getText());
					entreprise.modifSalle((Salle) ressource, nom, capacite);
				}
				else {
			    	JOptionPane.showMessageDialog(null, "Veillez ecrire un nombre", "Erreur", JOptionPane.ERROR_MESSAGE);			
				}
			}
			if (ressource.getType() == Ressource.CALCULATEUR) {
				entreprise.modifCalculateur((Calculateur) ressource, nom);
			}	
		}
		else {
	    	JOptionPane.showMessageDialog(null, "Veillez ecrire son nom", "Erreur", JOptionPane.ERROR_MESSAGE);			
		}

		
		maj();
	}

	private void actionBoutonAnnuler() {
		modeModification = false;
		maj();
	}

	private void actionBoutonSupprimer() {
		modeModification = false;
		String texte = "<html> êtes-vous sur de vouloir supprimer cette ressource ? <br> La suppression de cette ressource supprimera tout son contenu. </html>";
		int res = JOptionPane.showConfirmDialog(null, texte, "Attention", JOptionPane.YES_NO_OPTION);			
		if (res == 0) { //0 = yes
			if (entreprise.ressourceTravailleDansUnProjetParId(ressource.getType(), ressource.getId())) {
				entreprise.suppRessource(ressource.getType(), ressource);
				fir.dispose();				
			}
			else {
		    	JOptionPane.showMessageDialog(null, "Cette ressource est attaché aux act/projets suivant (pas encore implémenté)", "Erreur", JOptionPane.ERROR_MESSAGE);			
			}
		}		
		maj();
	}

	private void maj() {
		removeAll();
		creerInterface();
		revalidate();
		repaint();			
	}

	
	//------------------------------------------------------------------------------->>>>> Getteur
	public int getIdRessource() {
		return ressource.getId();
	}

	private String getStringByIntOfType(int type) {
		String s = "";
		switch (type) {
		case Ressource.PERSONNE:
			s = "Personne";
			break;
		case Ressource.SALLE:
			s = "Salle";
			break;
		case Ressource.CALCULATEUR:
			s = "Calculateur";
			break;

		default:
			break;
		}
		return s;
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
