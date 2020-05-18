package Panel;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Fenetre.FenetreInfoRessource;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;
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
	private JButton boutonModifier, boutonTerminer, boutonSupprimer, boutonAnnuler;
	private Checkbox checkBoxestAdmin;

	
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

		if (modeModification) {
			infoModifier(gc);
		}
		else {
			info(gc);
		}
		
		if (ressource.getType() == Ressource.PERSONNE) {
			colChefDeProjet(gc);
			colCompetence(gc);
		}
		colActivite(gc);
	}
	
	private void info(GridBagConstraints gc) {
		gc.insets = new Insets(2, 5, 2, 0);
		
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

			gc.gridy ++;
			this.add(labelInfo("Mot de passe : " + mdp), gc);
		}
		
		if (ressource.getType() == Ressource.SALLE) {
			gc.gridy ++;
			this.add(labelInfo("Capacité : " + capacite), gc);			
		}
		
		gc.gridy ++;
		this.add(labelInfo("Type : " + getStringByIntOfType(ressource.getType())), gc);
		
		gc.gridy ++;
		this.add(panelBouton(), gc);		
	}
	
	private void infoModifier(GridBagConstraints gc) {
		gc.insets = new Insets(2, 5, 2, 0);
		
		gc.fill = GridBagConstraints.BOTH;
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
		this.add(labelInfo("Type : " + ressource.getType()), gc);
		
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

		ArrayList<Projet> projets = ((Personne) ressource).getListeDeProjet();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy++;
		gc.gridheight = GridBagConstraints.REMAINDER;
		this.add(creerScrollPane(listProjet(projets)), gc);

		
		/*ArrayList<String> listeProjet = new ArrayList<String>();
		for (int i=0; i<projets.size(); i++) {
			listeProjet.add(projets.get(i).getNom());
		}	
		gc.gridy ++;
		gc.gridheight = GridBagConstraints.REMAINDER;
		if (projets.size()>0) {
			this.add(panelContenuColonne(listeProjet), gc);		
		}*/
	}
	
	private void colCompetence(GridBagConstraints gc) {
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.NORTH;
		gc.gridx ++;
		gc.gridy = 0;
		
		this.add(labelTitreColonne("COMPETENCES"),gc);
		//this.add(panelTitreInfoColonne("COMPETENCES", "domaine", "niveau"), gc);

		ArrayList<Competence> competences = ((Personne) ressource).getListeDeCompetence();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy++;
		gc.gridheight = GridBagConstraints.REMAINDER;
		this.add(creerScrollPane(listCompetence(competences)), gc);
		/*ArrayList<String> listeDomaine = new ArrayList<String>();
		ArrayList<String> listeNiveau = new ArrayList<String>();
		for (int i=0; i<competences.size(); i++) {
			listeDomaine.add(competences.get(i).getNom());
			listeNiveau.add(competences.get(i).getStringNiveau());
		}	
		gc.gridy =1;
		gc.gridheight = GridBagConstraints.REMAINDER;
		if (competences.size()>0) {
			this.add(panelContenuColonne("Domaine", "Niveau", listeDomaine, listeNiveau), gc);
		}*/
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
		this.add(creerScrollPane(listActivite(listeAct)), gc);
		/*ArrayList<Projet> listeProj = entreprise.getListeProjetdeRessourceParId(ressource.getType(), ressource.getId());
		ArrayList<String> act = new ArrayList<String>();
		ArrayList<String> projet = new ArrayList<String>();
		for (int i=0; i<listeAct.size(); i++) {
			act.add(listeAct.get(i).getTitre());
			projet.add(listeProj.get(i).getNom());
		}	
		gc.gridy =1;
		gc.gridheight = GridBagConstraints.REMAINDER;
		if (listeAct.size()>0) {
			this.add(panelContenuColonne("Activité", "Projet", act, projet), gc);
		}*/
		
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

	private JPanel panelContenuColonne(String nomCol1, String nomCol2, ArrayList<String> liste1, ArrayList<String> liste2) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.insets = new Insets(0, 8, 0, 10);
		gc.fill = GridBagConstraints.CENTER;
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		panel.add(labelInfoColonne(nomCol1), gc);
		gc.gridx = 1;
		panel.add(labelInfoColonne(nomCol2), gc);

		gc.insets = new Insets(0, 8, 0, 5);
		gc.gridx = 0;
		gc.gridy = 1;
		panel.add(panelContenuColonne(liste1), gc);
		gc.gridx = 1;
		panel.add(panelContenuColonne(liste2), gc);
		return panel;
	}
	
	private JPanel panelContenuColonne(ArrayList<String> liste) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		for (int i=0; i<liste.size(); i++) {
			panel.add(labelContenusColonne(liste.get(i)));

		}
		return panel;
	}

	private JLabel labelContenusColonne(String nom) {
		JLabel label = new JLabel(nom);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		return label;
	}

	private JPanel panelJtextfield(String nom, JTextField tf) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		gc.insets = new Insets(5, 5, 5, 5);
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
		if (entreprise.getUser().estAdmin()) {
			panel.setBackground(couleurFond);
			panel.setLayout(new GridBagLayout());
			GridBagConstraints gc = new GridBagConstraints();
			gc.ipadx = gc.anchor = GridBagConstraints.CENTER;
			gc.ipady = gc.anchor = GridBagConstraints.CENTER;
			gc.fill = GridBagConstraints.CENTER;

			gc.insets = new Insets(5, 5, 5, 5);
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
		}
		return panel;
	}
	
	
	private JList<Projet> listProjet(ArrayList<Projet> l){
		Projet [] tr = new Projet [l.size()];
		for (int i=0; i<tr.length; i++) {
			tr[i] = l.get(i);
		}
		JList<Projet> jlt = new JList<Projet>(tr);
		jlt.setBackground(couleurFond);
		
		jlt.setFont(new Font("Arial", Font.BOLD, 15));
		jlt.setForeground(PanelPrincipal.BLANC);
		return jlt;
	}

	private JList<Competence> listCompetence(ArrayList<Competence> l){
		Competence [] tr = new Competence [l.size()];
		for (int i=0; i<tr.length; i++) {
			tr[i] = l.get(i);
		}
		JList<Competence> jlt = new JList<Competence>(tr);
		jlt.setBackground(couleurFond);
		
		jlt.setFont(new Font("Arial", Font.BOLD, 15));
		jlt.setForeground(PanelPrincipal.BLANC);
		return jlt;
	}

	private JList<Activite> listActivite(ArrayList<Activite> l){
		Activite [] tr = new Activite [l.size()];
		for (int i=0; i<tr.length; i++) {
			tr[i] = l.get(i);
		}
		JList<Activite> jlt = new JList<Activite>(tr);
		jlt.setBackground(couleurFond);
		
		jlt.setFont(new Font("Arial", Font.BOLD, 15));
		jlt.setForeground(PanelPrincipal.BLANC);
		return jlt;
	}

	
	private JScrollPane creerScrollPane(JList l) {
		JScrollPane scrollPane = new JScrollPane(l);
		scrollPane.setViewportView(l);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPane;
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
			if (entreprise.ressourceTravailleDansUneActiviteParId(ressource.getType(), ressource.getId())) {
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
