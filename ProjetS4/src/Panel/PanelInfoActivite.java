package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisActiviteListener;
import EcouteurEvenement.SourisRessourceListener;
import Model.Activite;
import Model.Entreprise;
import Ressource.Calculateur;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;

public class PanelInfoActivite extends JPanel{

	private Entreprise entreprise;
	private Activite activite;
	//private boolean selectionner;
	private Color couleurFond;

	public PanelInfoActivite (Entreprise entreprise) {
		this.entreprise = entreprise;
		activite = entreprise.getActiviteSelectionner();
		this.setBackground(PanelPrincipal.BLEU3);
		if (activite != null) {
			afficheInterface();
		}
	}

	public PanelInfoActivite (Entreprise entreprise, Activite activite) {
		this.entreprise = entreprise;
		this.activite = activite;
		if (activite != null) {
			couleurFond = PanelPrincipal.BLEU3;
			if (entreprise.getActiviteSelectionner() != null) {
				if (activite.getId() == entreprise.getActiviteSelectionner().getId()) {
					couleurFond = PanelPrincipal.BLEU2;
				}					
			}
			this.setBackground(couleurFond);
			this.setLayout(new BorderLayout());
			this.add(afficheInterface(), BorderLayout.CENTER);
		}
	}

	/*public void afficheInterface() {
	this.removeAll();
	this.setLayout(new BorderLayout());
	this.add(marge(), BorderLayout.WEST);
	this.add(creerInterface(), BorderLayout.CENTER);
	this.add(emploiDuTempsActivite(), BorderLayout.SOUTH);
}*/

	public JPanel afficheInterface() {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new GridBagLayout());
		
		/* Le gridBagConstraints va définir la position et la taille des éléments */
		GridBagConstraints gc = new GridBagConstraints();
		
		/* le parametre fill sert à définir comment le composant sera rempli GridBagConstraints.BOTH permet d'occuper tout l'espace disponible
		 * horizontalement et verticalement GridBagConstraints.HORIZONTAL maximise horizontalement GridBagConstraints.VERTICAL maximise verticalement
		 */
		gc.fill = GridBagConstraints.BOTH;
		
		/* insets définir la marge entre les composant new Insets(margeSupérieure, margeGauche, margeInférieur, margeDroite) */
		gc.insets = new Insets(5, 5, 5, 5);
		
		/* ipady permet de savoir où on place le composant s'il n'occupe pas la totalité de l'espace disponnible */
		gc.ipady = gc.anchor = GridBagConstraints.CENTER;

		/* weightx définit le nombre de cases en abscisse */
		gc.weightx = 5;
		
		/* weightx définit le nombre de cases en ordonnée */
		gc.weighty = 1;
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		

		
		
			panel.add(creerLabel(activite.getTitre(), true), gc);
			gc.gridx = 0;
			gc.gridy = 1;
			panel.add(labelInfo("Chef de projet: pas encore implementé"), gc);
			
			gc.gridx = 1;
			gc.gridy = 0;
			panel.add(labelInfo("Commence le: " + activite.getJourDebut()), gc);
			gc.gridx = 1;
			gc.gridy = 1;
			panel.add(labelInfo("Charge de travail: "+activite.getChargeJHomme()+" jour/homme"), gc);


