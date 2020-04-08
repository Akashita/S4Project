package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import EcouteurEvenement.SourisActiviteListener;
import EcouteurEvenement.SourisProjetListener;
import EcouteurEvenement.SourisRessourceListener;
import Model.Activite;
import Model.Entreprise;
import Model.Projet;
import Ressource.Calculateur;
import Ressource.Personne;
import Ressource.Ressource;
import Ressource.Salle;

public class PanelInfoProjet extends JPanel{
	
	private Entreprise entreprise;
	private Projet projet;

	public PanelInfoProjet(Entreprise entreprise) {
		this.entreprise = entreprise;
		this.projet = entreprise.getProjetSelectionner();
		if (projet != null) {
			this.setBackground(PanelPrincipal.BLANC);
			this.setLayout(new BorderLayout());
			this.add(afficheInterface(), BorderLayout.CENTER);
		}
	}
	
	/*public void afficheInterface() {
		this.removeAll();
		this.setLayout(new BorderLayout());
		this.add(marge(), BorderLayout.WEST);
		this.add(marge(), BorderLayout.EAST);
		this.add(marge(), BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		panel.setLayout(new  BorderLayout());
		panel.add(infoProjet(), BorderLayout.NORTH);
		panel.add(panelActivite(), BorderLayout.CENTER);
		this.add(panel, BorderLayout.CENTER);

	}*/
	
	public JPanel afficheInterface() {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLANC);
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
		int nbActivite = 5;
		gc.weighty = nbActivite+1;
		
		
		gc.gridx = 0;
		gc.gridy = 0;
		panel.add(creerLabel("Chef de projet: pas encore implementé"), gc);
		gc.gridx = 1;
		panel.add(creerLabel("Priorité: "+(int)projet.getPriorite()), gc);
		gc.gridx = 2;
		panel.add(creerLabel("DeadLine: pas encore implementé"), gc);

		for (int i=0; i<3; i++) {
			String type = null;
			switch (i){
			case 0: type = Ressource.PERSONNE;
			break;
			case 1: type = Ressource.SALLE;
			break;
			case 2: type = Ressource.CALCULATEUR;
			break;
			}
				gc.gridx = i+3;
				gc.gridy = 0;
				panel.add(creerLabelInterfaceRessource(type), gc);
		}
		
		//affiche les activité
		if (nbActivite > 0) {
			gc.gridy = 0;
			
			ArrayList<Activite> listeActivite = projet.getListe();
			gc.gridx = 0;
			gc.gridwidth = GridBagConstraints.REMAINDER;
			for (int i=0; i<nbActivite; i++) {
				gc.gridy ++;
				if (i<projet.getListe().size()) {
					
					Activite activite = listeActivite.get(i);
					
					JPanel panelActivite;
					ArrayList<Ressource> listeRes = activite.getListeRessourceType(Ressource.PERSONNE);
					if (activite.getAfficheEDT() && listeRes.size() > 0) { //on affiche son edt
						panelActivite = new PanelEDTActivite(entreprise, activite);
					}
					else { // sinon on affiche ses infos
						panelActivite = new PanelInfoActivite(entreprise, activite);
					}
					panelActivite.addMouseListener(new SourisActiviteListener(entreprise, activite));
					panel.add(panelActivite, gc);			

				}
				else {
					JPanel caseVide = new JPanel();
					caseVide.setBackground(PanelPrincipal.BLANC);
					panel.add(caseVide, gc);					
				}
			}
		}
		
		return panel;
	}
		
		
		private JPanel creerLabelInterfaceRessource(String type) {
			JPanel panel = new JPanel();
			panel.setBackground(PanelPrincipal.BLANC);		
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
		}

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

	private JPanel marge() {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLANC);	
		panel.setPreferredSize(new Dimension(20,20));
		return panel;
	}

//	==================PANEL PROJET============================================
	
	/**
	 * Creer le panel qui affiche les informations du projet
	 * @return panel
	 */
	private JPanel infoProjet() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(this.getWidth(),200));
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.WHITE);	

		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 0;
		panel.add(creerLabel("Chef de projet: pas encore implementé"), c);

		c.gridx = 1;
		c.gridwidth = 1;
		c.gridy = 0;
		panel.add(creerLabel("Priorité: "+(int)projet.getPriorite()), c);

		c.gridx = 2;
		c.gridwidth = 1;
		c.gridy = 0;
		panel.add(creerLabel("DeadLine: pas encore implementé"), c);

		return panel;
	}
	
//	==================PANEL ACTIVITE============================================	
	
	/*private JPanel panelActivite() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(listeActivite(), BorderLayout.WEST);
		panel.add(new PanelInfoActivite(entreprise), BorderLayout.CENTER);
		return panel;
	}

	private JPanel listeActivite() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(180, this.getHeight()));
		panel.setBackground(PanelPrincipal.BLANC);	
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	
		ArrayList<Activite> listeActivite = projet.getListe();
		if (listeActivite.size() > 0) {
			for (int i=0; i<listeActivite.size(); i++) {
				Activite act = listeActivite.get(i);
				boolean selectionner = false;
				if (entreprise.getActiviteSelectionner() != null) {
					if (act.getId() == entreprise.getActiviteSelectionner().getId()) {
						selectionner = true;
					}					
				}
				panel.add(creerLabel(act, selectionner));
				panel.add(Box.createRigidArea(new Dimension(0, 10)));		

			}				
		}
		else {
			panel.add(new JLabel("aucune activité présente"));
		}
		return panel;
	}*/

	private JLabel creerLabel(Activite activite, boolean selectionner) {
	//JPanel panel = new JPanel();
	//panel.setBackground(PanelPrincipal.BLANC);
	//panel.setPreferredSize(new Dimension( this.getWidth(),130));
	//panel.setLayout(new BorderLayout());
	JLabel label = new JLabel(activite.getTitre());
	if(selectionner) {	
		label.setFont(new Font("Arial", Font.BOLD, 35));
		label.setForeground(PanelPrincipal.BLEU1);
	}
	else {
		label.setFont(new Font("Arial", Font.BOLD, 30));
		label.setForeground(PanelPrincipal.BLEU2);			
	}
	label.addMouseListener(new SourisActiviteListener(entreprise, activite));
    //label.setHorizontalAlignment(JLabel.CENTER);
	//panel.add(label);
	return label;
}
	private JLabel creerLabel(Activite activite) {
		JLabel label = new JLabel(activite.getTitre());
		label.setFont(new Font("Arial", Font.PLAIN, 30));
		label.setForeground(PanelPrincipal.BLEU1);
		label.addMouseListener(new SourisActiviteListener(entreprise, activite));
		return label;
	}

//	==================METHODE GENERAL============================================	
	

	private JPanel creerLabel(String nom) {
		JPanel panel = new JPanel();
		panel.setBackground(PanelPrincipal.BLANC);
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

	
	public String getProjetNom() {
		return projet.getNom();
	}
	
	public Projet getProjet() {
		return projet;
	}
}
