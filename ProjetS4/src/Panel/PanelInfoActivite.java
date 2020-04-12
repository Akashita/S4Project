package Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EcouteurEvenement.SourisRessourceListener;
import Model.Activite;
import Model.Entreprise;
import Ressource.Personne;
import Ressource.Ressource;

public class PanelInfoActivite extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entreprise entreprise;
	private Activite activite;
	private Color couleurFond;
	private boolean estSurSouris;

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
			panel.add(labelInfo("Travail achevée: pas encore implementé"), gc);
			
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

	private JLabel creerLabel(String nom, boolean estGras) {
		JLabel label = new JLabel(nom);
		if(estGras) {
			label.setFont(new Font("Arial", Font.BOLD, 30));
		}
		else {
			label.setFont(new Font("Arial", Font.PLAIN, 30));
		}
		return label;
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

	public Activite getActivite() {
		return activite;
	}
}