			for (int i=2; i<5; i++) {
				String type = null;
				switch (i){
				case 2: type = Ressource.PERSONNE;
				break;
				case 3: type = Ressource.SALLE;
				break;
				case 4: type = Ressource.CALCULATEUR;
				break;
				}
				gc.gridx = i;
				gc.gridy = 0;
				gc.gridheight = 2;
				//gc.gridy = 2;

				ArrayList<Ressource> listeR = activite.getListeRessourceType(type);
				if (listeR.size() > 0) {
					panel.add(afficheListe(listeR), gc);				
				}
				else {
					JPanel listeVide = new JPanel();
					listeVide.setBackground(couleurFond);
					panel.add(listeVide, gc);				
				}
			}			
		
		
		return panel;
	}

	
	
	private JPanel afficheListe(ArrayList<Ressource> listeR) {
		JPanel panel = new JPanel();
		panel.setBackground(couleurFond);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		for (int i=0; i<listeR.size(); i++) {
			String nom;
			Ressource ressource = listeR.get(i);
			if (ressource.getType() == Ressource.PERSONNE) {
				nom = (((Personne) ressource).getPrenom()) + " " + ressource.getNom();
			}
			else {
				nom = ressource.getNom();
			}
			panel.add(creerLabel(nom, ressource));
		}
		return panel;
	}
	
	private JLabel creerLabel(String nom, Ressource ressource) {
		JLabel label = new JLabel(nom);
		label.addMouseListener(new SourisRessourceListener(entreprise, ressource));
		return label;
	}
	
	/*private JPanel marge() {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLEU3);	
		panel.setPreferredSize(new Dimension(20,this.getHeight()));
		return panel;
	}
	
	private JPanel creerInterface() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(infoActivite(), BorderLayout.NORTH);
		panel.add(listeRessource(), BorderLayout.CENTER);
		return panel;
	}*/
	
	/**
	 * Creer le panel qui affiche les informations de l'activite 
	 * @return panel
	 */
	/*private JPanel infoActivite() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(this.getWidth(),100));
		panel.setLayout(new GridBagLayout());
		panel.setBackground(PanelPrincipal.BLEU3);		
	    
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 0;
		panel.add(labelInfo("Charge de travail: "+ activite.getChargeJHomme()+" jour/homme"), c);

		c.gridx = 1;
		c.gridwidth = 1;
		c.gridy = 0;
		panel.add(labelInfo("Commence le: " + activite.getJourDebut()), c);

		c.gridx = 2;
		c.gridwidth = 1;
		c.gridy = 0;
		panel.add(labelInfo("DeadLine: pas encore implementé"), c);

	
		return panel;
	}*/
	
	/**
	 * Creer le panel qui affiche la liste de ressource de l'activite
	 * @return panel
	 */
	/*private JPanel listeRessource() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		panel.setBackground(PanelPrincipal.BLEU3);		
	
		panel.add(afficheRessourceType(Ressource.PERSONNE));
		panel.add(afficheRessourceType(Ressource.SALLE));
		panel.add(afficheRessourceType(Ressource.CALCULATEUR));

		return panel;
	}*/
	
	/**
	 * Creer le panel qui affiche la liste de la ressource choisie
	 * @return panel
	 */
	/*private JPanel afficheRessourceType (String type) {
		JPanel panelPrincipal = new JPanel ();
		panelPrincipal.setLayout(new BorderLayout());
		panelPrincipal.setBackground(PanelPrincipal.BLEU3);		

		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(PanelPrincipal.BLEU3);		

		JPanel panelListe = new JPanel();
		panelListe.setBackground(PanelPrincipal.BLEU3);		
		panelListe.setLayout(new BoxLayout(panelListe, BoxLayout.Y_AXIS));

		ArrayList<Ressource> listeR = activite.getListeRessourceType(type);
		panelInfo.add(creerLabelInterfaceRessource(type));
		if (listeR.size()>0) {
			for(int i=0; i<listeR.size(); i++) {
				Ressource res = null;
				JLabel label = null;
				if (type == Ressource.PERSONNE) {
					res = (Personne)listeR.get(i);
					label = creerLabel(((Personne) res).getPrenom() + " " + res.getNom(), false);					
				}
				if (type == Ressource.SALLE) {
					res = (Salle)listeR.get(i);
					label = creerLabel(res.getNom(), false);					
					
				}
				if (type == Ressource.CALCULATEUR) {
					res = (Calculateur)listeR.get(i);
					label = creerLabel(res.getNom(), false);					
					
				}
				label.addMouseListener(new SourisRessourceListener(entreprise, res));
				panelListe.add(label);	
			}
		}
		panelPrincipal.add(panelInfo, BorderLayout.NORTH);
		panelPrincipal.add(panelListe, BorderLayout.WEST);
		return panelPrincipal;
	}*/
	
	/*private JPanel emploiDuTempsActivite() {
		JPanel panel = new JPanel ();
		panel.setLayout(new BorderLayout());
		panel.setBackground(PanelPrincipal.BLEU1);		
		panel.add(new PanelEDTActivite(entreprise, activite), BorderLayout.CENTER);
		//panel.add(creerLabel("afficher l'emploi du temps de l'activité (Pas encore Implémenté)", true, PanelPrincipal.BLANC));
		return panel;
	}*/
	
	
	
	/*private JPanel creerLabelInterfaceRessource(String type) {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLEU3);		
	    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel labelTxt = new JLabel();
		JLabel labelIco = new JLabel();
		if (type == Ressource.PERSONNE) {
			labelIco = new JLabel(new ImageIcon("images/user_grey.png"));
			labelTxt = creerLabel("  "+Ressource.PERSONNE+"s", true);
		}
		if (type == Ressource.SALLE) {
			labelIco = new JLabel(new ImageIcon("images/door_grey.png"));
			labelTxt = creerLabel("  "+Ressource.SALLE+"s", true);					
		}
		if (type == Ressource.CALCULATEUR) {
			labelIco = new JLabel(new ImageIcon("images/computer_grey.png"));	
			labelTxt = creerLabel("  "+Ressource.CALCULATEUR+"s", true);	
		}
		panel.add(labelIco);
		panel.add(labelTxt);
		return panel;
	}*/
	

	private JLabel creerLabel(String nom, boolean estGras) {
		JLabel label = new JLabel(nom);
		if(estGras) {
			label.setFont(new Font("Arial", Font.BOLD, 15));
		}
		else {
			label.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return label;
	}
	
	/*private JLabel creerLabel(String nom, boolean estGras, Color couleur) {
		JLabel label = new JLabel(nom);
		label.setForeground(couleur);
		if(estGras) {
			label.setFont(new Font("Arial", Font.BOLD, 15));
		}
		else {
			label.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return label;
	}*/

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

	public Activite getActivite() {
		return activite;
	}
}
